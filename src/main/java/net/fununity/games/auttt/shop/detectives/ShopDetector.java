package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.player.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;

public class ShopDetector extends ShopItem {
    public ShopDetector(TTTPlayer tttPlayer) {
        super(DetectiveItems.DETECTOR, tttPlayer);
    }
}
