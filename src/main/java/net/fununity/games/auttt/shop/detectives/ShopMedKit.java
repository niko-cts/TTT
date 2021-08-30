package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.player.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.main.api.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ShopMedKit extends ShopItem {

    private static final ItemBuilder ITEM = new ItemBuilder(Material.GOLDEN_CARROT).setName(TranslationKeys.TTT_GAME_SHOP_ITEM_MED_KIT_NAME).setLore(TranslationKeys.TTT_GAME_SHOP_ITEM_MED_KIT_DESCRIPTION);

    public ShopMedKit(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse(ITEM.translate(tttPlayer.getApiPlayer().getLanguage()));
    }

    @EventHandler
    public void onConsumeItem(PlayerItemConsumeEvent event) {
        if (!event.getPlayer().getUniqueId().equals(tttPlayer.getApiPlayer().getUniqueId()) || event.getItem().getType() != Material.GOLDEN_CARROT) return;
        event.getPlayer().setHealth(event.getPlayer().getHealth() + 10);
        event.setCancelled(false);
        removeItem();
    }
}
