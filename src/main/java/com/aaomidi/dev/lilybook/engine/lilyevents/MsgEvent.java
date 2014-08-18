package com.aaomidi.dev.lilybook.engine.lilyevents;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyEvent;
import com.earth2me.essentials.User;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;


public class MsgEvent extends LilyEvent {
    public MsgEvent(ChannelType channelType) {
        super(channelType);
    }

    @Override
    public void execute(LilyBook instance, String sender, String message) {
        ArrayList<String> messageInformation = new ArrayList<>(Arrays.asList(message.split(":")));
        if (instance.getServer().getOnlinePlayers() == null) {
            return;
        }
        for (Player player : instance.getServer().getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(messageInformation.get(1))) {
                if (LilyBook.getEssentials() != null) {
                    User user = LilyBook.getEssentials().getUser(player);
                    if (user != null) {
                        if (user._getIgnoredPlayers().contains(messageInformation.get(0))) {

                        }
                    }
                }
                String msg = StringManager.getCrossServerMessage(sender, messageInformation.get(2), messageInformation.get(0));
                StringManager.sendRawMessage(player, msg);
            }
            if (player.hasPermission("lilybook.socialspy")) {
                String msg = StringManager.getStaffCrossServerMessage(sender, messageInformation.get(2), messageInformation.get(0), messageInformation.get(1));
                StringManager.sendMessage(player, msg);
            }
        }
    }
}
