package me.farmman.grassfix.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.List;

public class ConfigManager {
    private final JavaPlugin plugin;

    private FileConfiguration config;
    private FileConfiguration bugBlocks;
    private FileConfiguration messages;
    private FileConfiguration permissions;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();

        bugBlocks = loadCustomConfig("bug_blocks.yml");
        messages = loadCustomConfig("messages.yml");
        permissions = loadCustomConfig("permissions.yml");
    }

    private FileConfiguration loadCustomConfig(String fileName) {
        File configFile = new File(plugin.getDataFolder(), fileName);
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public void reloadConfigs() {
        plugin.reloadConfig();
        config = plugin.getConfig();
        bugBlocks = loadCustomConfig("bug_blocks.yml");
        messages = loadCustomConfig("messages.yml");
        permissions = loadCustomConfig("permissions.yml");
    }

    public List<String> getBugBlocks() {
        return bugBlocks.getStringList("blocks");
    }

    public void addBugBlock(String block) {
        List<String> blocks = getBugBlocks();
        if (!blocks.contains(block)) {
            blocks.add(block);
            bugBlocks.set("blocks", blocks);
            saveBugBlocks();
        }
    }

    public void removeBugBlock(String block) {
        List<String> blocks = getBugBlocks();
        if (blocks.contains(block)) {
            blocks.remove(block);
            bugBlocks.set("blocks", blocks);
            saveBugBlocks();
        }
    }

    private void saveBugBlocks() {
        try {
            bugBlocks.save(new File(plugin.getDataFolder(), "bug_blocks.yml"));
        } catch (Exception e) {
            plugin.getLogger().severe("Could not save bug_blocks.yml");
        }
    }

    public FileConfiguration getMessages() {
        return messages;
    }

    public FileConfiguration getPermissions() {
        return permissions;
    }

    public boolean isPluginEnabled() {
        return config.getBoolean("enabled", true);
    }

    public void setPluginEnabled(boolean enabled) {
        config.set("enabled", enabled);
        plugin.saveConfig();
    }

    public boolean areMessagesEnabled() {
        return config.getBoolean("messages-enabled", true);
    }

    public void setMessagesEnabled(boolean enabled) {
        config.set("messages-enabled", enabled);
        plugin.saveConfig();
    }

    public boolean isStuckMessageEnabled() {
        return config.getBoolean("stuck-message-enabled", true);
    }

    public void setStuckMessageEnabled(boolean enabled) {
        config.set("stuck-message-enabled", enabled);
        plugin.saveConfig();
    }
}