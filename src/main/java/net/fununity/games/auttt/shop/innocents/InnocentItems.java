package net.fununity.games.auttt.shop.innocents;

import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum InnocentItems implements ShopItems {
    NIGHTVISION(1, 0, 1, 1, ShopNightVision.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_NIGHTVISION_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_NIGHTVISION_DESCRIPTION),
    ARROWS(1, 0, 5, 5, ShopArrows.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_ARROWS_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_ARROWS_DESCRIPTION),
    RADAR(2, 0, 1, 1, ShopRadar.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_DESCRIPTION),
    ANTI_BOOM_BODY(3, 1, 5, 5, ShopAntiBoomBody.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_ANTI_BOOM_BODY_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_ANTI_BOOM_BODY_DESCRIPTION),
    IRON_SWORD(3, 0, 1, 1, ShopIronSword.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_IRON_SWORD_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_IRON_SWORD_DESCRIPTION);

    private final int coinsCost;
    private final int maximumUses;
    private final int maximumBuys;
    private final int maximumAtOnce;
    private final Class<? extends ShopItem> shopClass;
    private final ItemStack item;
    private final String nameKey;
    private final String descriptionKey;

    InnocentItems(int coinsCost, int maximumUses, int maximumBuys, int maximumAtOnce, Class<? extends ShopItem> shopClass, ItemStack item, String nameKey, String descriptionKey) {
        this.coinsCost = coinsCost;
        this.maximumUses = maximumUses;
        this.maximumBuys = maximumBuys;
        this.maximumAtOnce = maximumAtOnce;
        this.shopClass = shopClass;
        this.item = item;
        this.nameKey = nameKey;
        this.descriptionKey = descriptionKey;
    }

    /**
     * The amount of coins cost in the shop.
     * @return int - coins cost per buy
     * @since 0.0.1
     */
    @Override
    public int getCoinsCost() {
        return this.coinsCost;
    }

    /**
     * Get the maximum amount of uses of this item.
     * (0 equals infinite uses or passive item)
     * @return int - maximum amount of uses per item.
     * @since 0.0.1
     */
    @Override
    public int getMaximumUses() {
        return this.maximumUses;
    }

    /**
     * Get the maximum amount a user can buy this item.
     * (0 equals infinite)
     * @return int - maximum amount of buys per item.
     * @since 0.0.1
     */
    @Override
    public int getMaximumBuys() {
        return this.maximumBuys;
    }

    /**
     * Get the maximum amount a user can have this item.
     * (0 equals infinite)
     * @return int - maximum amount a user can have this item at once.
     * @since 0.0.1
     */
    @Override
    public int getMaximumAmountAtOnce() {
        return this.maximumAtOnce;
    }

    /**
     * Get the class of the shop.
     * @return {@link ShopItem} - The shop class.
     * @since 0.0.1
     */
    @Override
    public Class<? extends ShopItem> getShopClass() {
        return this.shopClass;
    }


    /**
     * Returns the item displayed (and given) for the shopItem.
     * @return ItemStack - displayed item.
     * @since 1.1
     */
    @Override
    public ItemStack getItem() {
        return item;
    }

    /**
     * Get the translation name key for the shop item.
     * @return String - the name key of the shop.
     * @since 1.1
     */
    @Override
    public String getNameKey() {
        return nameKey;
    }

    /**
     * Get the translation description (lore) key for the shop item.
     * @return String - the description key of the shop.
     * @since 1.1
     */
    @Override
    public String getDescriptionKey() {
        return descriptionKey;
    }
}
