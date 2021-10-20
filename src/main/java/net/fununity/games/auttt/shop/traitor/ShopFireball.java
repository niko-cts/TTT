package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.rooms.RoomsManager;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.mgs.gamestates.GameManager;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class ShopFireball extends ShopItem {

    public ShopFireball(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!didPlayerUse(event)) return;
        if (RoomsManager.getInstance().getVent().isInVent(event.getPlayer().getUniqueId()) || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true);
            return;
        }
        use(true);
        Fireball fireball = event.getPlayer().launchProjectile(Fireball.class);
        fireball.setBounce(false);
        fireball.setVelocity(fireball.getVelocity().multiply(1.5));
        fireball.setMetadata("ttt-fireball", new FixedMetadataValue(TTT.getInstance(), "ttt-fireball"));
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!event.getEntity().hasMetadata("ttt-fireball")) return;
        Location location = event.getEntity().getLocation();
        location.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, location, 0);

        for (Entity nearbyEntity : location.getWorld().getNearbyEntities(location, 3, 3, 3)) {
            if (nearbyEntity instanceof Player) {
                TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(nearbyEntity.getUniqueId());
                if (tttPlayer != null && tttPlayer.getRole() != Role.TRAITOR && !GameManager.getInstance().isSpectator(tttPlayer.getApiPlayer().getUniqueId())) {
                    tttPlayer.getApiPlayer().getPlayer().damage(10);
                }
            }
        }
    }
}
