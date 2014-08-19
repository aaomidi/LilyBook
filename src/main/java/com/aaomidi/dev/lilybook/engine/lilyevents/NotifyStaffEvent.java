package com.aaomidi.dev.lilybook.engine.lilyevents;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyEvent;
import org.bukkit.entity.Player;

public class NotifyStaffEvent extends LilyEvent {
    public NotifyStaffEvent(ChannelType channelType) {
        super(channelType);
    }

    @Override
    public void execute(LilyBook instance, String sender, String message) {
        for (Player player : instance.getServer().getOnlinePlayers()) {
            if (player.hasPermission("lilybook.notify")) {
                continue;
            }
            StringManager.sendMessage(player, message);
        }
    }
}
