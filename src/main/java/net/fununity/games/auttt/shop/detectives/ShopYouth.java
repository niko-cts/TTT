package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class ShopYouth extends ShopItem {
    public ShopYouth(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        Player player = tttPlayer.getApiPlayer().getPlayer();
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH)
                .setBaseValue(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 4);
    }
}
