package net.fununity.games.auttt.commands;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.gui.ShopGUI;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.command.handler.APICommand;
import net.fununity.main.api.messages.MessagePrefix;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import org.bukkit.command.CommandSender;

public class ShopCommand extends APICommand {

    public ShopCommand() {
        super("shop", "", TranslationKeys.TTT_COMMAND_SHOP_USAGE, TranslationKeys.TTT_COMMAND_SHOP_DESCRIPTION, "s");
    }

    @Override
    public void onCommand(APIPlayer apiPlayer, String[] strings) {
        if (GameLogic.getInstance().getTTTPlayers().isEmpty() || GameManager.getInstance().getCurrentGameState() != GameState.INGAME) {
            apiPlayer.sendMessage(MessagePrefix.ERROR, TranslationKeys.TTT_COMMAND_SHOP_NOTAVAILABLE);
            return;
        }
        ShopGUI.open(GameLogic.getInstance().getTTTPlayer(apiPlayer.getUniqueId()));
    }

    @Override
    public void onConsole(CommandSender commandSender, String[] strings) {
        // not needed
    }
}
