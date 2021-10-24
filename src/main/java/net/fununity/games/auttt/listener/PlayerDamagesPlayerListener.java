package net.fununity.games.auttt.listener;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.UUID;

public class PlayerDamagesPlayerListener implements Listener {

    @EventHandler
    public void onEntityDamagesEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || (!(event.getDamager() instanceof Player) && !(event.getDamager() instanceof Arrow))) return;
        if (GameManager.getInstance().isSpectator((Player) event.getEntity())) return;
        if (GameManager.getInstance().getCurrentGameState() != GameState.INGAME) return;
        TTTPlayer damagedTTT = GameLogic.getInstance().getTTTPlayer(event.getEntity().getUniqueId());
        if (damagedTTT == null || damagedTTT.getRole() != Role.TRAITOR) return;

        UUID damager;

        if (event.getDamager() instanceof Arrow) {
            ProjectileSource shooter = ((Arrow) event.getDamager()).getShooter();
            if (shooter instanceof Player) {
                damager = event.getDamager().getUniqueId();
            } else
                return;
        } else if(event.getDamager() instanceof Player) {
            damager = event.getDamager().getUniqueId();
        } else
            return;

        TTTPlayer damagerTTT = GameLogic.getInstance().getTTTPlayer(damager);

        if (damagerTTT != null && damagerTTT.getRole() == Role.TRAITOR)
            event.setDamage(0);
    }

}
