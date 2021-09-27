package net.fununity.games.auttt;

import net.fununity.games.auttt.commands.CoinsInfoCommand;
import net.fununity.games.auttt.commands.JokerCommand;
import net.fununity.games.auttt.commands.ShopCommand;
import net.fununity.games.auttt.commands.TraitorChatCommand;
import net.fununity.games.auttt.language.EnglishMessages;
import net.fununity.games.auttt.language.GermanMessages;
import net.fununity.games.auttt.listener.*;
import net.fununity.games.auttt.shop.GeneralShopListener;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.minigames.MinigameNames;
import net.fununity.main.api.minigames.stats.minigames.Minigames;
import net.fununity.main.api.util.RegisterUtil;
import net.fununity.mgs.Minigame;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of the plugin.
 * @author Niko
 * @since 0.0.1
 */
public class TTT extends JavaPlugin {

    private static TTT instance;
    private double traitorAmount;
    private double detectiveAmount;
    private int minAmountForDetective;
    private int maxTraitorAmount;

    /**
     * Will be called, when the plugin enables.
     * @since 0.0.1
     */
    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.traitorAmount = this.getConfig().getDouble("traitor.amount");
        this.maxTraitorAmount = this.getConfig().getInt("traitor.maximum");
        this.detectiveAmount = this.getConfig().getInt("detective.amount");
        this.minAmountForDetective = this.getConfig().getInt("detective.minimum");

        new EnglishMessages();
        new GermanMessages();

        RegisterUtil registerUtil = new RegisterUtil(this);
        registerUtil.addListeners(new PlayerInteractListener(), new PlayerQuitListener(), new PlayerJoinListener(), new PlayerDropListener(), new PlayerDamagesPlayerListener(), new ProjectileHitListener(), new ChatListener(), new GeneralShopListener());
        registerUtil.addCommands(new TraitorChatCommand(), new CoinsInfoCommand(), new ShopCommand(), new JokerCommand());
        registerUtil.register();

        Minigame ttt = new Minigame(MinigameNames.TTT.getDisplayName(), Minigames.TTT, GameLogic.class);
        ttt.setAutoReward(false);
        ttt.setDeathMessage(false);
        ttt.setScoreboard(false);
        ttt.setTablist(false);
        ttt.setProtectionTime(30);
        ttt.setExtraLobbyItem(new ItemBuilder(Material.PAPER).setName(ChatColor.GOLD + "JokerGUI").craft());
    }

    /**
     * Get the multiplier to determine the amount of traitors in a round.
     * AmountOfPlayers * multipliers = traitors in round
     * @return double - the traitor multiplier to calculate the amount of traitors.
     * @since 0.0.1
     */
    public double getTraitorAmountMultiplier() {
        return traitorAmount;
    }

    /**
     * Get the multiplier to determine the amount of detectives in a round.
     * AmountOfPlayers * multipliers = detectives in round
     * @return double - the detective multiplier to calculate the amount of traitors.
     * @since 0.0.1
     */
    public double getDetectiveAmountMultiplier() {
        return detectiveAmount;
    }

    /**
     * Get the minimum amount of players so detectives will be selected.
     * @return int - minimum amount of players for detectives.
     * @since 0.0.1
     */
    public int getMinAmountForDetective() {
        return minAmountForDetective;
    }

    /**
     * Get the maximum possible amount of traitors in one round.
     * @return int - maximum amount of traitors
     * @since 0.0.1
     */
    public int getMaxTraitorAmount() {
        return maxTraitorAmount;
    }

    /**
     * Get the instance of this class.
     * @return {@link TTT} - instance of this class.
     * @since 0.0.1
     */
    public static TTT getInstance() {
        return instance;
    }
}
