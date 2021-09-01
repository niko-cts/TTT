package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.mgs.gamestates.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

public class ShopJihad extends ShopItem {

    private static final int TICK_DURATION = 20 * 5;
    private static final double DISTANCE = 5;

    public ShopJihad(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!didPlayerUse(event)) return;
        use(true);
        Player player = event.getPlayer();
        TNTPrimed tnt = (TNTPrimed) event.getPlayer().getWorld().spawnEntity(player.getEyeLocation(), EntityType.PRIMED_TNT);
        tnt.setFuseTicks(TICK_DURATION + 5);
        tnt.setInvulnerable(false);
        event.getPlayer().addPassenger(tnt);
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(TTT.getInstance(), () ->
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 1, 1), 0, 10);
        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), () -> {
            task.cancel();
            tnt.remove();
            if (!player.isOnline()) return;
            for (TTTPlayer online : GameLogic.getInstance().getTTTPlayers()) {
                if (GameManager.getInstance().isPlayer(online.getApiPlayer().getPlayer()) &&
                        online.getApiPlayer().getPlayer().getLocation().distance(player.getLocation()) <= DISTANCE) {
                    online.getApiPlayer().getPlayer().damage(30, player);
                }
            }
        }, TICK_DURATION);
    }
}
