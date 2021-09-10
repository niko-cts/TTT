package net.fununity.games.auttt.corpse;

/**
 * Elements, which can be found in a corpse.
 * @see DefaultCorpseInventory
 * @author Niko
 * @since 1.1
 */
public enum CorpseElements {

    HEAD(false),
    BLOOD(false),
    KILLWEAPON(false),
    HELDITEM(true),
    DETEFILES(true);

    private final boolean take;

    /**
     * Instantiates the enum.
     * @param take boolean - can be taken out of the gui.
     * @since 1.1
     */
    CorpseElements(boolean take) {
        this.take = take;
    }

    /**
     * Check if the element can be taken out of the gui.
     * @return boolean - can take out of corpse gui.
     * @since 1.1
     */
    public boolean canTake() {
        return take;
    }
}
