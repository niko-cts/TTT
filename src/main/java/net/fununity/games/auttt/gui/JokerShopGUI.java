package net.fununity.games.auttt.gui;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.actionbar.ActionbarMessage;
import net.fununity.main.api.inventory.ClickAction;
import net.fununity.main.api.inventory.CustomInventory;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.item.UsefulItems;
import net.fununity.main.api.messages.MessagePrefix;
import net.fununity.main.api.player.APIPlayer;
import net.fununity.main.api.player.BalanceHandler;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import net.fununity.misc.translationhandler.translations.Language;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Queue;
import java.util.UUID;

public class JokerShopGUI {

    private JokerShopGUI() {
        throw new UnsupportedOperationException("JokerShopGUI should not be instantiated.");
    }

    public static final int JOKER_TRAITOR = 500;
    public static final int JOKER_DETECTIVE = 400;

    public static void open(APIPlayer apiPlayer) {
        Language lang = apiPlayer.getLanguage();
        CustomInventory menu = new CustomInventory(lang.getTranslation(TranslationKeys.TTT_GUI_JOKERSHOP_TITLE), 9*3);

        int traitorCost = (int) (JOKER_TRAITOR * (apiPlayer.hasPermission("ttt.joker") ? 0.5 : 1));

        ItemStack traitorItem = new ItemBuilder(Material.REDSTONE)
                .setName(lang.getTranslation(TranslationKeys.TTT_GUI_JOKERSHOP_TRAITOR_NAME, "${cost}", ""+traitorCost))
                .setLore(lang.getTranslation(TranslationKeys.TTT_GUI_JOKERSHOP_TRAITOR_LORE, "${off}", "50%").split(";")).craft();
        menu.setItem(12, traitorItem, new ClickAction() {
            @Override
            public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int i) {
                if (GameLogic.getInstance().getTraitorJoker().contains(apiPlayer.getUniqueId())) {
                    apiPlayer.sendActionbar(new ActionbarMessage(TranslationKeys.TTT_GUI_JOKERSHOP_ALREADY_BOUGHT));
                    return;
                }
                if (BalanceHandler.getInstance().getMoney(apiPlayer.getUniqueId()) < traitorCost) {
                    apiPlayer.sendActionbar(new ActionbarMessage(net.fununity.main.api.messages.TranslationKeys.API_PLAYER_MONEY_NOT_ENOUGH));
                    return;
                }
                apiPlayer.getPlayer().closeInventory();
                openConfirmGUI(apiPlayer, Role.TRAITOR, traitorCost, traitorItem);
            }
        });

        int detectiveCost = (int) (JOKER_DETECTIVE * (apiPlayer.hasPermission("ttt.joker") ? 0.5 : 1));
        ItemStack detectiveItem = new ItemBuilder(Material.INK_SACK, (short) 4)
                .setName(lang.getTranslation(TranslationKeys.TTT_GUI_JOKERSHOP_DETECTIVE_NAME, "${cost}", ""+detectiveCost))
                .setLore(lang.getTranslation(TranslationKeys.TTT_GUI_JOKERSHOP_DETECTIVE_LORE, "${off}", "50%").split(";")).craft();
        menu.setItem(14, detectiveItem, new ClickAction() {
            @Override
            public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int i) {
                if (GameLogic.getInstance().getDetectiveJoker().contains(apiPlayer.getUniqueId())) {
                    apiPlayer.sendActionbar(new ActionbarMessage(TranslationKeys.TTT_GUI_JOKERSHOP_ALREADY_BOUGHT));
                    return;
                }
                if (BalanceHandler.getInstance().getMoney(apiPlayer.getUniqueId()) < detectiveCost) {
                    apiPlayer.sendActionbar(new ActionbarMessage(net.fununity.main.api.messages.TranslationKeys.API_PLAYER_MONEY_NOT_ENOUGH));
                    return;
                }
                apiPlayer.getPlayer().closeInventory();
                openConfirmGUI(apiPlayer, Role.DETECTIVE, detectiveCost, detectiveItem);
            }
        });

        menu.fill(UsefulItems.BACKGROUND_BLACK);
        menu.open(apiPlayer);
    }

    private static void openConfirmGUI(APIPlayer apiPlayer, Role role, int cost, ItemStack item) {
        Language lang = apiPlayer.getLanguage();
        CustomInventory menu = new CustomInventory(lang.getTranslation(TranslationKeys.TTT_GUI_JOKERSHOP_CONFIRM_TITLE), 9*3);

        menu.setItem(15, item, new ClickAction() {
            @Override
            public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int i) {
                if (BalanceHandler.getInstance().getMoney(apiPlayer.getUniqueId()) < cost) {
                    apiPlayer.sendActionbar(new ActionbarMessage(net.fununity.main.api.messages.TranslationKeys.API_PLAYER_MONEY_NOT_ENOUGH));
                    return;
                }
                apiPlayer.getPlayer().closeInventory();
                if (GameManager.getInstance().getCurrentGameState() != GameState.LOBBY) {
                    apiPlayer.sendMessage(MessagePrefix.ERROR, TranslationKeys.TTT_GUI_JOKERSHOP_GAME_STARTED);
                    return;
                }
                BalanceHandler.getInstance().removeMoney(apiPlayer.getUniqueId(), cost, true);
                if (role == Role.TRAITOR)
                   GameLogic.getInstance().getTraitorJoker().add(apiPlayer.getUniqueId());
                else
                    GameLogic.getInstance().getDetectiveJoker().add(apiPlayer.getUniqueId());

                apiPlayer.sendMessage(MessagePrefix.SUCCESS, TranslationKeys.TTT_GUI_JOKERSHOP_CONFIRM_SEND, "${role}", role.getColoredName());
            }
        });

        menu.fill(UsefulItems.BACKGROUND_BLACK);
        menu.open(apiPlayer);
    }

    /**
     * Removes joker player and give them back their money.
     * @since 0.1
     */
    public static void payback() {
        Queue<UUID> traitorJoker = GameLogic.getInstance().getTraitorJoker();
        while (!traitorJoker.isEmpty()) {
            UUID uuid = traitorJoker.poll();
            payback(uuid, JOKER_TRAITOR);
        }
        Queue<UUID> detectiveJoker = GameLogic.getInstance().getDetectiveJoker();
        while (!detectiveJoker.isEmpty()) {
            UUID uuid = detectiveJoker.poll();
            payback(uuid, JOKER_DETECTIVE);
        }
    }

    /**
     * Returns the money back to the given player.
     * @param uuid UUID - the uuid of the player.
     * @param maxCost int - the maximum amount without the reduction.
     * @since 0.1
     */
    public static void payback(UUID uuid, int maxCost) {
        APIPlayer apiPlayer = FunUnityAPI.getInstance().getPlayerHandler().getPlayer(uuid);
        if (apiPlayer != null) {
            int cost = (int) (maxCost * (apiPlayer.hasPermission("ttt.joker") ? 0.5 : 1));
            BalanceHandler.getInstance().giveMoney(uuid, cost, false);
            apiPlayer.sendMessage(MessagePrefix.INFO, TranslationKeys.TTT_GUI_JOKERSHOP_PAYBACK_INFO);
        }
    }
}
