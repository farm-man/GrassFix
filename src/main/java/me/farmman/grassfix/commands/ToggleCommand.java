package me.farmman.grassfix.commands;

import org.bukkit.command.CommandSender;
import me.farmman.grassfix.GrassFix;
import me.farmman.grassfix.utils.MessageUtils;

public class ToggleCommand {
    private final GrassFix plugin;

    public ToggleCommand(GrassFix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("grassfix.toggle")) {
            MessageUtils.sendMessage(sender, "no-permission", plugin);
            return;
        }

        if (args.length < 1) {
            MessageUtils.sendMessage(sender, "toggle-usage", plugin);
            return;
        }

        boolean enable = args[0].equalsIgnoreCase("on");
        plugin.getConfigManager().setPluginEnabled(enable);

        String messagePath = enable ? "plugin-enabled" : "plugin-disabled";
        MessageUtils.sendConfigMessage(sender, messagePath, plugin);
    }
}