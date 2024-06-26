package net.fununity.games.auttt.shop.traitor;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.ShopItems;
import net.fununity.games.auttt.shop.innocents.InnocentItems;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.hologram.HologramText;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.npc.NPC;
import net.fununity.npc.events.PlayerInteractAtNPCEvent;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class ShopBoomBody extends ShopItem {

    private static final double DAMAGE_RADIUS = 4;
    private static final double DAMAGE_AMOUNT = 15;
    private NPC npc;
    private Location hologramLocation;

    public ShopBoomBody(ShopItems shopItem, TTTPlayer tttPlayer) {
        super(shopItem, tttPlayer);
        giveItemToUse();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!didPlayerUse(event)) return;
        use(true);

        npc = new NPC("", tttPlayer.getApiPlayer().getPlayer().getLocation(), "ttt_undefined", false);
        npc.setLookAtPlayer(false);
        npc.click(this::clickedOn);

        this.hologramLocation = npc.getLocation().clone().add(0, 1.8D, 0);
        HologramText hologramText = new HologramText();
        hologramText.addLine(TranslationKeys.TTT_GAME_PLAYER_CORPSE_NOTFOUND);
        FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers().forEach(on -> on.showHologram(hologramText, hologramLocation));
    }

    public void clickedOn(PlayerInteractAtNPCEvent event) {
        APIPlayer apiPlayer = event.getPlayer();
        if (!event.getAction().isRightClick() || GameLogic.getInstance().gameManager.isSpectator(apiPlayer.getPlayer())) return;
        TTTPlayer clicker = GameLogic.getInstance().getTTTPlayer(apiPlayer.getUniqueId());
        if (clicker == null || clicker.getRole() == Role.TRAITOR) return;
        if (clicker.hasShopItem(InnocentItems.ANTI_BOOM_BODY)) {
            apiPlayer.playSound(Sound.AMBIENT_CAVE);
            apiPlayer.sendMessage(TranslationKeys.TTT_GAME_SHOP_ITEM_ANTI_BOOM_BODY_WARN);
            return;
        }

        Location location = npc.getLocation().clone();
        location.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, location, 3);
        location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
        for (TTTPlayer player : GameLogic.getInstance().getTTTPlayerByRole(Role.INNOCENT, Role.DETECTIVE)) {
            if (player.getApiPlayer().getPlayer().getLocation().distance(location) <= DAMAGE_RADIUS) {
                player.getApiPlayer().getPlayer().damage(DAMAGE_AMOUNT);
            }
        }
        npc.destroy();
        FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers().forEach(o -> o.hideHolograms(hologramLocation));
    }
}
