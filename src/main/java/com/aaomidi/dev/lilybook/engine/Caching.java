package com.aaomidi.dev.lilybook.engine;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import com.aaomidi.dev.lilybook.engine.objects.ProxyPlayers;
import com.aaomidi.dev.lilybook.engine.objects.ProxyStaff;
import com.google.common.collect.MapMaker;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;

public class Caching {
    @Getter
    private static ConcurrentMap<String, ProxyPlayers> networkPlayersMap;
    @Getter
    private static ConcurrentMap<String, ProxyStaff> networkStaffMap;
    @Getter
    private static ConcurrentMap<String, LilyPlayer> lilyPlayersMap;
    private final LilyBook instance;

    public Caching(LilyBook instance) {
        this.instance = instance;
        this.init();
    }

    public static void proxyPlayersManagement(String message, String sender) {
        String[] messageArray = message.split(":");
        ArrayList<String> players = new ArrayList<>(Arrays.asList(messageArray));
        Character action = players.get(0).charAt(0);
        players.remove(0);
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
                    return;
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
                    return;
                }
            case '!':
                if (!networkPlayersMap.containsKey(sender)) {
                    networkPlayersMap.put(sender, new ProxyPlayers(sender));
                }
                ProxyPlayers proxyPlayers = networkPlayersMap.get(sender);
                proxyPlayers.resetPlayers();
                if (players.size() > 0) {
                    for (String playerName : players) {
                        proxyPlayers.addPlayer(playerName);
                    }
                    // return;
                }
        }
    }

    public static void proxyStaffManagement(String message, String sender) {
        String[] messageArray = message.split(":");
        ArrayList<String> players = new ArrayList<>(Arrays.asList(messageArray));
        Character action = players.get(0).charAt(0);
        players.remove(0);
        switch (action) {
            case '+':
                if (players.size() > 0) {
                    if (!networkStaffMap.containsKey(sender)) {
                        networkStaffMap.put(sender, new ProxyStaff(sender));
                    }
                    ProxyStaff proxyStaff = networkStaffMap.get(sender);
                    for (String playerName : players) {
                        proxyStaff.addStaff(playerName);
                    }
                }
            case '-':
                if (players.size() > 0) {

                    if (!networkStaffMap.containsKey(sender)) {
                        networkStaffMap.put(sender, new ProxyStaff(sender));
                    }
                    ProxyStaff proxyStaff = networkStaffMap.get(sender);
                    for (String playerName : players) {
                        proxyStaff.removeStaff(playerName);
                    }
                }
            case '!':
                if (!networkStaffMap.containsKey(sender)) {
                    networkStaffMap.put(sender, new ProxyStaff(sender));
                }
                ProxyStaff proxyStaff = networkStaffMap.get(sender);
                proxyStaff.resetStaff();
                if (players.size() > 0) {
                    for (String playerName : players) {
                        proxyStaff.addStaff(playerName);
                    }
                }

        }
    }

    public void init() {
        networkPlayersMap = new MapMaker().concurrencyLevel(3).makeMap();
        networkStaffMap = new MapMaker().concurrencyLevel(3).makeMap();
        lilyPlayersMap = new MapMaker().concurrencyLevel(3).makeMap();
    }

    public void cleanUp(String playerName) {
        lilyPlayersMap.remove(playerName);
    }

}
