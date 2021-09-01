package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;

/**
 * Shop Item for the traitor.
 * Most of the logic happens {@link net.fununity.games.auttt.corpse.PlayerCorpse}
 * @see ShopItem
 * @author Niko
 * @since 1.1
 */
public class ShopNomNomDevice extends ShopItem {
    public ShopNomNomDevice(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse();
    }
}
