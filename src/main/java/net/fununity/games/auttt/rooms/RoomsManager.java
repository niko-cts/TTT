package net.fununity.games.auttt.rooms;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.rooms.vent.Vent;
import net.fununity.games.auttt.shop.ShopItem;
import net.fununity.games.auttt.shop.detectives.DetectiveItems;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.actionbar.ActionbarMessage;
import net.fununity.main.api.util.LocationUtil;
import net.fununity.mgs.gamespecifc.Arena;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * This class manages all special rooms of ttt.
 * @author Niko
 * @since 1.1
 */
public class RoomsManager {

    private static RoomsManager instance;

    /**
     * Loads the room manager and instantiates the class.
     * @param arena Arena - the played arena.
     * @since 1.1
     */
    public static void loadManager(Arena arena) {
        instance = new RoomsManager(arena);
    }

    /**
     * Get the singleton instance of this class.
     * @return {@link RoomsManager} - instance of this class.
     * @since 1.1
     */
    public static RoomsManager getInstance() {
        return instance;
    }

    private final Tester tester;
    private final Generator generator;
    private final Vent vent;
    private final Trap trap;

    private RoomsManager(Arena arena) {
        Map<String, List<Location>> locations = arena.getTeamLocations();
        if (locations.containsKey("tester_activate") && locations.containsKey("tester_teleportback") && locations.containsKey("tester_teleportin"))
            this.tester = new Tester(locations.get("tester_room"), locations.get("tester_reactionblocks"), locations.get("tester_redstone"), locations.get("tester_activate").get(0),
                    locations.get("tester_teleportback").get(0), locations.get("tester_teleportin").get(0));
        else
            this.tester = null;

        if (locations.containsKey("generator_button"))
            this.generator = new Generator(locations.get("generator_button").get(0));
        else
            this.generator = null;

        if (locations.containsKey("vent") && locations.containsKey("vent_out"))
            this.vent = new Vent(locations.get("vent"), locations.get("vent_out"));
        else
            this.vent = null;

        if (locations.containsKey("trap_activation") && locations.containsKey("trap_blocks"))
            this.trap = new Trap(locations.get("trap_activation").get(0), locations.get("trap_blocks"));
        else
            this.trap = null;
    }

    public void checkForActivation(Player player, Location location) {
        if (tester != null && LocationUtil.equalsLocationBlock(location, tester.getActivationBlock())) {
            if (generator == null || generator.isEnabled())
                tester.startTester(player);
        } else if (generator != null && LocationUtil.equalsLocationBlock(location, generator.getActivationBlock())) {
            generator.buttonPressed(player);
        }
    }

    public void checkForTrap(Player player, Location location) {
        if (trap != null && LocationUtil.equalsLocationBlock(location, trap.getActivationBlock())) {
            trap.buttonPressed(GameLogic.getInstance().getTTTPlayer(player.getUniqueId()));
        }
    }

    public void checkForVent(Player player, Location location) {
        if (vent != null) {
            TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(player.getUniqueId());
            if (tttPlayer.getRole() == Role.TRAITOR) {
                vent.jumpIn(player, location);
                return;
            }
            List<ShopItem> moveSensors = tttPlayer.getShopItemsOfType(DetectiveItems.MOVE_SENSOR);

            if (!moveSensors.isEmpty() &&
                    moveSensors.get(0).equalsItem(tttPlayer.getApiPlayer().getPlayer().getInventory().getItemInMainHand())) {
                moveSensors.get(0).use(true);
                vent.markLocation(tttPlayer.getApiPlayer(), location);
            } else {
                player.playSound(location, Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);
                FunUnityAPI.getInstance().getActionbarManager().addActionbar(player.getUniqueId(), new ActionbarMessage(TranslationKeys.TTT_GAME_ROOM_VENT_CANTENTER));
            }
        }
    }

    public Generator getGenerator() {
        return generator;
    }

    public Vent getVent() {
        return vent;
    }

    public Trap getTrap() {
        return trap;
    }
}
