package me.farmman.grassfix.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import me.farmman.grassfix.GrassFix;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageUtils {
    public static void sendMessage(CommandSender sender, String path, GrassFix plugin) {
        FileConfiguration messages = plugin.getConfigManager().getMessages();
        String message = messages.getString(path);

        if (message != null && !message.isEmpty()) {
            sender.sendMessage(GradientParser.parseGradients(message));
        }
    }

    public static void sendConfigMessage(CommandSender sender, String path, GrassFix plugin) {
        if (plugin.getConfigManager().areMessagesEnabled()) {
            sendMessage(sender, path, plugin);
        }
    }

    public static @NotNull String parseGradients(String message) {
        return message;
    }
    public static void sendMessageList(CommandSender sender, List<String> messages) {
        if (messages != null) {
            for (String message : messages) {
                sender.sendMessage(parseGradients(message));
            }
        }
    }
}