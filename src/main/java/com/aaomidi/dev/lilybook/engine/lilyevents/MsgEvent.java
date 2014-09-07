package com.aaomidi.dev.lilybook.engine.lilyevents;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.Caching;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyEvent;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import com.earth2me.essentials.User;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;


public class MsgEvent extends LilyEvent {
    public MsgEvent(ChannelType channelType) {
        super(channelType);
    }

    @Override
    public void execute(final LilyBook instance, final String sender, final String message) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<String> messageInformation = new ArrayList<>(Arrays.asList(message.split(":")));
                if (instance.getServer().getOnlinePlayers() == null) {
                    return;
                }
                String messageSender = messageInformation.get(0);
                String messageReceiver = messageInformation.get(1);
                String sentMessage = messageInformation.get(2);
                for (Player player : instance.getServer().getOnlinePlayers()) {
                    if (player.getName().equalsIgnoreCase(messageReceiver)) {
                        if (LilyBook.getEssentials() != null) {
                            User user = LilyBook.getEssentials().getUser(player);
                            if (user != null) {
                                if (user._getIgnoredPlayers().contains(messageSender)) {
                                    continue;
                                }
                            }
                        }
                        String msg = StringManager.getCrossServerMessage(sender, sentMessage, messageSender);
                        StringManager.sendRawMessage(player, msg);
                        LilyPlayer lilyPlayer = Caching.getLilyPlayersMap().get(player.getName());
                        lilyPlayer.setConversee(messageSender);
                        continue;
                    }
                    if (player.hasPermission("lilybook.socialspy")) {
                        String msg = StringManager.getStaffCrossServerMessage(sender, sentMessage, messageSender, messageReceiver);
                        StringManager.sendRawMessage(player, msg);
                    }
                }

            }
        }.runTaskAsynchronously(instance);
    }
}
