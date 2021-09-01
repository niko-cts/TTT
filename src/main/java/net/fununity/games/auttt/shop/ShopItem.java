package net.fununity.games.auttt.shop;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * This is the abstract class for every shop item.
 * It implements a {@link Listener} and will be registered as it.
 * This class will be extended by every other shop item.
 * @author Niko
 * @since 1.1
 */
public abstract class ShopItem implements Listener {

    protected final ShopItems shopItem;
    protected final TTTPlayer tttPlayer;
    private ItemStack itemUse;
    private int used;

    /**
     * Instantiates the class and registers the event listener.
     * @param shopItem {@link ShopItems} - The shop item references.
     * @param tttPlayer {@link TTTPlayer} - The Player who buys this shop item instance.
     * @since 1.1
     */
    public ShopItem(ShopItems shopItem, TTTPlayer tttPlayer) {
        this.shopItem = shopItem;
        this.tttPlayer = tttPlayer;
        TTT.getInstance().getServer().getPluginManager().registerEvents(this, TTT.getInstance());
    }

    /**
     * Player used the shop item.
     * May reduce the item by once
     * and if {@link ShopItem#getMaximumUses()} has reached, will remove the item.
     * @param reduceItem boolean - reduces the item by once.
     * @since 0.0.1
     */
    public void use(boolean reduceItem) {
        if (getMaximumUses() == 0) return;
        if (reduceItem)
            removeItemStack();
        used++;
        if (this.used == getMaximumUses()) {
            removeItem();
        }
    }

    /**
     * Removes the itemstack by one.
     * @since 1.1
     */
    private void removeItemStack() {
        PlayerInventory inventory = tttPlayer.getApiPlayer().getPlayer().getInventory();
        for (int i = 0; i < inventory.getContents().length; i++) {
            ItemStack content = inventory.getContents()[i];
            if (itemUse.equals(content)) {
                if (content.getAmount() > 1)
                    content.setAmount(content.getAmount() - 1);
                else
                    inventory.setItem(i, new ItemStack(Material.AIR));
            }
        }
    }

    /**
     * Get the maximum uses of the shopItem.
     * @see ShopItems#getMaximumUses()
     * @return int - the maximum amount of uses
     * @since 1.1
     */
    public int getMaximumUses() {
        return this.shopItem.getMaximumUses();
    }

    /**
     * Sets and gives the player the item to use this ShopItem.
     * @param itemSack ItemStack - the item to give.
     * @since 1.1
     */
    public void giveItemToUse(ItemStack itemSack) {
        this.itemUse = itemSack;
        tttPlayer.getApiPlayer().getPlayer().getInventory().addItem(itemUse);
    }

    /**
     * Sets and gives the player the item to use this ShopItem.
     * @see ShopItems#getTranslatedItem(Language)
     * @since 1.1
     */
    public void giveItemToUse() {
        giveItemToUse(shopItem.getTranslatedItem(tttPlayer.getApiPlayer().getLanguage()));
    }

    /**
     * Check if the player interacts with the shopitem.
     * @param event PlayerInteractEvent - the triggered interact event.
     * @return boolean - player interacted with the corresponding shopItem
     * @since 1.1
     */
    public boolean didPlayerUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return false;
        if (GameManager.getInstance().getCurrentGameState() != GameState.INGAME) return false;
        if (event.getHand() != EquipmentSlot.HAND || !event.getPlayer().getUniqueId().equals(tttPlayer.getApiPlayer().getUniqueId())) return false;
        return event.getPlayer().getInventory().getItemInMainHand().getType() == itemUse.getType();
    }

    /**
     * Returns the ShopItems references.
     * @return {@link ShopItems} - the ShopItems references.
     * @since 1.1
     */
    public ShopItems getShopItem() {
        return shopItem;
    }

    /**
     * Removes the ShopItem from the TTTPlayer.
     * @since 1.1
     */
    public void removeItem() {
        tttPlayer.getShopItems().remove(this);
        HandlerList.unregisterAll(this);
    }

    /**
     * Listener to cancel the drop of the shopitem
     * @param event PlayerDropItemEvent - the event that was triggerd
     * @since 1.1
     */
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (itemUse == null || !event.getPlayer().getUniqueId().equals(tttPlayer.getApiPlayer().getUniqueId())) return;
        if (event.getItemDrop().getItemStack().equals(itemUse))
            event.setCancelled(true);
    }
}
