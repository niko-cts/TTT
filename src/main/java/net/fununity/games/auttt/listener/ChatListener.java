package net.fununity.games.auttt.listener;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.main.api.common.util.SpecialChars;
import net.fununity.mgs.gamestates.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * The chat listener class is for changing the chat behavior for the game.
 * @author Niko
 * @since 1.1
 */
public class ChatListener implements Listener {

    private static final String FORMAT = "<role><name> " + ChatColor.DARK_GRAY + SpecialChars.DOUBLE_ARROW_RIGHT + ChatColor.GRAY + " <message>";

    /**
     * Will be called, when a player chats.
     * Will change the format of the chat for all participants.
     * @param event AsyncPlayerChatEvent - the event that has been triggerd.
     * @since 1.1
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (GameLogic.getInstance().getTTTPlayers().isEmpty() || GameManager.getInstance().isSpectator(event.getPlayer())) return;
        event.setCancelled(true);
        TTTPlayer player = GameLogic.getInstance().getTTTPlayer(event.getPlayer().getUniqueId());

        String message = FORMAT.replace("<name>", event.getPlayer().getName()).replace("<message>", event.getMessage());

        for (Player on : Bukkit.getOnlinePlayers()) {
            TTTPlayer tttOn = GameLogic.getInstance().getTTTPlayer(on.getUniqueId());
            on.sendMessage(message.replace("<role>",
                    player.getRole() == Role.DETECTIVE || tttOn == null || tttOn.getRole() == Role.TRAITOR ?
                            player.getRole().getColor() + "": Role.INNOCENT.getColor()+""));
        }
    }

}
