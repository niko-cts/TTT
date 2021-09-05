package net.fununity.games.auttt.rooms;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.traitor.TraitorItems;
import net.fununity.main.api.server.BroadcastHandler;
import net.fununity.main.api.util.LocationUtil;
import net.fununity.mgs.gamestates.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class Tester {

    private final List<Location> playerRoomLocation;
    private final List<Location> reactionBlocks;
    private final List<Location> redstoneBlocks;
    private final Location activationBlock;

    public Tester(List<Location> playerRoomLocation, List<Location> reactionBlocks, List<Location> redstoneBlocks, Location activationBlock) {
        this.playerRoomLocation = playerRoomLocation;
        this.reactionBlocks = reactionBlocks;
        this.activationBlock = activationBlock;
        this.redstoneBlocks = redstoneBlocks;
    }

    public void startTester() {
        for (Location reactionBlock : reactionBlocks)
            reactionBlock.getBlock().setData((byte) 4);
        for (Location redstoneBlock : redstoneBlocks)
            redstoneBlock.getBlock().setType(Material.REDSTONE_BLOCK);

        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), () -> {

            Player testingPlayer = GameManager.getInstance().getPlayers().stream()
                    .filter(p -> playerRoomLocation.stream().anyMatch(l -> LocationUtil.equalsLocationBlock(p.getLocation(), l))).findFirst().orElse(null);
            if (testingPlayer == null) {
                endTester();
                return;
            }

            TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(testingPlayer.getUniqueId());
            if (tttPlayer == null) {
                endTester();
                return;
            }

            BroadcastHandler.broadcastMessage(TranslationKeys.TTT_GAME_TESTER_JOINED, "${name}", tttPlayer.getApiPlayer().getPlayer().getName());
            for (Location reactionBlock : reactionBlocks)
                reactionBlock.getBlock().setData(tttPlayer.getRole() == Role.TRAITOR && !tttPlayer.hasShopItem(TraitorItems.TESTER_FAKER) ? (byte) 14 : (byte) 13);
            Bukkit.getScheduler().runTaskLater(TTT.getInstance(), this::endTester, 20 * 3L);
        }, 20 * 2);
    }

    private void endTester() {
        for (Location reactionBlock : reactionBlocks)
            reactionBlock.getBlock().setData((byte) 7);

        for (Location redstoneBlock : redstoneBlocks)
            redstoneBlock.getBlock().setType(Material.AIR);
    }

    public Location getActivationBlock() {
        return activationBlock;
    }
}
