package net.fununity.games.auttt.player;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.util.CoinsUtil;
import net.fununity.games.auttt.util.TTTScoreboard;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.common.util.SpecialChars;
import net.fununity.main.api.hologram.APIHologram;
import net.fununity.main.api.hologram.HologramText;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.item.UsefulItems;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.npc.NPC;
import net.fununity.npc.events.PlayerInteractAtNPCEvent;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayInUseEntity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;

public class PlayerCorpse {

    private static final int FOUND_PROCESS_STEPS = 30;

    protected final TTTPlayer tttPlayer;
    protected final OffsetDateTime death;

    private final Location hologramLocation;
    private final NPC npc;
    private final CorpseInventory corpseInventory;
    private TTTPlayer foundBy;

    public PlayerCorpse(TTTPlayer tttPlayer, PlayerDeathEvent event) {
        this.tttPlayer = tttPlayer;
        this.death = OffsetDateTime.now();

        this.corpseInventory = new CorpseInventory(this, event);

        this.npc = new NPC("", tttPlayer.getApiPlayer().getPlayer().getLocation(), "ttt_undefined", false);
        this.npc.setLookAtPlayer(false);
        this.npc.click(this::clickedOn);

        this.hologramLocation = this.npc.getLocation().clone().add(0, 1.8D, 0);
        updateHologram(TranslationKeys.TTT_GAME_PLAYER_CORPSE_NOTFOUND, true);
    }

    public void clickedOn(PlayerInteractAtNPCEvent event) {
        APIPlayer apiPlayer = event.getPlayer();
        if (foundBy != null || event.getAction() != PacketPlayInUseEntity.EnumEntityUseAction.INTERACT || event.getHand() != EquipmentSlot.HAND ||
                GameLogic.getInstance().gameManager.isSpectator(apiPlayer.getPlayer())) return;
        found(GameLogic.getInstance().getTTTPlayer(apiPlayer.getUniqueId()));
    }

    public void found(TTTPlayer foundBy) {
        this.foundBy = foundBy;
        this.tttPlayer.setFound(true);
        CoinsUtil.foundBody(foundBy, this.tttPlayer);

        updateHologram(this.tttPlayer.getColoredName(), false);
        this.npc.equip(EnumItemSlot.HEAD, new ItemBuilder(UsefulItems.PLAYER_HEAD).setSkullOwner(this.tttPlayer.getApiPlayer().getDatabasePlayer().getPlayerTextures()).craft());
        this.npc.click((event) -> this.corpseInventory.openGUI(event.getPlayer()));

        for (TTTPlayer tttPlayer : GameLogic.getInstance().getTTTPlayers()) {
            tttPlayer.getApiPlayer().sendMessage(TranslationKeys.TTT_GAME_PLAYER_FOUND,
                    Arrays.asList("${name}", "${role}", "${found}"),
                    Arrays.asList(this.tttPlayer.getColoredName(), this.tttPlayer.getRole().getColoredName(),
                            tttPlayer.getRole() == Role.TRAITOR ? foundBy.getColoredName() : foundBy.getApiPlayer().getPlayer().getName()));

            TTTScoreboard.updateScoreboard(tttPlayer);
        }
    }

    private void updateHologram(String key, boolean translate) {
        on.hideHolograms(this.hologramLocation);
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


