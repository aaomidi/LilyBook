package com.aaomidi.dev.lilybook.engine.commands;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class MessageCommand extends LilyCommand {
    public MessageCommand(String name, String permission, boolean forcePlayer, String usage) {
        super(name, permission, forcePlayer, usage);
    }

    @Override
    public boolean execute(final LilyBook instance, LilyPlayer lilyPlayer, final CommandSender commandSender, Command command, final String[] args) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (args.length == 0) {
                    StringManager.sendMessage(commandSender, getUsage());
                    return;
                }
                if (args.length == 1) {
                    StringManager.sendMessage(commandSender, getUsage());
                    return;
                }
                String receiver = args[0];
                if (receiver.length() > 16) {
                    StringManager.sendMessage(commandSender, "&cThat player is not online");
                }
                String message = "";
                for (int i = 2; i < args.length; i++) {
                    String arg = args[i];
                    arg = arg.replace(":", "");
                    message += arg + " ";
                }
                instance.getLilyManager().messageRequest(ChannelType.MESSAGE_PLAYER, String.format("%s:%s:%s", commandSender.getName(), receiver, message));
            }
        }.runTaskAsynchronously(instance);
        return true;
    }
}
