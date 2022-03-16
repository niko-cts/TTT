package net.fununity.games.auttt.listener;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.gui.ShopGUI;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.rooms.RoomsManager;
import net.fununity.games.auttt.util.DetectiveFilesUtil;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.actionbar.ActionbarMessage;
import net.fununity.main.api.actionbar.ActionbarMessageType;
import net.fununity.main.api.common.util.RandomUtil;
import net.fununity.main.api.item.ItemBuilder;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerInteractListener implements Listener {

    private final Logger log = TTT.getInstance().getLogger();
    private final TreeMap<Float, ItemStack> occurrenceMap;
    private float totalFloat;
    private final TreeMap<Float, ItemStack> enderMap;
    private float enderFloat;

    public PlayerInteractListener() {
        this.occurrenceMap = new TreeMap<>();
        final FileConfiguration cfg = TTT.getInstance().getConfig();

        for (String block : cfg.getString("chest.contents").split(";")) {
            Object[] item = getBlockOutOfString(block);
            if (item == null) continue;

            this.totalFloat += (float) item[1];
            occurrenceMap.put(this.totalFloat, (ItemStack) item[0]);
        }

        this.enderMap = new TreeMap<>();
        for (String block : cfg.getString("chest.endercontents").split(";")) {
            Object[] item = getBlockOutOfString(block);
            if (item == null) continue;
            this.enderFloat += (float)  item[1];
            enderMap.put(this.enderFloat, (ItemStack) item[0]);
        }
    }

    private Object[] getBlockOutOfString(String block) {
        Material dropItem;
        String[] data = block.split(":");
        if (data.length < 4 || data.length > 8) {
            String colItemDrops = "item-drops";
            log.log(Level.WARNING, "An entry in {0} has too less data in it!", colItemDrops);
            return null;
        }

        try {
            dropItem = Material.valueOf(data[0]);
        } catch (IllegalArgumentException exception) {
            log.log(Level.WARNING, "The Material {0} is illegal and wont be included.", data[0]);
            return null;
        }

        float possibility;
        int amount;
        short subID;
        try {
            subID = Short.parseShort(data[1]);
            amount = Integer.parseInt(data[2]);
            possibility = Float.parseFloat(data[3]);
            if (possibility == 0)
                return null;
        } catch (NumberFormatException exception) {
            log.log(Level.WARNING, "Either the subID {0}, the amount {1} or the the possibility {2} is not a number.",
                    new Object[]{data[1], data[2], data[3]});
            return null;
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
        return new Object[]{item, possibility};
    }

    @EventHandler
    public void onPlayerRightClicks(PlayerInteractEvent event) {
        if(GameManager.getInstance().isSpectator(event.getPlayer()) || GameManager.getInstance().getCurrentGameState() != GameState.INGAME) return;
        if (event.getHand() != EquipmentSlot.HAND) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if(GameLogic.getInstance().getTTTPlayers().isEmpty()) return;
            Material type = event.getPlayer().getInventory().getItemInMainHand().getType();
            if (type == Material.PAPER) {
                ShopGUI.open(GameLogic.getInstance().getTTTPlayer(event.getPlayer().getUniqueId()));
            } else if(type == Material.KNOWLEDGE_BOOK)
                DetectiveFilesUtil.openFiles(GameLogic.getInstance().getTTTPlayer(event.getPlayer().getUniqueId()));
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block block = event.getClickedBlock();
        if (block.getType() == Material.CHEST) {
            event.setCancelled(true);
            block.setType(Material.AIR);
            giveRandomItem(event.getPlayer(), totalFloat, occurrenceMap);
            return;
        }

        if (block.getType() == Material.ENDER_CHEST) {
            event.setCancelled(true);
            if (GameLogic.getInstance().getTTTPlayers().isEmpty()) {
                FunUnityAPI.getInstance().getActionbarManager().addActionbar(event.getPlayer().getUniqueId(),
                        new ActionbarMessage(TranslationKeys.TTT_GAME_ENDERCHEST_CANTOPEN).setType(ActionbarMessageType.TIMED));
                return;
            }

            block.setType(Material.AIR);
            giveRandomItem(event.getPlayer(), enderFloat, enderMap);
            return;
        }

        if (GameLogic.getInstance().getTTTPlayers().isEmpty()) return;

        switch (block.getType()) {
            case WOOD_BUTTON:
                RoomsManager.getInstance().checkForTrap(event.getPlayer(), block.getLocation());
                break;
            case STONE_BUTTON:
                RoomsManager.getInstance().checkForActivation(event.getPlayer(), block.getLocation());
                break;
            case IRON_TRAPDOOR:
                if (block.getLocation().clone().subtract(1, 0, 0).getBlock().getType() == Material.COAL_BLOCK ||
                        block.getLocation().clone().subtract(0, 0, 1).getBlock().getType() == Material.COAL_BLOCK ||
                        block.getLocation().clone().add(1, 0, 0).getBlock().getType() == Material.COAL_BLOCK ||
                        block.getLocation().clone().add(0, 0, 1).getBlock().getType() == Material.COAL_BLOCK)
                    RoomsManager.getInstance().checkForVent(event.getPlayer(), block.getLocation());
                break;
            default:
                break;
        }
    }

    private void giveRandomItem(Player player, float totalFloat, TreeMap<Float, ItemStack> map) {
        float value = RandomUtil.getRandomInt(Math.round(totalFloat)) + RandomUtil.getRandom().nextFloat();
        Map.Entry<Float, ItemStack> entry = map.ceilingEntry(value);
        int i = 0;
        while (entry == null && i < 100) {
            value = RandomUtil.getRandomInt(Math.round(totalFloat)) + RandomUtil.getRandom().nextFloat();
            entry = map.ceilingEntry(value);
            i++;
        }

        if (entry == null) return;

        player.getInventory().addItem(entry.getValue());
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
    }

}
