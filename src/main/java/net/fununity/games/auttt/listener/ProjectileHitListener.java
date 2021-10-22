package net.fununity.games.auttt.listener;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTTPlayer;
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
            return;
        }
        if (!(event.getHitEntity() instanceof Player)) return;

        TTTPlayer hit = GameLogic.getInstance().getTTTPlayer(event.getHitEntity().getUniqueId());
        TTTPlayer shooter = GameLogic.getInstance().getTTTPlayer(((Player) event.getEntity().getShooter()).getUniqueId());
        if (hit != null && hit.getRole() == Role.TRAITOR && shooter != null && shooter.getRole() == Role.TRAITOR) {
            ((Arrow) event.getEntity()).spigot().setDamage(0);
        }
    }

}
