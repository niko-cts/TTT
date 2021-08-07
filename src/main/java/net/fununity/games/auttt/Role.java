package net.fununity.games.auttt;

import org.bukkit.ChatColor;

public enum Role {

    TRAITOR(ChatColor.RED, "Traitor"),
    DETECTIVE(ChatColor.BLUE, "Detective"),
    INNOCENT(ChatColor.GREEN, "Innocent");

    private final ChatColor color;
    private final String name;

    Role(ChatColor color, String nameKey) {
        this.color = color;
        this.name = nameKey;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getColoredName() {
        return color + name;
    }
}
