package me.farmman.grassfix.listeners;

import me.farmman.grassfix.GrassFix;
import me.farmman.grassfix.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class PlayerMovementListener implements Listener {
    private final GrassFix plugin;

    public PlayerMovementListener(GrassFix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!plugin.getConfigManager().isPluginEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        Location loc = player.getLocation();
        Block block = loc.getBlock();

        List<String> bugBlocks = plugin.getConfigManager().getBugBlocks();
        Material blockType = block.getType();

        if (bugBlocks.contains(blockType.name())) {
            Location safeLocation = findSafeLocation(loc);
            if (safeLocation != null) {
                player.teleport(safeLocation.add(0, 0.5, 0));
                if (plugin.getConfigManager().areMessagesEnabled()) {
                    MessageUtils.sendMessage(player, "stuck-message", plugin);
                }
            }
        }
    }

    private Location findSafeLocation(Location startLoc) {
        for (int y = startLoc.getBlockY(); y < startLoc.getWorld().getMaxHeight(); y++) {
            Location checkLoc = new Location(
                    startLoc.getWorld(),
                    startLoc.getX(),
                    y,
                    startLoc.getZ()
            );

            if (isLocationSafe(checkLoc)) {
                return checkLoc;
            }
        }

        for (int radius = 1; radius <= 5; radius++) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x == 0 && z == 0) continue;

                    Location checkLoc = new Location(
                            startLoc.getWorld(),
                            startLoc.getX() + x,
                            startLoc.getY(),
                            startLoc.getZ() + z
                    );

                    for (int y = checkLoc.getBlockY(); y < checkLoc.getWorld().getMaxHeight(); y++) {
                        Location verticalCheck = new Location(
                                checkLoc.getWorld(),
                                checkLoc.getX(),
                                y,
                                checkLoc.getZ()
                        );

                        if (isLocationSafe(verticalCheck)) {
                            return verticalCheck;
                        }
                    }
                }
            }
        }

        return null;
    }

    private boolean isLocationSafe(Location loc) {
        Block feet = loc.getBlock();
        Block head = loc.clone().add(0, 1, 0).getBlock();
        Block ground = loc.clone().subtract(0, 1, 0).getBlock();

        List<String> bugBlocks = plugin.getConfigManager().getBugBlocks();

        return !bugBlocks.contains(feet.getType().name()) &&
                !bugBlocks.contains(head.getType().name()) &&
                !bugBlocks.contains(ground.getType().name()) &&
                feet.getType().isAir() &&
                head.getType().isAir() &&
                !ground.getType().isAir();
    }
}