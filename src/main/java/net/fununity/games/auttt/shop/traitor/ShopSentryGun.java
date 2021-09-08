package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class ShopSentryGun extends ShopItem {

    private static final int DESTROY_SECONDS = 30;
    private static final int SHOOT_RADIUS = 10;
    private static final double SHOOT_ANGLE = 12;

    private ArmorStand sentry;
    private UUID lastUUID;

    /**
     * Instantiates the class and registers the event listener.
     * @param shopItem  {@link ShopItems} - The shop item references.
     * @param tttPlayer {@link TTTPlayer} - The Player who buys this shop item instance.
     * @since 1.1
     */
    public ShopSentryGun(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!didPlayerUse(event)) return;
        use(true);
        spawnSentry(event.getPlayer().getLocation());
    }

    private void spawnSentry(Location location) {
        this.sentry = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        this.sentry.setItemInHand(new ItemStack(Material.BOW));
        this.sentry.setHelmet(new ItemStack(Material.GOLD_HELMET));
        this.sentry.setMetadata("ttt-sentry", new FixedMetadataValue(TTT.getInstance(), 1));
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(TTT.getInstance(), this::checkForShoot, 20L, 20L);
        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), ()->{
            task.cancel();
            this.sentry.remove();
        }, 20 * DESTROY_SECONDS);
    }

    private void checkForShoot() {
        TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayerByRole(Role.DETECTIVE, Role.INNOCENT).stream()
                .filter(t -> t.getApiPlayer().getPlayer().getWorld().equals(sentry.getWorld()))
                .filter(t -> t.getApiPlayer().getPlayer().getLocation().distance(sentry.getLocation()) <= SHOOT_RADIUS)
                .filter(t -> t.getApiPlayer().getPlayer().getEyeLocation().toVector().subtract(sentry.getEyeLocation().toVector())
                        .angle(sentry.getEyeLocation().getDirection()) >= SHOOT_ANGLE)
                .min((o1, o2) -> Boolean.compare(
                        o1.getApiPlayer().getUniqueId().equals(lastUUID),
                        o2.getApiPlayer().getUniqueId().equals(lastUUID))).orElse(null);
        if (tttPlayer == null) return;
        this.lastUUID = tttPlayer.getApiPlayer().getUniqueId();
        Arrow arrow = sentry.launchProjectile(Arrow.class, tttPlayer.getApiPlayer().getPlayer().getEyeLocation()
                .toVector().subtract(sentry.getEyeLocation().toVector()).normalize().multiply(1.5));
        arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
    }

}
