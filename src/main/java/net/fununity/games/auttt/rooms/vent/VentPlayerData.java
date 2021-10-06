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
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This class caches and manages traitors entered the vent.
 * @see Vent
 * @author Niko
 * @since 1.1
 */
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

    /**
     * Will be called, when the player enters the vent.
     * Starts timer and sets player as passenger.
     * @param vent ArmorStand - the armorstand representing the vent entry.
     * @since 1.1
     */
    protected void jumpIn(ArmorStand vent) {
        Player player = apiPlayer.getPlayer();
        this.secondsBar.addPlayer(player);
        UUID uuid = player.getUniqueId();


        FunUnityAPI.getInstance().getActionbarManager().addActionbar(uuid, new ActionbarMessage(TranslationKeys.TTT_GAME_ROOM_VENT_ENTERED).setDuration(5));
        ventId = new ArrayList<>(this.vent.ventLocations.keySet()).indexOf(vent);
        vent.addPassenger(player);
        player.setAllowFlight(true);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0, false, false));
        if (this.vent.gift)
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 0, false, false));

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.hidePlayer(TTT.getInstance(), player);
            Bukkit.getScheduler().runTaskLater(TTT.getInstance(), ()->
                TTTScoreboard.reAddPlayer(onlinePlayer), 1L);
        }

        task = Bukkit.getScheduler().runTaskTimer(TTT.getInstance(), () -> {

            if (secondsLeft <= 0) {
                apiPlayer.sendMessage(MessagePrefix.INFO, TranslationKeys.TTT_GAME_ROOM_VENT_TIMEREACHED);
                this.vent.quit(player);
                return;
            }

            secondsBar.setTitle(ChatColor.LIGHT_PURPLE + "" + this.secondsLeft);
            secondsBar.setProgress(Math.abs((double) secondsLeft / DURATION));
            secondsLeft--;
        }, 0L, 20L);
    }

    /**
     * Will be called, when the player quits the vent.
     * @since 1.1
     */
    protected void jumpOut() {
        Player player = apiPlayer.getPlayer();
        this.secondsBar.removePlayer(player);
        task.cancel();

        getArmorStandFromVentId().removePassenger(player);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 0, true, true));
        player.removePotionEffect(PotionEffectType.POISON);
        player.setAllowFlight(false);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.showPlayer(TTT.getInstance(), player);
        }

        player.teleport(vent.ventLocations.get(getArmorStandFromVentId()));
    }

    /**
     * Gets the ArmorStand corresponding to the index of the vent
     * @return ArmorStand - the armorstand as a vent entry.
     * @since 1.1
     */
    private ArmorStand getArmorStandFromVentId() {
        return new ArrayList<>(vent.ventLocations.keySet()).get(ventId);
    }

    /**
     * Get the vent id the player currently is.
     * @return int - id of vent entry.
     * @since 1.1
     */
    protected int getVentId() {
        return ventId;
    }

    /**
     * Sets the vent id the player should switch to.
     * @param ventId int - the new vent entry.
     * @since 1.1
     */
    protected void setVentId(int ventId) {
        if (this.ventId != null)
            getArmorStandFromVentId().removePassenger(apiPlayer.getPlayer());
        this.ventId = ventId;
        getArmorStandFromVentId().addPassenger(apiPlayer.getPlayer());
    }

    /**
     * Get the amount of seconds left the player can stand in the vent.
     * @return int - seconds left in the vent.
     * @since 1.1
     */
    protected int getSecondsLeft() {
        return secondsLeft;
    }

    /**
     * Sets the seconds left the player can stay in vent.
     * @param secondsLeft int - seconds left.
     * @since 1.1
     */
    protected void setSecondsLeft(int secondsLeft) {
        this.secondsLeft = secondsLeft;
    }
}
