package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.ShopAdrenalin;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.games.auttt.shop.ShopRadar;
import net.fununity.games.auttt.shop.innocents.ShopIronSword;
import net.fununity.games.auttt.shop.innocents.ShopArrows;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum TraitorItems implements ShopItems {
    ARROWS(1, 0, 5, 5, ShopArrows.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_ARROWS_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_ARROWS_DESCRIPTION),
    TRAP_TICKET(1, 0, 10, 10, ShopTrapTicket.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_TICKET_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_TICKET_DESCRIPTION),
    RADAR(2, 0, 1, 1, ShopRadar.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_DESCRIPTION),
    NOM_NOM_DEVICE(3, 0, 1, 1, ShopNomNomDevice.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_NOM_NOM_DEVICE_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_NOM_NOM_DEVICE_DESCRIPTION),
    AXE(3, 2, 5, 5, ShopAxe.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_DESCRIPTION),
    IRON_SWORD(3, 251, 1, 1, ShopIronSword.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_IRON_SWORD_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_IRON_SWORD_DESCRIPTION),
    ADRENALIN(3, 1, 5, 5, ShopAdrenalin.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_ADRENALIN_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_ADRENALIN_DESCRIPTION),
    FIREBALL(4, 1, 5, 5, ShopFireball.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_FIREBALL_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_FIREBALL_DESCRIPTION),
    TRAP_REPAIR(4, 1, 1, 1, ShopTrapRepair.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_DESCRIPTION),
    BOOM_BODY(4, 1, 5, 5, ShopBoomBody.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_BOOM_BODY_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_BOOM_BODY_DESCRIPTION),
    JIHAD(4, 1, 1, 1, ShopJihad.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_JIHAD_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_JIHAD_DESCRIPTION),
    TESTER_FAKER(5, 1, 2, 2, ShopTesterFaker.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_TESTER_FAKER_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_TESTER_FAKER_DESCRIPTION),
    IDENT_THIEF(5, 1, 5, 5, ShopIdentThief.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_IDENT_THIEF_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_IDENT_THIEF_DESCRIPTION),
    GENERATOR(5, 1, 5, 5, ShopGenerator.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_GENERATOR_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_GENERATOR_DESCRIPTION),
    INVISIBILITY(6, 1, 5, 5, ShopInvisibility.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_INVISIBILITY_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_INVISIBILITY_DESCRIPTION),
    ROBOT(7, 1, 2, 2, ShopRobot.class, new ItemStack(Material.PAPER), TranslationKeys.TTT_GAME_SHOP_ITEM_ROBOT_NAME, TranslationKeys.TTT_GAME_SHOP_ITEM_ROBOT_DESCRIPTION);

    private final int coinsCost;
    private final int maximumUses;
    private final int maximumBuys;
    private final int maximumAtOnce;
    private final Class<? extends ShopItem> shopClass;
    private final ItemStack item;
    private final String nameKey;
    private final String descriptionKey;

    TraitorItems(int coinsCost, int maximumUses, int maximumBuys, int maximumAtOnce, Class<? extends ShopItem> shopClass, ItemStack item, String nameKey, String descriptionKey) {
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

    /**
     * Returns the translated item for players inventory.
     * @param language Language - the language to translate in.
     * @return ItemStack - translated item.
     * @since 1.1
     */
    @Override
    public ItemStack getTranslatedItem(Language language) {
        return new ItemBuilder(getItem()).setName(language.getTranslation(getNameKey())).setLore(language.getTranslation(getDescriptionKey())).craft();
    }
}
