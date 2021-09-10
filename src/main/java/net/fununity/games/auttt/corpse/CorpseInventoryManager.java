package net.fununity.games.auttt.corpse;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.detectives.DetectiveItems;
import net.fununity.games.auttt.util.DetectiveFilesUtil;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.inventory.ClickAction;
import net.fununity.main.api.inventory.CustomInventory;
import net.fununity.main.api.item.UsefulItems;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * This class manages all inventories for one specific corpse.
 * @see PlayerCorpse
 * @author Niko
 * @since 1.1
 */
public class CorpseInventoryManager {

    private final Map<UUID, IndividualCorpseInventory> corpseInventories;
    private final Set<UUID> analyzing;

    private final PlayerCorpse playerCorpse;
    private final DefaultCorpseInventory defaultCorpseInventory;

    /**
     * Instantiates the class.
     * @param playerCorpse {@link PlayerCorpse} - the player corpse.
     * @param event PlayerDeathEvent - the death event of the player.
     * @since 1.1
     */
    public CorpseInventoryManager(PlayerCorpse playerCorpse, PlayerDeathEvent event) {
        this.corpseInventories = new HashMap<>();
        this.analyzing = new HashSet<>();

        this.playerCorpse = playerCorpse;
        this.defaultCorpseInventory = new DefaultCorpseInventory(this, playerCorpse, event);
    }

    /**
     * Opens the inventory for the given player.
     * @param apiPlayer APIPlayer - the player.
     * @since 1.1
     */
    public void openInventory(APIPlayer apiPlayer) {
        if (corpseInventories.containsKey(apiPlayer.getUniqueId())) {
            TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(apiPlayer.getUniqueId());
            if (tttPlayer != null)
                corpseInventories.get(apiPlayer.getUniqueId()).openInventory(tttPlayer);
            return;
        }

        CustomInventory menu = new CustomInventory(playerCorpse.tttPlayer.getColoredName(), 9 * 5);
        menu.setSpecialHolder(apiPlayer.getUniqueId().toString() + "|" + this.playerCorpse.tttPlayer.getApiPlayer().getUniqueId());


        for (int i = 1; i < 9; i++) {
            menu.setItem(i, playerCorpse.tttPlayer.getRole().getGlass());
            menu.setItem(menu.getInventory().getSize() - i, playerCorpse.tttPlayer.getRole().getGlass());
        }
        for (int i = 0; i < menu.getInventory().getSize(); i += 9) {
            menu.setItem(i, playerCorpse.tttPlayer.getRole().getGlass());
            if (i + 8 < menu.getInventory().getSize())
                menu.setItem(i + 8, playerCorpse.tttPlayer.getRole().getGlass());
        }

        Language lang = apiPlayer.getLanguage();

        menu.setItem(22, UsefulItems.getPlayerHead(playerCorpse.tttPlayer.getApiPlayer()).setName(playerCorpse.tttPlayer.getColoredName())
                .setLore(lang.getTranslation(analyzing.contains(apiPlayer.getUniqueId()) ? TranslationKeys.TTT_GUI_CORPSE_ANALYZING : TranslationKeys.TTT_GUI_CORPSE_ANALYZABLE).split(";")).craft(), new ClickAction() {
            @Override
            public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int i) {
                if (analyzing.contains(apiPlayer.getUniqueId()) || GameManager.getInstance().getCurrentGameState() != GameState.INGAME) return;
                TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(apiPlayer.getUniqueId());
                if (tttPlayer.getRole() == Role.DETECTIVE)
                    startDetectiveFilesTimer();
                apiPlayer.getPlayer().closeInventory();

                analyzing.add(apiPlayer.getUniqueId());
                Bukkit.getScheduler().runTaskLater(TTT.getInstance(), () -> {

                    corpseInventories.put(apiPlayer.getUniqueId(), new IndividualCorpseInventory(defaultCorpseInventory));
                    analyzing.remove(apiPlayer.getUniqueId());

                    if (apiPlayer.hasCustomData("openInv") && ((CustomInventory) apiPlayer.getCustomData("openInv"))
                            .getSpecialHolder().equals(apiPlayer.getUniqueId().toString() + "|" + playerCorpse.tttPlayer.getApiPlayer().getUniqueId())) {
                        openInventory(apiPlayer);
                    }


                }, 20L * (tttPlayer.hasShopItem(DetectiveItems.SUPER_IDENT) ? 10 : 30));
                openInventory(apiPlayer);
            }
        });

        menu.open(apiPlayer);
    }

    private void startDetectiveFilesTimer() {
        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), () -> DetectiveFilesUtil.analyzed(playerCorpse.tttPlayer), 20 * 40);
    }

    public void reopenCorpseGUI() {
        for (APIPlayer onlinePlayer : FunUnityAPI.getInstance().getPlayerHandler().getOnlinePlayers()) {
            if (onlinePlayer.hasCustomData("openInv") &&
                    ((CustomInventory) onlinePlayer.getCustomData("openInv")).getSpecialHolder().equals("corpse-" + playerCorpse.tttPlayer.getApiPlayer().getUniqueId())) {
                onlinePlayer.getPlayer().closeInventory();
                openInventory(onlinePlayer);
            }
        }
    }
}
