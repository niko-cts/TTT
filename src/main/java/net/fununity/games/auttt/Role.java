package net.fununity.games.auttt;

import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.games.auttt.shop.detectives.DetectiveItems;
import net.fununity.games.auttt.shop.innocents.InnocentItems;
import net.fununity.games.auttt.shop.traitor.TraitorItems;
import net.fununity.main.api.item.UsefulItems;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public enum Role {

    TRAITOR(ChatColor.RED, "Traitor", UsefulItems.BACKGROUND_RED, TraitorItems.values()),
    DETECTIVE(ChatColor.BLUE, "Detective", UsefulItems.BACKGROUND_BLUE, DetectiveItems.values()),
    INNOCENT(ChatColor.GREEN, "Innocent", UsefulItems.BACKGROUND_GREEN, InnocentItems.values());

    private final ChatColor color;
    private final String name;
    private final ItemStack glass;
    private final ShopItems[] shopItems;

    Role(ChatColor color, String name, ItemStack glass, ShopItems[] shopItems) {
        this.color = color;
        this.name = name;
        this.glass = glass;
        this.shopItems = shopItems;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getColoredName() {
        return color + name;
    }

    public ItemStack getGlass() {
        return glass;
    }

    public ShopItems[] getShopItems() {
        return shopItems;
    }
}
