package com.aaomidi.dev.lilybook.engine.configuration;


import com.aaomidi.dev.lilybook.LilyBook;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ConfigReader {
    private static FileConfiguration config;
    @Getter
    private static Map<String, Boolean> modularCommands;
    private final LilyBook instance;

    public ConfigReader(LilyBook instance, FileConfiguration config) {
        this.instance = instance;
        ConfigReader.config = config;
        this.getCommandSettings();
    }

    public static String getPrefix() {
        return config.getString("General-Settings.Prefix");
    }

    public static Map<String, Object> getRestartSettings() {
        return config.getConfigurationSection("Modular-Settings.Teleport-On-Restart").getValues(false);
    }

    public static Map<String, Object> getPlayerListSettings() {
        return config.getConfigurationSection("Modular-Settings.Send-Player-List").getValues(false);
    }

    public static Map<String, Object> getConnectionNotifySettings() {

        return config.getConfigurationSection("Modular-Settings.Staff-Connection-Notify").getValues(false);
    }

    private void getCommandSettings() {
        Map<String, Object> commandConfig = config.getConfigurationSection("Commands").getValues(false);
        modularCommands = new HashMap<>();
        for (Map.Entry<String, Object> entry : commandConfig.entrySet()) {
            try {
                modularCommands.put(entry.getKey().toLowerCase(), (Boolean) entry.getValue());
            } catch (Exception ex) {
                throw new Error("Please enter a boolean value for " + entry.getKey() + " in the commands settings.", ex);
            }
        }
    }

}
