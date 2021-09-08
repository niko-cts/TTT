package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

public class ShopInvisibility extends ShopItem {

    private BukkitTask bukkitTask;

    public ShopInvisibility(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!didPlayerUse(event)) return;
        removeItemStack();
        tttPlayer.getApiPlayer().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 30, 1, false, false));
        this.bukkitTask = Bukkit.getScheduler().runTaskLater(TTT.getInstance(), this::removeItem, 20*30);
    }



    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (!event.getDamager().getUniqueId().equals(tttPlayer.getApiPlayer().getUniqueId())) return;
        if (this.bukkitTask == null) return;
        ((Player) event.getDamager()).removePotionEffect(PotionEffectType.INVISIBILITY);
        this.bukkitTask.cancel();
        removeItem();
    }
}
