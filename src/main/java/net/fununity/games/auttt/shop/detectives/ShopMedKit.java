package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ShopMedKit extends ShopItem {

    public ShopMedKit(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse();
    }

    @EventHandler
    public void onConsumeItem(PlayerItemConsumeEvent event) {
        if (!event.getPlayer().getUniqueId().equals(tttPlayer.getApiPlayer().getUniqueId()) || event.getItem().getType() != Material.GOLDEN_CARROT) return;
        event.getPlayer().setHealth(event.getPlayer().getHealth() + 10);
        event.setCancelled(false);
        removeItem();
    }
}
