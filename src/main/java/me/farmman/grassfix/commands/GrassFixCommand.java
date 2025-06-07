package me.farmman.grassfix.commands;

import me.farmman.grassfix.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import me.farmman.grassfix.GrassFix;
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
            new HelpCommand(plugin).execute(sender, args);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "on":
            case "off":
                new ToggleCommand(plugin).execute(sender, args);
                break;
            case "help":
                new HelpCommand(plugin).execute(sender, args);
                break;
            case "message":
                new MessageToggleCommand(plugin).execute(sender, args);
                break;
            case "addbugblock":
                new AddBugBlockCommand(plugin).execute(sender, args);
                break;
            case "removebugblock":
                new RemoveBugBlockCommand(plugin).execute(sender, args);
                break;
            case "reload":
                new ReloadCommand(plugin).execute(sender, args);
                break;
            default:
                MessageUtils.sendMessage(sender, "unknown-command", plugin);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("on");
            completions.add("off");
            completions.add("help");
            completions.add("message");
            completions.add("addbugblock");
            completions.add("removebugblock");
            completions.add("reload");
        } else if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "message":
                    completions.add("on");
                    completions.add("off");
                    break;
                case "addbugblock":
                case "removebugblock":
                    break;
            }
        }

        return completions;
    }
}