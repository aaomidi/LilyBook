package com.aaomidi.dev.lilybook.engine.commands;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.StringManager;
import com.aaomidi.dev.lilybook.engine.modules.LilyCommand;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class AdminChatCommand extends LilyCommand {
    public AdminChatCommand(String name, String permission, boolean forcePlayer, String usage) {
        super(name, permission, forcePlayer, usage);
    }

    @Override
    public boolean execute(LilyBook instance, LilyPlayer lilyPlayer, final CommandSender commandSender, Command command, String[] args) {
        if (args.length == 0) {
            if (lilyPlayer == null) {
                StringManager.sendMessage(commandSender, "&cSorry, this command is only executable via players in-game.");
                return true;
            }
            if (lilyPlayer.isStaffChat()) {
                lilyPlayer.setStaffChat(false);
                StringManager.sendMessage(commandSender, "&bStaff chat was disabled for you.");
                return true;
            }
            lilyPlayer.setStaffChat(true);
            StringManager.sendMessage(commandSender, "&bStaff chat was enabled for you.");
            return true;
        }
        String message = "";
        for (String arg : args) {
            message += arg + " ";
        }
        StringManager.sendAdminChatMessage(commandSender.getName(), message);
        return true;
    }
}
