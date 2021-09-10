package net.fununity.games.auttt.util;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.actionbar.ActionbarMessage;
import net.fununity.main.api.minigames.stats.minigames.StatType;
import net.fununity.mgs.stats.PlayerStatsManager;

import java.util.Arrays;

/**
 * This class manages stats and coins of the player.
 * @author Niko
 * @since 1.0
 */
public class CoinsUtil {

    private CoinsUtil() {
        throw new UnsupportedOperationException("CoinsUtil is a utility class");
    }

    /**
     * Will be called, when a player killed another player.
     * @param player {@link net.fununity.games.auttt.TTTPlayer} - the player who died.
     * @param killer {@link net.fununity.games.auttt.TTTPlayer} - the player who killed.
     * @since 1.0
     */
    public static void kill(TTTPlayer player, TTTPlayer killer) {
        int addCoins = 0;
        if (killer.getRole() == Role.TRAITOR) {
            if (player.getRole() == Role.INNOCENT)
                addCoins = 1;
            else if (player.getRole() == Role.DETECTIVE)
                addCoins = 3;
             PlayerStatsManager.addStat(killer.getApiPlayer().getUniqueId(), StatType.POINTS, 5);
        } else if (killer.getRole() != Role.TRAITOR) {
            if (player.getRole() == Role.TRAITOR) {
                addCoins = 2;
                PlayerStatsManager.addStat(killer.getApiPlayer().getUniqueId(), StatType.POINTS, 10);
            } else if (player.getRole() == Role.INNOCENT)
                PlayerStatsManager.addStat(killer.getApiPlayer().getUniqueId(), StatType.POINTS, -10);
            else if (player.getRole() == Role.DETECTIVE)
                PlayerStatsManager.addStat(killer.getApiPlayer().getUniqueId(), StatType.POINTS, -20);
        }

        if (addCoins != 0)
            killer.setCoins(killer.getCoins() + addCoins);
        killer.getApiPlayer().sendMessage(TranslationKeys.TTT_GAME_PLAYER_KILLED,
                Arrays.asList("${name}", "${role}", "${coins}"),
                Arrays.asList(player.getColoredName(), player.getRole().getColoredName(), addCoins+""));
    }

    /**
     * Will be called, when the game finishes.
     * @param role Role[] - the roles who won.
     * @since 1.0
     */
    public static void win (Role... role) {
        for (TTTPlayer tttPlayer : GameLogic.getInstance().getTTTPlayerByRole(role)) {
            PlayerStatsManager.addStat(tttPlayer.getApiPlayer().getUniqueId(), StatType.POINTS, 10);
        }
    }

    /**
     * Will be called, when a player found a dead body.
     * @param foundBy {@link net.fununity.games.auttt.TTTPlayer} - the player who found the body.
     * @param tttPlayer {@link net.fununity.games.auttt.TTTPlayer} - the players corpse.
     * @since 1.0
     */
    public static void foundBody(TTTPlayer foundBy, TTTPlayer tttPlayer) {
        int addCoins = 1 + tttPlayer.getCoins() / 2;
        foundBy.setCoins(foundBy.getCoins() + addCoins);
        foundBy.getApiPlayer().sendActionbar(new ActionbarMessage(TranslationKeys.TTT_GAME_PLAYER_RECEIVED_COINS), "${amount}", "" + addCoins);
    }

    /**
     * Gives the player start points.
     * @param tttPlayer {@link net.fununity.games.auttt.TTTPlayer} - the player who should get the coins.
     * @since 1.0
     */
    public static void startCoins(TTTPlayer tttPlayer) {
        tttPlayer.setCoins(tttPlayer.getRole() == Role.INNOCENT ? 10 : 200);
    }
}
