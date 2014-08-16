package com.aaomidi.dev.lilybook.engine;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RunnableManager {
    private final LilyBook instance;
    private BukkitRunnable playerListSendRunnable;

    public RunnableManager(LilyBook instance) {
        this.instance = instance;
        this.initialize();

    }

    public void initialize() {
        this.scheduleSendPlayerList();
    }

    private void scheduleSendPlayerList() {
        playerListSendRunnable = new BukkitRunnable() {
            @Override
            public void run() {
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
                instance.getLilyManager().messageRequest(ChannelType.SEND_PLAYER_LIST, sb.toString());
            }
        };
        playerListSendRunnable.runTaskAsynchronously(instance);
    }

    public void cancelRunnables() {
        playerListSendRunnable.cancel();
    }
}
