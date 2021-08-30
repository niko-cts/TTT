package net.fununity.games.auttt.rooms;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.player.TTTPlayer;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.detectives.DetectiveItems;
import net.fununity.games.auttt.shop.innocents.InnocentItems;
import net.fununity.games.auttt.shop.traitor.TraitorItems;
import net.fununity.main.api.actionbar.ActionbarMessage;
import net.fununity.main.api.common.util.RandomUtil;
import net.fununity.main.api.server.BroadcastHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Generator {

    private final Location location;
    private boolean enabled;

    public Generator(Location location) {
        this.location = location;
        this.enabled = true;
    }

    private void disable() {
        this.enabled = false;
        for (TTTPlayer tttPlayer : GameLogic.getInstance().getTTTPlayerByRole(Role.INNOCENT, Role.DETECTIVE)) {
            if (tttPlayer.hasShopItem(InnocentItems.NIGHTVISION, DetectiveItems.NIGHTVISION))
                continue;
            Player player = tttPlayer.getApiPlayer().getPlayer();
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.SPEED);
        }
        BroadcastHandler.broadcastMessage(TranslationKeys.TTT_GAME_ROOM_GENERATOR_DISABLE_BROADCAST);
    }

    private void enable() {
        this.enabled = true;
        for (TTTPlayer tttPlayer : GameLogic.getInstance().getTTTPlayerByRole(Role.INNOCENT, Role.DETECTIVE)) {
            if (tttPlayer.hasShopItem(InnocentItems.NIGHTVISION, DetectiveItems.NIGHTVISION))
                continue;
            Player player = tttPlayer.getApiPlayer().getPlayer();
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 9999, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 9999, 1));
        }
        BroadcastHandler.broadcastMessage(TranslationKeys.TTT_GAME_ROOM_GENERATOR_ENABLE_BROADCAST);
    }

    public boolean isEnabled() {
        return enabled;
    }

    protected Location getActivationBlock() {
        return location;
    }

    protected void buttonPressed(Player player) {
        TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(player.getUniqueId());
        if (tttPlayer.getRole() == Role.TRAITOR && !isEnabled()) {
            if (tttPlayer.hasShopItem(TraitorItems.GENERATOR)) {
                tttPlayer.getShopItemsOfType(TraitorItems.GENERATOR).forEach(ShopItem::removeItem);
                disable();
            } else {
                tttPlayer.getApiPlayer().sendActionbar(new ActionbarMessage(TranslationKeys.TTT_GAME_ROOM_GENERATOR_DISABLE_NOTHAVE));
                return;
            }
        }
        if (!isEnabled()) {
            if (RandomUtil.getRandom().nextBoolean() && RandomUtil.getRandom().nextBoolean()) {
                enable();
            } else {
                tttPlayer.getApiPlayer().sendActionbar(new ActionbarMessage(TranslationKeys.TTT_GAME_ROOM_GENERATOR_ENABLE_TRYAGAIN));
            }
        }
    }
}
