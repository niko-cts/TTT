package net.fununity.games.auttt.rooms;

import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.traitor.TraitorItems;
import net.fununity.main.api.actionbar.ActionbarMessage;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trap {

    private final Location trapActivation;
    private final Map<Block, Material> blockSaver;
    private boolean activateable;
    private boolean testerProtected;

    public Trap(Location trapActivation, List<Location> trapBlocks) {
        this.trapActivation = trapActivation;
        this.blockSaver = new HashMap<>();
        for (Location trapBlock : trapBlocks)
            blockSaver.put(trapBlock.getBlock(), trapBlock.getBlock().getType());

        this.activateable = true;
        this.testerProtected = false;
    }

    public void buttonPressed(TTTPlayer tttPlayer) {
        if (testerProtected || tttPlayer.getRole() != Role.TRAITOR) return;
        if (tttPlayer.hasShopItem(TraitorItems.TRAP_TICKET))
            return;

        if (!activateable) {
            if (!tttPlayer.hasShopItem(TraitorItems.TRAP_REPAIR)) {
                tttPlayer.getApiPlayer().sendMessage(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_NOTHAVE);
                return;
            }
            tttPlayer.getShopItemsOfType(TraitorItems.TRAP_REPAIR).get(0).removeItem();
            tttPlayer.getApiPlayer().sendActionbar(new ActionbarMessage(TranslationKeys.TTT_GAME_SHOP_ITEM_TRAP_REPAIR_USED));
        } else
            this.activateable = false;

        for (Block block : this.blockSaver.keySet()) {
            block.setType(Material.AIR);
            block.getWorld().playEffect(block.getLocation(), Effect.BLAZE_SHOOT, 0);
        }

        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), this::resetBlocks, 20 * 4L);
    }

    public void resetBlocks() {
        this.blockSaver.forEach(Block::setType);
        setTesterProtected(false);
    }

    public void setTesterProtected(boolean testerProtected) {
        this.testerProtected = testerProtected;
    }

    public Location getActivationBlock() {
        return trapActivation;
    }
}
