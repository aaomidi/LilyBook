package com.aaomidi.dev.lilybook.engine.commands;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.modules.Callback;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SendCommand extends LilyCommand {
    public SendCommand(String name, String permission, boolean forcePlayer, String usage) {
        super(name, permission, forcePlayer, usage);
    }

    @Override
    public boolean execute(LilyBook instance, LilyPlayer lilyPlayer, final CommandSender commandSender, Command command, String[] args) {
        if (args.length == 0) {
            StringManager.sendMessage(commandSender, getUsage());
            return true;
        }
        if (args.length == 1) {
            StringManager.sendMessage(commandSender, "&cPlease specify a server.");
            return true;
        }
        final String playerName = args[0];
        if (instance.getServer().getPlayer(playerName) == null) {
            StringManager.sendMessage(commandSender, "&cThat player is not online");
        }
        final String serverName = args[1];
        Callback<Boolean> callback = new Callback<Boolean>() {
            @Override
            public void execute(Boolean response) {
                if (!response) {
                    StringManager.sendMessage(commandSender, "&cThat server seems to be offline");
                    return;
                }
                StringManager.sendMessage(commandSender, String.format("&bSent player &e%s &bto server &e%s.", playerName, serverName));
                // return;
            }
        };
        instance.getLilyManager().asyncTeleportRequest(playerName, serverName, callback);
        return true;
    }
}
