package me.farmman.grassfix.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import me.farmman.grassfix.GrassFix;
import me.farmman.grassfix.utils.MessageUtils;
import java.util.List;

public class PlayerMovementListener implements Listener {
    private final GrassFix plugin;

    public PlayerMovementListener(GrassFix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!plugin.getConfigManager().isPluginEnabled()) return;

        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock();
        Material blockType = block.getType();

        List<String> bugBlocks = plugin.getConfigManager().getBugBlocks();
        if (bugBlocks.contains(blockType.toString())) {
            Location safeLocation = findSafeLocation(player.getLocation(), bugBlocks);
            if (safeLocation != null) {
                player.teleport(safeLocation);
                MessageUtils.sendConfigMessage(player, "stuck-message", plugin);
            }
        }
    }

    private Location findSafeLocation(Location location, List<String> bugBlocks) {
        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    Location checkLoc = location.clone().add(x, y, z);
                    Material blockType = checkLoc.getBlock().getType();
                    if (!bugBlocks.contains(blockType.toString())) {
                        return checkLoc;
                    }
                }
            }
        }
        return null;
    }
}