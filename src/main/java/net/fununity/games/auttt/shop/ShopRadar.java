package net.fununity.games.auttt.shop;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Comparator;

public class ShopRadar extends ShopItem {

    public ShopRadar(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse();
        Bukkit.getScheduler().runTaskTimer(TTT.getInstance(), () ->
                Bukkit.getOnlinePlayers().stream()
                        .filter(on -> !on.getUniqueId().equals(tttPlayer.getApiPlayer().getUniqueId()))
                        .filter(on -> on.getWorld().getUID().equals(tttPlayer.getApiPlayer().getPlayer().getWorld().getUID()))
                        .min(Comparator.comparingDouble(o -> o.getLocation().distance(getPlayer().getLocation())))
                        .ifPresent(o -> getPlayer().setCompassTarget(o.getLocation())), 0L, 20 * 2L);
    }

    private Player getPlayer() {
        return tttPlayer.getApiPlayer().getPlayer();
    }
}
