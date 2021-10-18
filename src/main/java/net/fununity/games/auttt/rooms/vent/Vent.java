package net.fununity.games.auttt.rooms.vent;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.actionbar.ActionbarMessage;
import net.fununity.main.api.player.APIPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

/**
 * The vent class caches all information about the vent in game.
 * @see VentPlayerData
 * @author Niko
 * @since 1.1
 */
public class Vent implements Listener {

    protected final Map<ArmorStand, Location> ventLocations;
    private final Map<UUID, VentPlayerData> playerData;
    private final Set<UUID> currentlyInVent;
    protected boolean gift;

    private final Map<Integer, Set<UUID>> detectiveWatcher;

    /**
     * Instantiates the class.
     * Creates armor stands on the {@param vent} locations, so the player can be a passenger later.
     * @param vent List<Location> - list of all spectative locations.
     * @param ventOut List<Location> - list of all leave locations.
     * @since 1.1
     */
    public Vent(List<Location> vent, List<Location> ventOut) {
        this.ventLocations = new HashMap<>();
        for (Location location : vent) {
            ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            armorStand.setVisible(false);
            armorStand.setSmall(true);
            Location locOut = ventOut.stream().min(Comparator.comparingDouble(o -> o.distance(location))).get();
            this.ventLocations.put(armorStand, locOut);
            Bukkit.getScheduler().runTaskTimer(TTT.getInstance(), ()->locOut.getWorld().playEffect(locOut, Effect.CHORUS_FLOWER_GROW, 0), 20L, 20L);
        }

        this.gift = false;
        this.playerData = new HashMap<>();
        this.currentlyInVent = new HashSet<>();
        this.detectiveWatcher = new HashMap<>();

        TTT.getInstance().getServer().getPluginManager().registerEvents(this, TTT.getInstance());
    }

    /**
     * Will be called, when a player enters the vent.
     * @param player Player - the player who enters.
     * @param location Location - the location of the iron trapdoor the player clicked.
     * @since 1.1
     */
    public void enters(Player player, Location location) {
        if (isInVent(player.getUniqueId())) return;

        if (playerData.containsKey(player.getUniqueId())) {
            VentPlayerData ventPlayerData = playerData.get(player.getUniqueId());

            if (ventPlayerData.getSecondsLeft() <= 0) {
                FunUnityAPI.getInstance().getActionbarManager().addActionbar(player.getUniqueId(),
                        new ActionbarMessage(TranslationKeys.TTT_GAME_ROOM_VENT_TIMEREACHED));
                return;
            }

            ArmorStand vent = ventLocations.keySet().stream().min(Comparator.comparingDouble(o -> o.getLocation().distance(location))).orElse(null);
            if (vent == null) return;

            this.currentlyInVent.add(player.getUniqueId());
            ventPlayerData.jumpIn(vent);
            return;
        }


        ArmorStand vent = ventLocations.keySet().stream().min(Comparator.comparingDouble(o -> o.getLocation().distance(location))).orElse(null);
        if (vent == null) return;

        VentPlayerData ventPlayerData = new VentPlayerData(this, FunUnityAPI.getInstance().getPlayerHandler().getPlayer(player));
        this.playerData.put(player.getUniqueId(), ventPlayerData);
        this.currentlyInVent.add(player.getUniqueId());
        ventPlayerData.jumpIn(vent);
    }

    /**
     * Will be called, when the player quits the vent.
     * @param player Player - player who quits
     * @since 1.1
     */
    protected void quit(Player player) {
        this.currentlyInVent.remove(player.getUniqueId());
        VentPlayerData ventPlayerData = this.playerData.get(player.getUniqueId());

        // Watcher
        for (UUID uuid : detectiveWatcher.getOrDefault(ventPlayerData.getVentId(), new HashSet<>())) {
            APIPlayer dete = FunUnityAPI.getInstance().getPlayerHandler().getPlayer(uuid);
            if (dete != null)
                dete.sendMessage(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_SIGNAL, "${name}", player.getName());
        }

        ventPlayerData.jumpOut();
    }

    /**
     * Will be called, when a detective marks the location with a {@link net.fununity.games.auttt.shop.detectives.DetectiveItems#MOVE_SENSOR}
     * @param apiPlayer APIPlayer - the detective who marks.
     * @param location Location - the location of the iron trapdoor.
     * @since 1.1
     */
    public void markLocation(APIPlayer apiPlayer, Location location) {
        ArmorStand vent = ventLocations.keySet().stream().min(Comparator.comparingDouble(o -> o.getLocation().distance(location))).orElse(null);
        if (vent == null) return;
        int index = new ArrayList<>(ventLocations.keySet()).indexOf(vent);
        Set<UUID> watcher = this.detectiveWatcher.getOrDefault(index, new HashSet<>());
        watcher.add(apiPlayer.getUniqueId());
        this.detectiveWatcher.put(index, watcher);
        apiPlayer.sendMessage(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_MARKED);
    }

    /**
     * Will be called, when a detective gifts the vent.
     * Will poison all traitors in vent.
     * @since 1.1
     */
    public void gift() {
        if (this.gift) return;

        for (UUID uuid : this.currentlyInVent) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 0, false, false));
        }

        this.gift = true;
        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), () -> {
            this.gift = false;
            for (UUID uuid : this.currentlyInVent) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null)
                    player.removePotionEffect(PotionEffectType.POISON);
            }
        }, 20 * 60);
    }


    /**
     * Will be called, when a player scrolls.
     * Switches a player through the vents.
     * @param event PlayerItemHeldEvent - event that was triggerd
     * @since 1.1
     */
    @EventHandler
    public void onScroll(PlayerItemHeldEvent event) {
        if (!currentlyInVent.contains(event.getPlayer().getUniqueId())) return;

        VentPlayerData ventPlayerData = this.playerData.get(event.getPlayer().getUniqueId());

        int slot = ventPlayerData.getVentId();
        if (event.getPreviousSlot() < event.getNewSlot())
            slot = ventLocations.size() - 1 == slot ? 0 : slot + 1;
        else
            slot = slot == 0 ? ventLocations.size() - 1 : slot - 1;
        ventPlayerData.setVentId(slot);
    }


    /**
     * Will be called, when a player sneaks.
     * Calls {@link Vent#quit(Player)}.
     * @param event PlayerToggleSneakEvent - event that was triggerd.
     * @since 1.1
     */
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if (currentlyInVent.contains(event.getPlayer().getUniqueId()))
            quit(event.getPlayer());
    }


    /**
     * Will be called, when a player sneaks.
     * Calls {@link Vent#quit(Player)}.
     * @param event PlayerToggleSneakEvent - event that was triggerd.
     * @since 1.1
     */
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (currentlyInVent.contains(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    /**
     * Will be called, when a player clicks in inventory.
     * @param event InventoryClickEvent - event that was triggerd.
     * @since 1.1
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (currentlyInVent.contains(event.getWhoClicked().getUniqueId()))
            event.setCancelled(true);
    }

    /**
     * Will be called, when a player quits the server.
     * @param event PlayerQuitEvent - event that was triggerd.
     * @since 1.1
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (currentlyInVent.contains(event.getPlayer().getUniqueId())) {
            quit(event.getPlayer());
        }
    }

    public boolean isInVent(UUID uuid) {
        return currentlyInVent.contains(uuid);
    }
}
