package net.fununity.games.auttt.rooms.vent;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.util.TTTScoreboard;
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
import org.bukkit.entity.ArmorStand;
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

    private Integer ventId;
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

        ArmorStand vent = this.vent.ventLocations.keySet().stream().min(Comparator.comparingDouble(o -> o.getLocation().distance(location))).orElse(null);
        if (vent == null) return;

        FunUnityAPI.getInstance().getActionbarManager().addActionbar(uuid, new ActionbarMessage(TranslationKeys.TTT_GAME_ROOM_VENT_ENTERED).setDuration(5));
        ventId = new ArrayList<>(this.vent.ventLocations.keySet()).indexOf(vent);
        vent.addPassenger(player);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0, false, false));
        if (this.vent.gift)
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 0, false, false));

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.hidePlayer(TTT.getInstance(), player);
            Bukkit.getScheduler().runTaskLater(TTT.getInstance(), ()->
                TTTScoreboard.reAddPlayer(onlinePlayer), 1L);
        }

        task = Bukkit.getScheduler().runTaskTimer(TTT.getInstance(), () -> {
            secondsBar.setTitle(ChatColor.LIGHT_PURPLE + "" + this.secondsLeft);
            secondsBar.setProgress((double) secondsLeft / DURATION);

            if (secondsLeft <= 0) {
                apiPlayer.sendMessage(MessagePrefix.INFO, TranslationKeys.TTT_GAME_ROOM_VENT_TIMEREACHED);
                this.vent.jumpOut(player);
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
        getLocationFromVentId().removePassenger(player);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.showPlayer(TTT.getInstance(), player);
        }

        player.teleport(vent.ventLocations.get(getLocationFromVentId()));
    }

    private ArmorStand getLocationFromVentId() {
        return new ArrayList<>(vent.ventLocations.keySet()).get(ventId);
    }

    public int getVentId() {
        return ventId;
    }

    public void setVentId(int ventId) {
        if (this.ventId != null)
            getLocationFromVentId().removePassenger(apiPlayer.getPlayer());
        this.ventId = ventId;
        getLocationFromVentId().addPassenger(apiPlayer.getPlayer());
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public APIPlayer getPlayer() {
        return apiPlayer;
    }
}
