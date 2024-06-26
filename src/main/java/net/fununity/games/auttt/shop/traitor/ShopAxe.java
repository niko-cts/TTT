package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.actionbar.ActionbarMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ShopAxe extends ShopItem {

    private long lastHit;

    public ShopAxe(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        this.lastHit = 0;
        giveItemToUse();
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getUniqueId().equals(tttPlayer.getApiPlayer().getUniqueId())) return;
        Player damager = (Player) event.getDamager();
        if (damager.getInventory().getItemInMainHand().getType() != Material.GOLDEN_AXE) return;
        if (System.currentTimeMillis() - lastHit <= 2000) {
            FunUnityAPI.getInstance().getActionbarManager().addActionbar(damager.getUniqueId(), new ActionbarMessage(TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_COOLDOWN));
            event.setCancelled(true);
            return;
        }
        use(false);
        damager.getInventory().getItemInMainHand().setDurability((short) (getUsed() * 15 + getUsed()));
        if (getUsed() == getMaximumUses()) {
            damager.removePotionEffect(PotionEffectType.SLOW);
            damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 1, false));
        }

        this.lastHit = System.currentTimeMillis();
        event.setDamage(10);
    }

    @EventHandler
    public void onItem(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if (!player.getUniqueId().equals(tttPlayer.getApiPlayer().getUniqueId())) return;
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        if (newItem != null && newItem.getType() == Material.GOLDEN_AXE) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1, false));
        } else {
            ItemStack previousItem = player.getInventory().getItem(event.getPreviousSlot());
            if (previousItem != null && previousItem.getType() == Material.GOLDEN_AXE) {
                player.removePotionEffect(PotionEffectType.SLOW);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 1, false));
            }
        }
    }

}
