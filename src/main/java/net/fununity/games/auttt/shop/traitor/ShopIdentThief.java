package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.npc.events.PlayerInteractAtNPCEvent;

/**
 * This class represents the {@link TraitorItems#IDENT_THIEF} shop item.
 * It lets a traitor steals the identiy of another player.
 * The rest of the logic can be found here: {@link net.fununity.games.auttt.corpse.PlayerCorpse#clickedOn(PlayerInteractAtNPCEvent)}.
 * @author Niko
 * @since 1.1
 */
public class ShopIdentThief extends ShopItem {
    public ShopIdentThief(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse();
    }

}
