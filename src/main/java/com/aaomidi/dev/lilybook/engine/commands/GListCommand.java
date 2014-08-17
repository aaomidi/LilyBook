package com.aaomidi.dev.lilybook.engine.commands;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.Caching;
import com.aaomidi.dev.lilybook.engine.CommandsManager;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import com.aaomidi.dev.lilybook.engine.objects.ProxyPlayers;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class GListCommand extends LilyCommand {
    public GListCommand(String name, String permission, boolean forcePlayer, String usage) {
        super(name, permission, forcePlayer, usage);
        CommandsManager.register(this);
    }

    @Override
    public boolean execute(LilyBook instance, LilyPlayer lilyPlayer, CommandSender commandSender, Command command, String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, ProxyPlayers> entry : Caching.getNetworkPlayersMap().entrySet()) {
            stringBuilder.append(String.format("&8&l[&b%s&8&l] &r&e%d\n", entry.getKey(), entry.getValue().getPlayersCount()));
        }
        return true;
    }
}
