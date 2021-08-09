package net.fununity.games.auttt.listener;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.player.TTTPlayer;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamagesPlayerListener implements Listener {

    @EventHandler
    public void onEntityDamagesEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) return;
        if (GameManager.getInstance().isSpectator((Player) event.getEntity())) return;
        if (GameManager.getInstance().getCurrentGameState() != GameState.INGAME) return;
        TTTPlayer damagedTTT = GameLogic.getInstance().getTTTPlayer(event.getEntity().getUniqueId());
        TTTPlayer damagerTTT = GameLogic.getInstance().getTTTPlayer(event.getDamager().getUniqueId());
        if (damagedTTT == null || damagerTTT == null) return;
        if (damagedTTT.getRole() == Role.TRAITOR && damagerTTT.getRole() == Role.TRAITOR)
            event.setDamage(0);
    }


}
