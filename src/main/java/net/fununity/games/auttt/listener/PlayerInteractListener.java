package net.fununity.games.auttt.listener;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.player.PlayerCorpse;
import net.fununity.main.api.common.util.RandomUtil;
import net.fununity.main.api.item.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerInteractListener implements Listener {

    private final TreeMap<Float, ItemStack> occurrenceMap;
    private float totalFloat;

    public PlayerInteractListener() {
        this.occurrenceMap = new TreeMap<>();
        final FileConfiguration cfg = TTT.getInstance().getConfig();
        final Logger log = TTT.getInstance().getLogger();
        final String colItemDrops = "item-drops";

        for (String block : cfg.getString("chest.contents").split(";")) {
            Material dropItem;
            String[] data = block.split(":");
            if (data.length < 4 || data.length > 8) {
                log.log(Level.WARNING, "An entry in {0} has too less data in it!", colItemDrops);
                continue;
            }

            try {
                dropItem = Material.valueOf(data[0]);
            } catch (IllegalArgumentException exception) {
                log.log(Level.WARNING, "The Material {0} is illegal and wont be included.", data[0]);
                continue;
            }

            float possibility;
            int amount;
            short subID;
            try {
                subID = Short.parseShort(data[1]);
                amount = Integer.parseInt(data[2]);
                possibility = Float.parseFloat(data[3]);
                if (possibility == 0)
                    continue;
            } catch (NumberFormatException exception) {
                log.log(Level.WARNING, "Either the subID {0}, the amount {1} or the the possibility {2} is not a number.",
                        new Object[]{data[1], data[2], data[3]});
                continue;
            }

            ItemStack item = new ItemStack(Material.STONE);
            if (data.length == 4)
                item = new ItemStack(dropItem, amount, subID);
            else if (data.length == 6) {
                try {
                    item = new ItemBuilder(dropItem, subID).setAmount(amount)
                            .addEnchantment(Enchantment.getByName(data[4]),
                                    Integer.parseInt(data[5]), true).craft();
                } catch (NumberFormatException exception) {
                    log.log(Level.WARNING, "{0} is not a valid enchantment level", data[5]);
                } catch (IllegalArgumentException exception) {
                    log.log(Level.WARNING, "{0} is not a valid enchantment", data[4]);
                }
            } else if (data.length == 8) {
                try {
                    item = new ItemBuilder(dropItem, subID).setAmount(amount)
                            .addPotionEffect(new PotionEffect(PotionEffectType.getByName(data[4]),
                                    Integer.parseInt(data[5]), Integer.parseInt(data[6]),
                                    true, true, Color.fromRGB(Integer.parseInt(data[7])))).craft();
                } catch (NumberFormatException exception) {
                    log.log(Level.WARNING, "Either the duration {0}, the level {1} or the rgb {2} is not a number.",
                            new Object[]{data[5], data[6], data[7]});
                } catch (IllegalArgumentException exception) {
                    log.log(Level.WARNING, "{0} is not a valid potion", data[4]);
                }
            } else
                log.log(Level.WARNING, "The entry {0} has an invalid data length", Arrays.toString(data));

            this.totalFloat += possibility;
            occurrenceMap.put(this.totalFloat, item);
        }
    }

    @EventHandler
    public void onPlayerRightClicks(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || !GameLogic.getInstance().isIngame(event.getPlayer())) return;
        Block block = event.getClickedBlock();
        if (block.getType() == Material.CHEST) {
            event.setCancelled(true);
            block.setType(Material.AIR);
            giveRandomItem(event.getPlayer());
        }
    }

    private void giveRandomItem(Player player) {
        float value = RandomUtil.getRandomInt(Math.round(totalFloat)) + RandomUtil.getRandom().nextFloat();
        Map.Entry<Float, ItemStack> entry = occurrenceMap.ceilingEntry(value);
        int i = 0;
        while (entry == null && i < 100) {
            value = RandomUtil.getRandomInt(Math.round(totalFloat)) + RandomUtil.getRandom().nextFloat();
            entry = occurrenceMap.ceilingEntry(value);
            i++;
        }

        if (entry == null) return;

        player.getInventory().addItem(entry.getValue());
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
    }

}
