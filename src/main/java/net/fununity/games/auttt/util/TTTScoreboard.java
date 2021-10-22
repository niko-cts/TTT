package net.fununity.games.auttt.util;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.common.util.RandomUtil;
import net.fununity.main.api.common.util.SpecialChars;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.mgs.Minigame;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import net.fununity.mgs.language.Constants;
import net.fununity.misc.translationhandler.translations.Language;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
     * @param apiPlayer APIPlayer - the ttt player.
     * @since 0.0.1
     */
    public static void updateScoreboard(APIPlayer apiPlayer) {
        Scoreboard board = apiPlayer.getPlayer().getScoreboard();
        GameLogic game = GameLogic.getInstance();
        TTTPlayer tttPlayer = game.getTTTPlayer(apiPlayer.getUniqueId());

        if (board.getObjective(OBJ_NAME) != null)
            board.getObjective(OBJ_NAME).unregister();

        Objective obj = board.registerNewObjective(OBJ_NAME, OBJ_NAME);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(FunUnityAPI.getPrefix());
        obj.getScore(Minigame.getInstance().getName()).setScore(42);
        obj.getScore("§a ").setScore(11);

        Language language = apiPlayer.getLanguage();
        if (tttPlayer != null) {
            obj.getScore(language.getTranslation(TranslationKeys.SCOREBOARD_YOUR_ROLE)).setScore(10);
            obj.getScore(LINE_COL + tttPlayer.getRole().getColoredName()).setScore(9);

            obj.getScore(" §5").setScore(8);
        }

        obj.getScore(language.getTranslation(Constants.SCOREBOARD_PLAYER)).setScore(7);

        int playerSize = tttPlayer != null && tttPlayer.getRole() == Role.TRAITOR || game.gameManager.isSpectator(apiPlayer.getPlayer()) ?
                game.getPlayers().size() : (int) (game.getPlayers().stream().filter(p -> !game.gameManager.isSpectator(p)).count() +
                game.getTTTPlayers().stream().filter(t -> t.isDead() && !t.isFound()).count());

        obj.getScore(LINE_COL + "§a" + playerSize + "§7/§a" + game.getStartedPlayerAmount()).setScore(6);

        long foundTraitors = game.getTTTPlayerByRole(Role.TRAITOR).stream().filter(t -> t.isDead() && t.isFound()).count();
        obj.getScore(" §f ").setScore(5);
        obj.getScore(language.getTranslation(TranslationKeys.SCOREBOARD_FOUND_TRAITORS)).setScore(4);
        obj.getScore(LINE_COL + "§e" + foundTraitors + "§d").setScore(3);

        if (tttPlayer != null) {
            obj.getScore(" §d ").setScore(2);
            obj.getScore(language.getTranslation(TranslationKeys.SCOREBOARD_COINS)).setScore(1);
            obj.getScore(LINE_COL + "§e" + tttPlayer.getCoins() + "§8").setScore(0);
        }
    }

    /**
     * Updates the tab list for the player.
     * Will show the given player all current ttt player with there status.
     * @param apiPlayer APIPlayer - Player to update
     */
    public static void updateTablist(APIPlayer apiPlayer) {
        Scoreboard playerBoard = Bukkit.getScoreboardManager().getNewScoreboard();
        apiPlayer.getPlayer().setScoreboard(playerBoard);

        TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(apiPlayer.getUniqueId());
        for (APIPlayer online : FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers()) {
            Scoreboard onlineBoard = online.getPlayer().getScoreboard();

            // online is same as player or online has no scoreboard
            if (online.getUniqueId().equals(apiPlayer.getUniqueId()) || onlineBoard == null)
                continue;

            // is player in party with online
            boolean partyTogether = apiPlayer.getPartyOwner() != null && apiPlayer.getPartyOwner().equals(online.getPartyOwner());

            TTTPlayer onlineTTT = GameLogic.getInstance().getTTTPlayer(online.getUniqueId());
            // player to online board
            if (tttPlayer != null) {
                Team onlineGetsPlayerTeam = getPlayerTeam(tttPlayer, onlineBoard, partyTogether, onlineTTT != null && onlineTTT.getRole() == Role.TRAITOR);
                onlineGetsPlayerTeam.addEntry(apiPlayer.getPlayer().getName());
            }

            // online to player board
            Team playerGetsOnlineTeam = getPlayerTeam(onlineTTT, playerBoard, partyTogether, tttPlayer != null && tttPlayer.getRole() == Role.TRAITOR);
            playerGetsOnlineTeam.addEntry(online.getPlayer().getName());
        }

        // player to player board
        Team playerGetsPlayerTeam = getPlayerTeam(tttPlayer, playerBoard, apiPlayer.getPartyOwner() != null, true);
        playerGetsPlayerTeam.addEntry(apiPlayer.getPlayer().getName());

        updateScoreboard(apiPlayer);
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


        if (tttPlayer == null) {
            prefix = ChatColor.GRAY + "";
            teamPriority = "ZZZ";
        } else if (tttPlayer.isFound() || // Player was found
                (tttPlayer.isDead() && (trueVision || // player dead
                        GameManager.getInstance().getCurrentGameState() == GameState.ENDING))) // ending phase
            { // online is traitor
            prefix = ChatColor.DARK_RED + "" + ChatColor.BOLD + SpecialChars.CHROSS + " " + tttPlayer.getRole().getColor();
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


        String teamName = teamPriority + (tttPlayer != null ? tttPlayer.getApiPlayer().getUniqueId().toString().substring(0, 16 - teamPriority.length()) : "zzz" + RandomUtil.getRandomString(5));

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
