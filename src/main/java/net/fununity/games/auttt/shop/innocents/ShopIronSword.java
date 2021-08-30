package net.fununity.games.auttt.shop.innocents;

import net.fununity.games.auttt.player.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopIronSword extends ShopItem {

    public ShopIronSword(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse(new ItemStack(Material.IRON_SWORD));
        removeItem();
    }
}
