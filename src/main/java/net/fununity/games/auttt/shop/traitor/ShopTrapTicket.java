package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.main.api.actionbar.ActionbarMessage;
import org.bukkit.Bukkit;

import java.util.List;

public class ShopTrapTicket extends ShopItem {
    public ShopTrapTicket(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), () -> {
            List<ShopItem> trapTickets = tttPlayer.getShopItemsOfType(TraitorItems.TRAP_TICKET);
            if (trapTickets.size() % 2 == 0) {
                tttPlayer.getApiPlayer().sendActionbar(new ActionbarMessage(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_TICKET_REMOVED));
                trapTickets.forEach(ShopItem::removeItem);
            }
        }, 5);
    }
}
