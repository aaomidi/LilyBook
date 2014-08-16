package com.aaomidi.dev.lilybook.engine.lilyevents;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.LilyManager;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyEvent;
import org.bukkit.entity.Player;


public class DispatchEvent extends LilyEvent {
    public DispatchEvent(ChannelType channelType) {
        super(channelType);
        LilyManager.register(this);
    }

    @Override
    public void execute(LilyBook instance, String sender, String message) {
        if (instance.getServer().getOnlinePlayers() != null) {
            for (Player player : instance.getServer().getOnlinePlayers()) {
                if (!player.hasPermission("lilybook.notify")) {
                    return;
                }
                StringManager.sendMessage(player, String.format("&bThe command &e%s &bexecuted from &e%s &b was successfully executed on the server.", message, sender));
            }
        }
        instance.getServer().dispatchCommand(instance.getServer().getConsoleSender(), message);

    }
}
