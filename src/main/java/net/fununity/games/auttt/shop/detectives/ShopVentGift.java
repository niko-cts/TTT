package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.rooms.RoomsManager;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;

public class ShopVentGift extends ShopItem {
    public ShopVentGift(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        if (RoomsManager.getInstance().getVent() != null)
            RoomsManager.getInstance().getVent().gift();
    }
}
