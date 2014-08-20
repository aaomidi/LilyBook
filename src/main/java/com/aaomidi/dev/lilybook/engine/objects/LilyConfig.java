package com.aaomidi.dev.lilybook.engine.objects;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public class LilyConfig {
    @Getter
    private final File file;
    private YamlConfiguration config;

    public YamlConfiguration getConfig() {
        if (config != null) {
            return config;
        }
        config = YamlConfiguration.loadConfiguration(file);
        return config;
    }

    public void saveConfig() {
        try {
            getConfig().save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
