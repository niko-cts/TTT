package net.fununity.games.auttt.corpse;

public class CorpseItem {

    private final CorpseElements corpseElement;
    private final Object item;

    public CorpseItem(CorpseElements corpseElement, Object item) {
        this.corpseElement = corpseElement;
        this.item = item;
    }

    public CorpseElements getCorpseElement() {
        return corpseElement;
    }

    public Object getItem() {
        return item;
    }
}
