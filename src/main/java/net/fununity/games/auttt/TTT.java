package net.fununity.games.auttt;

import net.fununity.games.auttt.language.EnglishMessage;
import net.fununity.games.auttt.listener.PlayerInteractListener;
import net.fununity.main.api.minigames.stats.minigames.Minigames;
import net.fununity.main.api.util.RegisterUtil;
import net.fununity.mgs.Minigame;
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
        registerUtil.addListener(new PlayerInteractListener());
        registerUtil.register();

        Minigame ttt = new Minigame("TTT", Minigames.TTT, GameLogic.class);
        ttt.setAutoReward(false);
        ttt.setDeathMessage(false);
        ttt.setScoreboard(false);
        ttt.setTablist(false);
        ttt.setProtectionTime(30);
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
