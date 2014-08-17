package com.aaomidi.dev.lilybook.engine;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import com.aaomidi.dev.lilybook.engine.objects.ProxyPlayers;
import com.google.common.collect.MapMaker;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class Caching {
    private final LilyBook instance;
    @Getter
    private static ConcurrentMap<String, ProxyPlayers> networkPlayersMap;
    @Getter
    private static ConcurrentMap<String, LilyPlayer> lilyPlayersMap;
    private static Pattern pattern;

    public Caching(LilyBook instance) {
        this.instance = instance;
        this.init();
    }

    public void init() {
        networkPlayersMap = new MapMaker().concurrencyLevel(3).makeMap();
        lilyPlayersMap = new MapMaker().concurrencyLevel(3).makeMap();
        pattern = Pattern.compile(":");
    }

    public void cleanUp(String playerName) {
        lilyPlayersMap.remove(playerName);
    }

    public static void proxyPlayersManagement(String message, String sender) {
        String[] messageArray = pattern.split(message);
        List<String> players = Arrays.asList(messageArray);
        Character action = players.get(0).charAt(0);
        players.remove(0);
        ConcurrentMap<String, ProxyPlayers> networkPlayersMap = Caching.getNetworkPlayersMap();
        switch (action) {
            case '+':
                if (players.size() > 0) {
                    if (!networkPlayersMap.containsKey(sender)) {
                        networkPlayersMap.put(sender, new ProxyPlayers(sender));
                    }
                    ProxyPlayers proxyPlayers = networkPlayersMap.get(sender);
                    for (String playerName : players) {
                        proxyPlayers.addPlayer(playerName);
                    }
                }
            case '-':
                if (players.size() > 0) {
                    if (!networkPlayersMap.containsKey(sender)) {
                        networkPlayersMap.put(sender, new ProxyPlayers(sender));
                    }
                    ProxyPlayers proxyPlayers = networkPlayersMap.get(sender);
                    for (String playerName : players) {
                        proxyPlayers.removePlayer(playerName);
                    }
                }
            case '!':
                if (players.size() > 0) {
                    if (!networkPlayersMap.containsKey(sender)) {
                        networkPlayersMap.put(sender, new ProxyPlayers(sender));
                    }
                    ProxyPlayers proxyPlayers = networkPlayersMap.get(sender);
                    proxyPlayers.resetPlayers();
                    for (String playerName : players) {
                        proxyPlayers.addPlayer(playerName);
                    }
                }
        }
    }

}
