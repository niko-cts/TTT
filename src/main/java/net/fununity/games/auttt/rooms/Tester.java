package net.fununity.games.auttt.rooms;

import net.fununity.games.auttt.GameLogic;
import net.fununity.games.auttt.Role;
import net.fununity.games.auttt.TTT;
import net.fununity.games.auttt.TTTPlayer;
import net.fununity.games.auttt.language.TranslationKeys;
import net.fununity.games.auttt.shop.traitor.TraitorItems;
import net.fununity.main.api.FunUnityAPI;
import net.fununity.main.api.actionbar.ActionbarMessage;
import net.fununity.main.api.server.BroadcastHandler;
import net.fununity.main.api.util.LocationUtil;
import net.fununity.mgs.gamestates.GameManager;
import net.fununity.mgs.gamestates.GameState;
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
    private final Location teleportOut;
    private final Location teleportIn;
    private long testerCooldown;

    public Tester(List<Location> playerRoomLocation, List<Location> reactionBlocks, List<Location> redstoneBlocks,
                  Location activationBlock, Location teleportOut, Location teleportIn) {
        this.playerRoomLocation = playerRoomLocation;
        this.reactionBlocks = reactionBlocks;
        this.activationBlock = activationBlock;
        this.redstoneBlocks = redstoneBlocks;
        this.teleportOut = teleportOut;
        this.teleportIn = teleportIn;
        this.testerCooldown = System.currentTimeMillis();
    }

    public void startTester(Player player) {
        if (System.currentTimeMillis() - testerCooldown < 5000) {
            FunUnityAPI.getInstance().getActionbarManager().addActionbar(player.getUniqueId(),
                    new ActionbarMessage(TranslationKeys.TTT_GAME_ROOM_TESTER_COOLDOWN));
            return;
        }
        for (Location reactionBlock : reactionBlocks)
            reactionBlock.getBlock().setData((byte) 4);

        for (Location redstoneBlock : redstoneBlocks)
            redstoneBlock.getBlock().setType(Material.REDSTONE_BLOCK);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.getUniqueId().equals(player.getUniqueId()) && !GameManager.getInstance().isSpectator(onlinePlayer)) {
                for (Location location : playerRoomLocation) {
                    if (LocationUtil.equalsLocationBlock(location, onlinePlayer.getLocation())){
                        onlinePlayer.teleport(teleportOut);
                    }
                }
            }
        }
        player.teleport(teleportIn);

        BroadcastHandler.broadcastMessage(TranslationKeys.TTT_GAME_ROOM_TESTER_JOINED, "${name}", player.getName());

        Bukkit.getScheduler().runTaskLater(TTT.getInstance(), () -> {

            TTTPlayer tttPlayer = GameLogic.getInstance().getTTTPlayer(player.getUniqueId());

            if (tttPlayer == null || !player.isOnline() ||
                    GameManager.getInstance().getCurrentGameState() != GameState.INGAME || GameManager.getInstance().isSpectator(player)) {
                endTester();
                return;
            }

            for (Location reactionBlock : reactionBlocks)
                reactionBlock.getBlock().setData(tttPlayer.getRole() == Role.TRAITOR && !tttPlayer.hasShopItem(TraitorItems.TESTER_FAKER) ? (byte) 14 : (byte) 13);

            Bukkit.getScheduler().runTaskLater(TTT.getInstance(), this::endTester, 20 * 4L);
        }, 20 * 3);
    }

    private void endTester() {
        for (Location reactionBlock : reactionBlocks)
            reactionBlock.getBlock().setData((byte) 7);

        for (Location redstoneBlock : redstoneBlocks)
            redstoneBlock.getBlock().setType(Material.AIR);

        this.testerCooldown = System.currentTimeMillis();
    }

    public Location getActivationBlock() {
        return activationBlock;
    }
}
