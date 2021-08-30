package net.fununity.games.auttt.rooms;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Generator {

    private final Location location;
    private boolean enabled;

    public Generator(Location location) {
        this.location = location;
        this.enabled = true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Location getActivationBlock() {
        return location;
    }

    public void buttonPressed(Player player) {

    }
}
