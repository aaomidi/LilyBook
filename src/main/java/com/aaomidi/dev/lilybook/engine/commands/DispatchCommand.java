package com.aaomidi.dev.lilybook.engine.commands;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DispatchCommand extends LilyCommand {

    public DispatchCommand(String name, String permission, boolean forcePlayer, String usage) {
        super(name, permission, forcePlayer, usage);
    }

    @Override
    public boolean execute(LilyBook instance, LilyPlayer lilyPlayer, final CommandSender commandSender, Command command, String[] args) {
        if (args.length == 0) {
            StringManager.sendMessage(commandSender, getUsage());
            return true;
        }
        String argument = args[0];
        String cmd = "";
        String serverName = null;
        if (argument.equalsIgnoreCase("-s")) {
            if (args.length == 1) {
                StringManager.sendMessage(commandSender, "&bPlease specify a server.");
                return true;
            } else if (args.length == 2) {
                StringManager.sendMessage(commandSender, "&bPlease specify a command.");
                return true;
            } else {
                serverName = args[1];
                int x = 0;
                for (String arg : args) {
                    if (x >= 2) {
                        cmd += arg + " ";
                    }
                    x++;
                }
            }
        } else {
            if (args.length > 1) {
                StringManager.sendMessage(commandSender, getUsage());
                return true;
            }
        }
        instance.getLilyManager().asyncMessageRequest(ChannelType.DISPATCH_COMMAND, cmd, serverName);
        if (serverName == null) {
            serverName = "all";
        }
        StringManager.sendMessage(commandSender, String.format("&bThe command &e%s&bwas executed on &e%s &bserver(s).", cmd, serverName));
        return true;
    }
}
