package com.aaomidi.dev.lilybook.engine.lilyevents;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.Caching;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyEvent;


public class SendStaffEvent extends LilyEvent {
    public SendStaffEvent(ChannelType channelType) {
        super(channelType);
    }

    @Override
    public void execute(LilyBook instance, String sender, String message) {
        Caching.proxyStaffManagement(message, sender);
    }
}
