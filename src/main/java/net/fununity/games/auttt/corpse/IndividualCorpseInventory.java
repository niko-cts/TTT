package net.fununity.games.auttt.corpse;

import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.main.api.inventory.ClickAction;
import net.fununity.main.api.inventory.CustomInventory;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.main.api.item.UsefulItems;
import net.fununity.main.api.player.APIPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class IndividualCorpseInventory {

    private final DefaultCorpseInventory defaultCorpseInventory;
    private final Map<Integer, CorpseItem> individualElements;

    public IndividualCorpseInventory(DefaultCorpseInventory defaultCorpseInventory) {
        this.defaultCorpseInventory = defaultCorpseInventory;
        this.individualElements = new HashMap<>();

        for (Map.Entry<Integer, CorpseElements> entry : this.defaultCorpseInventory.individualElements.entrySet()) {
            if (entry.getValue() == CorpseElements.BLOOD) {
                long min = ChronoUnit.MINUTES.between(this.defaultCorpseInventory.playerCorpse.death, OffsetDateTime.now());
                ItemBuilder item;
                if (min >= 5)
                    item = new ItemBuilder(Material.WEB).setName(TranslationKeys.TTT_GUI_CORPSE_TIME_LONG);
                else if (min >= 2)
                    item = new ItemBuilder(Material.REDSTONE_BLOCK).setName(TranslationKeys.TTT_GUI_CORPSE_TIME_CLOSE);
                else
                    item = new ItemBuilder(Material.REDSTONE).setName(TranslationKeys.TTT_GUI_CORPSE_TIME_CLOSE);
                this.individualElements.put(entry.getKey(), new CorpseItem(entry.getValue(), item));
            }
        }
    }

    public void openInventory(TTTPlayer tttPlayer) {
        CustomInventory menu = new CustomInventory(defaultCorpseInventory.playerCorpse.tttPlayer.getColoredName(), 9 * 5);
        menu.setSpecialHolder("corpse-" + defaultCorpseInventory.playerCorpse.tttPlayer.getApiPlayer().getUniqueId());

        for (int i = 1; i < 9; i++) {
            menu.setItem(i, tttPlayer.getRole().getGlass());
            menu.setItem(menu.getInventory().getSize() - i, tttPlayer.getRole().getGlass());
        }

        for (int i = 0; i < menu.getInventory().getSize(); i += 9) {
            menu.setItem(i, tttPlayer.getRole().getGlass());
            if (i + 8 < menu.getInventory().getSize())
                menu.setItem(i + 8, tttPlayer.getRole().getGlass());
        }

        for (int i = 10; i < DefaultCorpseInventory.INVENTORY_SIZE; i += ((i+1) % 8 == 0 ? 3 : 1)) {
            CorpseItem corpseItem = this.defaultCorpseInventory.staticElements.getOrDefault(i, null);
            if (corpseItem == null) {
                corpseItem = this.individualElements.getOrDefault(i, null);
                if (corpseItem == null) {
                    continue;
                }
            }
            CorpseElements corpseElement = corpseItem.getCorpseElement();

            ClickAction clickAction = new ClickAction() {
                @Override
                public void onClick(APIPlayer apiPlayer, ItemStack itemStack, int i) {
                    if (!corpseElement.canTake()) return;
                    defaultCorpseInventory.staticElements.put(i, new CorpseItem(corpseElement, new ItemStack(Material.AIR)));
                    apiPlayer.getPlayer().getInventory().addItem(itemStack);
                    defaultCorpseInventory.corpseInventoryManager.reopenCorpseGUI();
                }
            };

            Object item = corpseItem.getItem();
            if (item instanceof ItemBuilder) {
                menu.setItem(i, ((ItemBuilder) item).translate(tttPlayer.getApiPlayer().getLanguage()), clickAction);
            } else if (item instanceof ItemStack) {
                menu.setItem(i, (ItemStack) item, clickAction);
            }
        }

        menu.fill(UsefulItems.BACKGROUND_GRAY);
        menu.open(tttPlayer.getApiPlayer());
    }

}
