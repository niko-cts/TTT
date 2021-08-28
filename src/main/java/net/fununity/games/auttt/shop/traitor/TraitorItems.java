package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.shop.ShopAdrenalin;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.games.auttt.shop.ShopRadar;

public enum TraitorItems implements ShopItems {
    TRAP_TICKET(ShopTrapTicket.class),
    RADAR(ShopRadar.class),
    NOM_NOM_DEVICE(ShopNomNomDevice.class),
    AXE(ShopAxe.class),
    ADRENALIN(ShopAdrenalin.class),
    FIREBALL(ShopFireball.class),
    TRAP_REPAIR(ShopTrapRepair.class),
    BOOM_BODY(ShopBoomBody.class),
    JIHAD(ShopJihad.class),
    TESTER_FAKER(ShopTesterFaker.class),
    IDENT_THIEF(ShopIdentThief.class),
    GENERATOR(ShopGenerator.class),
    INVISIBILITY(ShopInvisibility.class),
    ROBOT(ShopRobot.class);

    private final int coinsCost;
    private final int maximumUses;
    private final int maximumBuys;
    private final int maximumAtOnce;
    private final Class<? extends ShopItem> shopClass;

    TraitorItems(int coinsCost, int maximumUses, int maximumBuys, int maximumAtOnce, Class<? extends ShopItem> shopClass) {
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
    }}
