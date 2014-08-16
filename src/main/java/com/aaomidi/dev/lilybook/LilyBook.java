package com.aaomidi.dev.lilybook;


import com.aaomidi.dev.lilybook.engine.CommandsManager;
import com.aaomidi.dev.lilybook.engine.LilyManager;
import lilypad.client.connect.api.Connect;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

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

    public void onLoad() {

    }

    public void onEnable() {
        this.setupLily();
        this.registerClasses();
    }

    public void onDisable() {

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

}
