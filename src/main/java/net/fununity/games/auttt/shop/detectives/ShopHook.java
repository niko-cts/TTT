package net.fununity.games.auttt.shop.detectives;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.main.api.messages.MessagePrefix;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

public class ShopHook extends ShopItem {

    private static final long COOLDOWN = 3000;
    private long lastTime;

    public ShopHook(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        this.lastTime = 0;
        giveItemToUse();
    }

    @EventHandler
    public void onProjectileHit (PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.IN_GROUND && event.getState() != PlayerFishEvent.State.CAUGHT_ENTITY) return;
        Player player = event.getPlayer();
        TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(player.getUniqueId());
        if (tttPlayer == null || !tttPlayer.hasShopItem(DetectiveItems.HOOK)) return;

        if (System.currentTimeMillis() - this.lastTime < COOLDOWN) {
            tttPlayer.getApiPlayer().sendMessage(MessagePrefix.ERROR, TranslationKeys.TTT_GAME_SHOP_ITEM_HOOK_COOLDOWN);
            return;
        }
        this.lastTime = System.currentTimeMillis();

        if (event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY) {
            Vector vec = player.getLocation().subtract(event.getCaught().getLocation()).toVector().multiply(0.3);
            vec.setY(0.3);
            event.getCaught().setVelocity(vec);
            ((Player) event.getCaught()).damage(1, player);
        } else if (event.getState() == PlayerFishEvent.State.IN_GROUND) {
            Vector vec = event.getHook().getLocation().subtract(player.getLocation()).toVector().multiply(0.3);
            vec.setY(0.5);
            player.setVelocity(vec);
        }
    }
}
