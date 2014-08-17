package com.aaomidi.dev.lilybook.engine;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.commands.DispatchCommand;
import com.aaomidi.dev.lilybook.engine.commands.FindCommand;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandsManager implements CommandExecutor {
    private final LilyBook instance;
    private static HashMap<String, LilyCommand> commands;

    public CommandsManager(LilyBook instance) {
        this.instance = instance;
        commands = new HashMap<>();
        this.setupCommands();
    }

    public void setupCommands() {
        new DispatchCommand("dispatch", "lilybook.dispatch", true, "&3/dispatch &e(-s ServerName) [Command]");
        new FindCommand("find", "lilybook.find", false, "&3/find &e[PlayerName]");
    }

    public static void register(LilyCommand lilyCommand) {
        commands.put(lilyCommand.getName(), lilyCommand);
        LilyBook instance = LilyBook.getInstance();
        instance.getCommand(lilyCommand.getName()).setExecutor(instance.getCommandsManager());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String commandLabel, String[] args) {
        LilyPlayer lilyPlayer = null;
        lilyPlayer = Caching.getLilyPlayersMap().get(commandSender.getName());
        if (commands.containsKey(command.getName())) {
            LilyCommand lilyCommand = commands.get(command.getName());
            if (!commandSender.hasPermission(lilyCommand.getPermission())) {
                StringManager.sendMessage(commandSender, "&cSorry, you do not have permission to run that command.");
                return true;
            }
            if (lilyCommand.isForcePlayer()) {
                if (!(commandSender instanceof Player)) {
                    StringManager.sendMessage(commandSender, "&cSorry, this command is only executable via players in-game.");
                    return true;
                }
                return lilyCommand.execute(instance, lilyPlayer, commandSender, command, args);
            }

            return lilyCommand.execute(instance, lilyPlayer, commandSender, command, args);
        }
        return true;
    }
}
