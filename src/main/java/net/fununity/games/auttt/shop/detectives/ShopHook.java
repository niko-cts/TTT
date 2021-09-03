package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

public class ShopHook extends ShopItem {

    public ShopHook(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse();
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof FishHook) || !(event.getEntity().getShooter() instanceof Player)) return;
        TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(((Player) event.getEntity().getShooter()).getUniqueId());
        if (tttPlayer == null || !tttPlayer.hasShopItem(DetectiveItems.HOOK)) return;
        if (event.getEntity() instanceof Player) {
            Vector vec = tttPlayer.getApiPlayer().getPlayer().getLocation().toVector().subtract(event.getEntity().getLocation().toVector()).normalize();
            vec.setY(1.5);
            event.getEntity().setVelocity(vec);
            ((Player) event.getEntity()).damage(1, tttPlayer.getApiPlayer().getPlayer());
        } else if(event.getHitBlock() != null) {
            Vector vec = event.getHitBlock().getLocation().toVector().subtract(tttPlayer.getApiPlayer().getPlayer().getLocation().toVector()).normalize();
            vec.setY(1.75);
            tttPlayer.getApiPlayer().getPlayer().setVelocity(vec);
        }
    }
}
