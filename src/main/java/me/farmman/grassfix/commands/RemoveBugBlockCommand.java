package me.farmman.grassfix.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.Material;
import me.farmman.grassfix.GrassFix;
import me.farmman.grassfix.utils.MessageUtils;

public class RemoveBugBlockCommand {
    private final GrassFix plugin;

    public RemoveBugBlockCommand(GrassFix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("grassfix.removebugblock")) {
            MessageUtils.sendMessage(sender, "no-permission", plugin);
            return;
        }

        if (args.length < 2) {
            MessageUtils.sendMessage(sender, "removebugblock-usage", plugin);
            return;
        }

        try {
            Material material = Material.valueOf(args[1].toUpperCase());
            plugin.getConfigManager().removeBugBlock(material.toString());
            MessageUtils.sendConfigMessage(sender, "block-removed", plugin);
        } catch (IllegalArgumentException e) {
            MessageUtils.sendMessage(sender, "invalid-block", plugin);
        }
    }
}