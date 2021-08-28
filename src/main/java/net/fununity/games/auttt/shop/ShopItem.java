package net.fununity.games.auttt.shop;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.player.TTTPlayer;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public abstract class ShopItem implements Listener {

    protected final ShopItems shopItem;
    protected final TTTPlayer tttPlayer;
    private Material itemUse;
    private int used;

    public ShopItem(ShopItems shopItem, TTTPlayer tttPlayer) {
        this.shopItem = shopItem;
        this.tttPlayer = tttPlayer;
        TTT.getInstance().getServer().getPluginManager().registerEvents(this, TTT.getInstance());
    }

    public void use(boolean reduceItem) {
        if(getMaximumUses() == 0) return;
        if (reduceItem)
           removeItem(tttPlayer.getApiPlayer().getPlayer().getInventory().getItemInMainHand());
        used++;
        if (this.used == getMaximumUses()) {
            tttPlayer.getShopItems().remove(this);
        }
    }


    private void removeItem(ItemStack item) {
        PlayerInventory inventory = tttPlayer.getApiPlayer().getPlayer().getInventory();
        for (int i = 0; i < inventory.getContents().length; i++) {
            ItemStack content = inventory.getContents()[i];
            if (item.equals(content)) {
                if (content.getAmount() > 1)
                    content.setAmount(content.getAmount() - 1);
                else
                    inventory.setItem(i, new ItemStack(Material.AIR));
            }
        }
    }


    public int getMaximumUses() {
        return this.shopItem.getMaximumUses();
    }

    public int getUsed() {
        return used;
    }

    public void setItemUse(Material itemUse) {
        this.itemUse = itemUse;
    }

    public boolean didPlayerUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return false;
        if (GameManager.getInstance().getCurrentGameState() != GameState.INGAME) return false;
        if (event.getHand() != EquipmentSlot.HAND || !event.getPlayer().getUniqueId().equals(tttPlayer.getApiPlayer().getUniqueId())) return false;
        return event.getPlayer().getInventory().getItemInMainHand().getType() == itemUse;
    }

}
