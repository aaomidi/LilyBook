package com.aaomidi.dev.lilybook.engine.bukkitevents;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.Caching;
import com.aaomidi.dev.lilybook.engine.configuration.ConfigReader;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class ConnectionEvent implements Listener {
    private final LilyBook instance;

    public ConnectionEvent(LilyBook instance) {
        this.instance = instance;

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        LilyPlayer lilyPlayer = new LilyPlayer(player.getName(), false, null);
        Caching.getLilyPlayersMap().put(player.getName(), lilyPlayer);

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        instance.getCaching().cleanUp(player.getName());
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onStaffJoin(PlayerJoinEvent event) {
        if (!String.valueOf(ConfigReader.getConnectionNotifySettings().get("Active")).equalsIgnoreCase("true")) {
            return;
        }
        if (!String.valueOf(ConfigReader.getConnectionNotifySettings().get("Server")).equalsIgnoreCase(LilyBook.getSERVER_NAME())) {
            return;
        }
        Player player = event.getPlayer();
        if (!player.hasPermission("lilybook.staff")) {
            return;
        }
        String message = String.format("&e%s &bjoined the server!", player.getName());
        instance.getLilyManager().asyncMessageRequest(ChannelType.NOTIFY_STAFF, message);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStaffLeave(PlayerQuitEvent event) {
        if (!String.valueOf(ConfigReader.getConnectionNotifySettings().get("Active")).equalsIgnoreCase("true")) {
            return;
        }
        if (!String.valueOf(ConfigReader.getConnectionNotifySettings().get("Server")).equalsIgnoreCase(LilyBook.getSERVER_NAME())) {
            return;
        }
        Player player = event.getPlayer();
        if (!player.hasPermission("lilybook.staff")) {
            return;
        }
        String message = String.format("&e%s &bleft the server!", player.getName());
        instance.getLilyManager().asyncMessageRequest(ChannelType.NOTIFY_STAFF, message);
    }

}
