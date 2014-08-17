package com.aaomidi.dev.lilybook.engine.modules;

import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.objects.LilyPlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public abstract class LilyCommand {
    @Getter
    private final String name;
    @Getter
    private final String permission;
    @Getter
    private final boolean forcePlayer;
    @Getter
    private final String usage;

    public abstract boolean execute(LilyBook instance, LilyPlayer lilyPlayer, CommandSender commandSender, Command command, String args[]);

    @Override
    public String toString() {
        return String.format("Command=(name=%s, permission=%s)", name, permission);
    }
}
