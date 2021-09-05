package net.fununity.games.auttt.language;

import net.fununity.main.api.common.messages.MessageList;
import net.fununity.misc.translationhandler.TranslationHandler;

public class EnglishMessage extends MessageList {

    public EnglishMessage() {
        super(TranslationHandler.getInstance().getLanguageHandler().getLanguageByCode("en"));

        add(TranslationKeys.TTT_COMMAND_TRAITORCHAT_USAGE, "tchat <message>");
        add(TranslationKeys.TTT_COMMAND_TRAITORCHAT_DESCRIPTION, "&7Send a message to all traitor.");
        add(TranslationKeys.TTT_COMMAND_TRAITORCHAT_NOTALLOWED, "&cOnly traitors can use the traitor chat.");

        add(TranslationKeys.TTT_COMMAND_COINSINFO_USAGE, "coinsinfo");
        add(TranslationKeys.TTT_COMMAND_COINSINFO_DESCRIPTION, "&7Opens a book with information about the coins system.");
        add(TranslationKeys.TTT_COMMAND_COINSINFO_BOOK, "ToDo: Add Coins info content");

        add(TranslationKeys.TTT_COMMAND_SHOP_USAGE, "shop");
        add(TranslationKeys.TTT_COMMAND_SHOP_DESCRIPTION, "&7Opens the shop.");
        add(TranslationKeys.TTT_COMMAND_SHOP_NOTAVAILABLE, "&cThis command is currently not available.");

        add(TranslationKeys.TTT_COMMAND_JOKER_USAGE, "joker");
        add(TranslationKeys.TTT_COMMAND_JOKER_DESCRIPTION, "&7Opens the joker shop.");
        add(TranslationKeys.TTT_COMMAND_JOKER_NOTAVAILABLE, "&cThis command is currently not available.");

        add(TranslationKeys.TTT_GAME_TESTER_JOINED, "&e${name} &7has entered the tester.");

        add(TranslationKeys.ROLE_CALLOUT_TITLE, "${color}Deine Rolle");
        add(TranslationKeys.ROLE_CALLOUT_SUBTITLE, "&8Du bist ${color}${role}");
        add(TranslationKeys.ROLE_CALLOUT_TEXT, "&7Du bist ${color}${role}&7! Bla");
        add(TranslationKeys.ROLE_WON, "&aThe ${role} &ahave won the game!");

        add(TranslationKeys.TTT_GAME_PLAYER_DIED, "${name} &7died!");
        add(TranslationKeys.TTT_GAME_PLAYER_FOUND, "${name} &7was found by ${found}");
        add(TranslationKeys.TTT_GAME_PLAYER_KILLED, "&7You killed ${name} &7who was ${role} &7(${coins} coins received)");
        add(TranslationKeys.TTT_GAME_PLAYER_RECEIVED_COINS, "&7You found &e${amount} coins &7in this corpse!");
        add(TranslationKeys.TTT_GAME_PLAYER_CORPSE_NOTFOUND, "&7Unidentified");

        add(TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_START, "These are your files. Analyzed corpses will be listed here with there roles. If you analyze a corpse it will take up to 40 seconds till it gets listed.");
        add(TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_CASE_SUPER, "&0Player: ${name}\n&0Role: ${role}");
        add(TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_CASE_NORMAL, "&0Player: ${name}");

        add(TranslationKeys.TTT_GAME_ITEM_SHOP_NAME, "&bShop");
        add(TranslationKeys.TTT_GAME_ITEM_SHOP_LORE, "&7Opens the shop,;&7where you can buy useful items.");
        add(TranslationKeys.TTT_GAME_ITEM_FILES_NAME, "&bFiles");
        add(TranslationKeys.TTT_GAME_ITEM_FILES_LORE, "&7Opens the detectives files");
        add(TranslationKeys.TTT_GAME_ITEM_ANALYZER_NAME, "&eCorpse Analyzer");
        add(TranslationKeys.TTT_GAME_ITEM_ANALYZER_LORE, "&7If you found a unidentified corpse;&7you can analyze it with this item.");

        // SHOP ITEM
        // TRAITOR
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ARROWS_NAME, "&ePfeilvorrat");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ARROWS_DESCRIPTION, "&7");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_TICKET_NAME, "&eFallen-Stop");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_TICKET_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_TICKET_REMOVED, "&eYou removed your trap ticket.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_NAME, "&eRadar");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_NOM_NOM_DEVICE_NAME, "&eNom-Nom Device");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_NOM_NOM_DEVICE_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_NAME, "&eStreitaxt");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_COOLDOWN, "&cYou axe is on cooldown!");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_IRON_SWORD_NAME, "&eEisenschwert");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_IRON_SWORD_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ADRENALIN_NAME, "&eAdrenalin");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ADRENALIN_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_FIREBALL_NAME, "&eInferno");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_FIREBALL_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_NAME, "&eReperatur-Kit Typ F");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_NOTHAVE, "&cThe trap was already used. You need to buy the Trap-Repair item in the shop.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_USED, "&7You used your trap-repair item.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_BOOM_BODY_NAME, "&eBoom Body");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_BOOM_BODY_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_JIHAD_NAME, "&eSprengweste");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_JIHAD_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TESTER_FAKER_NAME, "&eInnocent-Pass");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TESTER_FAKER_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_IDENT_THIEF_NAME, "&eIdentitätsdieb");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_IDENT_THIEF_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_GENERATOR_NAME, "&eSaboteur");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_GENERATOR_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_INVISIBILITY_NAME, "&eTarnknappe");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_INVISIBILITY_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ROBOT_NAME, "&eElliot KM-01");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ROBOT_DESCRIPTION, "");

        // INNOCENT
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ANTI_BOOM_BODY_NAME, "&eBoomBody-Stop");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ANTI_BOOM_BODY_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ANTI_BOOM_BODY_WARN, "&cWarning this is a boom body!");
        
        // DETECTIVE
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_NIGHTVISION_NAME, "&eNachtsichtgerät");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_NIGHTVISION_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_SUPER_IDENT_NAME, "&eSuper-Identifizierer");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_SUPER_IDENT_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_HOOK_NAME, "&eEnterhaken");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_HOOK_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_BLOCK_NAME, "&eFallenfrei");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_BLOCK_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_NAME, "&eBewegungsmelder");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_SIGNAL, "&eMove sensor! &6${name} &7came out of the vent!");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_MARKED, "&aYour move sensor was set.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_DETECTOR_NAME, "&eDetektor");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_DETECTOR_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_VENT_GIFT_NAME, "&eGiftige Luft");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_VENT_GIFT_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_HEAL_STATION_NAME, "&eSamariter");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_HEAL_STATION_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ONE_SHOT_BOW_NAME, "&eOne-Shot-Bogen");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ONE_SHOT_BOW_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MED_KIT_NAME, "Medi-Kit");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MED_KIT_DESCRIPTION, "");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_YOUTH_NAME, "&eJugend");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_YOUTH_DESCRIPTION, "");


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

        add(TranslationKeys.TTT_GUI_SHOP_TITLE, "&eItemshop");
        add(TranslationKeys.TTT_GUI_SHOP_ERROR_NOTENOUGHCOINS, "&cYou don't have enough shop coins"); // ACTIONBAR
        add(TranslationKeys.TTT_GUI_SHOP_ERROR_MAXIMUCURRENTAMOUNT, "&cYou can't have more of this shop item"); // ACTIONBAR
        add(TranslationKeys.TTT_GUI_SHOP_ERROR_MAXIMUBUYSAMOUNT, "&cYou've reached the maximum buyable amount"); // ACTIONBAR
        add(TranslationKeys.TTT_GUI_SHOP_COINS_NAME, "&eYou have &6${amount} coins");
        add(TranslationKeys.TTT_GUI_SHOP_COINS_LORE, "&7Click to get info;&7how you receive coins.");
        add(TranslationKeys.TTT_GUI_SHOP_BUYED, "&aYou have successfully bought &e${name}&a!");

        add(TranslationKeys.SCOREBOARD_YOUR_ROLE, "&6Your role");
        add(TranslationKeys.SCOREBOARD_FOUND_TRAITORS, "&6Found Traitor");
        add(TranslationKeys.SCOREBOARD_COINS, "&6Shop coins");

        // GENERATOR
        add(TranslationKeys.TTT_GAME_ROOM_GENERATOR_DISABLE_BROADCAST, "&cThe Generator was disabled!");
        add(TranslationKeys.TTT_GAME_ROOM_GENERATOR_DISABLE_NOTHAVE, "&cYou need the Saboteur item shop first"); // ACTIONBAR
        add(TranslationKeys.TTT_GAME_ROOM_GENERATOR_ENABLE_BROADCAST, "&aThe Generator was reactivated!au");
        add(TranslationKeys.TTT_GAME_ROOM_GENERATOR_ENABLE_TRYAGAIN, "&7Try again..."); // ACTIONBAR

        // VENT
        add(TranslationKeys.TTT_GAME_ROOM_VENT_TIMEREACHED, "&cYou can no longer be in a vent.");
        add(TranslationKeys.TTT_GAME_ROOM_VENT_ENTERED, "&7In Vent - &eScroll&7 to move - &6Shift&7 to leave");

        insertIntoLanguage();
    }
}
