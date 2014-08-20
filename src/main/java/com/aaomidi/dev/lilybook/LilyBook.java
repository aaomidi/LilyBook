package com.aaomidi.dev.lilybook;


import com.aaomidi.dev.lilybook.engine.Caching;
import com.aaomidi.dev.lilybook.engine.CommandsManager;
import com.aaomidi.dev.lilybook.engine.LilyManager;
import com.aaomidi.dev.lilybook.engine.RunnableManager;
import com.aaomidi.dev.lilybook.engine.bukkitevents.ConnectionEvent;
import com.aaomidi.dev.lilybook.engine.bukkitevents.TalkEvent;
import com.aaomidi.dev.lilybook.engine.configuration.ConfigReader;
import com.earth2me.essentials.Essentials;
import lilypad.client.connect.api.Connect;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;

public class LilyBook extends JavaPlugin {
    @Getter
    private final static String LILY_CHANNEL = "MyLily.";
    @Getter
    private static String SERVER_NAME;
    @Getter
    private static Essentials essentials;
    @Getter
    private Connect connect;
    @Getter
    private LilyManager lilyManager;
    @Getter
    private ConfigReader configReader;
    @Getter
    private CommandsManager commandsManager;
    @Getter
    private RunnableManager runnableManager;
    @Getter
    private Caching caching;

    public static LilyBook getInstance() {
        return JavaPlugin.getPlugin(LilyBook.class);
    }

    public void onLoad() {
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }

    }

    public void onEnable() {
        this.setupLily();
        this.setupEssentials();
        this.registerClasses();
        registerEvent(new ConnectionEvent(this));
        registerEvent(new TalkEvent(this));

    }

    private void setupLily() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("LilyPad-Connect");
        if (plugin == null) {
            this.setEnabled(false);
            this.getLogger().log(Level.SEVERE, "LilyBook was shut down since LilyPad-Connect was not found!");
        } else {
            connect = (Connect) this.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
            SERVER_NAME = connect.getSettings().getUsername();
        }
    }

    private void setupEssentials() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("Essentials");
        if (plugin == null) {
            this.getLogger().log(Level.INFO, "Essentials was not found on the server, disabling integration with essentials.");
            return;
        }
        essentials = (Essentials) plugin;
    }

    private void registerClasses() {
        configReader = new ConfigReader(this, this.getConfig());
        lilyManager = new LilyManager(this);
        caching = new Caching(this);
        runnableManager = new RunnableManager(this);
        commandsManager = new CommandsManager(this);
    }

    private void registerEvent(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void onDisable() {
        this.teleportOnRestart();
        runnableManager.cancelRunnables();
    }

    private void teleportOnRestart() {
        Map<String, Object> restartSettings = ConfigReader.getRestartSettings();
        if (!String.valueOf(restartSettings.get("Active")).equalsIgnoreCase("true")) {
            return;
        }
        if (this.getServer().getOnlinePlayers() == null) {
            return;
        }
        for (Player player : this.getServer().getOnlinePlayers()) {
            lilyManager.teleportRequest(player.getName(), (String) restartSettings.get("Server"));
        }

    }
}
