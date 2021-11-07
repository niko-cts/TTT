package net.fununity.games.auttt;

import net.fununity.games.auttt.corpse.PlayerCorpse;
import net.fununity.games.auttt.gui.JokerShopGUI;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.rooms.RoomsManager;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.util.CoinsUtil;
import net.fununity.games.auttt.util.TTTScoreboard;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.common.util.RandomUtil;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.player.BalanceHandler;
import net.fununity.main.api.server.BroadcastHandler;
import net.fununity.main.api.server.ServerSetting;
import net.fununity.mgs.Minigame;
import net.fununity.mgs.gamespecifc.Arena;
import net.fununity.mgs.gamestates.Game;
import net.fununity.mgs.gamestates.GameState;
import net.fununity.mgs.listener.RunningListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * The base game logic class needed for the MinigameSystem.
 * @see Game
 * @author Niko
 * @since 0.0.1
 */
public class GameLogic extends Game {

    private static GameLogic instance;

    private final Queue<UUID> traitorJoker;
    private final Queue<UUID> detectiveJoker;
    private final List<TTTPlayer> tttPlayers;

    /**
     * Instantiates the class and initializes every list.
     * @since 0.0.1
     */
    public GameLogic() {
        instance = this;
        this.traitorJoker = new LinkedList<>();
        this.detectiveJoker = new LinkedList<>();
        this.tttPlayers = new ArrayList<>();
    }

    /**
     * Will be called, when the minigame starts.
     * Gives all players their items and starts the timer for the role selection.
     * @since 0.0.1
     */
    @Override
    public void startMinigame() {
        FunUnityAPI.getInstance().getServerSettings().disable(ServerSetting.FOOD_LEVEL_CHANGE);
        FunUnityAPI.getInstance().getActionbarManager().start();

        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), () -> {
            if (gameManager.getCurrentGameState() != GameState.INGAME) return;

            // ROLES SORTING
            int maximumTraitor = (int) Math.min(TTT.getInstance().getMaxTraitorAmount(), Math.round(TTT.getInstance().getTraitorAmountMultiplier() * getPlayers().size()));
            List<UUID> players = new ArrayList<>();
            getPlayers().forEach(p -> players.add(p.getUniqueId()));

            Collections.shuffle(players);

            addPlayerRole(maximumTraitor, traitorJoker, players, Role.TRAITOR);

            int maximumDetectives = TTT.getInstance().getMinAmountForDetective() <= getPlayers().size() ?
                    (int) Math.round(TTT.getInstance().getDetectiveAmountMultiplier() * getPlayers().size()) : 0;
            addPlayerRole(maximumDetectives, detectiveJoker, players, Role.DETECTIVE);

            ItemBuilder shop = new ItemBuilder(Material.PAPER).setName(TranslationKeys.TTT_GAME_ITEM_SHOP_NAME).setLore(TranslationKeys.TTT_GAME_ITEM_SHOP_LORE);
            ItemBuilder analyzer = new ItemBuilder(Material.STICK).setName(TranslationKeys.TTT_GAME_ITEM_ANALYZER_NAME).setLore(TranslationKeys.TTT_GAME_ITEM_ANALYZER_LORE);
            ItemBuilder files = new ItemBuilder(Material.KNOWLEDGE_BOOK).setName(TranslationKeys.TTT_GAME_ITEM_FILES_NAME).setLore(TranslationKeys.TTT_GAME_ITEM_FILES_LORE);

            for (TTTPlayer detectives : getTTTPlayerByRole(Role.DETECTIVE)) {
                PlayerInventory inv = detectives.getApiPlayer().getPlayer().getInventory();
                if (inv.getItem(8) == null)
                    inv.setItem(8, files.translate(detectives.getApiPlayer().getLanguage()));
                else
                    inv.addItem(files.translate(detectives.getApiPlayer().getLanguage()));
            }

            while (!players.isEmpty()) {
                UUID player = players.get(RandomUtil.getRandomInt(players.size()));
                players.remove(player);
                tttPlayers.add(new TTTPlayer(FunUnityAPI.getInstance().getPlayerHandler().getPlayer(player), Role.INNOCENT));
            }

            JokerShopGUI.payback();

            for (TTTPlayer tttPlayer : tttPlayers) {
                PlayerInventory inv = tttPlayer.getApiPlayer().getPlayer().getInventory();

                if (inv.getItem(7) == null)
                    inv.setItem(7, shop.translate(tttPlayer.getApiPlayer().getLanguage()));
                else
                    inv.addItem(shop.translate(tttPlayer.getApiPlayer().getLanguage()));

                if (inv.getItem(8) == null)
                    inv.setItem(8, analyzer.translate(tttPlayer.getApiPlayer().getLanguage()));
                else
                    inv.addItem(analyzer.translate(tttPlayer.getApiPlayer().getLanguage()));

                CoinsUtil.startCoins(tttPlayer);
                tttPlayer.getApiPlayer().getTitleSender().sendTitle(TranslationKeys.ROLE_CALLOUT_TITLE, "${role}",
                        tttPlayer.getRole().getColoredName() + "", 20 * 5);
                tttPlayer.getApiPlayer().getTitleSender().sendSubtitle(TranslationKeys.ROLE_CALLOUT_SUBTITLE,
                        "${role}",
                        tttPlayer.getRole().getColoredName(),
                        20 * 5);
                tttPlayer.getApiPlayer().sendMessage(TranslationKeys.ROLE_CALLOUT_TEXT,
                        "${role}",
                        tttPlayer.getRole().getColoredName());

            }
            FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers().forEach(TTTScoreboard::updateTablist);
        }, 20L * Minigame.getInstance().getProtectionTime());
        RoomsManager.loadManager(getArena());
    }

    /**
     * Give an amount of players the given role.
     * @param amount int - amount to add to the list.
     * @param preferred Queue<UUID> - Prioritized uuids
     * @param alternatives List<Player> - Alternative Players to add randomly
     * @param role Role - Role to give the player.
     * @since 0.0.1
     */
    private void addPlayerRole(int amount, Queue<UUID> preferred, List<UUID> alternatives, Role role) {
        while (amount > 0) {
            if (alternatives.isEmpty() && preferred.isEmpty()) {
                TTT.getInstance().getLogger().log(Level.WARNING, "There are no more players available to fit in {0}!", role.name());
                return;
            }

            UUID uuid;
            if (preferred.isEmpty()) {
                uuid = alternatives.get(RandomUtil.getRandomInt(alternatives.size()));
            } else {
                uuid = preferred.poll();
            }
            alternatives.remove(uuid);
            if (getTTTPlayer(uuid) != null)
                continue;

            tttPlayers.add(new TTTPlayer(FunUnityAPI.getInstance().getPlayerHandler().getPlayer(uuid), role));
            amount--;
        }
    }

    @Override
    public void joinGame(Player player, boolean spectator) {
        super.joinGame(player, spectator);
        if (spectator) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                onlinePlayer.hidePlayer(TTT.getInstance(), player);
            TTTScoreboard.updateTablist(FunUnityAPI.getInstance().getPlayerHandler().getPlayer(player));
        }
    }

    /**
     * Will be called every second from the mgs.
     * @param i - seconds left
     * @since 0.0.1
     */
    @Override
    public void minigameCountdown(int i) {
        // nothing to do here
    }

    /**
     * Will be called, when the minigame ends.
     * @return List<Player> - All players who won the game
     * @since 0.0.1
     */
    @Override
    public List<Player> endMinigame() {
        long innoAlive = tttPlayers.stream().filter(t -> t.getRole() != Role.TRAITOR && isIngame(t.getApiPlayer().getPlayer())).count();

        List<Player> winner = new ArrayList<>();
        for (TTTPlayer tttPlayer : tttPlayers) {
            if (!tttPlayer.getApiPlayer().getPlayer().isOnline())
                continue;

            if (innoAlive == 0) {
                if (tttPlayer.getRole() == Role.TRAITOR)
                    winner.add(tttPlayer.getApiPlayer().getPlayer());
            } else if (tttPlayer.getRole() != Role.TRAITOR)
                winner.add(tttPlayer.getApiPlayer().getPlayer());
        }
        FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers().forEach(TTTScoreboard::updateTablist);

        CoinsUtil.win(innoAlive == 0 ? Role.TRAITOR : Role.INNOCENT, Role.DETECTIVE);

        BroadcastHandler.broadcastMessage(TranslationKeys.ROLE_WON, "${role}", innoAlive == 0 ? Role.TRAITOR.getColoredName() : Role.INNOCENT.getColoredName());

        if (Minigame.getInstance().getRewardTokens() != 0)
            for (Player player : winner)
                BalanceHandler.getInstance().giveMoney(player.getUniqueId(), Minigame.getInstance().getRewardTokens(), true);

        return winner;
    }

    /**
     * Will be called, when a player dies in minigame.
     * @param player Player - the player who dies.
     * @param event PlayerDeathEvent - the triggerd event.
     * @return boolean - player should respawn. (Always on false)
     * @since 0.0.1
     */
    @Override
    public boolean playerDiesInMinigame(Player player, PlayerDeathEvent event) {
        event.setDroppedExp(0);
        event.setKeepInventory(true);

        TTTPlayer tttPlayer = getTTTPlayer(player.getUniqueId());
        tttPlayer.setAlive(false);

        for (TTTPlayer traitor : getTTTPlayerByRole(Role.TRAITOR)) {
            traitor.getApiPlayer().sendMessage(TranslationKeys.TTT_GAME_PLAYER_DIED, "${name}", tttPlayer.getRole().getColor() + tttPlayer.getApiPlayer().getPlayer().getName());
            traitor.getApiPlayer().playSound(Sound.BLOCK_ANVIL_HIT);
        }

        UUID damager = RunningListener.getLastDamager(player.getUniqueId());
        if (damager != null) {
            TTTPlayer damagerTTT = getTTTPlayer(damager);
            if (damagerTTT != null)
                CoinsUtil.kill(tttPlayer, damagerTTT);
        }

        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), () -> {
            TTTScoreboard.reAddPlayer(tttPlayer.getApiPlayer().getPlayer());
            getTTTPlayerByRole(Role.TRAITOR).forEach(t -> TTTScoreboard.updateScoreboard(t.getApiPlayer()));
            TTTScoreboard.updateScoreboard(tttPlayer.getApiPlayer());
            TTTScoreboard.updateTablist(tttPlayer.getApiPlayer());
        }, 2L);

        new PlayerCorpse(tttPlayer, event);

        player.getInventory().clear();

        // win check:
        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), this::checkForGameEnd, 2L);
        return false;
    }

    /**
     * Will be called, when a player leaves the game.
     * Removes the player from the list and checks for game ending.
     * @param player Player - the player who left.
     * @since 0.0.1
     */
    @Override
    public void playerLeaves(Player player) {
        if (isProtected() || getTTTPlayers().size() == 0) {
            checkForGameEnd();
            return;
        }

        TTTPlayer tttPlayer = getTTTPlayer(player.getUniqueId());
        new ArrayList<>(tttPlayer.getShopItems()).forEach(ShopItem::removeItem);
        tttPlayers.remove(tttPlayer);

        FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers().forEach(TTTScoreboard::updateTablist);

        // end check:
        checkForGameEnd();
    }

    /**
     * Checks for a game ending and calls {@link Game#systemStopGame()}.
     * @since 0.0.1
     */
    private void checkForGameEnd() {
        long traitorsAlive = tttPlayers.stream().filter(t -> t.getRole() == Role.TRAITOR && isIngame(t.getApiPlayer().getPlayer())).count();
        long innoAlive = tttPlayers.stream().filter(t -> t.getRole() != Role.TRAITOR && isIngame(t.getApiPlayer().getPlayer())).count();

        if (traitorsAlive == 0 || innoAlive == 0)
            systemStopGame();
    }

    /**
     * Will be called, when the extra lobby item was used by a player.
     * WIll open the {@link JokerShopGUI}.
     * @param player Player - player who clicked.
     * @since 0.0.1
     */
    @Override
    public void useExtraLobbyItem(Player player) {
        JokerShopGUI.open(FunUnityAPI.getInstance().getPlayerHandler().getPlayer(player));
    }

    /**
     * Will be called, when the game world was loaded.
     * @param arena Arena - arena that was selected.
     * @since 0.0.1
     */
    @Override
    public void gameWorldLoaded(Arena arena) {
        // nothing to do here
    }

    /**
     * Returns the traitorJoker queue
     * @return Queue<UUID> - Prioritized Joker list.
     * @since 0.0.1
     */
    public Queue<UUID> getTraitorJoker() {
        return traitorJoker;
    }

    /**
     * Returns the detective joker queue
     * @return Queue<UUID> - Prioritized Joker list.
     * @since 0.0.1
     */
    public Queue<UUID> getDetectiveJoker() {
        return detectiveJoker;
    }

    /**
     * Returns a list with all players of the given roles.
     * @param roles Role[] - The whitelisted roles
     * @return List<TTTPlayer> - All players who have the given roles
     * @since 0.0.1
     */
    public List<TTTPlayer> getTTTPlayerByRole(Role... roles) {
        return getTTTPlayers().stream().filter(t -> Arrays.asList(roles).contains(t.getRole())).collect(Collectors.toList());
    }

    /**
     * Returns the TTTPlayer by their uuid
     * @param uuid UUID - The uuid of the Player.
     * @return {@link TTTPlayer} - Instance of the TTTPlayer
     * @since 0.0.1
     */
    public TTTPlayer getTTTPlayer(UUID uuid) {
        return tttPlayers.stream().filter(t -> t.getApiPlayer().getUniqueId().equals(uuid)).findFirst().orElse(null);
    }

    /**
     * Returns all TTTPlayers
     * @return List<TTTPlayer> - Copied list of all ttt player
     * @since 0.0.1
     */
    public List<TTTPlayer> getTTTPlayers() {
        return new ArrayList<>(tttPlayers);
    }

    /**
     * Returns the instance of this class.
     * @return {@link GameLogic} - the instance of this class.
     * @since 0.0.1
     */
    public static GameLogic getInstance() {
        return instance;
    }
}
