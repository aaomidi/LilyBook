package com.aaomidi.dev.lilybook.engine.commands;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.configuration.I18n;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class SendCommand extends LilyCommand {
    public SendCommand(String name, String permission, boolean forcePlayer, String usage) {
        super(name, permission, forcePlayer, usage);
    }

    @Override
    public boolean execute(final LilyBook instance, final LilyPlayer lilyPlayer, final CommandSender commandSender, final Command command, final String[] args) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (args.length == 0) {
                    StringManager.sendMessage(commandSender, getUsage());
                    return;
                }
                if (args.length == 1) {
                    StringManager.sendMessage(commandSender, I18n.ERROR_NO_SERVER_ARGUMENT);
                    return;
                }
                final String playerName = args[0];
                final String serverName = args[1];
                boolean response = instance.getLilyManager().teleportRequest(playerName, serverName);
                if (!response) {
                    StringManager.sendMessage(commandSender, I18n.ERROR_PLAYER_NOT_ONLINE);
                    return;
                }
                //StringManager.sendMessage(commandSender, String.format("&bSent player &e%s &bto server &e%s.", playerName, serverName));
                StringManager.sendMessage(commandSender, StringManager.purifyMessage(I18n.SEND_CONFIRMATION, serverName, playerName, null, null, null));
                return;
            }
        }.runTaskAsynchronously(instance);
        return true;
    }
}
