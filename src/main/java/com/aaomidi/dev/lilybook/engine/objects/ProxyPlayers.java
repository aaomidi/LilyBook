package com.aaomidi.dev.lilybook.engine.objects;


import lombok.Getter;

import java.util.HashSet;
import java.util.Iterator;

public class ProxyPlayers implements Iterable<String> {
    @Getter
    private final HashSet<String> players;
    @Getter
    private final String serverName;

    public ProxyPlayers(String serverName) {
        players = new HashSet<>();
        this.serverName = serverName;
    }

    public boolean addPlayer(String playerName) {
        return players.add(playerName);
    }

    public boolean removePlayer(String playerName) {
        return players.remove(playerName);
    }

    public void resetPlayers() {
        players.clear();
    }

    public int getPlayersCount() {
        return players.size();
    }

    @Override
    public Iterator<String> iterator() {
        return players.iterator();
    }
}
