package net.fununity.games.auttt.rooms;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.player.TTTPlayer;
import net.fununity.main.api.util.LocationUtil;
import net.fununity.mgs.gamestates.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class Tester {

    private final List<Location> playerRoomLocation;
    private final List<Location> reactionBlocks;
    private final Location activationBlock;

    public Tester(List<Location> playerRoomLocation, List<Location> reactionBlocks, Location activationBlock) {
        this.playerRoomLocation = playerRoomLocation;
        this.reactionBlocks = reactionBlocks;
        this.activationBlock = activationBlock;
    }

    public void startTester() {
        for (Location reactionBlock : reactionBlocks)
            reactionBlock.getBlock().setData((byte) 4);

        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), () -> {

            Player testingPlayer = GameManager.getInstance().getPlayers().stream().filter(p -> playerRoomLocation.stream().anyMatch(l -> LocationUtil.equalsLocationBlock(p.getLocation(), l))).findFirst().orElse(null);
            if (testingPlayer == null) {
                endTester();
                return;
            }

            TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(testingPlayer.getUniqueId());
            if(tttPlayer == null) {
                endTester();
                return;
            }

            for (Location reactionBlock : reactionBlocks)
                reactionBlock.getBlock().setData(tttPlayer.getRole() == Role.TRAITOR ? (byte) 14 : (byte) 13);
            Bukkit.getScheduler().runTaskLater(TTT.getInstance(), this::endTester, 20 * 2L);
        }, 20 * 2);
    }

    private void endTester() {
        for (Location reactionBlock : reactionBlocks)
            reactionBlock.getBlock().setData((byte) 7);
    }

    public Location getActivationBlock() {
        return activationBlock;
    }
}
