package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

public class ShopDetector extends ShopItem {

    private BukkitTask bukkitTask;
    private int seconds;

    public ShopDetector(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        this.seconds = 10;
        giveItemToUse();
    }

    @EventHandler
    public void onInteractPlayer(PlayerInteractEvent event) {
        if (!didPlayerUse(event)) return;
        use(true);
        Location location = event.getPlayer().getEyeLocation();
        Particle particle;
        if (GameLogic.getInstance().getTTTPlayerByRole(Role.TRAITOR).stream().anyMatch(p -> p.getApiPlayer().getPlayer().getLocation().distance(location) <= 5))
            particle = Particle.REDSTONE;
        else
            particle = Particle.VILLAGER_HAPPY;

        bukkitTask = Bukkit.getScheduler().runTaskTimer(TTT.getInstance(), () -> {
            seconds--;
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    location.getWorld().spawnParticle(particle, location.clone().add(x, 0, z), 0);
                }
            }
            if (seconds == 0)
                cancelTask();
        }, 0L, 20L);
    }

    private void cancelTask() {
        this.bukkitTask.cancel();
    }
}
