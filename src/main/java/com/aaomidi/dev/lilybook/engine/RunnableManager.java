package com.aaomidi.dev.lilybook.engine;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.configuration.ConfigReader;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.codemine.jchatter.JChat;

public class RunnableManager {
    private final LilyBook instance;
    @Getter
    private BukkitRunnable playerListSendRunnable;
    @Getter
    private BukkitRunnable staffListSendRunnable;

    public RunnableManager(LilyBook instance) {
        this.instance = instance;
        this.initialize();

    }

    public void initialize() {
        this.scheduleSendPlayerList();
        this.scheduleSendStaffList();
    }

    private void scheduleSendPlayerList() {
        playerListSendRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (!String.valueOf(ConfigReader.getPlayerListSettings().get("Active")).equalsIgnoreCase("true")) {
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("!");
                if (instance.getServer().getOnlinePlayers() == null) {
                    sb.append(":");
                } else {
                    for (Player player : instance.getServer().getOnlinePlayers()) {
                        sb.append(":");
                        sb.append(player.getName());
                    }
                }
                instance.getLilyManager().messageRequest(ChannelType.PLAYER_LIST, sb.toString());
            }
        };
        playerListSendRunnable.runTaskTimerAsynchronously(instance, 100L, 1200L);
    }

    private void scheduleSendStaffList() {
        staffListSendRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                sb.append("!");
                if (instance.getServer().getOnlinePlayers() == null) {
                    sb.append(":");
                } else {
                    for (Player player : instance.getServer().getOnlinePlayers()) {
                        if (!player.hasPermission("lilybook.stafflist")) {
                            continue;
                        }
                        Essentials essentials = LilyBook.getEssentials();
                        if (essentials != null) {
                            User user = LilyBook.getEssentials().getUser(player);
                            if (user.isVanished()) {
                                continue;
                            }
                        }
                        sb.append(":");
                        sb.append(player.getName());
                    }
                }
                instance.getLilyManager().asyncMessageRequest(ChannelType.STAFF_LIST, sb.toString());
            }

        };
        staffListSendRunnable.runTaskTimerAsynchronously(instance, 100L, 600L);
    }

    public void sendAlertMessages() {
        JChat jChat = new JChat();
    }

    public void cancelRunnables() {
        playerListSendRunnable.cancel();
        staffListSendRunnable.cancel();
    }
}
