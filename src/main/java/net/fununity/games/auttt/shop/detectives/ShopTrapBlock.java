package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.rooms.RoomsManager;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;

public class ShopTrapBlock extends ShopItem {
    public ShopTrapBlock(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        if (RoomsManager.getInstance().getTrap() != null)
            RoomsManager.getInstance().getTrap().setTesterProtected(true);
    }
}
