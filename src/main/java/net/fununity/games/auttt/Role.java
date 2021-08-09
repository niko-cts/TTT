package net.fununity.games.auttt;

import net.fununity.main.api.item.UsefulItems;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public enum Role {

    TRAITOR(ChatColor.RED, "Traitor", UsefulItems.BACKGROUND_RED),
    DETECTIVE(ChatColor.BLUE, "Detective", UsefulItems.BACKGROUND_BLUE),
    INNOCENT(ChatColor.GREEN, "Innocent", UsefulItems.BACKGROUND_GREEN);

    private final ChatColor color;
    private final String name;
    private final ItemStack glass;

    Role(ChatColor color, String nameKey, ItemStack glass) {
        this.color = color;
        this.name = nameKey;
        this.glass = glass;
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
}
