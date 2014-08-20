package com.aaomidi.dev.lilybook.engine;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.commands.*;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.logging.Level;

public class CommandsManager implements CommandExecutor {
    private final LilyBook instance;
    private HashMap<String, LilyCommand> commands;

    public CommandsManager(LilyBook instance) {
        this.instance = instance;
        commands = new HashMap<>();
        this.setupCommands();
    }

    private void setupCommands() {
        register(new AdminChatCommand("adminchat", "lilybook.adminchat", false, "&3/adminchat &e(Message)"));
        register(new AlertCommand("alert", "lilybook.alert", false, "&3/alert &e(-s ServerName) [Command]"));
        register(new DispatchCommand("dispatch", "lilybook.dispatch", false, "&3/dispatch &e(-s ServerName) [Command]"));
        register(new FindCommand("find", "lilybook.find", false, "&3/find &e[PlayerName]"));
        register(new GListCommand("glist", "lilybook.glist", false, "&3/glist"));
        register(new MessageCommand("message", "lilybook.message", true, "&3/msg &e[PlayerName] [Message]"));
        register(new ReplyCommand("reply", "lilybook.message", true, "&3/reply &e[Message]"));
        register(new SendAllCommand("sendall", "lilybook.sendall", false, "&3/sendall &e[ServerName]"));
        register(new SendCommand("send", "lilybook.send", false, "&3/send &e[PlayerName] &e[ServerName]"));
        register(new ServerCommand("server", "lilybook.server", true, "&3/server &e(ServerName)"));
    }

    private void register(LilyCommand lilyCommand) {
        PluginCommand command = instance.getCommand(lilyCommand.getName());
        if (command == null) {
            return;
        }
        commands.put(lilyCommand.getName(), lilyCommand);
        instance.getCommand(lilyCommand.getName()).setExecutor(this);
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
        } else {
            instance.getLogger().log(Level.SEVERE, "Command wasn't found in the commands map.");
        }
        return true;
    }
}
