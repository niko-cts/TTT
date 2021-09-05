package net.fununity.games.auttt.rooms;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.rooms.vent.Vent;
import net.fununity.games.auttt.shop.detectives.DetectiveItems;
import net.fununity.main.api.util.LocationUtil;
import net.fununity.mgs.gamespecifc.Arena;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class RoomsManager {

    private static RoomsManager instance;

    public static void loadManager(Arena arena) {
        instance = new RoomsManager(arena);
    }

    public static RoomsManager getInstance() {
        return instance;
    }

    private final Tester tester;
    private final Generator generator;
    private final Vent vent;
    private final Trap trap;

    private RoomsManager(Arena arena) {
        Map<String, List<Location>> locations = arena.getTeamLocations();
        if (locations.containsKey("tester_activate"))
            this.tester = new Tester(locations.get("tester_room"), locations.get("tester_reactionblocks"), locations.get("tester_redstone"), locations.get("tester_activate").get(0));
        else
            this.tester = null;

        if (locations.containsKey("generator_button"))
            this.generator = new Generator(locations.get("generator_button").get(0));
        else
            this.generator = null;

        if (locations.containsKey("vent"))
            this.vent = new Vent(locations.get("vent"), locations.get("vent_out"));
        else
            this.vent = null;

        if (locations.containsKey("trap_activation"))
            this.trap = new Trap(locations.get("trap_activation").get(0), locations.get("trap_blocks"));
        else
            this.trap = null;
    }

    public void checkForActivation(Player player, Location location) {
        System.out.println(location + "  " + tester + "  " + tester.getActivationBlock());
        if (tester != null && LocationUtil.equalsLocationBlock(location, tester.getActivationBlock())) {
            if (generator == null || generator.isEnabled())
                tester.startTester();
        } else if (generator != null && LocationUtil.equalsLocationBlock(location, generator.getActivationBlock())) {
            generator.buttonPressed(player);
        }
    }

    public void checkForTrap(Player player, Location location) {
        System.out.println(location + "  " + trap + "  " + trap.getActivationBlock());
        if (trap != null && LocationUtil.equalsLocationBlock(location, trap.getActivationBlock())) {
            trap.buttonPressed(GameLogic.getInstance().getTTTPlayer(player.getUniqueId()));
        }
    }

    public void checkForVent(Player player, Location location) {
        if (vent != null) {
            TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(player.getUniqueId());
            if (tttPlayer.getRole() == Role.TRAITOR)
                vent.jumpIn(player, location);
            else if (tttPlayer.hasShopItem(DetectiveItems.MOVE_SENSOR) &&
                    tttPlayer.getApiPlayer().getPlayer().getInventory().getItemInMainHand().equals(DetectiveItems.MOVE_SENSOR.getTranslatedItem(tttPlayer.getApiPlayer().getLanguage()))) {
                tttPlayer.getShopItemsOfType(DetectiveItems.MOVE_SENSOR).get(0).use(true);
                vent.markLocation(tttPlayer.getApiPlayer(), location);
            } else
                player.playSound(location, Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);
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
