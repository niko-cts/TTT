package net.fununity.games.auttt.listener;

import net.fununity.games.auttt.TTT;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (GameManager.getInstance().getCurrentGameState() != GameState.INGAME) return;
        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), () -> {
            if (!event.getPlayer().isOnline()) return;
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.hidePlayer(TTT.getInstance(), event.getPlayer());
            }
        }, 10L);
    }

}
