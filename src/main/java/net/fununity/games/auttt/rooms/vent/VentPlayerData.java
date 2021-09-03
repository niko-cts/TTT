package net.fununity.games.auttt.rooms.vent;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.actionbar.ActionbarMessage;
import net.fununity.main.api.messages.MessagePrefix;
import net.fununity.main.api.player.APIPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;

public class VentPlayerData {

    private static final int DURATION = 30;
    private final APIPlayer apiPlayer;
    private final Vent vent;

    private int ventId;
    private BukkitTask task;
    private final BossBar secondsBar;
    private int secondsLeft;

    public VentPlayerData(Vent vent, APIPlayer player) {
        this.vent = vent;
        this.apiPlayer = player;
        this.secondsLeft = DURATION;
        this.secondsBar = Bukkit.getServer().createBossBar(ChatColor.LIGHT_PURPLE + "" + this.secondsLeft, BarColor.PURPLE, BarStyle.SOLID);
    }

    public void jumpIn(Location location) {
        Player player = apiPlayer.getPlayer();
        this.secondsBar.addPlayer(player);
        UUID uuid = player.getUniqueId();

        Location ventLoc = vent.ventLocations.keySet().stream().min(Comparator.comparingDouble(o -> o.distance(location))).orElse(null);
        if (ventLoc == null) return;

        FunUnityAPI.getInstance().getActionbarManager().addActionbar(uuid, new ActionbarMessage(TranslationKeys.TTT_GAME_ROOM_VENT_ENTERED).setDuration(5));
        ventId = new ArrayList<>(vent.ventLocations.keySet()).indexOf(ventLoc);
        player.teleport(ventLoc);
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0, false, false));
        if (vent.gift)
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 0, false, false));


        task = Bukkit.getScheduler().runTaskTimer(TTT.getInstance(), () -> {
            secondsBar.setTitle(ChatColor.LIGHT_PURPLE + "" + this.secondsLeft);
            secondsBar.setProgress((double) secondsLeft / DURATION);

            if (secondsLeft <= 0) {
                apiPlayer.sendMessage(MessagePrefix.INFO, TranslationKeys.TTT_GAME_ROOM_VENT_TIMEREACHED);
                vent.jumpOut(player);
            }
            secondsLeft--;
        }, 0L, 20L);
    }

    protected void jumpOut() {
        Player player = apiPlayer.getPlayer();
        this.secondsBar.removePlayer(player);
        task.cancel();
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 0, true, true));
        player.removePotionEffect(PotionEffectType.POISON);

        player.teleport(vent.ventLocations.get(getLocationFromVentId()));

    }

    private Location getLocationFromVentId() {
        return new ArrayList<>(vent.ventLocations.keySet()).get(ventId);
    }

    public int getVentId() {
        return ventId;
    }

    public void setVentId(int ventId) {
        this.ventId = ventId;
        apiPlayer.getPlayer().teleport(getLocationFromVentId());
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public APIPlayer getPlayer() {
        return apiPlayer;
    }
}
