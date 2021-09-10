package net.fununity.games.auttt.commands;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.gui.JokerShopGUI;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.command.handler.APICommand;
import net.fununity.main.api.messages.MessagePrefix;
import net.fununity.main.api.player.APIPlayer;
import org.bukkit.command.CommandSender;

/**
 * Command class to allow players open the joker gui even after {@link net.fununity.mgs.gamestates.GameState#LOBBY}.
 * @author Niko
 * @since 1.1
 */
public class JokerCommand extends APICommand {

    public JokerCommand() {
        super("joker", "", TranslationKeys.TTT_COMMAND_JOKER_USAGE, TranslationKeys.TTT_COMMAND_JOKER_DESCRIPTION, "traitor", "detective", "jokershop");
    }

    @Override
    public void onCommand(APIPlayer apiPlayer, String[] strings) {
        if (!GameLogic.getInstance().getTTTPlayers().isEmpty()) {
            apiPlayer.sendMessage(MessagePrefix.ERROR, TranslationKeys.TTT_COMMAND_JOKER_NOTAVAILABLE);
            return;
        }
        JokerShopGUI.open(apiPlayer);
    }

    @Override
    public void onConsole(CommandSender commandSender, String[] strings) {
        // not needed
    }
}
