package net.fununity.games.auttt.language;

import net.fununity.main.api.common.messages.MessageList;
import net.fununity.misc.translationhandler.TranslationHandler;

public class EnglishMessage extends MessageList {

    public EnglishMessage() {
        super(TranslationHandler.getInstance().getLanguageHandler().getLanguageByCode("en"));

        add(TranslationKeys.ROLE_CALLOUT_TITLE, "${color}Deine Rolle");
        add(TranslationKeys.ROLE_CALLOUT_SUBTITLE, "&8Du bist ${color}${role}");
        add(TranslationKeys.ROLE_CALLOUT_TEXT, "&7Du bist ${color}${role}&7! Bla");
        add(TranslationKeys.ROLE_WON, "&aThe ${role} &ahave won the game!");

        add(TranslationKeys.PLAYER_DIED, "${name} &7died!");
        add(TranslationKeys.PLAYER_FOUND, "${name} &7was found by ${found}");

        add(TranslationKeys.TTT_GUI_CORPSE_INVENTORY, "&eDeath inventory");
        add(TranslationKeys.TTT_GUI_CORPSE_DEATH_SINCE, "&8Death is &e${till}ago");
        add(TranslationKeys.TTT_GUI_CORPSE_KILL_WEAPON, "&eThis is the kill weapon");
        add(TranslationKeys.TTT_GUI_CORPSE_FOUND_BY, "&7The corpse was found by this player.");
        add(TranslationKeys.TTT_GUI_CORPSE_FOUND_SINCE, "&8Found &e${till}ago");

        add(TranslationKeys.SCOREBOARD_YOUR_ROLE, "&6Your role");
        add(TranslationKeys.SCOREBOARD_FOUND_TRAITORS, "&6Found Traitor");

        insertIntoLanguage();
    }
}
