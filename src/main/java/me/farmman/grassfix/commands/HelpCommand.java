package me.farmman.grassfix.commands;

import org.bukkit.command.CommandSender;
import me.farmman.grassfix.GrassFix;
import me.farmman.grassfix.utils.MessageUtils;
import java.util.List;

public class HelpCommand {
    private final GrassFix plugin;

    public HelpCommand(GrassFix plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("grassfix.help")) {
            MessageUtils.sendMessage(sender, "no-permission", plugin);
            return;
        }

        List<String> helpMessages = plugin.getConfigManager().getMessages().getStringList("help");
        for (String message : helpMessages) {
            sender.sendMessage(MessageUtils.parseGradients(message));
        }
    }
}