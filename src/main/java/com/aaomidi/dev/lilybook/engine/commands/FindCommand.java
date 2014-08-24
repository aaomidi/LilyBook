package com.aaomidi.dev.lilybook.engine.commands;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.Caching;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.configuration.I18n;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import com.aaomidi.dev.lilybook.engine.objects.ProxyPlayers;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;


public class FindCommand extends LilyCommand {
    public FindCommand(String name, String permission, boolean forcePlayer, String usage) {
        super(name, permission, forcePlayer, usage);
    }

    @Override
    public boolean execute(LilyBook instance, LilyPlayer lilyPlayer, final CommandSender commandSender, Command command, String[] args) {
        if (args.length == 0) {
            StringManager.sendMessage(commandSender, getUsage());
            return true;
        }
        final String playerName = args[0];
        if (playerName.length() > 16) {
            StringManager.sendMessage(commandSender, I18n.ERROR_PLAYER_NOT_FOUND);
            return true;
        }

        final ConcurrentMap<String, ProxyPlayers> networkPlayersMap = Caching.getNetworkPlayersMap();

        new BukkitRunnable() {
            final HashMap<String, String> possibleMatches = new HashMap<>();

            @Override
            public void run() {
                for (ProxyPlayers proxyPlayers : networkPlayersMap.values()) {
                    for (String pName : proxyPlayers.getPlayers()) {
                        if (pName.toLowerCase().startsWith(playerName)) {
                            possibleMatches.put(pName, proxyPlayers.getServerName());
                        }
                    }
                }
                if (possibleMatches.isEmpty()) {
                    StringManager.sendMessage(commandSender, I18n.ERROR_PLAYER_NOT_FOUND);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                // sb.append(String.format("&bThe server found %d possible match(es) for your entry %s.\n", possibleMatches.size(), playerName));
                sb.append(StringManager.purifyMessage(I18n.FIND_POSITIVE, null, null, null, possibleMatches.size(), playerName));
                for (Map.Entry<String, String> entrySet : possibleMatches.entrySet()) {
                    // sb.append(String.format("&bPlayer: &e%s &bon Server: &e%s \n", entrySet.getKey(), entrySet.getValue()));
                    sb.append(StringManager.purifyMessage(I18n.FIND_FORMAT, entrySet.getValue(), entrySet.getKey(), null, null, null));
                }
                StringManager.sendMessage(commandSender, sb.toString());
            }
        }.runTaskAsynchronously(instance);
        return true;
    }
}
