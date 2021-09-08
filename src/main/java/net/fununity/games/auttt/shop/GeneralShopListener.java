package net.fununity.games.auttt.shop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class GeneralShopListener implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!event.getEntity().hasMetadata("ttt-oneshot")) return;
        if (event.getHitEntity() != null && event.getHitEntity() instanceof Player)
            ((Player) event.getHitEntity()).damage(30);
    }

    @EventHandler
    public void onEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().hasMetadata("ttt-sentry")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntity(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().hasMetadata("ttt-sentry"))
            event.setCancelled(true);
    }
}
