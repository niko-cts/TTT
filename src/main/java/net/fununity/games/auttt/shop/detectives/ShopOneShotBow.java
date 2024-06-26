package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class ShopOneShotBow extends ShopItem {

    public ShopOneShotBow(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse();
        tttPlayer.getApiPlayer().getPlayer().getInventory().addItem(new ItemStack(Material.ARROW));
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        Player player = (Player) event.getEntity().getShooter();
        if (!player.getUniqueId().equals(tttPlayer.getApiPlayer().getUniqueId())) return;
        if (!equalsItem(player.getInventory().getItemInMainHand())) return;
        event.getEntity().setMetadata("ttt-oneshot", new FixedMetadataValue(TTT.getInstance(), this));
        use(true);
    }
}
