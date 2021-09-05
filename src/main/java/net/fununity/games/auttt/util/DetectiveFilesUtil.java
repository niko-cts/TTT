package net.fununity.games.auttt.util;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.detectives.DetectiveItems;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DetectiveFilesUtil {

    private DetectiveFilesUtil() {
        throw new UnsupportedOperationException("DetectiveFilesUtil is a utility class.");
    }

    private static final Set<TTTPlayer> SCANNED_FILES = new HashSet<>();

    public static void openFiles(TTTPlayer tttPlayer) {
        APIPlayer player = tttPlayer.getApiPlayer();

        boolean superIdent = tttPlayer.hasShopItem(DetectiveItems.SUPER_IDENT);

        Language lang = player.getLanguage();
        ItemBuilder itemBuilder = new ItemBuilder(Material.WRITTEN_BOOK).addPage(lang.getTranslation(TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_START));
        for (TTTPlayer analyzedCase : new ArrayList<>(SCANNED_FILES)) {
            itemBuilder.addPage(lang.getTranslation(superIdent ?
                            TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_CASE_SUPER : TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_CASE_NORMAL,
                    Arrays.asList("${name}", "${role}"), Arrays.asList(analyzedCase.getColoredName(), analyzedCase.getRole().getColoredName())));
        }

        player.openBook(itemBuilder.craft());
    }

    public static void analyzed(TTTPlayer tttPlayer) {
        SCANNED_FILES.add(tttPlayer);
    }

}
