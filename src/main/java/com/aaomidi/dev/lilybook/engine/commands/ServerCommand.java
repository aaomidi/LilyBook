package com.aaomidi.dev.lilybook.engine.commands;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.Caching;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.configuration.I18n;
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
            //sb.append("&bServers currently online are: ");
            sb.append(I18n.SERVER_COMMAND);
            int x = 1;
            for (ProxyPlayers proxyPlayers : Caching.getNetworkPlayersMap().values()) {
                //sb.append(String.format("&e%s &8(&b%d&8)", proxyPlayers.getServerName(), proxyPlayers.getPlayersCount()));
                sb.append(StringManager.purifyMessage(I18n.SERVER_FORMAT, proxyPlayers.getServerName(), null, null, proxyPlayers.getPlayersCount(), null));
                if (x == Caching.getNetworkPlayersMap().size()) {
                    sb.append(String.format("%s.", I18n.COMMA_AND_DOT_COLOR));
                } else {
                    sb.append(String.format("%s, ", I18n.COMMA_AND_DOT_COLOR));
                }
                x++;
            }
            StringManager.sendMessage(commandSender, sb.toString());
            return true;
        }
        if (args.length == 1) {
            String serverName = args[0];
            if (serverName.equalsIgnoreCase(LilyBook.getSERVER_NAME())) {
                StringManager.sendMessage(commandSender, I18n.ERROR_ON_SERVER);
            }
            Callback<Boolean> callback = new Callback<Boolean>() {
                @Override
                public void execute(Boolean response) {
                    if (!response) {
                        StringManager.sendMessage(commandSender, I18n.ERROR_SERVER_OFFLINE);
                    }
                }
            };

            if (!instance.getLilyManager().asyncTeleportRequest(commandSender.getName(), serverName, callback)) {
                StringManager.sendMessage(commandSender, I18n.ERROR_ON_SERVER);
            }
            return true;
        }
        StringManager.sendMessage(commandSender, getUsage());
        return true;
    }
}
