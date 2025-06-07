package me.farmman.grassfix.commands;

import org.bukkit.command.CommandSender;
import me.farmman.grassfix.GrassFix;
import me.farmman.grassfix.utils.MessageUtils;

public class ReloadCommand {
    private final GrassFix plugin;

    public ReloadCommand(GrassFix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("grassfix.reload")) {
            MessageUtils.sendMessage(sender, "no-permission", plugin);
            return;
        }

        plugin.getConfigManager().reloadConfigs();
        MessageUtils.sendConfigMessage(sender, "plugin-reloaded", plugin);
    }
}