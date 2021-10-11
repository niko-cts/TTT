package net.fununity.games.auttt.listener;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.util.TTTScoreboard;
import net.fununity.main.api.event.player.APIPlayerJoinEvent;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(APIPlayerJoinEvent event) {
        if (GameManager.getInstance().getCurrentGameState() != GameState.INGAME) return;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.hidePlayer(TTT.getInstance(), event.getAPIPlayer().getPlayer());
        }
        TTTScoreboard.updateTablist(event.getAPIPlayer());
    }

}
