package com.aaomidi.dev.lilybook.engine;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.lilyevents.*;
import com.aaomidi.dev.lilybook.engine.modules.Callback;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyEvent;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;
import lilypad.client.connect.api.event.ServerAddEvent;
import lilypad.client.connect.api.request.impl.MessageRequest;
import lilypad.client.connect.api.request.impl.RedirectRequest;
import lilypad.client.connect.api.result.FutureResult;
import lilypad.client.connect.api.result.StatusCode;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class LilyManager {
    private final LilyBook instance;
    private HashMap<String, LilyEvent> lilyEvents;

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
        register(new AdminChatEvent(ChannelType.ADMIN_CHAT_MESSAGE));
        register(new AlertEvent(ChannelType.ALERT_SERVERS));
        register(new DispatchEvent(ChannelType.DISPATCH_COMMAND));
        register(new MsgEvent(ChannelType.MESSAGE_PLAYER));
        register(new NotifyStaffEvent(ChannelType.NOTIFY_STAFF));
        register(new SendPlayersEvent(ChannelType.PLAYER_LIST));
        register(new SendStaffEvent(ChannelType.STAFF_LIST));
    }

    public void register(LilyEvent lilyEvent) {
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

    @EventListener
    public void onServerAdd(ServerAddEvent event) {
        try {
            String serverName = event.getServer();
            instance.getRunnableManager().getPlayerListSendRunnable().runTaskAsynchronously(instance);
        } catch (Exception ex) {
            throw new RuntimeException("Error when listening to ServerAddEvent." + ex);
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
            MessageRequest request = null;
            List<String> serverList = null;
            if (servers != null) {
                serverList = new ArrayList<>();
                Collections.addAll(serverList, servers);
            }
            request = new MessageRequest(serverList, channelType.toString(), message);
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
    public void asyncMessageRequest(final ChannelType channelType, final String message, final String... servers) {
        new BukkitRunnable() {
            @Override
            public void run() {
                messageRequest(channelType, message, servers);
            }
        }.runTaskAsynchronously(instance);
    }

    public boolean teleportRequest(final String playerName, final String serverName) {
        try {
            RedirectRequest request = new RedirectRequest(serverName, playerName);
            FutureResult future = instance.getConnect().request(request);
            if (future.await().getStatusCode().equals(StatusCode.SUCCESS)) {
                return true;
            }
            return false;
        } catch (Exception ex) {
            throw new RuntimeException("Error whilst redirecting a player." + ex);
        }
    }

    public void asyncTeleportRequest(final String playerName, final String serverName, final Callback<Boolean> callback) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final boolean result = teleportRequest(playerName, serverName);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        callback.execute(result);
                    }
                }.runTask(instance);
            }
        }.runTaskAsynchronously(instance);
    }
}
