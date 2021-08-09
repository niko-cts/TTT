package net.fununity.games.auttt;

import net.fununity.games.auttt.gui.JokerShopGUI;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.player.PlayerCorpse;
import net.fununity.games.auttt.player.TTTPlayer;
import net.fununity.games.auttt.util.CoinsUtil;
import net.fununity.games.auttt.util.TTTScoreboard;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.common.util.RandomUtil;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.minigames.stats.minigames.StatType;
import net.fununity.main.api.player.BalanceHandler;
import net.fununity.main.api.server.BroadcastHandler;
import net.fununity.main.api.server.ServerSetting;
import net.fununity.mgs.Minigame;
import net.fununity.mgs.gamespecifc.Arena;
import net.fununity.mgs.gamestates.Game;
import net.fununity.mgs.gamestates.GameState;
import net.fununity.mgs.listener.RunningListener;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.*;
import java.util.stream.Collectors;

public class GameLogic extends Game {

    private static GameLogic instance;

    private final Queue<UUID> traitorJoker;
    private final Queue<UUID> detectiveJoker;
    private final List<TTTPlayer> tttPlayers;

    public GameLogic() {
        instance = this;
        this.traitorJoker = new LinkedList<>();
        this.detectiveJoker = new LinkedList<>();
        this.tttPlayers = new ArrayList<>();
    }

    @Override
    public void startMinigame() {
        FunUnityAPI.getInstance().getServerSettings().disable(ServerSetting.FOOD_LEVEL_CHANGE);

        ItemBuilder shop = new ItemBuilder(Material.PAPER).setName(TranslationKeys.TTT_GAME_ITEM_SHOP_NAME).setLore(TranslationKeys.TTT_GAME_ITEM_SHOP_LORE);
        ItemBuilder analyzer = new ItemBuilder(Material.STICK).setName(TranslationKeys.TTT_GAME_ITEM_ANALYZER_NAME).setLore(TranslationKeys.TTT_GAME_ITEM_ANALYZER_LORE);
        for (Player player : getPlayers()) {
            Language lang = FunUnityAPI.getInstance().getPlayerHandler().getPlayer(player).getLanguage();
            player.getInventory().setItem(7, shop.translate(lang));
            player.getInventory().setItem(8, analyzer.translate(lang));
        }

        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), () -> {
            if (gameManager.getCurrentGameState() != GameState.INGAME) return;

            // ROLES SORTING
            int maximumTraitor = (int) Math.min(TTT.getInstance().getMaxTraitorAmount(), Math.round(TTT.getInstance().getTraitorAmount() * getPlayers().size()));
            List<Player> players = getPlayers();

            addPlayersToList(maximumTraitor, traitorJoker, players, Role.TRAITOR);

            int maximumDetectives = TTT.getInstance().getMinAmountForDetective() >= getPlayers().size() ? (int) Math.round(TTT.getInstance().getDetectiveAmount() * getPlayers().size()) : 0;
            addPlayersToList(maximumDetectives, detectiveJoker, players, Role.DETECTIVE);

            while (!players.isEmpty()) {
                Player player = players.get(RandomUtil.getRandomInt(players.size()));
                players.remove(player);
                tttPlayers.add(new TTTPlayer(FunUnityAPI.getInstance().getPlayerHandler().getPlayer(player), Role.INNOCENT));
            }

            JokerShopGUI.payback();

            for (TTTPlayer tttPlayer : tttPlayers) {
                CoinsUtil.startCoins(tttPlayer);
                tttPlayer.getApiPlayer().getTitleSender().sendTitle(TranslationKeys.ROLE_CALLOUT_TITLE, "${color}",
                        tttPlayer.getRole().getColor() + "", 20 * 5);
                tttPlayer.getApiPlayer().getTitleSender().sendSubtitle(TranslationKeys.ROLE_CALLOUT_SUBTITLE,
                        Arrays.asList("${color}", "${role}"),
                        Arrays.asList(tttPlayer.getRole().getColor() + "", tttPlayer.getRole().getColoredName()),
                        20 * 5);
                tttPlayer.getApiPlayer().sendMessage(TranslationKeys.ROLE_CALLOUT_TEXT,
                        Arrays.asList("${color}", "${role}"),
                        Arrays.asList(tttPlayer.getRole().getColor() + "", tttPlayer.getRole().getColoredName()));
                TTTScoreboard.updateScoreboard(tttPlayer);
                TTTScoreboard.updateTablist(tttPlayer);
            }
        }, 20L * Minigame.getInstance().getProtectionTime());
    }

    private void addPlayersToList(int amount, Queue<UUID> preferred, List<Player> alternatives, Role role) {
        while (amount > 0) {
            if (alternatives.isEmpty() && preferred.isEmpty()) {
                TTT.getInstance().getLogger().warning("There are no more players available to fit in the role!");
                return;
            }

            UUID uuid;
            if(preferred.isEmpty()) {
                Player random = alternatives.get(RandomUtil.getRandomInt(alternatives.size()));
                alternatives.remove(random);
                uuid = random.getUniqueId();
            } else
                uuid = preferred.poll();
            tttPlayers.add(new TTTPlayer(FunUnityAPI.getInstance().getPlayerHandler().getPlayer(uuid), role));
            amount--;
        }
    }

    @Override
    public void minigameCountdown(int i) {
        // nothing to do here
    }

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

            TTTScoreboard.updateScoreboard(tttPlayer);
            TTTScoreboard.updateTablist(tttPlayer);
        }

        CoinsUtil.win(innoAlive == 0 ? Role.TRAITOR : Role.INNOCENT, Role.DETECTIVE);

        BroadcastHandler.broadcastMessage(TranslationKeys.ROLE_WON, "${role}", innoAlive == 0 ? Role.TRAITOR.getColoredName() : Role.INNOCENT.getColoredName());

        if (Minigame.getInstance().getRewardTokens() != 0)
            for (Player player : winner)
                BalanceHandler.getInstance().giveMoney(player.getUniqueId(), Minigame.getInstance().getRewardTokens(), false);

        return winner;
    }


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
            TTTScoreboard.playerDied(tttPlayer);
            getTTTPlayerByRole(Role.TRAITOR).forEach(TTTScoreboard::updateScoreboard);
            TTTScoreboard.updateScoreboard(tttPlayer);
            TTTScoreboard.updateTablist(tttPlayer);
        }, 2L);

        new PlayerCorpse(tttPlayer, event);

        player.getInventory().clear();

        // win check:
        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), this::checkForGameEnd, 2L);
        return false;
    }

    @Override
    public void playerLeaves(Player player) {
        tttPlayers.remove(getTTTPlayer(player.getUniqueId()));

        for (TTTPlayer tttPlayer : getTTTPlayers()) {
            TTTScoreboard.updateScoreboard(tttPlayer);
            TTTScoreboard.updateTablist(tttPlayer);
        }

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

    @Override
    public void useExtraLobbyItem(Player player) {
        JokerShopGUI.open(FunUnityAPI.getInstance().getPlayerHandler().getPlayer(player));
    }

    @Override
    public void gameWorldLoaded(Arena arena) {
        // nothing to do here
    }

    @Override
    public int calculatePoints(Map<StatType, Double> playerStats) {
        return super.calculatePoints(playerStats);
    }

    public Queue<UUID> getTraitorJoker() {
        return traitorJoker;
    }

    public Queue<UUID> getDetectiveJoker() {
        return detectiveJoker;
    }

    public List<TTTPlayer> getTTTPlayerByRole(Role... roles) {
        return getTTTPlayers().stream().filter(t -> Arrays.asList(roles).contains(t.getRole())).collect(Collectors.toList());
    }

    public TTTPlayer getTTTPlayer(UUID uuid) {
        return tttPlayers.stream().filter(t -> t.getApiPlayer().getUniqueId().equals(uuid)).findFirst().orElse(null);
    }

    public List<TTTPlayer> getTTTPlayers() {
        return new ArrayList<>(tttPlayers);
    }

    public static GameLogic getInstance() {
        return instance;
    }
}
