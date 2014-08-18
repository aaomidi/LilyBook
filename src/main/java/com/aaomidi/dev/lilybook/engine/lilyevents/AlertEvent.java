package com.aaomidi.dev.lilybook.engine.lilyevents;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyEvent;
import org.bukkit.entity.Player;

public class AlertEvent extends LilyEvent {
    public AlertEvent(ChannelType channelType) {
        super(channelType);
    }

    @Override
    public void execute(LilyBook instance, String sender, String message) {
        if (instance.getServer().getOnlinePlayers() == null) {
            return;
        }
        for (Player player : instance.getServer().getOnlinePlayers()) {
            StringManager.sendMessage(player, message);
        }
    }
}
