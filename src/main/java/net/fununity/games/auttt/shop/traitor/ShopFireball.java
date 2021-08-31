package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.main.api.item.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class ShopFireball extends ShopItem {

    private static final ItemBuilder ITEM = new ItemBuilder(Material.IRON_HOE).setName(TranslationKeys.TTT_GAME_SHOP_ITEM_FIREBALL_NAME).setLore(TranslationKeys.TTT_GAME_SHOP_ITEM_FIREBALL_DESCRIPTION);

    public ShopFireball(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse(ITEM.translate(tttPlayer.getApiPlayer().getLanguage()));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!didPlayerUse(event)) return;
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
        location.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), 3f, false, true);
    }
}
