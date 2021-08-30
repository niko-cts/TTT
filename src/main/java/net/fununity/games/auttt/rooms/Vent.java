package net.fununity.games.auttt.rooms;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.actionbar.ActionbarMessage;
import net.fununity.main.api.messages.MessagePrefix;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class Vent implements Listener {

    private final Map<Location, Location> vent;
    private final Map<UUID, Integer> inVent;
    private final Map<UUID, Integer> secondsUUID;
    private final Map<UUID, BukkitTask> taskUUID;

    public Vent(List<Location> vent, List<Location> ventOut) {
        this.vent = new HashMap<>();
        for (Location location : vent)
            this.vent.put(location, ventOut.stream().min(Comparator.comparingDouble(o -> o.distance(location))).get());

        this.inVent = new HashMap<>();
        this.secondsUUID = new HashMap<>();
        this.taskUUID = new HashMap<>();
    }

    public void jumpIn(Player player, Location location) {
        UUID uuid = player.getUniqueId();
        if (this.secondsUUID.containsKey(uuid) && this.secondsUUID.get(uuid) >= 30) {
            FunUnityAPI.getInstance().getPlayerHandler().getPlayer(player).sendMessage(MessagePrefix.ERROR, TranslationKeys.TTT_GAME_ROOM_VENT_TIMEREACHED);
            return;
        }

        Location ventLoc = vent.keySet().stream().min(Comparator.comparingDouble(o -> o.distance(location))).orElse(null);
        if (ventLoc == null) return;
        if (!this.secondsUUID.containsKey(uuid))
            this.secondsUUID.put(uuid, 0);

        FunUnityAPI.getInstance().getActionbarManager().addActionbar(uuid, new ActionbarMessage(TranslationKeys.TTT_GAME_ROOM_VENT_ENTERED).setDuration(5));
        this.inVent.put(uuid, new ArrayList<>(vent.keySet()).indexOf(ventLoc));
        player.teleport(ventLoc);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 999, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 999, 0, false, false));

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(TTT.getInstance(), () -> {
            Integer seconds = secondsUUID.get(uuid);
            if (seconds >= 30) {
                FunUnityAPI.getInstance().getPlayerHandler().getPlayer(player).sendMessage(MessagePrefix.INFO, TranslationKeys.TTT_GAME_ROOM_VENT_TIMEREACHED);
                jumpOut(player);
            } else
                secondsUUID.put(uuid, seconds + 1);
        }, 20L, 20L);
        this.taskUUID.put(uuid, task);
    }

    private void jumpOut(Player player) {
        taskUUID.get(player.getUniqueId()).cancel();
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 0, false, false));
        player.teleport(vent.get(getByUUIDIndex(player.getUniqueId())));

        inVent.remove(player.getUniqueId());
        taskUUID.remove(player.getUniqueId());
    }

    @EventHandler
    public void onScroll(PlayerItemHeldEvent event) {
        if (!inVent.containsKey(event.getPlayer().getUniqueId())) return;

        int slot = inVent.get(event.getPlayer().getUniqueId());
        if (event.getPreviousSlot() < event.getNewSlot())
            slot = vent.size() - 1 == slot ? 0 : slot + 1;
        else
            slot = slot == 0 ? vent.size() - 1 : slot - 1;
        event.getPlayer().teleport(getByUUIDIndex(event.getPlayer().getUniqueId()));
    }

    private Location getByUUIDIndex(UUID uuid) {
        return new ArrayList<>(vent.keySet()).get(inVent.get(uuid));
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        if (inVent.containsKey(event.getPlayer().getUniqueId()))
            jumpOut(event.getPlayer());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (inVent.containsKey(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (inVent.containsKey(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }
}
