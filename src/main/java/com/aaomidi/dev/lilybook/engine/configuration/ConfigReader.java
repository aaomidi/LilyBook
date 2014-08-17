package com.aaomidi.dev.lilybook.engine.configuration;


import com.aaomidi.dev.lilybook.LilyBook;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class ConfigReader {
    private final LilyBook instance;
    private static FileConfiguration config;

    public ConfigReader(LilyBook instance, FileConfiguration config) {
        this.instance = instance;
        ConfigReader.config = config;
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
}
