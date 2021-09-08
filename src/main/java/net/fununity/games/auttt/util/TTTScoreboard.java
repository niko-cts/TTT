package net.fununity.games.auttt.util;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.common.util.SpecialChars;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.mgs.Minigame;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import net.fununity.mgs.language.Constants;
import net.fununity.misc.translationhandler.translations.Language;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * The scoreboard utility class for tab and side board.
 * @author Niko
 * @since 0.0.1
 */
public class TTTScoreboard {

    private TTTScoreboard() {
        throw new UnsupportedOperationException("TTTScoreboard is a utility class.");
    }

    private static final String OBJ_NAME = "ttt-scoreboard";
    private static final String LINE_COL = "§8» ";

    /**
     * Updates the ttt scoreboard for the given player.
     * @param player TTTPlayer - the ttt player.
     * @since 0.0.1
     */
    public static void updateScoreboard(TTTPlayer player) {
        Scoreboard board = player.getApiPlayer().getPlayer().getScoreboard();
        GameLogic game = GameLogic.getInstance();

        if (board.getObjective(OBJ_NAME) != null)
            board.getObjective(OBJ_NAME).unregister();

        Objective obj = board.registerNewObjective(OBJ_NAME, OBJ_NAME);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(FunUnityAPI.getPrefix());
        obj.getScore(Minigame.getInstance().getName()).setScore(42);
        obj.getScore("§a ").setScore(12);

        Language language = player.getApiPlayer().getLanguage();
        obj.getScore(language.getTranslation(TranslationKeys.SCOREBOARD_YOUR_ROLE)).setScore(11);
        obj.getScore(LINE_COL + player.getRole().getColoredName()).setScore(10);

        obj.getScore(" §5").setScore(9);

        obj.getScore(language.getTranslation(Constants.SCOREBOARD_PLAYER)).setScore(8);

        int playerSize = player.getRole() == Role.TRAITOR || game.gameManager.isSpectator(player.getApiPlayer().getPlayer()) ?
                game.getPlayers().size() : (int) (game.getPlayers().stream().filter(p -> !game.gameManager.isSpectator(p)).count() +
                game.getTTTPlayers().stream().filter(t -> t.isDead() && !t.isFound()).count());

        obj.getScore(LINE_COL + "§a" + playerSize + "§7/§a" + game.getStartedPlayerAmount()).setScore(7);

        obj.getScore("§a  ").setScore(6);


        long foundTraitors = game.getTTTPlayerByRole(Role.TRAITOR).stream().filter(t -> t.isDead() && t.isFound()).count();
        obj.getScore(" §f ").setScore(5);
        obj.getScore(language.getTranslation(TranslationKeys.SCOREBOARD_FOUND_TRAITORS)).setScore(4);
        obj.getScore(LINE_COL + "§e" + foundTraitors + "§d").setScore(3);

        obj.getScore(" §d ").setScore(2);
        obj.getScore(language.getTranslation(TranslationKeys.SCOREBOARD_COINS)).setScore(1);
        obj.getScore(LINE_COL + "§e" + player.getCoins() + "§8").setScore(0);
    }


    /**
     * Updates the tab list for the player.
     * Will show the given player all current ttt player with there status.
     * @param player TTTPlayer - Player to update
     */
    public static void updateTablist(TTTPlayer player) {
        APIPlayer apiPlayer = player.getApiPlayer();
        Scoreboard playerBoard = Bukkit.getScoreboardManager().getNewScoreboard();
        player.getApiPlayer().getPlayer().setScoreboard(playerBoard);

        for (TTTPlayer online : GameLogic.getInstance().getTTTPlayers()) {
            Scoreboard onlineBoard = online.getApiPlayer().getPlayer().getScoreboard();

            // online is same as player or online has no scoreboard
            if (online.getApiPlayer().getUniqueId().equals(apiPlayer.getUniqueId()) || onlineBoard == null)
                continue;

            // is player in party with online
            boolean partyTogether = apiPlayer.getPartyOwner() != null && apiPlayer.getPartyOwner().equals(online.getApiPlayer().getPartyOwner());

            // player to online board
            Team onlineGetsPlayerTeam = getPlayerTeam(player, onlineBoard, partyTogether, online.getRole() == Role.TRAITOR || GameManager.getInstance().isSpectator(online.getApiPlayer().getPlayer()));
            onlineGetsPlayerTeam.addEntry(apiPlayer.getPlayer().getName());

            // online to player board
            Team playerGetsOnlineTeam = getPlayerTeam(online, playerBoard, partyTogether, player.getRole() == Role.TRAITOR || GameManager.getInstance().isSpectator(player.getApiPlayer().getPlayer()));
            playerGetsOnlineTeam.addEntry(online.getApiPlayer().getPlayer().getName());
        }

        // player to player board
        Team playerGetsPlayerTeam = getPlayerTeam(player, playerBoard, apiPlayer.getPartyOwner() != null, true);
        playerGetsPlayerTeam.addEntry(apiPlayer.getPlayer().getName());

        updateScoreboard(player);
    }

    /**
     * Creates a team with prefix and suffix for the ttt tablist.
     * @param tttPlayer {@link TTTPlayer} - Player to create the team from
     * @param board  Scoreboard - Board to create the team on
     * @param party  boolean - Team gets the party suffix.
     * @param trueVision boolean - gets the real prefix, according to the current ttt status.
     * @return Team - the created scoreboard team.
     * @since 0.0.1
     */
    private static Team getPlayerTeam(TTTPlayer tttPlayer, Scoreboard board, boolean party, boolean trueVision) {
        String teamPriority;
        String prefix;

        if (tttPlayer.isFound() || // Player was found
                (tttPlayer.isDead() && // player dead
                        (GameManager.getInstance().getCurrentGameState() == GameState.ENDING || // ending phase
                                trueVision))) { // online is traitor
            prefix = "§4§l" + SpecialChars.CHROSS + " " + tttPlayer.getRole().getColor();
            teamPriority = "ZZ" + tttPlayer.getRole().ordinal();
        } else if (tttPlayer.getRole() == Role.DETECTIVE ||  // Player is detective
                GameManager.getInstance().getCurrentGameState() == GameState.ENDING || // ending phase
                trueVision) {
            prefix = tttPlayer.getRole().getColor() + "";
            teamPriority = tttPlayer.getRole().ordinal() + "";
        } else {
            prefix = Role.INNOCENT.getColor() + "";
            teamPriority = Role.INNOCENT.ordinal() + "";
        }


        String teamName = teamPriority + tttPlayer.getApiPlayer().getUniqueId().toString().substring(0, 16 - teamPriority.length());

        Team team = board.getTeam(teamName);
        if (team != null) {
            team.unregister();
        }

        team = board.registerNewTeam(teamName);

        if (party) {
            team.setSuffix(" §d" + SpecialChars.STAR);
        }

        team.setPrefix(prefix);
        return team;
    }

    /**
     * Player dies in game and should be added on the tab list.
     * @param player Player - the player who died.
     * @since 0.0.1
     */
    public static void reAddPlayer(Player player) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) player).getHandle());

        for (APIPlayer onlinePlayer : FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers())
            onlinePlayer.sendPacket(packet);
    }

}
