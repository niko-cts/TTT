package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.actionbar.ActionbarMessage;
import net.fununity.main.api.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ShopAxe extends ShopItem {

    private static final ItemBuilder ITEM = new ItemBuilder(Material.GOLD_AXE)
            .setName(TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_NAME)
            .setLore(TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_DESCRIPTION)
            .setDurability((short) 2);

    private long lastHit;

    public ShopAxe(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        this.lastHit = 0;
        giveItemToUse(ITEM.translate(tttPlayer.getApiPlayer().getLanguage()));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getUniqueId().equals(tttPlayer.getApiPlayer().getUniqueId())) return;
        Player damager = (Player) event.getDamager();
        if (damager.getInventory().getItemInMainHand().getType() != Material.GOLD_AXE) return;
        if (System.currentTimeMillis() - lastHit <= 2000) {
            FunUnityAPI.getInstance().getActionbarManager().addActionbar(damager.getUniqueId(), new ActionbarMessage(TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_COOLDOWN));
            event.setCancelled(true);
            return;
        }
        this.lastHit = System.currentTimeMillis();
        damager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*3, 1));
        event.setDamage(10);
    }

}
