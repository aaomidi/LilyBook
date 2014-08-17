package com.aaomidi.dev.lilybook.engine.commands;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.CommandsManager;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SendAllCommand extends LilyCommand {
    public SendAllCommand(String name, String permission, boolean forcePlayer, String usage) {
        super(name, permission, forcePlayer, usage);
        CommandsManager.register(this);
    }

    @Override
    public boolean execute(final LilyBook instance, LilyPlayer lilyPlayer, CommandSender commandSender, Command command, String[] args) {
        if (args.length == 0) {
            StringManager.sendMessage(commandSender, getUsage());
            return true;
        }
        final String serverName = args[0];
        if (instance.getServer().getOnlinePlayers() == null) {
            return true;
        }
        for (final Player player : instance.getServer().getOnlinePlayers()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    instance.getLilyManager().teleportRequest(player.getName(), serverName);
                }
            }.runTaskAsynchronously(instance);
        }
        StringManager.sendMessage(commandSender, String.format("&bSent all players to &e%s.", serverName));
        return true;
    }
}
