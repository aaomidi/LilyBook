package com.aaomidi.dev.lilybook.engine.bukkitevents;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.Caching;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TalkEvent implements Listener {
    private final LilyBook instance;

    public TalkEvent(LilyBook instance) {
        this.instance = instance;
    }


    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onTalk(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        LilyPlayer lilyPlayer = Caching.getLilyPlayersMap().get(player.getName());
        if (lilyPlayer == null || !lilyPlayer.isStaffChat()) {
            return;
        }
        event.setCancelled(true);
        StringManager.sendAdminChatMessage(player.getName(), message);
    }
}
