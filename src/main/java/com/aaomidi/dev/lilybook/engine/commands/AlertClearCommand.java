package com.aaomidi.dev.lilybook.engine.commands;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.configuration.I18n;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class AlertClearCommand extends LilyCommand {
    public AlertClearCommand(String name, String permission, boolean forcePlayer, String usage) {
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
                StringManager.sendMessage(commandSender, I18n.ERROR_NO_SERVER_ARGUMENT);
                return true;
            } else if (args.length == 2) {
                StringManager.sendMessage(commandSender, I18n.ERROR_NO_COMMAND_ARGUMENT);
                return true;
            } else {
                serverName = args[1];
                for (int i = 2; i < args.length; i++) {
                    String arg = args[i];
                    message += arg + " ";
                }
            }
        } else {
            if (args.length < 1) {
                StringManager.sendMessage(commandSender, getUsage());
                return true;
            }
            for (String arg : args) {
                message += arg + " ";
            }
        }
        instance.getLilyManager().asyncMessageRequest(ChannelType.ALERT_CLEAR_SERVERS, message, serverName);
        if (serverName == null) {
            serverName = "all";
        }
        StringManager.sendMessage(commandSender, StringManager.purifyMessage(I18n.ALERT_CONFIRMATION, serverName, null, null, null, null));
        return true;
    }
}
