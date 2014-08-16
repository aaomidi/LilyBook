package com.aaomidi.dev.lilybook.engine.modules;

import com.aaomidi.dev.lilybook.LilyBook;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ChannelType {
    DISPATCH_COMMAND("DispatchCommand"),
    SEND_PLAYER_LIST("SendPlayerList");

    private final String channelName;

    @Override
    public String toString() {
        return LilyBook.getLILY_CHANNEL() + channelName;
    }

}
