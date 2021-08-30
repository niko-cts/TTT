package net.fununity.games.auttt.rooms;

import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Vent {

    private final Location one;
    private final Location two;
    private final Set<UUID> uuids;

    public Vent(Location one, Location two) {
        this.one = one;
        this.two = two;
        this.uuids = new HashSet<>();
    }

    public Location getOne() {
        return one;
    }

    public Location getTwo() {
        return two;
    }

    public Set<UUID> getUuids() {
        return uuids;
    }
}
