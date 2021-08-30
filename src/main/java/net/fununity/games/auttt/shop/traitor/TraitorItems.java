package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.shop.ShopAdrenalin;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.games.auttt.shop.ShopRadar;
import net.fununity.games.auttt.shop.innocents.ShopIronSword;
import net.fununity.games.auttt.shop.innocents.ShopArrows;

public enum TraitorItems implements ShopItems {
    ARROWS(1, 0, 5, 5, ShopArrows.class),
    TRAP_TICKET(1, 0, 10, 10, ShopTrapTicket.class),
    RADAR(2, 0, 1, 1, ShopRadar.class),
    NOM_NOM_DEVICE(3, 0, 1, 1, ShopNomNomDevice.class),
    AXE(3, 2, 5, 5, ShopAxe.class),
    IRON_SWORD(3, 251, 1, 1, ShopIronSword.class),
    ADRENALIN(3, 1, 5, 5, ShopAdrenalin.class),
    FIREBALL(4, 1, 5, 5, ShopFireball.class),
    TRAP_REPAIR(4, 1, 1, 1, ShopTrapRepair.class),
    BOOM_BODY(4, 1, 5, 5, ShopBoomBody.class),
    JIHAD(4, 1, 1, 1, ShopJihad.class),
    TESTER_FAKER(5, 1, 2, 2, ShopTesterFaker.class),
    IDENT_THIEF(5, 1, 5, 5, ShopIdentThief.class),
    GENERATOR(5, 1, 5, 5, ShopGenerator.class),
    INVISIBILITY(6, 1, 5, 5, ShopInvisibility.class),
    ROBOT(7, 1, 2, 2, ShopRobot.class);

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
