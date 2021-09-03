package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.rooms.vent.Vent;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;

/**
 * Move sensor of the vent.
 * All other logic are located here {@link Vent}
 * @author Niko
 * @since 1.1
 */
public class ShopMoveSensor extends ShopItem {

    public ShopMoveSensor(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse();
    }
}
