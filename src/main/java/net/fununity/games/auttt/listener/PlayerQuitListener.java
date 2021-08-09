package net.fununity.games.auttt.listener;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.gui.JokerShopGUI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (GameLogic.getInstance().getTraitorJoker().contains(uuid)) {
            GameLogic.getInstance().getTraitorJoker().remove(uuid);
            JokerShopGUI.payback(uuid, JokerShopGUI.JOKER_TRAITOR);
        }
        if (GameLogic.getInstance().getDetectiveJoker().contains(uuid)) {
            GameLogic.getInstance().getDetectiveJoker().remove(uuid);
            JokerShopGUI.payback(uuid, JokerShopGUI.JOKER_DETECTIVE);
        }
    }

}
