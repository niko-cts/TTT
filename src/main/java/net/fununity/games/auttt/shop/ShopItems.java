package net.fununity.games.auttt.shop;

public interface ShopItems {

    String name();

    /**
     * The amount of coins cost in the shop.
     * @return int - coins cost per buy
     * @since 0.0.1
     */
    int getCoinsCost();

    /**
     * Get the maximum amount of uses of this item.
     * (0 equals infinite uses or passive item)
     * @return int - maximum amount of uses per item.
     * @since 0.0.1
     */
    int getMaximumUses();

    /**
     * Get the maximum amount a user can buy this item.
     * (0 equals infinite)
     * @return int - maximum amount of buys per item.
     * @since 0.0.1
     */
    int getMaximumBuys();

    /**
     * Get the maximum amount a user can have this item.
     * (0 equals infinite)
     * @return int - maximum amount a user can have this item at once.
     * @since 0.0.1
     */
    int getMaximumAmountAtOnce();

    /**
     * Get the class of the shop.
     * @return {@link ShopItem} - The shop class.
     * @since 0.0.1
     */
    Class<? extends ShopItem> getShopClass();

}
