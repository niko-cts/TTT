package net.fununity.games.auttt.shop;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class GeneralShopListener implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!event.getEntity().hasMetadata("ttt-oneshot")) return;
        if (event.getHitEntity() != null && event.getHitEntity() instanceof Player)
            ((Player) event.getHitEntity()).damage(30);
    }

}
