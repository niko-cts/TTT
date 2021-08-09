package net.fununity.games.auttt.language;

import net.fununity.main.api.common.messages.MessageList;
import net.fununity.misc.translationhandler.TranslationHandler;

public class EnglishMessage extends MessageList {

    public EnglishMessage() {
        super(TranslationHandler.getInstance().getLanguageHandler().getLanguageByCode("en"));

        add(TranslationKeys.TTT_COMMAND_TRAITORCHAT_USAGE, "/tchat <message>");
        add(TranslationKeys.TTT_COMMAND_TRAITORCHAT_DESCRIPTION, "&7Send a message to all traitor.");
        add(TranslationKeys.TTT_COMMAND_TRAITORCHAT_NOTALLOWED, "&cYou can not execute this command.");

        add(TranslationKeys.ROLE_CALLOUT_TITLE, "${color}Deine Rolle");
        add(TranslationKeys.ROLE_CALLOUT_SUBTITLE, "&8Du bist ${color}${role}");
        add(TranslationKeys.ROLE_CALLOUT_TEXT, "&7Du bist ${color}${role}&7! Bla");
        add(TranslationKeys.ROLE_WON, "&aThe ${role} &ahave won the game!");

        add(TranslationKeys.TTT_GAME_PLAYER_DIED, "${name} &7died!");
        add(TranslationKeys.TTT_GAME_PLAYER_FOUND, "${name} &7was found by ${found}");
        add(TranslationKeys.TTT_GAME_PLAYER_KILLED, "&7You killed ${name} &7who was ${role} &7(${coins} coins received)");
        add(TranslationKeys.TTT_GAME_PLAYER_RECEIVED_COINS, "&7You found &e${amount} coins &7in this corpse!");

        add(TranslationKeys.TTT_GAME_ITEM_SHOP_NAME, "&bShop");
        add(TranslationKeys.TTT_GAME_ITEM_SHOP_LORE, "&7Opens the shop,;&7where you can buy useful items.");
        add(TranslationKeys.TTT_GAME_ITEM_ANALYZER_NAME, "&eCorpse Analyzer");
        add(TranslationKeys.TTT_GAME_ITEM_ANALYZER_LORE, "&7If you found a unidentified corpse;&7you can analyze it with this item.");

        add(TranslationKeys.TTT_GUI_CORPSE_TIME_CLOSE, "&7This corpse has fresh blood");
        add(TranslationKeys.TTT_GUI_CORPSE_TIME_LONG, "&7This corpse was killed &ea while ago");
        add(TranslationKeys.TTT_GUI_CORPSE_KILLWEAPON, "&eSplinter of the kill weapon");
        add(TranslationKeys.TTT_GUI_CORPSE_ANALYZING, "&6Corpse is being analyzed...;&7This process takes 30 seconds");
        add(TranslationKeys.TTT_GUI_CORPSE_ANALYZABLE, "&7Click here to start analyzing;&7this corpse to find more information;&7about the death.");

        add(TranslationKeys.TTT_GUI_JOKERSHOP_TITLE, "&eJoker shop");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_TRAITOR_NAME, "&cTraitor joker &8- &e${cost} Tokens");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_TRAITOR_LORE, "&7Buy this joker card, to get;&7prioritized at the traitor selection.;&7If you won't get traitor,;&7you'll receive back your money.;;&6&lPremiums get ${off} off!");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_ALREADY_BOUGHT, "&cYou have already bought this joker.");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_DETECTIVE_NAME, "&dDetective joker &8- &e${cost} Tokens");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_DETECTIVE_LORE, "&7Buy this joker card, to get;&7prioritized at the detective selection.;&7If you won't get detective,;&7you'll receive back your money.;;&6&lPremiums get ${off} off!");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_CONFIRM_TITLE, "&2Confirm purchase");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_GAME_STARTED, "&cThe game has already started!");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_CONFIRM_SEND, "&aYou successfully purchased the joker!");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_PAYBACK_INFO, "&7You joker card was not used. You have received your money back.");


        add(TranslationKeys.SCOREBOARD_YOUR_ROLE, "&6Your role");
        add(TranslationKeys.SCOREBOARD_FOUND_TRAITORS, "&6Found Traitor");
        add(TranslationKeys.SCOREBOARD_COINS, "&6Shop coins");

        insertIntoLanguage();
    }
}
