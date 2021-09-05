package net.fununity.games.auttt.corpse;

public enum CorpseElements {

    HEAD(false),
    BLOOD(false),
    KILLWEAPON(false),
    HELDITEM(true),
    DETEFILES(true);

    private final boolean take;
    CorpseElements(boolean take) {
        this.take = take;
    }

    public boolean canTake() {
        return take;
    }
}
