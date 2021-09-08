package net.fununity.games.auttt.gui;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.main.api.actionbar.ActionbarMessage;
import net.fununity.main.api.inventory.ClickAction;
import net.fununity.main.api.inventory.CustomInventory;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.item.UsefulItems;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.main.api.util.Utils;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopGUI {

    public static void open(TTTPlayer tttPlayer) {
        APIPlayer apiPlayer = tttPlayer.getApiPlayer();
        Language lang = apiPlayer.getLanguage();
        ShopItems[] shopItems = tttPlayer.getRole().getShopItems();
        CustomInventory menu = new CustomInventory(lang.getTranslation(TranslationKeys.TTT_GUI_SHOP_TITLE), Utils.getPerfectInventorySize(shopItems.length) + 9);

        for (ShopItems shopItem : shopItems) {
            menu.addItem(new ItemBuilder(shopItem.getItem())
                    .setName(lang.getTranslation(shopItem.getNameKey()))
                    .setLore(lang.getTranslation(shopItem.getDescriptionKey(), "${cost}", shopItem.getCoinsCost() + "")).craft(), new ClickAction() {
                @Override
                public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int i) {
                    setCloseInventory(tryToBuyItem(tttPlayer, shopItem));
                }
            });
        }

        for (int i = menu.getInventory().getSize() - 9; i < menu.getInventory().getSize(); i++)
            menu.setItem(i, UsefulItems.BACKGROUND_BLACK);
        menu.setItem(menu.getInventory().getSize() - 5, new ItemBuilder(Material.GOLD_INGOT)
                .setAmount(Math.max(Math.min(tttPlayer.getCoins(), 64), 1))
                .setName(lang.getTranslation(TranslationKeys.TTT_GUI_SHOP_COINS_NAME, "${amount}", tttPlayer.getCoins() + ""))
                .setLore(lang.getTranslation(TranslationKeys.TTT_GUI_SHOP_COINS_LORE).split(";"))
                .craft(), new ClickAction(true) {
            @Override
            public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int i) {
                Bukkit.getScheduler().runTaskLater(TTT.getInstance(), ()->Bukkit.dispatchCommand(apiPlayer.getPlayer(), "coinsinfo"), 1L);
            }
        });
        menu.open(apiPlayer);
    }

    public static boolean tryToBuyItem(TTTPlayer tttPlayer, ShopItems shopItem) {
        APIPlayer apiPlayer = tttPlayer.getApiPlayer();
        if (tttPlayer.getCoins() < shopItem.getCoinsCost()) {
            apiPlayer.sendActionbar(new ActionbarMessage(TranslationKeys.TTT_GUI_SHOP_ERROR_NOTENOUGHCOINS));
            return false;
        }
        if (shopItem.getMaximumAmountAtOnce() <= tttPlayer.getShopItemsOfType(shopItem).size()) {
            apiPlayer.sendActionbar(new ActionbarMessage(TranslationKeys.TTT_GUI_SHOP_ERROR_MAXIMUCURRENTAMOUNT));
            return false;
        }
        if (shopItem.getMaximumBuys() <= tttPlayer.getShopItemBuyAmounts(shopItem)) {
            apiPlayer.sendActionbar(new ActionbarMessage(TranslationKeys.TTT_GUI_SHOP_ERROR_MAXIMUBUYSAMOUNT));
            return false;
        }

        apiPlayer.sendActionbar(new ActionbarMessage(TranslationKeys.TTT_GUI_SHOP_BUYED), "${name}", apiPlayer.getLanguage().getTranslation(shopItem.getNameKey()));
        tttPlayer.buysShopItem(shopItem);
        return true;
    }

}
