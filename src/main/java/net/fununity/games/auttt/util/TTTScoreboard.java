package net.fununity.games.auttt.util;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.player.TTTPlayer;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.common.util.SpecialChars;
import net.fununity.main.api.minigames.stats.minigames.StatType;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.mgs.Minigame;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import net.fununity.mgs.language.Constants;
import net.fununity.mgs.stats.PlayerStatsManager;
import net.fununity.misc.translationhandler.translations.Language;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TTTScoreboard {

    private TTTScoreboard() {
        throw new UnsupportedOperationException("TTTScoreboard is a uitility class.");
    }

    private static final String OBJ_NAME = "ttt-scoreboard";
    private static final String LINE_COL = "§8» ";

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
                game.getPlayers().size() : (int) (game.getPlayers().size() + game.getTTTPlayers().stream().filter(t -> !t.isAlive() && !t.isFound()).count());

        obj.getScore(LINE_COL + "§a" + playerSize + "§7/§a" + game.getStartedPlayerAmount()).setScore(7);

        obj.getScore("§a  ").setScore(6);
        obj.getScore("§7" + language.getTranslation(StatType.KILLS.getTranslationKey())
                .replace(": §e" + StatType.KILLS.getPlaceHolder(), "")).setScore(5);
        obj.getScore(LINE_COL + "§e" + PlayerStatsManager.getKills(player.getApiPlayer().getUniqueId()) + " §c§d").setScore(4);


        long foundTraitors = game.getTTTPlayerByRole(Role.TRAITOR).stream().filter(t -> !t.isAlive() && t.isFound()).count();
        obj.getScore(" §f ").setScore(3);
        obj.getScore(language.getTranslation(TranslationKeys.SCOREBOARD_FOUND_TRAITORS)).setScore(2);
        obj.getScore(LINE_COL + "§e" + foundTraitors + "§8").setScore(1);
    }


    /**
     * Updates the tab list for the player.
     * Will show all player their exact rank.
     * Will be called in {@link APIPlayer}, when player permission and party have been loaded
     * @param player Player - Player to update
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
            Team onlineGetsPlayerTeam = getPlayerTeam(player, onlineBoard, partyTogether, online.getRole() == Role.TRAITOR || GameLogic.getInstance().gameManager.isSpectator(online.getApiPlayer().getPlayer()));
            onlineGetsPlayerTeam.addEntry(apiPlayer.getPlayer().getName());

            // online to player board
            Team playerGetsOnlineTeam = getPlayerTeam(online, playerBoard, partyTogether, player.getRole() == Role.TRAITOR || GameLogic.getInstance().gameManager.isSpectator(player.getApiPlayer().getPlayer()));
            playerGetsOnlineTeam.addEntry(online.getApiPlayer().getPlayer().getName());
        }

        // player to player board
        Team playerGetsPlayerTeam = getPlayerTeam(player, playerBoard, apiPlayer.getPartyOwner() != null, true);
        playerGetsPlayerTeam.addEntry(apiPlayer.getPlayer().getName());

        updateScoreboard(player);
    }

    /**
     * Get the team of the player, with suffix and prefix.
     * @param tttPlayer {@link TTTPlayer} - Player to get the team from
     * @param board  Scoreboard - Board to add the team
     * @param party  boolean - Player is in party
     * @return Team - Created Team
     */
    private static Team getPlayerTeam(TTTPlayer tttPlayer, Scoreboard board, boolean party, boolean vision) {
        String teamPriority;
        String prefix;

        if (tttPlayer.isFound() || // Player was found
                (!tttPlayer.isAlive() && // player dead
                        (GameManager.getInstance().getCurrentGameState() == GameState.ENDING || // ending phase
                                vision))) { // online is traitor
            prefix = "§4§l" + SpecialChars.CHROSS + " " + tttPlayer.getRole().getColor();
            teamPriority = "ZZ" + tttPlayer.getRole().ordinal();
        } else if (tttPlayer.getRole() == Role.DETECTIVE ||  // Player is detective
                GameManager.getInstance().getCurrentGameState() == GameState.ENDING || // ending phase
                vision) {
            prefix = tttPlayer.getRole().getColor() + "";
            teamPriority = tttPlayer.getRole().ordinal() + "";
        } else {
            prefix = ChatColor.YELLOW + "";
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

    public static void playerDied(TTTPlayer tttPlayer) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) tttPlayer.getApiPlayer().getPlayer()).getHandle());

        for (APIPlayer onlinePlayer : FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers())
            onlinePlayer.sendPacket(packet);
    }


    // Note: Packet wise changing tab list
    /*private static final MinecraftServer NMS_SERVER = ((CraftServer) TTT.getInstance().getServer()).getServer();

    public static void updateTablist(TTTPlayer tttPlayer) {
        Player player = tttPlayer.getApiPlayer().getPlayer();
        WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();

        for (APIPlayer onlinePlayer : FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers()) {
            TTTPlayer onlineTTT = GameLogic.getInstance().getTTTPlayer(onlinePlayer.getUniqueId());

            String name;
            if (tttPlayer.isFound() || // Player was found
                    (!tttPlayer.isAlive() && // player dead
                            (GameManager.getInstance().getCurrentGameState() == GameState.ENDING || // ending phase
                                    GameLogic.getInstance().gameManager.isSpectator(onlinePlayer.getPlayer()) || // online is spectator
                                    onlineTTT.getRole() == Role.TRAITOR))) // online is traitor
                name = "§z" + tttPlayer.getRole().getColor() + "" + ChatColor.STRIKETHROUGH + player.getName();
            else
            if (tttPlayer.getRole() == Role.DETECTIVE ||  // Player is detective
                    GameManager.getInstance().getCurrentGameState() == GameState.ENDING || // ending phase
                    GameLogic.getInstance().gameManager.isSpectator(onlinePlayer.getPlayer()) || // online player is spectator
                    onlineTTT.getRole() == Role.TRAITOR) // online player is traitor
                name = tttPlayer.getColoredName();
            else
                name = ChatColor.YELLOW + player.getName();

            EntityPlayer ePlayer = new EntityPlayer(NMS_SERVER, nmsWorld, new GameProfile(player.getUniqueId(), name.substring(0, Math.min(16, name.length()))), new PlayerInteractManager(nmsWorld));

            onlinePlayer.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ePlayer));

            Bukkit.getScheduler().runTaskLater(TTT.getInstance(), () ->
                    onlinePlayer.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ePlayer)), 1L);
        }
    }*/

}
