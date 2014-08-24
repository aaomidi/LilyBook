package com.aaomidi.dev.lilybook.engine.configuration;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.objects.LilyConfig;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.nio.file.Files;

public class ConfigWriter {
    @Getter
    private static LilyConfig languagesConfig;
    private final LilyBook instance;

    public ConfigWriter(LilyBook instance) {
        this.instance = instance;
        this.init();
    }

    public void init() {
        this.setupLanguageFile();
    }

    public void setupLanguageFile() {
        try {
            File file = new File(instance.getDataFolder(), "languages.yml");
            if (!file.exists()) {
                Files.copy(instance.getResource("languages.yml"), file.toPath());
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            languagesConfig = null;
            languagesConfig = new LilyConfig(file, config);
        } catch (Exception ex) {
            throw new Error("Error when creating/reading the languages.yml file." + ex);
        }
    }
}
