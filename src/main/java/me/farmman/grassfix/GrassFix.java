package me.farmman.grassfix;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import me.farmman.grassfix.commands.GrassFixCommand;
import me.farmman.grassfix.listeners.PlayerMovementListener;
import me.farmman.grassfix.utils.ConfigManager;

public class GrassFix extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);

        getCommand("gf").setExecutor((CommandExecutor) new GrassFixCommand(this));

        getServer().getPluginManager().registerEvents((Listener) new PlayerMovementListener(this), this);

        configManager.loadConfigs();

        getLogger().info("GrassFix plugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("GrassFix plugin disabled!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}