package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.player.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;

public class ShopHealStation extends ShopItem {

    private int minutesLeft;
    private BukkitTask task;

    public ShopHealStation(TTTPlayer player) {
        super(DetectiveItems.HEAL_STATION, player);
        this.minutesLeft = 6;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (!didPlayerUse(event)) return;
        super.use(true);
        Location location = tttPlayer.getApiPlayer().getPlayer().getLocation();
        Material material = location.getBlock().getType();
        byte data = location.getBlock().getData();
        location.getBlock().setType(Material.SKULL);
        location.getBlock().setData((byte) 3);
        location.getBlock().getState().update();

        this.task = Bukkit.getScheduler().runTaskTimer(TTT.getInstance(), ()->{
            Collection<Entity> nearbyEntities = location.getWorld().getNearbyEntities(location, 2, 2, 2);
            for (Entity nearbyEntity : nearbyEntities) {
                if (nearbyEntity instanceof Player)
                    ((Player) nearbyEntity).setHealth(((Player) nearbyEntity).getHealth() + 1);
            }
            minutesLeft--;
            if (minutesLeft == 0) {
                cancelTask();
                location.getBlock().setType(material);
                location.getBlock().setData(data);
            }
        }, 20, 20);
    }

    private void cancelTask() {
        this.task.cancel();
    }
}
