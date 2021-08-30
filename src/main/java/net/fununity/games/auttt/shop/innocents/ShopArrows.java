package net.fununity.games.auttt.shop.innocents;

import net.fununity.games.auttt.player.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopArrows extends ShopItem {

    public ShopArrows(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse(new ItemStack(Material.ARROW, 10));
        removeItem();
    }
}
