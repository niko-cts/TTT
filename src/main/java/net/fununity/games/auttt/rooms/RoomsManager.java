package net.fununity.games.auttt.rooms;

import net.fununity.main.api.util.LocationUtil;
import net.fununity.mgs.gamespecifc.Arena;
import org.bukkit.Location;
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
    private final List<Vent> vents;

    private RoomsManager(Arena arena) {
        Map<String, List<Location>> locations = arena.getTeamLocations();
        if (locations.containsKey("tester_activate"))
            this.tester = new Tester(locations.get("tester_room"), locations.get("tester_reactionblocks"), locations.get("tester_activate").get(0));
        else
            this.tester = null;

        if (locations.containsKey("generator_button"))
            this.generator = new Generator(locations.get("generator_button").get(0));
        else
            this.generator = null;

        for(int i=0;i<5;i++) {
            if(locations.containsKey("vent_" + i) )
        }
    }

    public void checkForActivation(Player player, Location location) {
        if (tester != null && LocationUtil.equalsLocationBlock(location, tester.getActivationBlock())) {
            if (generator == null || generator.isEnabled())
                tester.startTester();
        } else if(generator != null && LocationUtil.equalsLocationBlock(location, generator.getActivationBlock())) {
            generator.buttonPressed(player);
        }
    }

    public Generator getGenerator() {
        return generator;
    }
}
