package net.fununity.games.auttt.language;

import net.fununity.main.api.common.messages.MessageList;
import net.fununity.misc.translationhandler.TranslationHandler;

public class GermanMessages extends MessageList {

    public GermanMessages() {
        super(TranslationHandler.getInstance().getLanguageHandler().getLanguageByCode("de"));

        add(TranslationKeys.TTT_COMMAND_TRAITORCHAT_USAGE, "tchat <message>");
        add(TranslationKeys.TTT_COMMAND_TRAITORCHAT_DESCRIPTION, "&7Sende eine Nachricht an den Traitor-Chat.");
        add(TranslationKeys.TTT_COMMAND_TRAITORCHAT_NOTALLOWED, "&7Nur &4Traitor &7können den Traitor-Chat benutzen!");

        add(TranslationKeys.TTT_COMMAND_COINSINFO_USAGE, "coinsinfo");
        add(TranslationKeys.TTT_COMMAND_COINSINFO_DESCRIPTION, "&7Erfahre mehr zur Verwendung von Punkten!");
        add(TranslationKeys.TTT_COMMAND_COINSINFO_BOOK, "ToDo: Add Coins info content");

        add(TranslationKeys.TTT_COMMAND_SHOP_USAGE, "shop (<id>)");
        add(TranslationKeys.TTT_COMMAND_SHOP_DESCRIPTION, "&7Öffne deinen Shop.");
        add(TranslationKeys.TTT_COMMAND_SHOP_NOTAVAILABLE, "&7Der Shop ist erst &cnach &7der Schutzzeit verfügbar!");
        add(TranslationKeys.TTT_COMMAND_SHOP_NOTINRANGE, "&cID unbekannt&7! Nur IDs zwischen 1 und ${max} existieren.");

        add(TranslationKeys.TTT_COMMAND_JOKER_USAGE, "joker");
        add(TranslationKeys.TTT_COMMAND_JOKER_DESCRIPTION, "&7Nutze Joker um dein Schicksal vorherzubestimmen!");
        add(TranslationKeys.TTT_COMMAND_JOKER_NOTAVAILABLE, "&7Dieser Befehl ist nur §cvor Ende&7 der Schutzzeit verfügbar!");

        add(TranslationKeys.TTT_GAME_ROOM_TESTER_JOINED, "&e${name} &7hat den Traitor-Tester betreten.");
        add(TranslationKeys.TTT_GAME_ROOM_TESTER_COOLDOWN, "§cWarte 5 Sekunden um den Tester zu betreten!");

        add(TranslationKeys.ROLE_CALLOUT_TITLE, "${role}");
        add(TranslationKeys.ROLE_CALLOUT_SUBTITLE, "&7Du bist ein ${role} &7geworden!");
        add(TranslationKeys.ROLE_CALLOUT_TEXT, "&7Du bist ${role}&7! Viel Erfolg!");
        add(TranslationKeys.ROLE_WON, "&aDie ${role} &ahaben das Spiel gewonnen!");

        add(TranslationKeys.TTT_GAME_PLAYER_DIED, "${name} &7ist gestorben!");
        add(TranslationKeys.TTT_GAME_PLAYER_FOUND, "${name} &7wurde von ${found} &7gefunden.");
        add(TranslationKeys.TTT_GAME_PLAYER_KILLED, "&7Du hast ${name} &7(${role}&7) getötet! &a+ &e${coins} &7Punkte)");
        add(TranslationKeys.TTT_GAME_PLAYER_RECEIVED_COINS, "&a+ &e${amount} &7Punkte");
        add(TranslationKeys.TTT_GAME_PLAYER_CORPSE_NOTFOUND, "&7Nicht identifiziert...");

        add(TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_START, "Sieh deine Akten ein, um nähere Informationen zu Leichen zu erhalten. Bis die Ermittlungen fertig sind, kann es bis zu 40 Sekunden dauern.");
        add(TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_CASE_SUPER, "&0Spieler: ${name}\n&0Rolle: ${role}");
        add(TranslationKeys.TTT_GAME_PLAYER_DETECTIVE_FILES_CASE_NORMAL, "&0Spieler: ${name}");

        add(TranslationKeys.TTT_GAME_ENDERCHEST_CANTOPEN, "&7Dieses Item kann erst §cnach Ende 7der Schutzzeit eingesammelt werden!");

        add(TranslationKeys.TTT_GAME_ITEM_SHOP_NAME, "&bShop");
        add(TranslationKeys.TTT_GAME_ITEM_SHOP_LORE, "&7Öffne den Shop um;&7nützliche Items zu erhalten!");
        add(TranslationKeys.TTT_GAME_ITEM_FILES_NAME, "&bAkten");
        add(TranslationKeys.TTT_GAME_ITEM_FILES_LORE, "&7Sieh die Akten der;§7identifizierten Leichen ein.");
        add(TranslationKeys.TTT_GAME_ITEM_ANALYZER_NAME, "&eIdentifizierer");
        add(TranslationKeys.TTT_GAME_ITEM_ANALYZER_LORE, "&7Identifiziere unbekannte Leichen;&7mit diesem Item!");

        // SHOP ITEM
        // TRAITOR
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ARROWS_NAME, "&ePfeilvorrat");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ARROWS_DESCRIPTION, "§7Hol dir Nachschub für;§7deinen Bogen. 10 Pfeile für;§7nur einen Punkt!");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_TICKET_NAME, "&eFallen-Stop");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_TICKET_DESCRIPTION, "§7Wird das Item gekauft, kann die;§7Traitor-Falle nicht mehr ausgelöst;§7werden. Wird dieses Item ein zweites;§7mal gekauft, ist das aktivieren wieder;§7möglich.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_TICKET_REMOVED, "&7Fallen-Stop &aentfernt&7!");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_NAME, "&eRadar");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_RADAR_DESCRIPTION, "§7Zeigt dir den nähesten;§7oder einen ausgewählten;§7Spieler an.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_NOM_NOM_DEVICE_NAME, "&eNom-Nom Device");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_NOM_NOM_DEVICE_DESCRIPTION, "§7Verspeise alle alle 60 Sekunden;§7eine Leiche um 2 Herzen zu;§7regenerieren.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_NAME, "&eStreitaxt");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_DESCRIPTION, "§7Diese schwere Waffe fügt mit einem;§7Schlag 5 Herzen Schaden zu. Doch;§7durch ihr hohes Gewicht verlangsamst;§7du dich, selbst wenn du;§7die Axt wieder weggelegt hast.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_AXE_COOLDOWN, "&cAchtung! &7Axtschwung muss abklingen!");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_IRON_SWORD_NAME, "&eEisenschwert");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_IRON_SWORD_DESCRIPTION, "§7Besorge dir ein Eisenschwert;&7auch wenn du keine Enderchest;&7erwischt hast!");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ADRENALIN_NAME, "&eAdrenalin");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ADRENALIN_DESCRIPTION, "§7Dieser Trtank verleiht 10 Sekunden;§7den Effekt Schnelligkeit, wenn;§7er getrunken wird");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_FIREBALL_NAME, "&eInferno");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_FIREBALL_DESCRIPTION, "§7Dieser feurige Ball hinterlässt an;§7seiner Einschlagsstelle ein Meer;§7aus Flammen.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_NAME, "&eReperatur-Kit Typ F");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_DESCRIPTION, "§7Erlaubt es, die Falle zu reparieren;§7und ein weiteres Mal auszulösen.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_NOTHAVE, "&cDie Falle wurde bereits ausgelöst!;&7Kaufe das Reperatur-Kit im Shop, um sie wieder auszulösen.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_USED, "&7Du hast du Falle &arepariert&7!");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_BOOM_BODY_NAME, "&eBoom Body");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_BOOM_BODY_DESCRIPTION, "§7Wird er von einem Detective oder;§7Innocent identifiziert macht es...;§7Richtig! Kaboom");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_JIHAD_NAME, "&eSprengweste");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_JIHAD_DESCRIPTION, "§7Diese spezielle Weste explodiert 5 Sekunden;§7nachdem du sie angezogen hast.;§7Du wirst den Einsatz dieses Items;§7allerdings nicht überstehen...");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TESTER_FAKER_NAME, "&eInnocent-Pass");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TESTER_FAKER_DESCRIPTION, "§7Du wirst dauernd verdächtigt? Dieser;§7Störsender beeinflusst den Traitor-Tester;§7so, dass du nicht als Traitor enttarnt;§7wirst, wenn du ihn betrittst.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_IDENT_THIEF_NAME, "&eIdentitätsdieb");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_IDENT_THIEF_DESCRIPTION, "§7Täusche deine Verfolger und nimm;§7die Identität einer Leiche an.;§7Du spielst nun so lange als dieser;§7Spieler, bis die Leiche identifiziert wird.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_GENERATOR_NAME, "&eSaboteur");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_GENERATOR_DESCRIPTION, "§7Mit diesem Item kannst du den;§7Generator deaktivieren. Für alle;§7Innocents und Detectives ohne;§7Nachtsichtgerät wird alles dunkel.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_INVISIBILITY_NAME, "&eTarnknappe");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_INVISIBILITY_DESCRIPTION, "§7Verschwinde für 30 Sekunden in;§7den Schutz der Unsichtbarkeit.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_SENTRYGUN_NAME, "&eSentryGun");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_SENTRYGUN_DESCRIPTION, "");

        // INNOCENT
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ANTI_BOOM_BODY_NAME, "&eBoomBody-Stop");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ANTI_BOOM_BODY_DESCRIPTION, "§7Verhindert, dass du einen;§7BoomBody identifizierst.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ANTI_BOOM_BODY_WARN, "&cAchtung! &7Das ist ein Boom Body!");

        // DETECTIVE
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_NIGHTVISION_NAME, "&eNachtsichtgerät");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_NIGHTVISION_DESCRIPTION, "§7Selbst wenn der Generator sebotiert;§7wurde, kannst du mit diesem Gerät;§7im Dunklen sehen.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_SUPER_IDENT_NAME, "&eSuper-Identifizierer");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_SUPER_IDENT_DESCRIPTION, "§7Erhalte schneller Einsicht in;§7die Akten und erhalte;§7zusätzliche Informationen zu;§7Leichen.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_HOOK_NAME, "&eEnterhaken");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_HOOK_DESCRIPTION, "§7Triffst du einen Block mit;§7dem Enterhaken, kannst du;§7dich zu diesem hinziehen.;§7Triffst du einen Spieler, wird;§7dieser zu dir gezogen und erhält;§71 Herz Schaden.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_BLOCK_NAME, "&eFallenfrei");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_BLOCK_DESCRIPTION, "7Wird dieses Item gekauft, so;§7kann bei der nächsten Person,;§7die den Tester betritt nicht;§7die Falle ausgelöst werden.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_NAME, "&eBewegungsmelder");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_DESCRIPTION, "§7Kann vor dem Ausgang aus;§7eines Lüftungsschachtes;§7aufgestellt werden und;§7alamiert, wenn ein Traitor;§7aus dem Schacht heraustritt.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_SIGNAL, "&eBewegung gemeldet! &6${name} &7hat den Bewegungsmelder ausgelöst!");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MOVE_SENSOR_MARKED, "&7Bewegunsmelder &aplatziert&7!");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_DETECTOR_NAME, "&eDetektor");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_DETECTOR_DESCRIPTION, "§7Nähert sich ein Traitor bis;§7auf 10 Blöcke, färbt sich;§7der Detektor rot.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_VENT_GIFT_NAME, "&eGiftige Luft");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_VENT_GIFT_DESCRIPTION, "§7Vergiftet die Luft in den;§7Lüftungsschächten für;§760 Sekunden.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_HEAL_STATION_NAME, "&eSamariter");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_HEAL_STATION_DESCRIPTION, "§7Alle Spieler im Umkreis;§7von 2 Blöcken werden geheilt.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ONE_SHOT_BOW_NAME, "&eOne-Shot-Bogen");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_ONE_SHOT_BOW_DESCRIPTION, "§7Triffst du einen Spieler;§7mit diesem Bogen, so;§7stirbt dieser sofort.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MED_KIT_NAME, "&eMedi-Kit");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_MED_KIT_DESCRIPTION, "§7Wird das Medi-Kit angewendet,;§7werden sofort 5 Herzen;§7regeneriert.");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_YOUTH_NAME, "&eJugend");
        add(TranslationKeys.TTT_GAME_SHOP_ITEM_YOUTH_DESCRIPTION, "§7Erhöht die Kapazität an;§7maximalen Herzen um 2.");


        add(TranslationKeys.TTT_GUI_CORPSE_TIME_CLOSE, "&cFrisches Blut");
        add(TranslationKeys.TTT_GUI_CORPSE_TIME_LONG, "&cKlumpiges Blut");
        add(TranslationKeys.TTT_GUI_CORPSE_KILLWEAPON, "&7Splitter der Mordwaffe");
        add(TranslationKeys.TTT_GUI_CORPSE_ANALYZING, "&6Leiche wird analysiert...;&7Dieser Prozess dauert 30 Sekunden.");
        add(TranslationKeys.TTT_GUI_CORPSE_ANALYZABLE, "&7Klicke hier um die Analyse;&7dieser Leiche zu starten,;&7um mehr Informationen zu dieser;&7Leiche zu erhalten.");

        add(TranslationKeys.TTT_GUI_JOKERSHOP_TITLE, "&e&l♔ &eJoker");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_TRAITOR_NAME, "&cTraitor-Joker");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_TRAITOR_LORE, "&e${cost} Token ;;&7Kaufe dir einen Joker;&7um dein Schicksal fest;zulegen.");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_ALREADY_BOUGHT, "&7Du hast einen Joker bereits eingelöst!");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_DETECTIVE_NAME, "&dDetective-Joker");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_DETECTIVE_LORE, "&e${cost} Token ;;&7Kaufe dir einen Joker;&7um dein Schicksal fest;zulegen.");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_CONFIRM_TITLE, "&aKauf bestätigen!");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_CONFIRM_INFO, "&7Kaufe dir diesen Joker;&7um in dieser Runde deine Rolle schon vorab festzulegen!");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_GAME_STARTED, "&7Das Spiel hat &cbereits &7begonnen!");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_CONFIRM_SEND, "&7Du hast den Joker &aerfolgreich &7eingelöst!");
        add(TranslationKeys.TTT_GUI_JOKERSHOP_PAYBACK_INFO, "&7Dein Joker konnte &cnicht &7eingesetzt werden. Deine Token wurden zurückerstattet.");

        add(TranslationKeys.TTT_GUI_SHOP_TITLE, "&eItemshop");
        add(TranslationKeys.TTT_GUI_SHOP_ERROR_NOTENOUGHCOINS, "&7Du hast &cnicht &7genug Punkte!"); // ACTIONBAR
        add(TranslationKeys.TTT_GUI_SHOP_ERROR_MAXIMUCURRENTAMOUNT, "&7Du kannst &ckeine &7größere Anzahl dieses Items auf einmal kaufen!"); // ACTIONBAR
        add(TranslationKeys.TTT_GUI_SHOP_ERROR_MAXIMUBUYSAMOUNT, "&7Du hast die &cmaximale &7Anzahl dieses Items gekauft!"); // ACTIONBAR
        add(TranslationKeys.TTT_GUI_SHOP_COINS_NAME, "&7Du hast &c${amount} Punkte&7!");
        add(TranslationKeys.TTT_GUI_SHOP_COINS_LORE, "&7Erfahre, wie du Punkt erhalten;&7kannst.");
        add(TranslationKeys.TTT_GUI_SHOP_BUYED, "&7Du hast &e${name} &7gekauft!");

        add(TranslationKeys.SCOREBOARD_YOUR_ROLE, "&7Deine Rolle:");
        add(TranslationKeys.SCOREBOARD_FOUND_TRAITORS, "&7Gefundene Traitor:");
        add(TranslationKeys.SCOREBOARD_COINS, "&7Punkte:");

        // GENERATOR
        add(TranslationKeys.TTT_GAME_ROOM_GENERATOR_DISABLE_BROADCAST, "&7Der Generator wurde &csabotiert&7!!");
        add(TranslationKeys.TTT_GAME_ROOM_GENERATOR_DISABLE_NOTHAVE, "&7Kaufe das Item &eSaboteur &7um den Generator zu deaktivieren!"); // ACTIONBAR
        add(TranslationKeys.TTT_GAME_ROOM_GENERATOR_ENABLE_BROADCAST, "&7Generator wurde &arepariert&7!");
        add(TranslationKeys.TTT_GAME_ROOM_GENERATOR_ENABLE_TRYAGAIN, "&7Das hat nicht geklappt..."); // ACTIONBAR

        // VENT
        add(TranslationKeys.TTT_GAME_ROOM_VENT_TIMEREACHED, "&cZeitlimit! &7Du kannst nicht länger im Vent bleiben!");
        add(TranslationKeys.TTT_GAME_ROOM_VENT_ENTERED, "&6Vent &7betreten   &8« &eScrollen&7 zum Bewegen &8»   &6Shift&7 zum Verlassen");
        add(TranslationKeys.TTT_GAME_ROOM_VENT_CANTENTER, "&7Nur &4Traitor &7können das Vent betreten...");

        //TRAP
        add(TranslationKeys.TTT_GAME_ROOM_TRAP_CANTENTER, "&7Nur &4Traitor &7können die Falle auslösen");
        add(TranslationKeys.TTT_GAME_ROOM_TRAP_PROTECTED, "&7Die Falle wird von einem &9Detective &7blöckiert!");


        insertIntoLanguage();
    }
}
