package net.fununity.games.auttt.shop;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Comparator;

public class ShopRadar extends ShopItem {

    private static final ItemBuilder ITEM = new ItemBuilder(Material.COMPASS).setName(TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_NAME).setLore(TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_DESCRIPTION);

    public ShopRadar(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse(ITEM.translate(tttPlayer.getApiPlayer().getLanguage()));
        Bukkit.getScheduler().runTaskTimer(TTT.getInstance(), () ->
                Bukkit.getOnlinePlayers().stream()
                        .filter(on -> !on.getUniqueId().equals(tttPlayer.getApiPlayer().getUniqueId()))
                        .min(Comparator.comparingDouble(o -> o.getLocation().distance(getPlayer().getLocation())))
                        .ifPresent(o -> getPlayer().setCompassTarget(o.getLocation())), 0L, 20 * 2L);
    }

    private Player getPlayer() {
        return tttPlayer.getApiPlayer().getPlayer();
    }
}
