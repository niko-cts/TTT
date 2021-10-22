package net.fununity.games.auttt;

import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.games.auttt.util.TTTScoreboard;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.player.APIPlayer;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class TTTPlayer {

    private final APIPlayer player;
    private final Role role;
    private final List<ShopItem> shopItems;
    private final Map<ShopItems, Integer> shopItemBuys;
    private int coins;
    private boolean alive;
    private boolean found;

    public TTTPlayer(APIPlayer player, Role role) {
        this.player = player;
        this.role = role;
        this.shopItems = new ArrayList<>();
        this.shopItemBuys = new HashMap<>();
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
        TTTScoreboard.updateScoreboard(getApiPlayer());
    }

    public int getCoins() {
        return coins;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void found() {
        this.found = true;
        FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers().forEach(TTTScoreboard::updateTablist);
    }

    public boolean isDead() {
        return !alive;
    }

    public boolean isFound() {
        return found;
    }

    public String getColoredName() {
        return getRole().getColor() + getApiPlayer().getPlayer().getName();
    }


    // SHOP ITEMS

    public List<ShopItem> getShopItemsOfType(ShopItems shopItem) {
        return shopItems.stream().filter(s -> s.getShopItem() == shopItem).collect(Collectors.toList());
    }

    public void buysShopItem(ShopItems shopItem) {
        try {
            shopItems.add(shopItem.getShopClass().getConstructor(ShopItems.class, TTTPlayer.class).newInstance(shopItem, this));
            setCoins(getCoins() - shopItem.getCoinsCost());
            shopItemBuys.put(shopItem, shopItemBuys.getOrDefault(shopItem, 0) + 1);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            TTT.getInstance().getLogger().log(Level.WARNING, "There was an error instantiating the shop item ' {0} ': {1}", new String[]{shopItem.name(), e.getMessage()});
        }
    }

    public int getShopItemBuyAmounts(ShopItems shopItem) {
        return shopItemBuys.getOrDefault(shopItem, 0);
    }

    public boolean hasShopItem(ShopItems... shopItems) {
        return this.shopItems.stream().anyMatch(s -> Arrays.asList(shopItems).contains(s.getShopItem()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TTTPlayer tttPlayer = (TTTPlayer) o;
        return role == tttPlayer.role && player.getUniqueId().equals(tttPlayer.getApiPlayer().getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }
}
