package net.fununity.games.auttt.shop;

import org.bukkit.inventory.ItemStack;

/**
 * This interface is the base for the following enum shop lists:
 * @see net.fununity.games.auttt.shop.traitor.TraitorItems
 * @see net.fununity.games.auttt.shop.innocents.InnocentItems
 * @see net.fununity.games.auttt.shop.detectives.DetectiveItems
 * @author Niko
 * @since 1.1
 */
public interface ShopItems {

    String name();

    /**
     * The amount of coins cost in the shop.
     * @return int - coins cost per buy
     * @since 1.1
     */
    int getCoinsCost();

    /**
     * Get the maximum amount of uses of this item.
     * (0 equals infinite uses or passive item)
     * @return int - maximum amount of uses per item.
     * @since 1.1
     */
    int getMaximumUses();

    /**
     * Get the maximum amount a user can buy this item.
     * (0 equals infinite)
     * @return int - maximum amount of buys per item.
     * @since 1.1
     */
    int getMaximumBuys();

    /**
     * Get the maximum amount a user can have this item.
     * (0 equals infinite)
     * @return int - maximum amount a user can have this item at once.
     * @since 1.1
     */
    int getMaximumAmountAtOnce();

    /**
     * Get the class of the shop.
     * @return {@link ShopItem} - The shop class.
     * @since 1.1
     */
    Class<? extends ShopItem> getShopClass();

    /**
     * Returns the item displayed (and given) for the shopItem.
     * @return ItemStack - displayed item.
     * @since 1.1
     */
    ItemStack getItem();

    /**
     * Get the translation name key for the shop item.
     * @return String - the name key of the shop.
     * @since 1.1
     */
    String getNameKey();

    /**
     * Get the translation description (lore) key for the shop item.
     * @return String - the description key of the shop.
     * @since 1.1
     */
    String getDescriptionKey();
}
