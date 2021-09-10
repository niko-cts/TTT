package net.fununity.games.auttt.commands;

import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.command.handler.APICommand;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.player.APIPlayer;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

/**
 * Command class to give the player information about the coins.
 * @author Niko
 * @since 1.0
 */
public class CoinsInfoCommand extends APICommand {

    public CoinsInfoCommand() {
        super("coinsinfo", "", TranslationKeys.TTT_COMMAND_COINSINFO_USAGE, TranslationKeys.TTT_COMMAND_COINSINFO_DESCRIPTION, "infocoins", "coinshelp", "tttcoins");
    }

    @Override
    public void onCommand(APIPlayer apiPlayer, String[] strings) {
        apiPlayer.openBook(new ItemBuilder(Material.WRITTEN_BOOK)
                .addPage(apiPlayer.getLanguage().getTranslation(TranslationKeys.TTT_COMMAND_COINSINFO_BOOK).split(";")).craft());
    }

    @Override
    public void onConsole(CommandSender commandSender, String[] strings) {
        // not needed
    }
}
