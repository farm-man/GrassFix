package me.farmman.grassfix.commands;

import org.bukkit.command.CommandSender;
import me.farmman.grassfix.GrassFix;
import me.farmman.grassfix.utils.MessageUtils;

public class MessageToggleCommand {
    private final GrassFix plugin;

    public MessageToggleCommand(GrassFix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("grassfix.message")) {
            MessageUtils.sendMessage(sender, "no-permission", plugin);
            return;
        }

        if (args.length < 2) {
            MessageUtils.sendMessage(sender, "message-usage", plugin);
            return;
        }

        boolean enable = args[1].equalsIgnoreCase("on");
        plugin.getConfigManager().setMessagesEnabled(enable);

        String messagePath = enable ? "messages-enabled" : "messages-disabled";
        MessageUtils.sendConfigMessage(sender, messagePath, plugin);
    }
}