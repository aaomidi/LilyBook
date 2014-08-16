package com.aaomidi.dev.lilybook.engine;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.lilyevents.ReceivePlayersEvents;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyEvent;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;
import lilypad.client.connect.api.request.impl.MessageRequest;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LilyManager {
    private final LilyBook instance;
    private static HashMap<String, LilyEvent> lilyEvents;

    public LilyManager(LilyBook instance) {
        this.instance = instance;
        this.initialize();
    }

    private void initialize() {
        instance.getConnect().registerEvents(this);
        lilyEvents = new HashMap<>();
        this.setupEvents();
    }

    private void setupEvents() {
        new ReceivePlayersEvents(ChannelType.SEND_PLAYER_LIST);
    }

    public static void register(LilyEvent lilyEvent) {
        String lilyChannel = lilyEvent.getChannelType().toString();
        lilyEvents.put(lilyChannel, lilyEvent);
    }

    @EventListener
    public void onLilyMessage(MessageEvent event) {
        try {
            String sender = event.getSender();
            String message = event.getMessageAsString();
            String channel = event.getChannel();
            if (lilyEvents.containsKey(channel)) {
                LilyEvent lilyEvent = lilyEvents.get(channel);
                lilyEvent.execute(instance, sender, message);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error when listening to Lilypad messages." + ex);
        }
    }

    /**
     * Sends a message request to the proxy.
     *
     * @param channelType The channel to send the message on.
     * @param message     The information to go with the MessageRequest.
     * @param servers     List of servers.
     */
    public void messageRequest(final ChannelType channelType, final String message, final String... servers) {
        try {
            MessageRequest request;
            List<String> serverLists = new ArrayList<>();
            if (servers != null) {
                serverLists.addAll(Arrays.asList(servers));
            }
            request = new MessageRequest(serverLists, channelType.toString(), message);
            instance.getConnect().request(request);
        } catch (Exception ex) {
            throw new RuntimeException("Error while sending a message request." + ex);
        }
    }

    /**
     * Sends an async message request to the proxy.
     *
     * @param channelType The channel to send the message on.
     * @param message     The information to go with the MessageRequest.
     * @param servers     List of servers.
     */
    public void asyncMessageRequest(final ChannelType channelType, final String message, final String[] servers) {
        new BukkitRunnable() {
            @Override
            public void run() {
                messageRequest(channelType, message, servers);
            }
        }.runTaskAsynchronously(instance);
    }
}
