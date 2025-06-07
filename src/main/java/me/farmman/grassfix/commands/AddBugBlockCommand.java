package me.farmman.grassfix.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.Material;
import me.farmman.grassfix.GrassFix;
import me.farmman.grassfix.utils.MessageUtils;

public class AddBugBlockCommand {
    private final GrassFix plugin;

    public AddBugBlockCommand(GrassFix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("grassfix.addbugblock")) {
            MessageUtils.sendMessage(sender, "no-permission", plugin);
            return;
        }

        if (args.length < 2) {
            MessageUtils.sendMessage(sender, "addbugblock-usage", plugin);
            return;
        }

        try {
            Material material = Material.valueOf(args[1].toUpperCase());
            plugin.getConfigManager().addBugBlock(material.toString());
            MessageUtils.sendConfigMessage(sender, "block-added", plugin);
        } catch (IllegalArgumentException e) {
            MessageUtils.sendMessage(sender, "invalid-block", plugin);
        }
    }
}