package com.aaomidi.dev.lilybook;


import com.aaomidi.dev.lilybook.engine.Caching;
import com.aaomidi.dev.lilybook.engine.CommandsManager;
import com.aaomidi.dev.lilybook.engine.LilyManager;
import com.aaomidi.dev.lilybook.engine.RunnableManager;
import com.aaomidi.dev.lilybook.engine.configuration.ConfigReader;
import lilypad.client.connect.api.Connect;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.logging.Level;

public class LilyBook extends JavaPlugin {
    @Getter
    private final static String LILY_CHANNEL = "MyLily.";
    @Getter
    private static String SERVER_NAME;
    @Getter
    private Connect connect;
    @Getter
    private LilyManager lilyManager;
    @Getter
    private CommandsManager commandsManager;
    @Getter
    private RunnableManager runnableManager;
    @Getter
    private Caching caching;

    public void onLoad() {

    }

    public void onEnable() {
        this.setupLily();
        this.registerClasses();
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

    private void registerClasses() {
        lilyManager = new LilyManager(this);
    }

    public static LilyBook getInstance() {
        return JavaPlugin.getPlugin(LilyBook.class);
    }

    public void registerEvent(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    public void onDisable() {
        this.teleportOnRestart();

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
