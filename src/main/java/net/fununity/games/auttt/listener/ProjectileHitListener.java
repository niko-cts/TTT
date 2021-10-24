package net.fununity.games.auttt.listener;

import net.fununity.games.auttt.rooms.RoomsManager;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (GameManager.getInstance().getCurrentGameState() != GameState.INGAME) return;
        if (!(event.getEntity() instanceof Arrow) || !(event.getEntity().getShooter() instanceof Player)) return;
        if (event.getHitBlock() != null) {
            RoomsManager.getInstance().checkForTrap((Player) event.getEntity().getShooter(), event.getHitBlock().getLocation());
        }
    }

}
