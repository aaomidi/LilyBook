package com.aaomidi.dev.lilybook.engine;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.commands.*;
import com.aaomidi.dev.lilybook.engine.configuration.I18n;
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

    //TODO Make the usage editable via server admins.
    private void setupCommands() {
        register(new AdminChatCommand("adminchat", "lilybook.adminchat", false, I18n.COMMAND_ADMIN_CHAT));
        register(new AlertClearCommand("alertclear", "lilybook.alert", false, I18n.COMMAND_ALERT_CLEAR));
        register(new AlertCommand("alert", "lilybook.alert", false, I18n.COMMAND_ALERT));
        register(new DispatchCommand("dispatch", "lilybook.dispatch", false, I18n.COMMAND_DISPATCH));
        register(new FindCommand("find", "lilybook.find", false, I18n.COMMAND_FIND));
        register(new GListCommand("glist", "lilybook.glist", false, I18n.COMMAND_GLIST));
        register(new MessageCommand("message", "lilybook.message", true, I18n.COMMAND_MESSAGE));
        register(new ReplyCommand("reply", "lilybook.message", true, I18n.COMMAND_REPLY));
        register(new SendAllCommand("sendall", "lilybook.sendall", false, I18n.COMMAND_SEND_ALL));
        register(new SendCommand("send", "lilybook.send", false, I18n.COMMAND_SEND));
        register(new ServerCommand("server", "lilybook.server", true, I18n.COMMAND_SERVER));
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
                StringManager.sendMessage(commandSender, I18n.ERROR_NO_PERMISSION);
                return true;
            }
            if (lilyCommand.isForcePlayer()) {
                if (!(commandSender instanceof Player)) {
                    StringManager.sendMessage(commandSender, I18n.ERROR_NOT_PLAYER);
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
