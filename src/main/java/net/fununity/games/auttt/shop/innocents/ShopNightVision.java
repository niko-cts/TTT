package net.fununity.games.auttt.shop.innocents;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.rooms.Generator;
import net.fununity.games.auttt.rooms.RoomsManager;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class ShopNightVision extends ShopItem {

    public ShopNightVision(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        Generator generator = RoomsManager.getInstance().getGenerator();
        if (generator != null && !generator.isEnabled()) {
            Player player = tttPlayer.getApiPlayer().getPlayer();
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.SPEED);
        }
    }
}
