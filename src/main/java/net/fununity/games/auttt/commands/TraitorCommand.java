package net.fununity.games.auttt.commands;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.command.handler.APICommand;
import net.fununity.main.api.common.util.SpecialChars;
import net.fununity.main.api.messages.MessagePrefix;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.mgs.gamestates.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TraitorCommand extends APICommand {

    private static final String FORMAT = Role.TRAITOR.getColoredName() + " ${name} " + ChatColor.DARK_GRAY + SpecialChars.DOUBLE_ARROW_RIGHT + ChatColor.GRAY + " ${message}";

    public TraitorCommand() {
        super("traitor", "", TranslationKeys.TTT_COMMAND_TRAITORCHAT_USAGE, TranslationKeys.TTT_COMMAND_TRAITORCHAT_DESCRIPTION, "tchat", "t", "traitorchat");
    }

    @Override
    public void onCommand(APIPlayer apiPlayer, String[] args) {
        if (args.length == 0) {
            sendCommandUsage(apiPlayer);
            return;
        }
        TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(apiPlayer.getUniqueId());
        if (GameManager.getInstance().isSpectator(apiPlayer.getPlayer()) ||
              tttPlayer == null || tttPlayer.getRole() != Role.TRAITOR) {
           apiPlayer.sendMessage(MessagePrefix.ERROR, TranslationKeys.TTT_COMMAND_TRAITORCHAT_NOTALLOWED);
           return;
        }

        StringBuilder text = new StringBuilder();
        for (String s : args)
            text.append(s).append(" ");

        String message = FORMAT.replace("${name}", tttPlayer.getColoredName()).replace("${message}", text);
        for (TTTPlayer online : GameLogic.getInstance().getTTTPlayerByRole(Role.TRAITOR))
            online.getApiPlayer().sendRawMessage(message);
    }

    @Override
    public void onConsole(CommandSender commandSender, String[] strings) {
        // not needed
    }
}
