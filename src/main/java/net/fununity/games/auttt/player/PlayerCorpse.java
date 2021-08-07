package net.fununity.games.auttt.player;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.util.TTTScoreboard;
import net.fununity.main.api.common.util.DurationUtil;
import net.fununity.main.api.inventory.ClickAction;
import net.fununity.main.api.inventory.CustomInventory;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.item.UsefulItems;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.misc.translationhandler.translations.Language;
import net.fununity.npc.NPC;
import net.fununity.npc.events.PlayerInteractAtNPCEvent;
import net.minecraft.server.v1_12_R1.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class PlayerCorpse {

    private final TTTPlayer tttPlayer;
    private final Inventory deathChest;
    private final OffsetDateTime deathTime;
    private final ItemStack weapon;

    private NPC npc;
    private TTTPlayer foundBy;
    private OffsetDateTime foundTime;

    public PlayerCorpse(TTTPlayer tttPlayer, ItemStack[] items, ItemStack weapon) {
        this.tttPlayer = tttPlayer;
        this.deathChest = Bukkit.createInventory(null, 9 * 5);
        for (int i = 0; i < items.length; i++)
            deathChest.setItem(i, items[i]);
        this.deathTime = OffsetDateTime.now();
        this.weapon = weapon;

        this.npc = new NPC("?", tttPlayer.getApiPlayer().getPlayer().getLocation(), "ttt_undefined", false);
        this.npc.setLookAtPlayer(false);
        this.npc.click(this::clickedOn);
    }

    public void found(TTTPlayer foundBy) {
        this.foundBy = foundBy;
        this.foundTime = OffsetDateTime.now();
        this.tttPlayer.setFound(true);

        Location location = this.npc.getLocation().clone();
        this.npc.destroy();
        this.npc = new NPC(this.tttPlayer.getColoredName(), location, this.tttPlayer.getApiPlayer().getDatabasePlayer().getPlayerTextures(), false);
        this.npc.setLookAtPlayer(false);
        this.npc.click((event) -> openGUI(event.getPlayer()));

        for (TTTPlayer tttPlayer : GameLogic.getInstance().getTTTPlayers()) {
            tttPlayer.getApiPlayer().sendMessage(TranslationKeys.PLAYER_FOUND,
                    Arrays.asList("${name}", "${found}"),
                    Arrays.asList(this.tttPlayer.getColoredName(), foundBy.getColoredName()));

            TTTScoreboard.updateScoreboard(tttPlayer);
        }
    }

    public void clickedOn(PlayerInteractAtNPCEvent event) {
        APIPlayer apiPlayer = event.getPlayer();
        if (foundBy != null || event.getAction() != PacketPlayInUseEntity.EnumEntityUseAction.INTERACT || event.getHand() != EquipmentSlot.HAND ||
                GameLogic.getInstance().gameManager.isSpectator(apiPlayer.getPlayer())) return;
        TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(apiPlayer.getUniqueId());
        if (tttPlayer != null) {
            found(tttPlayer);
            openGUI(apiPlayer);
        }
    }

    public void openGUI(APIPlayer apiPlayer) {
        CustomInventory menu = new CustomInventory(tttPlayer.getColoredName(), 9 * 3);
        menu.fill(UsefulItems.BACKGROUND_BLACK);
        Language lang = apiPlayer.getLanguage();

        menu.setItem(11, UsefulItems.getPlayerHead(tttPlayer.getApiPlayer()).setName(tttPlayer.getColoredName()).craft());
        menu.setItem(12, new ItemBuilder(Material.CHEST).setName(lang.getTranslation(TranslationKeys.TTT_GUI_CORPSE_INVENTORY)).craft(), new ClickAction() {
            @Override
            public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int i) {
                if (!GameLogic.getInstance().isIngame(apiPlayer.getPlayer())) return;

                apiPlayer.getPlayer().closeInventory();
                apiPlayer.getPlayer().openInventory(deathChest);
            }
        });
        menu.setItem(13, new ItemBuilder(Material.WATCH)
                .setName(lang.getTranslation(TranslationKeys.TTT_GUI_CORPSE_DEATH_SINCE, "${till}", DurationUtil.getDuration(ChronoUnit.SECONDS.between(deathTime, OffsetDateTime.now())))).craft());

        menu.setItem(14, new ItemBuilder(weapon).setName(lang.getTranslation(TranslationKeys.TTT_GUI_CORPSE_KILL_WEAPON)).craft());

        menu.setItem(15, UsefulItems.getPlayerHead(foundBy.getApiPlayer())
                .setName(foundBy.getColoredName())
                .setLore(lang.getTranslation(TranslationKeys.TTT_GUI_CORPSE_FOUND_BY)).craft());
        menu.setItem(16, new ItemBuilder(Material.WATCH)
                .setName(lang.getTranslation(TranslationKeys.TTT_GUI_CORPSE_FOUND_SINCE, "${till}", DurationUtil.getDuration(ChronoUnit.SECONDS.between(foundTime, OffsetDateTime.now())))).craft());
        menu.open(apiPlayer);
    }
}


