package net.fununity.games.auttt.rooms.vent;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.messages.MessagePrefix;
import net.fununity.main.api.player.APIPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Vent implements Listener {

    protected final Map<Location, Location> ventLocations;
    private final Map<UUID, VentPlayerData> playerData;
    private final Set<UUID> currentlyInVent;
    protected boolean gift;

    private final Map<Integer, Set<UUID>> detectiveWatcher;

    public Vent(List<Location> vent, List<Location> ventOut) {
        this.ventLocations = new HashMap<>();
        for (Location location : vent)
            this.ventLocations.put(location, ventOut.stream().min(Comparator.comparingDouble(o -> o.distance(location))).get());

        this.gift = false;
        this.playerData = new HashMap<>();
        this.currentlyInVent = new HashSet<>();
        this.detectiveWatcher = new HashMap<>();

        TTT.getInstance().getServer().getPluginManager().registerEvents(this, TTT.getInstance());
    }

    public void jumpIn(Player player, Location location) {
        if (playerData.containsKey(player.getUniqueId())) {
            VentPlayerData ventPlayerData = playerData.get(player.getUniqueId());

            if (ventPlayerData.getSecondsLeft() <= 0) {
                ventPlayerData.getPlayer().sendMessage(MessagePrefix.ERROR, TranslationKeys.TTT_GAME_ROOM_VENT_TIMEREACHED);
                return;
            }
            this.currentlyInVent.add(player.getUniqueId());
            ventPlayerData.jumpIn(location);
            return;
        }

        VentPlayerData ventPlayerData = new VentPlayerData(this, FunUnityAPI.getInstance().getPlayerHandler().getPlayer(player));
        this.playerData.put(player.getUniqueId(), ventPlayerData);
        this.currentlyInVent.add(player.getUniqueId());
        ventPlayerData.jumpIn(location);
    }

    protected void jumpOut(Player player) {
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

    public void markLocation(APIPlayer apiPlayer, Location location) {
        Location ventLoc = ventLocations.keySet().stream().min(Comparator.comparingDouble(o -> o.distance(location))).orElse(null);
        if (ventLoc == null) return;
        int index = new ArrayList<>(ventLocations.keySet()).indexOf(ventLoc);
        Set<UUID> watcher = this.detectiveWatcher.getOrDefault(index, new HashSet<>());
        watcher.add(apiPlayer.getUniqueId());
        this.detectiveWatcher.put(index, watcher);
        apiPlayer.sendMessage(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_MARKED);
    }

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


    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if (currentlyInVent.contains(event.getPlayer().getUniqueId()))
            jumpOut(event.getPlayer());
    }


    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (currentlyInVent.contains(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (currentlyInVent.contains(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (currentlyInVent.contains(event.getWhoClicked().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (currentlyInVent.contains(event.getPlayer().getUniqueId())) {
            jumpOut(event.getPlayer());
        }
    }

}
