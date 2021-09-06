package net.fununity.games.auttt.rooms;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.detectives.DetectiveItems;
import net.fununity.games.auttt.shop.innocents.InnocentItems;
import net.fununity.games.auttt.shop.traitor.TraitorItems;
import net.fununity.main.api.actionbar.ActionbarMessage;
import net.fununity.main.api.common.util.RandomUtil;
import net.fununity.main.api.server.BroadcastHandler;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * This class represents the generator in the game.
 * @author Niko
 * @since 1.1
 */
public class Generator {

    private final Location location;
    private boolean enabled;

    /**
     * Instantiates the generator.
     * @param location Location - the activate button location
     * @since 1.1
     */
    public Generator(Location location) {
        this.location = location;
        this.enabled = true;
    }

    /**
     * Disables the generator
     * @since 1.1
     */
    private void disable() {
        this.enabled = false;
        for (TTTPlayer tttPlayer : GameLogic.getInstance().getTTTPlayerByRole(Role.INNOCENT, Role.DETECTIVE)) {
            if (tttPlayer.hasShopItem(InnocentItems.NIGHTVISION, DetectiveItems.NIGHTVISION))
                continue;
            Player player = tttPlayer.getApiPlayer().getPlayer();
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 9999, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 9999, 1));
        }
        BroadcastHandler.broadcastMessage(TranslationKeys.TTT_GAME_ROOM_GENERATOR_DISABLE_BROADCAST);
    }

    /**
     * (Re)enables the generator.
     * @since 1.1
     */
    private void enable() {
        this.enabled = true;
        for (TTTPlayer tttPlayer : GameLogic.getInstance().getTTTPlayerByRole(Role.INNOCENT, Role.DETECTIVE)) {
            if (tttPlayer.hasShopItem(InnocentItems.NIGHTVISION, DetectiveItems.NIGHTVISION))
                continue;
            Player player = tttPlayer.getApiPlayer().getPlayer();
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.removePotionEffect(PotionEffectType.SPEED);
        }
        BroadcastHandler.broadcastMessage(TranslationKeys.TTT_GAME_ROOM_GENERATOR_ENABLE_BROADCAST);
    }

    /**
     * Check if the generator is activated.
     * @return boolean - generator is enabled.
     * @since 1.1
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Get the location of the activation block
     * @return Location - the activation location.
     * @since 0.0.1
     */
    protected Location getActivationBlock() {
        return location;
    }

    /**
     * Will be called, when the activation block was clicked.
     * @param player Player - the player who clicked the button.
     * @since 1.1
     */
    protected void buttonPressed(Player player) {
        TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(player.getUniqueId());
        if (!isEnabled()) {
            if (RandomUtil.getRandom().nextBoolean() && RandomUtil.getRandom().nextBoolean()) {
                enable();
            } else {
                location.getWorld().playEffect(location, Effect.BOW_FIRE, 3);
                location.getWorld().playSound(location, Sound.BLOCK_ANVIL_HIT, 1, 1);
                tttPlayer.getApiPlayer().sendActionbar(new ActionbarMessage(TranslationKeys.TTT_GAME_ROOM_GENERATOR_ENABLE_TRYAGAIN));
            }
            return;
        }
        if (tttPlayer.getRole() == Role.TRAITOR) {
            if (tttPlayer.hasShopItem(TraitorItems.GENERATOR)) {
                tttPlayer.getShopItemsOfType(TraitorItems.GENERATOR).get(0).use(false);
                disable();
            } else {
                tttPlayer.getApiPlayer().sendActionbar(new ActionbarMessage(TranslationKeys.TTT_GAME_ROOM_GENERATOR_DISABLE_NOTHAVE));
            }
        }
    }
}
