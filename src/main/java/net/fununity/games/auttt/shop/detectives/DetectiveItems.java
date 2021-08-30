package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.games.auttt.shop.ShopRadar;
import net.fununity.games.auttt.shop.ShopAdrenalin;
import net.fununity.games.auttt.shop.innocents.ShopNightVision;

public enum DetectiveItems implements ShopItems {
    NIGHTVISION(2, 0, 1, 1, ShopNightVision.class),
    SUPER_IDENT(2, 0, 1, 1, ShopSuperIdent.class),
    RADAR(2, 0, 1, 1, ShopRadar.class),
    HOOK(3, 5, 5, 1, ShopHook.class),
    ADRENALIN(3, 1, 5, 5, ShopAdrenalin.class),
    TRAP_BLOCK(3, 1, 1, 1, ShopTrapBlock.class),
    MOVE_SENSOR(3, 1, 3, 3, ShopMoveSensor.class),
    DETECTOR(4, 1, 5, 5, ShopDetector.class),
    VENT_GIFT(4, 1, 5, 5, ShopVentGift.class),
    HEAL_STATION(4, 1, 5, 5, ShopHealStation.class),
    ONE_SHOT_BOW(5, 1, 5, 5, ShopOneShotBow.class),
    MED_KIT(5, 1, 5, 5, ShopMedKit.class),
    YOUTH(5, 0, 2, 2, ShopYouth.class);

    private final int coinsCost;
    private final int maximumUses;
    private final int maximumBuys;
    private final int maximumAtOnce;
    private final Class<? extends ShopItem> shopClass;

    DetectiveItems(int coinsCost, int maximumUses, int maximumBuys, int maximumAtOnce, Class<? extends ShopItem> shopClass) {
        this.coinsCost = coinsCost;
        this.maximumUses = maximumUses;
        this.maximumBuys = maximumBuys;
        this.maximumAtOnce = maximumAtOnce;
        this.shopClass = shopClass;
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
}
