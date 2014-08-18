package com.aaomidi.dev.lilybook.engine.commands;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class AlertCommand extends LilyCommand {
    public AlertCommand(String name, String permission, boolean forcePlayer, String usage) {
        super(name, permission, forcePlayer, usage);
    }

    @Override
    public boolean execute(LilyBook instance, LilyPlayer lilyPlayer, CommandSender commandSender, Command command, String[] args) {
        if (args.length == 0) {
            StringManager.sendMessage(commandSender, getUsage());
            return true;
        }
        String argument = args[0];
        String message = "";
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
                for (int i = 2; i < args.length; i++) {
                    String arg = args[i];
                    message += arg + " ";
                }
            }
        } else {
            if (args.length > 1) {
                StringManager.sendMessage(commandSender, getUsage());
                return true;
            }
        }
        instance.getLilyManager().asyncMessageRequest(ChannelType.ALERT_SERVERS, message, serverName);
        if (serverName == null) {
            serverName = "all";
        }
        StringManager.sendMessage(commandSender, String.format("&bYour message was broadcast on &e%s &bserver(s).", serverName));
        return true;
    }
}
