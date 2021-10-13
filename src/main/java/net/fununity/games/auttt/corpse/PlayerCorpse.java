package net.fununity.games.auttt.corpse;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.traitor.TraitorItems;
import net.fununity.games.auttt.util.CoinsUtil;
import net.fununity.games.auttt.util.TTTScoreboard;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.hologram.APIHologram;
import net.fununity.main.api.hologram.HologramText;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.item.UsefulItems;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.npc.NPC;
import net.fununity.npc.events.PlayerInteractAtNPCEvent;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayInUseEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerCorpse {

    protected final TTTPlayer tttPlayer;
    protected final OffsetDateTime death;

    private final Location hologramLocation;
    private final NPC npc;
    private final CorpseInventoryManager corpseInventoryManager;

    public PlayerCorpse(TTTPlayer tttPlayer, PlayerDeathEvent event) {
        this.tttPlayer = tttPlayer;
        this.death = OffsetDateTime.now();

        this.corpseInventoryManager = new CorpseInventoryManager(this, event);

        this.npc = new NPC("", tttPlayer.getApiPlayer().getPlayer().getLocation(), "ttt_undefined", false);
        this.npc.setLookAtPlayer(false);
        this.npc.click(this::clickedOn);

        this.hologramLocation = this.npc.getLocation().clone().add(0, 1.8D, 0);
        updateHologram(TranslationKeys.TTT_GAME_PLAYER_CORPSE_NOTFOUND, true);
    }

    public void clickedOn(PlayerInteractAtNPCEvent event) {
        APIPlayer apiPlayer = event.getPlayer();
        if (event.getAction() != PacketPlayInUseEntity.EnumEntityUseAction.INTERACT || event.getHand() != EquipmentSlot.HAND ||
                GameManager.getInstance().isSpectator(apiPlayer.getPlayer())) return;

        // NomNomDevice
        TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(apiPlayer.getUniqueId());
        if (tttPlayer == null) return;

        ItemStack item = tttPlayer.getApiPlayer().getPlayer().getInventory().getItemInMainHand();

        List<ShopItem> nomNomDevices = tttPlayer.getShopItemsOfType(TraitorItems.NOM_NOM_DEVICE);
        if (!nomNomDevices.isEmpty() && nomNomDevices.get(0).equalsItem(item)) {
            nomNomDevices.get(0).use(true);
            npc.destroy();
            FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers().forEach(on -> on.hideHolograms(this.hologramLocation));
            apiPlayer.playSound(Sound.ENTITY_PLAYER_BURP);
            return;
        }

        List<ShopItem> identThief = tttPlayer.getShopItemsOfType(TraitorItems.IDENT_THIEF);
        if (!identThief.isEmpty() && identThief.get(0).equalsItem(item)) {
            apiPlayer.getPlayer().setDisplayName(this.tttPlayer.getApiPlayer().getPlayer().getName());
            identThief.get(0).use(true);
            apiPlayer.playSound(Sound.ENTITY_ENDERMEN_TELEPORT);
        }

        if (item == null || item.getType() != Material.STICK) return;

        if (this.tttPlayer.isFound())
            this.corpseInventoryManager.openInventory(apiPlayer);
        else
            found(tttPlayer);
    }

    public void found(TTTPlayer foundBy) {
        this.tttPlayer.found();
        CoinsUtil.foundBody(foundBy, this.tttPlayer);

        updateHologram(this.tttPlayer.getColoredName(), false);
        this.npc.equip(EnumItemSlot.HEAD, new ItemBuilder(UsefulItems.PLAYER_HEAD).setSkullOwner(this.tttPlayer.getApiPlayer().getDatabasePlayer().getPlayerTextures()).craft());

        for (TTTPlayer tttPlayer : GameLogic.getInstance().getTTTPlayers()) {
            tttPlayer.getApiPlayer().sendMessage(TranslationKeys.TTT_GAME_PLAYER_FOUND,
                    Arrays.asList("${name}", "${role}", "${found}"),
                    Arrays.asList(this.tttPlayer.getColoredName(), this.tttPlayer.getRole().getColoredName(),
                            tttPlayer.getRole() == Role.TRAITOR ? foundBy.getColoredName() : foundBy.getApiPlayer().getPlayer().getName()));

            TTTScoreboard.updateScoreboard(tttPlayer.getApiPlayer());
        }
    }

    private void updateHologram(String key, boolean translate) {
        FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers().forEach(on ->on.hideHolograms(this.hologramLocation));
        if (translate) {
            HologramText hologramText = new HologramText();
            hologramText.addLine(key);
            FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers().forEach(on -> on.showHologram(hologramText, this.hologramLocation));
        } else {
            APIHologram hologram = new APIHologram(this.hologramLocation, Collections.singletonList(key));
            FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers().forEach(on -> on.showHologram(hologram));
        }
    }
}


