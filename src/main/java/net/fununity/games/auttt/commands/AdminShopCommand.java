package net.fununity.games.auttt.commands;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.gui.ShopGUI;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.command.handler.APICommand;
import net.fununity.main.api.messages.MessagePrefix;
import net.fununity.main.api.player.APIPlayer;
import org.bukkit.command.CommandSender;

/**
 * Command class to force open a shop.
 * @author Niko
 * @since 1.0
 */
public class AdminShopCommand extends APICommand {

    public AdminShopCommand() {
        super("adminshop", "command.adminshop", TranslationKeys.TTT_COMMAND_ADMINSHOP_USAGE, TranslationKeys.TTT_COMMAND_ADMINSHOP_DESCRIPTION);
    }

    @Override
    public void onCommand(APIPlayer apiPlayer, String[] args) {
        if (args.length != 1) {
           sendCommandUsage(apiPlayer);
           return;
        }
        try {
            Role role = Role.valueOf(args[0]);
            TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(apiPlayer.getUniqueId());
            if (tttPlayer != null)
                ShopGUI.open(tttPlayer, role.getShopItems());
            else
                apiPlayer.sendMessage(MessagePrefix.ERROR, TranslationKeys.TTT_COMMAND_SHOP_NOTAVAILABLE);
        } catch (IllegalArgumentException exception) {
            sendCommandUsage(apiPlayer);
        }
    }

    @Override
    public void onConsole(CommandSender commandSender, String[] strings) {
        // not needed
    }
}
