package net.fununity.games.auttt.rooms;

import org.bukkit.Location;

public class Generator {

    private boolean enabled;

    public Generator(Location activate) {

    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
