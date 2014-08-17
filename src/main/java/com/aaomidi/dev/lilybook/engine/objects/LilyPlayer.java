package com.aaomidi.dev.lilybook.engine.objects;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class LilyPlayer {
    @Getter
    private final String playerName;
    @Getter
    @Setter
    private boolean isStaffChat;

    @Override
    public String toString() {
        return playerName;
    }

}
