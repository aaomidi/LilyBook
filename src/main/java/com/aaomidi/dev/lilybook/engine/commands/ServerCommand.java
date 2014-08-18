package com.aaomidi.dev.lilybook.engine.commands;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.Caching;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.modules.Callback;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import com.aaomidi.dev.lilybook.engine.objects.ProxyPlayers;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ServerCommand extends LilyCommand {
    public ServerCommand(String name, String permission, boolean forcePlayer, String usage) {
        super(name, permission, forcePlayer, usage);
    }

    @Override
    public boolean execute(LilyBook instance, LilyPlayer lilyPlayer, final CommandSender commandSender, Command command, String[] args) {
        if (args.length == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("&bServers currently online are: ");
            int x = 1;
            for (ProxyPlayers proxyPlayers : Caching.getNetworkPlayersMap().values()) {
                sb.append(String.format("&e%s &8(&b%d&8)", proxyPlayers.getServerName(), proxyPlayers.getPlayersCount()));
                if (x == Caching.getNetworkPlayersMap().size()) {
                    sb.append("&e.");
                }
                sb.append("&e, ");
                x++;
            }
            StringManager.sendMessage(commandSender, sb.toString());
            return true;
        }
        if (args.length == 1) {
            String serverName = args[0];
            Callback<Boolean> callback = new Callback<Boolean>() {
                @Override
                public void execute(Boolean response) {
                    if (!response) {
                        StringManager.sendMessage(commandSender, "&cThat server seems to be offline.");
                    }
                }
            };
            instance.getLilyManager().asyncTeleportRequest(commandSender.getName(), serverName, callback);
            return true;
        }
        StringManager.sendMessage(commandSender, getUsage());
        return true;
    }
}
