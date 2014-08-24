package com.aaomidi.dev.lilybook.engine.commands;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.configuration.I18n;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class ReplyCommand extends LilyCommand {
    public ReplyCommand(String name, String permission, boolean forcePlayer, String usage) {
        super(name, permission, forcePlayer, usage);
    }

    @Override
    public boolean execute(LilyBook instance, LilyPlayer lilyPlayer, CommandSender commandSender, Command command, String[] args) {
        if (args.length == 0) {
            StringManager.sendMessage(commandSender, getUsage());
            return true;
        }
        if (lilyPlayer.getConversee() == null) {
            StringManager.sendMessage(commandSender, I18n.ERROR_NO_REPLY);
        }
        String message = "";
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            arg = arg.replace(":", "");
            message += arg + " ";
        }
        instance.getLilyManager().messageRequest(ChannelType.MESSAGE_PLAYER, String.format("%s:%s:%s", commandSender.getName(), lilyPlayer.getConversee(), message));
        return true;
    }
}
