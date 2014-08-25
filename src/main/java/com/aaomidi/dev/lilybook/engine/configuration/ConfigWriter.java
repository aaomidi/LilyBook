package com.aaomidi.dev.lilybook.engine.configuration;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.objects.LilyConfig;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Map;

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
            File file = new File(instance.getDataFolder(), "language.yml");
            if (!file.exists()) {
                Files.copy(instance.getResource("language.yml"), file.toPath());
            } else {
                Reader reader = new InputStreamReader(instance.getResource("language.yml"));
                YamlConfiguration updatedConfig = YamlConfiguration.loadConfiguration(reader);
                YamlConfiguration currentConfig = YamlConfiguration.loadConfiguration(file);
                for (Map.Entry<String, Object> entry : updatedConfig.getValues(true).entrySet()) {
                    if (currentConfig.contains(entry.getKey())) {
                        continue;
                    }
                    currentConfig.set(entry.getKey(), entry.getValue());
                }
                currentConfig.save(file);
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            languagesConfig = null;
            languagesConfig = new LilyConfig(file, config);
        } catch (Exception ex) {
            throw new Error("Error when creating/reading the languages.yml file." + ex);
        }
    }
}
