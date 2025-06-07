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

        GrassFixCommand commandExecutor = new GrassFixCommand(this);
        getCommand("gf").setExecutor(commandExecutor);
        getCommand("gf").setTabCompleter(commandExecutor);

        getServer().getPluginManager().registerEvents(new PlayerMovementListener(this), this);
        configManager.loadConfigs();

        getLogger().info("GrassFix включений!");
    }

    @Override
    public void onDisable() {
        getLogger().info("GrassFix виключений!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}