package net.fununity.games.auttt.listener;

import net.fununity.games.auttt.util.TTTScoreboard;
import net.fununity.main.api.event.player.LanguageChangeEvent;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Event listener for language changing events.
 * @author Niko
 * @since 1.1
 */
public class LanguageChangeListener implements Listener {

    /**
     * Will be called, when a player changes language.
     * @param event LanguageChangeEvent - the called event.
     * @since 1.1
     */
    @EventHandler
    public void onLanguage(LanguageChangeEvent event) {
        if (GameManager.getInstance().getCurrentGameState() == GameState.INGAME)
            TTTScoreboard.updateScoreboard(event.getAPIPlayer());
    }

}
