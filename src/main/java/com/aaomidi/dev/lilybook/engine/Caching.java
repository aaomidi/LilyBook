package com.aaomidi.dev.lilybook.engine;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayers;
import com.google.common.collect.MapMaker;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class Caching {
    private final LilyBook instance;
    @Getter
    private static ConcurrentMap<String, LilyPlayers> networkPlayersMap;
    private static Pattern pattern;

    public Caching(LilyBook instance) {
        this.instance = instance;
        this.init();
    }

    public void init() {
        networkPlayersMap = new MapMaker().concurrencyLevel(3).makeMap();
        pattern = Pattern.compile(":");
    }

    public static void proxyPlayersManagement(String message, String sender) {
        String[] messageArray = pattern.split(message);
        List<String> players = Arrays.asList(messageArray);
        Character action = players.get(0).charAt(0);
        players.remove(0);
        ConcurrentMap<String, LilyPlayers> networkPlayersMap = Caching.getNetworkPlayersMap();
        switch (action) {
            case '+':
                if (players.size() > 0) {
                    if (!networkPlayersMap.containsKey(sender)) {
                        networkPlayersMap.put(sender, new LilyPlayers(sender));
                    }
                    LilyPlayers lilyPlayers = networkPlayersMap.get(sender);
                    for (String playerName : players) {
                        lilyPlayers.addPlayer(playerName);
                    }
                }
            case '-':
                if (players.size() > 0) {
                    if (!networkPlayersMap.containsKey(sender)) {
                        networkPlayersMap.put(sender, new LilyPlayers(sender));
                    }
                    LilyPlayers lilyPlayers = networkPlayersMap.get(sender);
                    for (String playerName : players) {
                        lilyPlayers.removePlayer(playerName);
                    }
                }
            case '!':
                if (players.size() > 0) {
                    if (!networkPlayersMap.containsKey(sender)) {
                        networkPlayersMap.put(sender, new LilyPlayers(sender));
                    }
                    LilyPlayers lilyPlayers = networkPlayersMap.get(sender);
                    lilyPlayers.resetPlayers();
                    for (String playerName : players) {
                        lilyPlayers.addPlayer(playerName);
                    }
                }
        }
    }

}
