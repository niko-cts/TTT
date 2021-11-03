package net.fununity.games.auttt.language;

import net.fununity.main.api.common.messages.MessageList;
import net.fununity.misc.translationhandler.TranslationHandler;

public class EnglishMessages extends MessageList {

    public EnglishMessages() {
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
        add(TranslationKeys.TTT_COMMAND_SHOP_NOTINRANGE, "&cYou can only buy a shop item between 1 - ${max}.");

        add(TranslationKeys.TTT_COMMAND_JOKER_USAGE, "joker");
        add(TranslationKeys.TTT_COMMAND_JOKER_DESCRIPTION, "&7Opens the joker shop.");
        add(TranslationKeys.TTT_COMMAND_JOKER_NOTAVAILABLE, "&cThis command is currently not available.");

        add(TranslationKeys.TTT_GAME_ROOM_TESTER_JOINED, "&e${name} &7has entered the tester.");
        add(TranslationKeys.TTT_GAME_ROOM_TESTER_COOLDOWN, "&cThe tester has a cooldown of 5 seconds.");

        add(TranslationKeys.ROLE_CALLOUT_TITLE, "${role}");
        add(TranslationKeys.ROLE_CALLOUT_SUBTITLE, "&7You are ${role}");
        add(TranslationKeys.ROLE_CALLOUT_TEXT, "&7You are ${role}&7! &7Good luck!");
        add(TranslationKeys.ROLE_WON, "&aThe ${role} &ahave won the game!");

        add(TranslationKeys.TTT_GAME_PLAYER_DIED, "${name} &7died!");
        add(TranslationKeys.TTT_GAME_PLAYER_FOUND, "${name} &7was found by ${found}");
        add(TranslationKeys.TTT_GAME_PLAYER_KILLED, "&7You killed ${name} &7who was ${role} &7(${coins} coins received)");
        add(TranslationKeys.TTT_GAME_PLAYER_RECEIVED_COINS, "&7You found &e${amount} coins &7in this corpse!");
        add(TranslationKeys.TTT_GAME_PLAYER_CORPSE_NOTFOUND, "&7Unidentified");

        add(TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_START, "These are your files. Analyzed corpses will be listed here with there roles. If you analyze a corpse it will take up to 40 seconds till it gets listed.");
        add(TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_CASE_SUPER, "&0Player: ${name}\n&0Role: ${role}");
        add(TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_CASE_NORMAL, "&0Player: ${name}");

        add(TranslationKeys.TTT_GAME_ENDERCHEST_CANTOPEN, "&cYou can open the enderchest after the protection time.");

        add(TranslationKeys.TTT_GAME_ITEM_SHOP_NAME, "&bShop");
        add(TranslationKeys.TTT_GAME_ITEM_SHOP_LORE, "&7Opens the shop,;&7where you can buy useful items.");
        add(TranslationKeys.TTT_GAME_ITEM_FILES_NAME, "&bFiles");
        add(TranslationKeys.TTT_GAME_ITEM_FILES_LORE, "&7Opens the detectives files");
        add(TranslationKeys.TTT_GAME_ITEM_ANALYZER_NAME, "&eCorpse Analyzer");
        add(TranslationKeys.TTT_GAME_ITEM_ANALYZER_LORE, "&7If you found a unidentified corpse;&7you can analyze it with this item.");

        // SHOP ITEM
        // TRAITOR
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ARROWS_NAME, "&eArrow Supply");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ARROWS_DESCRIPTION, "&7Get 10 arrows for your bow.;;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_TICKET_NAME, "&eFallen-Stop");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_TICKET_DESCRIPTION, "&7If this item get bought,;&7the traitor trap won't work anymore.;&7If you buy this another time,;&7it can be activated it again.;;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_TICKET_REMOVED, "&eYou removed your trap ticket.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_NAME, "&eRadar");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_DESCRIPTION, "&7Shows you the closest player to you.;;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_NOM_NOM_DEVICE_NAME, "&eNom-Nom Device");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_NOM_NOM_DEVICE_DESCRIPTION, "&7Consume every 60 seconds a body;&7 to regenerate 10 hearts.;;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_NAME, "&eBattle Ax");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_DESCRIPTION, "&7These heavy weapons make 5 hearts;&7damage with 1 punch. ;&7But because of the weight,;&7it will make you slow.;;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_COOLDOWN, "&cYou axe is on cooldown!");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_IRON_SWORD_NAME, "&eIron Sword");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_IRON_SWORD_DESCRIPTION, "&7Get an iron sword, even if;&7no enderchest was available.;;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ADRENALIN_NAME, "&eAdrenaline");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ADRENALIN_DESCRIPTION, "&7This potion gives you a speed of 10 seconds;&7when you drink it.;;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_FIREBALL_NAME, "&eInferno");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_FIREBALL_DESCRIPTION, "&7This fiery ball leaves an ocean of flames;&7on his impact point.;;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_NAME, "&eReperatur-Kit Typ F");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_DESCRIPTION, ";;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_NOTHAVE, "&cThe trap was already used. You need to buy the Trap-Repair item in the shop.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_USED, "&7You used your trap-repair item.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_BOOM_BODY_NAME, "&eBoom Body");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_BOOM_BODY_DESCRIPTION, "&7If it gets identified from a detective or innocent,;&7it will make....;&7right! boom!;;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_JIHAD_NAME, "&eExplosive Vest");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_JIHAD_DESCRIPTION, "&7This special vest will explode after 5 seconds;&7when you equip it.;&7But sadly you won't survive it...;;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TESTER_FAKER_NAME, "&eInnocent-Pass");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TESTER_FAKER_DESCRIPTION, "&7Are you always suspected?;&7That jammer influences the traitor-tester;&7that you won't be shown as a traitor;&7 when you enter it.;;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_IDENT_THIEF_NAME, "&eIdentitätsdieb");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_IDENT_THIEF_DESCRIPTION, ";;&7Costs: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_GENERATOR_NAME, "&eSaboteur");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_GENERATOR_DESCRIPTION, "&7With this item, you can deactivate the generator.;&7For every innocent and detective without a night vision device,;&7it will be dark.;;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_INVISIBILITY_NAME, "&eCamouflage Cap");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_INVISIBILITY_DESCRIPTION, "&7Disappear for 30 seconds in the guard;&7of the invisibility.;;&7Costs: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_SENTRYGUN_NAME, "&eSentryGun");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_SENTRYGUN_DESCRIPTION, "&7A powerful gun tower who destroys everything;&7that gets too close to him.;&7Unfortunately, his view is limited.;;&7Cost: &e<tokensanzahl> Tokens");

        // INNOCENT
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ANTI_BOOM_BODY_NAME, "&eBoomBody-Stop");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ANTI_BOOM_BODY_DESCRIPTION, ";;&7Costs: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ANTI_BOOM_BODY_WARN, "&cWarning this is a boom body!");
        
        // DETECTIVE
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_NIGHTVISION_NAME, "&eNight Vision Device");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_NIGHTVISION_DESCRIPTION, "&7Even when the generator got sabotaged,; you can see it in the dark with that device.;;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_SUPER_IDENT_NAME, "&eSuper-Identifizierer");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_SUPER_IDENT_DESCRIPTION, ";;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_HOOK_NAME, "&eGrappling Hook");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_HOOK_DESCRIPTION, ";;&7Costs: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_HOOK_COOLDOWN, "&cYour grappling hook has a cooldown.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_BLOCK_NAME, "&eTrap Free");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_BLOCK_DESCRIPTION, ";;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_NAME, "&eMotion Detector");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_DESCRIPTION, ";;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_SIGNAL, "&eMove sensor! &7Someone came out of the vent!");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_MARKED, "&aYour move sensor was set.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_DETECTOR_NAME, "&eDetector");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_DETECTOR_DESCRIPTION, ";;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_VENT_GIFT_NAME, "&ePoisoning Air");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_VENT_GIFT_DESCRIPTION, ";;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_HEAL_STATION_NAME, "&eSamaritan");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_HEAL_STATION_DESCRIPTION, ";;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ONE_SHOT_BOW_NAME, "&eOne-Shot-Bow");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ONE_SHOT_BOW_DESCRIPTION, ";;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MED_KIT_NAME, "Medi-Kit");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MED_KIT_DESCRIPTION, ";;&7Cost: &e<tokensanzahl> Tokens");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_YOUTH_NAME, "&eYouth");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_YOUTH_DESCRIPTION, ";;&7Cost: &e<tokensanzahl> Tokens");


        add(TranslationKeys.TTT_GUI_CORPSE_TIME_CLOSE, "&7This corpse has fresh blood");
        add(TranslationKeys.TTT_GUI_CORPSE_TIME_LONG, "&7This corpse was killed &ea while ago");
        add(TranslationKeys.TTT_GUI_CORPSE_KILLWEAPON, "&eSplinter of the kill weapon");
        add(TranslationKeys.TTT_GUI_CORPSE_ANALYZING, "&6Corpse is being analyzed...;&7This process takes 30 seconds");
        add(TranslationKeys.TTT_GUI_CORPSE_ANALYZABLE, "&7Click here to start analyzing;&7this corpse to find more information;&7about the death.");

        add(TranslationKeys.TTT_GUI_JOKERSHOP_TITLE, "&eJoker shop");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_TRAITOR_NAME, "&cTraitor joker &8- &e${cost} Tokens");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_TRAITOR_LORE, "&7Buy this joker card, to get;&7prioritized at the traitor selection.;&7If you won't get traitor,;&7you'll receive back your money.;;&6&lPremiums get ${off} off!");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_ALREADY_BOUGHT, "&cYou have already bought a joker.");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_DETECTIVE_NAME, "&dDetective joker &8- &e${cost} Tokens");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_DETECTIVE_LORE, "&7Buy this joker card, to get;&7prioritized at the detective selection.;&7If you won't get detective,;&7you'll receive back your money.;;&6&lPremiums get ${off} off!");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_CONFIRM_TITLE, "&2Confirm purchase");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_CONFIRM_INFO, "&7Buy this joker card and;&7receive a prioritized selection;&7Keep in mind that the prioritized;&7selection is not random!");
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

        add(TranslationKeys.SCOREBOARD_YOUR_ROLE, "&7Your Role:");
        add(TranslationKeys.SCOREBOARD_FOUND_TRAITORS, "&7Found Traitor:");
        add(TranslationKeys.SCOREBOARD_COINS, "&7Coins:");

        // GENERATOR
        add(TranslationKeys.TTT_GAME_ROOM_GENERATOR_DISABLE_BROADCAST, "&cThe Generator was disabled!");
        add(TranslationKeys.TTT_GAME_ROOM_GENERATOR_DISABLE_NOTHAVE, "&cYou need the Saboteur item shop first"); // ACTIONBAR
        add(TranslationKeys.TTT_GAME_ROOM_GENERATOR_ENABLE_BROADCAST, "&aThe Generator was reactivated!au");
        add(TranslationKeys.TTT_GAME_ROOM_GENERATOR_ENABLE_TRYAGAIN, "&7Try again..."); // ACTIONBAR

        // VENT
        add(TranslationKeys.TTT_GAME_ROOM_VENT_TIMEREACHED, "&cYou can no longer be in a vent.");
        add(TranslationKeys.TTT_GAME_ROOM_VENT_ENTERED, "&7In Vent - &eScroll&7 to move - &6Shift&7 to leave");
        add(TranslationKeys.TTT_GAME_ROOM_VENT_CANTENTER, "&cOnly Traitor can enter the vent.");

        //TRAP
        add(TranslationKeys.TTT_GAME_ROOM_TRAP_CANTENTER, "&cOnly Traitor can activate the trap.");
        add(TranslationKeys.TTT_GAME_ROOM_TRAP_PROTECTED, "&cThe trap was protected by a detective.");


        insertIntoLanguage();
    }
}
