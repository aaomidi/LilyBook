package com.aaomidi.dev.lilybook.engine;


import com.aaomidi.dev.lilybook.engine.configuration.ConfigReader;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class StringManager {
    /**
     * Sends a message to a player. This colorizes the message and adds a prefix.
     *
     * @param sender  The person to send the message to.
     * @param message The message to send to the person.
     */
    public static void sendMessage(CommandSender sender, String message) {
        String prefix = ConfigReader.getPrefix();
        if (prefix != null) {
            message = prefix + message;
            message = colorize(message);
            sendRawMessage(sender, message);
            return;
        }
        sendRawMessage(sender, message);
        //return;
    }

    /**
     * Colorizes a string using the ChatColor.translateAlternateColorCodes.
     *
     * @param message The message to be colorized.
     * @return The colorized message.
     */
    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Sends a message to a player. This colorizes the message but does not add a prefix.
     *
     * @param sender  The person to send the message to.
     * @param message The message to send to the person.
     */
    public static void sendRawMessage(CommandSender sender, String message) {
        sender.sendMessage(colorize(message));
    }
}
