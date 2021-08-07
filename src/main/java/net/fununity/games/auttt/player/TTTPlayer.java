package net.fununity.games.auttt.player;

import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.util.TTTScoreboard;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.main.api.player.APIPlayer;

import java.util.ArrayList;
import java.util.List;

public class TTTPlayer {

    private final APIPlayer player;
    private final Role role;
    private final List<ShopItem> shopItems;
    private int coins;
    private boolean alive;
    private boolean found;

    public TTTPlayer(APIPlayer player, Role role) {
        this.player = player;
        this.role = role;
        this.shopItems = new ArrayList<>();
        this.coins = 0;
        this.alive = true;
        this.found = false;
    }

    public APIPlayer getApiPlayer() {
        return player;
    }

    public Role getRole() {
        return role;
    }

    public List<ShopItem> getShopItems() {
        return shopItems;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setFound(boolean found) {
        this.found = found;
        TTTScoreboard.updateTablist(this);
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isFound() {
        return found;
    }

    public String getColoredName() {
        return getRole().getColor() + getApiPlayer().getPlayer().getName();
    }

}
