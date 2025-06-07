package me.farmman.grassfix.commands;

import me.farmman.grassfix.GrassFix;
import me.farmman.grassfix.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class GrassFixCommand implements CommandExecutor, TabCompleter {
    private final GrassFix plugin;

    public GrassFixCommand(GrassFix plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            MessageUtils.sendMessage(sender, "unknown-command", plugin);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "on":
                plugin.getConfigManager().setPluginEnabled(true);
                MessageUtils.sendMessage(sender, "plugin-enabled", plugin);
                break;
            case "off":
                plugin.getConfigManager().setPluginEnabled(false);
                MessageUtils.sendMessage(sender, "plugin-disabled", plugin);
                break;
            case "message":
                if (args.length < 2) {
                    MessageUtils.sendMessage(sender, "message-usage", plugin);
                    return true;
                }
                boolean messageState = args[1].equalsIgnoreCase("on");
                plugin.getConfigManager().setMessagesEnabled(messageState);
                MessageUtils.sendMessage(sender, messageState ? "messages-enabled" : "messages-disabled", plugin);
                break;
            case "addbugblock":
                if (args.length < 2) {
                    MessageUtils.sendMessage(sender, "addbugblock-usage", plugin);
                    return true;
                }
                plugin.getConfigManager().addBugBlock(args[1].toUpperCase());
                MessageUtils.sendMessage(sender, "block-added", plugin);
                break;
            case "removebugblock":
                if (args.length < 2) {
                    MessageUtils.sendMessage(sender, "removebugblock-usage", plugin);
                    return true;
                }
                plugin.getConfigManager().removeBugBlock(args[1].toUpperCase());
                MessageUtils.sendMessage(sender, "block-removed", plugin);
                break;
            case "reload":
                plugin.getConfigManager().reloadConfigs();
                MessageUtils.sendMessage(sender, "plugin-reloaded", plugin);
                break;
            case "help":
                MessageUtils.sendMessageList(sender, plugin.getConfigManager().getMessages().getStringList("help"));
                break;
            default:
                MessageUtils.sendMessage(sender, "unknown-command", plugin);
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("on");
            completions.add("off");
            completions.add("message");
            completions.add("addbugblock");
            completions.add("removebugblock");
            completions.add("reload");
            completions.add("help");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("message")) {
            completions.add("on");
            completions.add("off");
        }
        return completions;
    }
}