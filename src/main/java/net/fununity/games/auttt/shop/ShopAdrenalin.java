package net.fununity.games.auttt.shop;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.item.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ShopAdrenalin extends ShopItem {

    private static final ItemBuilder ITEM = new ItemBuilder(Material.POTION).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 10, 1, true, true, Color.BLUE)).setName(TranslationKeys.TTT_GAME_SHOP_ITEM_ADRENALIN_NAME).setLore(TranslationKeys.TTT_GAME_SHOP_ITEM_ADRENALIN_DESCRIPTION);

    public ShopAdrenalin(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        tttPlayer.getApiPlayer().getPlayer().getInventory().addItem(ITEM.translate(tttPlayer.getApiPlayer().getLanguage()));
        removeItem();
    }
}
