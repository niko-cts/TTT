package net.fununity.games.auttt.listener;

import net.fununity.games.auttt.rooms.RoomsManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (event.getHitBlock() == null || !(event.getEntity() instanceof Arrow) || !(event.getEntity().getShooter() instanceof Player)) return;
        RoomsManager.getInstance().checkForTrap((Player) event.getEntity().getShooter(), event.getHitBlock().getLocation());
    }

}
