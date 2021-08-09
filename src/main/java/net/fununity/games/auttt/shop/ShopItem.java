package net.fununity.games.auttt.shop;

import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.player.TTTPlayer;

import java.util.ArrayList;
import java.util.List;

public abstract class ShopItem {

    private final List<Role> rolesAvailable;
    private final int maximumUses;
    private int used;

    public ShopItem(List<Role> rolesAvailable, int maximumUses) {
        this.rolesAvailable = rolesAvailable;
        this.maximumUses = maximumUses;
    }

    public void use(TTTPlayer player) {
        used++;
    }

    public List<Role> getRolesAvailable() {
        return new ArrayList<>(rolesAvailable);
    }

    public int getMaximumUses() {
        return maximumUses;
    }

    public int getUsed() {
        return used;
    }
}
