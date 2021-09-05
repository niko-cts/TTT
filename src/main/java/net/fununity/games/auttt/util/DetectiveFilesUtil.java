package net.fununity.games.auttt.util;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.detectives.DetectiveItems;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Material;

import java.util.*;

public class DetectiveFilesUtil {

    private DetectiveFilesUtil() {
        throw new UnsupportedOperationException("DetectiveFilesUtil is a utility class.");
    }

    private static final Map<UUID, List<TTTPlayer>> SCANNED_FILES = new HashMap<>();

    public static void openFiles(TTTPlayer tttPlayer) {
        APIPlayer player = tttPlayer.getApiPlayer();
        List<TTTPlayer> analyzedCases = SCANNED_FILES.getOrDefault(player.getUniqueId(), new ArrayList<>());

        boolean superIdent = tttPlayer.hasShopItem(DetectiveItems.SUPER_IDENT);

        Language lang = player.getLanguage();
        ItemBuilder itemBuilder = new ItemBuilder(Material.WRITTEN_BOOK).addPage(lang.getTranslation(TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_START));
        for (TTTPlayer analyzedCase : analyzedCases) {
            itemBuilder.addPage(lang.getTranslation(superIdent ?
                            TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_CASE_SUPER : TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_CASE_NORMAL,
                    Arrays.asList("${name}", "${role}"), Arrays.asList(analyzedCase.getColoredName(), analyzedCase.getRole().getColoredName())));
        }

        player.openBook(itemBuilder.craft());
    }

    public static void analyzed(UUID uuid, TTTPlayer tttPlayer) {
        List<TTTPlayer> analyzedCases = SCANNED_FILES.getOrDefault(uuid, new ArrayList<>());
        analyzedCases.add(tttPlayer);
        SCANNED_FILES.put(uuid, analyzedCases);
    }

}
