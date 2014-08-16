package com.aaomidi.dev.lilybook.engine.modules;


import com.aaomidi.dev.lilybook.LilyBook;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class LilyEvent {
    @Getter
    private final ChannelType channelType;

    public abstract void execute(LilyBook instance, String sender, String message);

    @Override
    public String toString() {
        return String.format("Event(channel=%s)", channelType.toString());
    }
}
