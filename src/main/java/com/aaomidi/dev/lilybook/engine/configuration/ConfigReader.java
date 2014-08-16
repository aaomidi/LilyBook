package com.aaomidi.dev.lilybook.engine.configuration;


import com.aaomidi.dev.lilybook.LilyBook;
import org.bukkit.configuration.file.FileConfiguration;

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
}
