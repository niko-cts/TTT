package net.fununity.games.auttt;

import net.fununity.games.auttt.commands.CoinsInfoCommand;
import net.fununity.games.auttt.commands.JokerCommand;
import net.fununity.games.auttt.commands.ShopCommand;
import net.fununity.games.auttt.commands.TraitorCommand;
import net.fununity.games.auttt.language.EnglishMessage;
import net.fununity.games.auttt.listener.PlayerDamagesPlayerListener;
import net.fununity.games.auttt.listener.PlayerDropListener;
import net.fununity.games.auttt.listener.PlayerInteractListener;
import net.fununity.games.auttt.listener.PlayerQuitListener;
import net.fununity.games.auttt.shop.GeneralShopListener;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.minigames.stats.minigames.Minigames;
import net.fununity.main.api.util.RegisterUtil;
import net.fununity.mgs.Minigame;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class TTT extends JavaPlugin {

    private static TTT instance;
    private double traitorAmount;
    private double detectiveAmount;
    private int minAmountForDetective;
    private int maxTraitorAmount;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.traitorAmount = this.getConfig().getDouble("traitor.amount");
        this.maxTraitorAmount = this.getConfig().getInt("traitor.maximum");
        this.detectiveAmount = this.getConfig().getInt("detective.amount");
        this.minAmountForDetective = this.getConfig().getInt("detective.minimum");

        new EnglishMessage();

        RegisterUtil registerUtil = new RegisterUtil(this);
        registerUtil.addListeners(new PlayerInteractListener(), new PlayerQuitListener(), new PlayerDropListener(), new PlayerDamagesPlayerListener(), new GeneralShopListener());
        registerUtil.addCommands(new TraitorCommand(), new CoinsInfoCommand(), new ShopCommand(), new JokerCommand());
        registerUtil.register();

        Minigame ttt = new Minigame("TTT", Minigames.TTT, GameLogic.class);
        ttt.setAutoReward(false);
        ttt.setDeathMessage(false);
        ttt.setScoreboard(false);
        ttt.setTablist(false);
        ttt.setProtectionTime(30);
        ttt.setExtraLobbyItem(new ItemBuilder(Material.PAPER).setName(ChatColor.GOLD + "JokerGUI").craft());
    }

    public double getTraitorAmount() {
        return traitorAmount;
    }

    public double getDetectiveAmount() {
        return detectiveAmount;
    }

    public int getMinAmountForDetective() {
        return minAmountForDetective;
    }

    public int getMaxTraitorAmount() {
        return maxTraitorAmount;
    }

    public static TTT getInstance() {
        return instance;
    }
}
