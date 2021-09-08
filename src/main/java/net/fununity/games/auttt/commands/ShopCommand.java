package net.fununity.games.auttt.commands;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.gui.ShopGUI;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.ShopItems;
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
    public void onCommand(APIPlayer apiPlayer, String[] args) {
        if (GameLogic.getInstance().getTTTPlayers().isEmpty() || GameManager.getInstance().getCurrentGameState() != GameState.INGAME) {
            apiPlayer.sendMessage(MessagePrefix.ERROR, TranslationKeys.TTT_COMMAND_SHOP_NOTAVAILABLE);
            return;
        }
        if (args.length == 0) {
            ShopGUI.open(GameLogic.getInstance().getTTTPlayer(apiPlayer.getUniqueId()));
            return;
        }

        int id;
        try {
            id = Integer.parseInt(args[0]) - 1;
        } catch (NumberFormatException exception) {
            sendCommandUsage(apiPlayer);
            return;
        }

        TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(apiPlayer.getUniqueId());
        ShopItems[] shopItems = tttPlayer.getRole().getShopItems();

        if (id < 0 || id >= shopItems.length) {
            apiPlayer.sendMessage(MessagePrefix.ERROR, TranslationKeys.TTT_COMMAND_SHOP_NOTINRANGE, "${max}", "" + shopItems.length);
            return;
        }

        ShopGUI.tryToBuyItem(tttPlayer, shopItems[id]);

    }

    @Override
    public void onConsole(CommandSender commandSender, String[] strings) {
        // not needed
    }
}
