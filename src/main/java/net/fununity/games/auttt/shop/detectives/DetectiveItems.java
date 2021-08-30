package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.games.auttt.shop.ShopRadar;
import net.fununity.games.auttt.shop.ShopAdrenalin;
import net.fununity.games.auttt.shop.innocents.ShopNightVision;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum DetectiveItems implements ShopItems {
    NIGHTVISION(2, 0, 1, 1, ShopNightVision.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_NIGHTVISION_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_NIGHTVISION_DESCRIPTION),
    SUPER_IDENT(2, 0, 1, 1, ShopSuperIdent.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_SUPER_IDENT_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_SUPER_IDENT_DESCRIPTION),
    RADAR(2, 0, 1, 1, ShopRadar.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_DESCRIPTION),
    HOOK(3, 5, 5, 1, ShopHook.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_HOOK_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_HOOK_DESCRIPTION),
    ADRENALIN(3, 1, 5, 5, ShopAdrenalin.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_ADRENALIN_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_ADRENALIN_DESCRIPTION),
    TRAP_BLOCK(3, 1, 1, 1, ShopTrapBlock.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_BLOCK_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_BLOCK_DESCRIPTION),
    MOVE_SENSOR(3, 1, 3, 3, ShopMoveSensor.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_DESCRIPTION),
    DETECTOR(4, 1, 5, 5, ShopDetector.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_DETECTOR_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_DETECTOR_DESCRIPTION),
    VENT_GIFT(4, 1, 5, 5, ShopVentGift.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_VENT_GIFT_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_VENT_GIFT_DESCRIPTION),
    HEAL_STATION(4, 1, 5, 5, ShopHealStation.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_HEAL_STATION_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_HEAL_STATION_DESCRIPTION),
    ONE_SHOT_BOW(5, 1, 5, 5, ShopOneShotBow.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_ONE_SHOT_BOW_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_ONE_SHOT_BOW_DESCRIPTION),
    MED_KIT(5, 1, 5, 5, ShopMedKit.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_MED_KIT_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_MED_KIT_DESCRIPTION),
    YOUTH(5, 0, 2, 2, ShopYouth.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_YOUTH_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_YOUTH_DESCRIPTION);

    private final int coinsCost;
    private final int maximumUses;
    private final int maximumBuys;
    private final int maximumAtOnce;
    private final Class<? extends ShopItem> shopClass;
    private final ItemStack item;
    private final String nameKey;
    private final String descriptionKey;

    DetectiveItems(int coinsCost, int maximumUses, int maximumBuys, int maximumAtOnce, Class<? extends ShopItem> shopClass, ItemStack item, String nameKey, String descriptionKey) {
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
