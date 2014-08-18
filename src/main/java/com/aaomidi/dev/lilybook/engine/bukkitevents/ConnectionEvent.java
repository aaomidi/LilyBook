package com.aaomidi.dev.lilybook.engine.bukkitevents;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.Caching;
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
}
