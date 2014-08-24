package com.aaomidi.dev.lilybook.engine.objects;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@RequiredArgsConstructor
public class LilyConfig {
    @Getter
    private final File file;
    @Getter
    private final YamlConfiguration config;

    public boolean saveConfig() {
        try {
            getConfig().save(file);
            return true;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean loadConfig() {
        try {
            getConfig().load(file);
            return true;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
