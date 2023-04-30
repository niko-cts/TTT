package net.fununity.games.auttt.corpse;

import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.common.util.RandomUtil;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.item.UsefulItems;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class DefaultCorpseInventory {

    protected static final int INVENTORY_SIZE = 45;
    protected final CorpseInventoryManager corpseInventoryManager;
    protected final PlayerCorpse playerCorpse;
    protected final Map<Integer, CorpseItem> staticElements;
    protected final Map<Integer, CorpseElements> individualElements;

    public DefaultCorpseInventory(CorpseInventoryManager corpseInventoryManager, PlayerCorpse playerCorpse, PlayerDeathEvent event) {
        this.corpseInventoryManager = corpseInventoryManager;
        this.playerCorpse = playerCorpse;
        this.staticElements = new HashMap<>();
        this.individualElements = new HashMap<>();

        this.staticElements.put(22, new CorpseItem(CorpseElements.HEAD, UsefulItems.getPlayerHead(playerCorpse.tttPlayer.getApiPlayer())));

        for (int i = 1; i < 9; i++) {
            this.staticElements.put(i, new CorpseItem(CorpseElements.GLASS, playerCorpse.tttPlayer.getRole().getGlass()));
            this.staticElements.put(INVENTORY_SIZE - i, new CorpseItem(CorpseElements.GLASS, playerCorpse.tttPlayer.getRole().getGlass()));
        }

        for (int i = 0; i < INVENTORY_SIZE; i += 9) {
            this.staticElements.put(i, new CorpseItem(CorpseElements.GLASS, playerCorpse.tttPlayer.getRole().getGlass()));
            if (i + 8 < INVENTORY_SIZE)
                this.staticElements.put(i + 8, new CorpseItem(CorpseElements.GLASS, playerCorpse.tttPlayer.getRole().getGlass()));
        }

        this.staticElements.put(22, new CorpseItem(CorpseElements.HEAD, UsefulItems.getPlayerHead(playerCorpse.tttPlayer.getApiPlayer())));

        if (RandomUtil.getRandom().nextBoolean()) // death
            insertIndividualElements(CorpseElements.BLOOD, RandomUtil.getRandomInt(5));

        if (RandomUtil.getRandom().nextBoolean()) { // killed item
            Entity killer = event.getEntity().getLastDamageCause().getEntity();
            Material material = null;
            if (killer instanceof Player) {
                ItemStack weapon = ((Player) killer).getInventory().getItemInMainHand();
                switch (weapon.getType()) {
                    case WOODEN_SWORD -> material = Material.STICK;
                    case STONE_SWORD -> material = Material.COBBLESTONE;
                    case IRON_SWORD -> material = Material.IRON_INGOT;
                    case DIAMOND_SWORD -> material = Material.DIAMOND;
                }
            } else if (killer instanceof Arrow)
                material = Material.BOW;
            if (material != null)
                insertItem(CorpseElements.KILLWEAPON, new ItemBuilder(material).setName(TranslationKeys.TTT_GUI_CORPSE_KILLWEAPON), 4);
        }

        if (RandomUtil.getRandom().nextBoolean()) { // hand item
            ItemStack item = event.getEntity().getInventory().getItemInMainHand().clone();
            if (item.getType() != Material.AIR)
                insertItem(CorpseElements.HELDITEM, item, 1);
        }

        if (playerCorpse.tttPlayer.getRole() == Role.DETECTIVE && RandomUtil.getRandom().nextBoolean())  // FILES
            insertItem(CorpseElements.DETEFILES, new ItemBuilder(Material.KNOWLEDGE_BOOK).setName(TranslationKeys.TTT_GAME_ITEM_FILES_NAME).setLore(TranslationKeys.TTT_GAME_ITEM_FILES_LORE), 1);
    }

    /**
     * Inserts only the {@link CorpseElements} itself.
     * The item will be created in {@link IndividualCorpseInventory}, when a player analyzes the corpse.
     * @param corpseElement {@link CorpseElements} - the element of the corpse item.
     * @param amount int - amount of items in the inventory.
     * @since 1.1
     */
    private void insertIndividualElements(CorpseElements corpseElement, int amount) {
        for (int i = 0; i < amount; i++) {
            int position;
            do {
                position = RandomUtil.getRandomInt(INVENTORY_SIZE);
            } while (staticElements.containsKey(position) || individualElements.containsKey(position));
            this.individualElements.put(position, corpseElement);
        }
    }

    /**
     * Inserts the item in the gui.
     * @param corpseElement {@link CorpseElements} - the element of the corpse item.
     * @param item Object - the displayed item.
     * @param amount int - amount of items in the inventory.
     * @since 1.1
     */
    private void insertItem(CorpseElements corpseElement, Object item, int amount) {
        for (int i = 0; i < amount; i++) {
            int position;
            do {
                position = RandomUtil.getRandomInt(INVENTORY_SIZE);
            } while (staticElements.containsKey(position) || individualElements.containsKey(position));
            this.staticElements.put(position, new CorpseItem(corpseElement, item));
        }
    }
}
