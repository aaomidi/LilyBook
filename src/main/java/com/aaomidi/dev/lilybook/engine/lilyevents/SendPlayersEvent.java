package com.aaomidi.dev.lilybook.engine.lilyevents;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.Caching;
import com.aaomidi.dev.lilybook.engine.LilyManager;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class SendPlayersEvent extends LilyEvent {

    public SendPlayersEvent(ChannelType channelType) {
        super(channelType);
        LilyManager.register(this);
    }


    @Override
    public void execute(final LilyBook instance, final String sender, final String message) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Caching.proxyPlayersManagement(message, sender);

            }
        }.runTaskAsynchronously(instance);
    }
}
