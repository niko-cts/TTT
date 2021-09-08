package net.fununity.games.auttt;

import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.games.auttt.shop.detectives.DetectiveItems;
import net.fununity.games.auttt.shop.innocents.InnocentItems;
import net.fununity.games.auttt.shop.traitor.TraitorItems;
import net.fununity.main.api.item.UsefulItems;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

/**
 * This class represents the available roles in the game.
 * Every player will have one of these roles.
 * @author Niko
 * @since 0.0.1
 */
public enum Role {

    TRAITOR(ChatColor.DARK_RED, "Traitor", UsefulItems.BACKGROUND_RED, TraitorItems.values()),
    DETECTIVE(ChatColor.BLUE, "Detective", UsefulItems.BACKGROUND_BLUE, DetectiveItems.values()),
    INNOCENT(ChatColor.GREEN, "Innocent", UsefulItems.BACKGROUND_GREEN, InnocentItems.values());

    private final ChatColor color;
    private final String name;
    private final ItemStack glass;
    private final ShopItems[] shopItems;

    /**
     * Instantiates the class.
     * @param color ChatColor - the color of the role.
     * @param name String - the displaying name of the role.
     * @param glass ItemStack - the representing item stack glass.
     * @param shopItems {@link ShopItems}[] - all shop items that are available for the role.
     * @since 0.0.1
     */
    Role(ChatColor color, String name, ItemStack glass, ShopItems[] shopItems) {
        this.color = color;
        this.name = name;
        this.glass = glass;
        this.shopItems = shopItems;
    }

    /**
     * Returns the ChatColor of the role.
     * @return ChatColor - the chatcolor of the role.
     * @since 0.0.1
     */
    public ChatColor getColor() {
        return color;
    }

    /**
     * Returns the colored name of the role.
     * @return String - the color + displaying name of the role
     * @since 0.0.1
     */
    public String getColoredName() {
        return color + name;
    }

    /**
     * The representing glass item stack of the role.
     * @return ItemStack - the representing glass.
     * @since 0.0.1
     */
    public ItemStack getGlass() {
        return glass;
    }

    /**
     * Returns an array with all shop items available for the role.
     * @return {@link ShopItems}[] - array of all available shopitems.
     * @since 1.1
     */
    public ShopItems[] getShopItems() {
        return shopItems;
    }
}
