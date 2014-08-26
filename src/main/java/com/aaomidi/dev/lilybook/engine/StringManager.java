package com.aaomidi.dev.lilybook.engine;


import com.aaomidi.dev.lilybook.LilyBook;
import com.aaomidi.dev.lilybook.engine.configuration.ConfigReader;
import com.aaomidi.dev.lilybook.engine.configuration.I18n;
import com.aaomidi.dev.lilybook.engine.modules.ChannelType;
import com.aaomidi.dev.lilybook.engine.objects.ProxyPlayers;
import com.aaomidi.dev.lilybook.engine.objects.ProxyStaff;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Map;

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

    /**
     * Sends a message formatted for StaffChat.
     *
     * @param sender  The sender.
     * @param message The message to be sent.
     */
    public static void sendAdminChatMessage(String sender, String message) {
        // String formattedMessage = colorize(String.format("&8[&4&lS&8][&a%s&8][&6%s&8] &e%s", LilyBook.getSERVER_NAME(), sender, message));
        String formattedMessage = colorize(purifyMessage(I18n.STAFF_MESSAGE_FORMAT, LilyBook.getSERVER_NAME(), sender, null, null, message));
        LilyBook.getInstance().getLilyManager().asyncMessageRequest(ChannelType.ADMIN_CHAT_MESSAGE, formattedMessage);
    }

    public static String getCrossServerMessage(String server, String message, String sender) {
        return colorize(purifyPrivateMessage(I18n.PRIVATE_MESSAGE_FORMAT, server, sender, null, message));
        // return colorize(String.format("&8&l[&b%s&8&l][&e%s&8&l]&r %s", server, sender, message));
    }

    public static String getStaffCrossServerMessage(String server, String message, String sender, String receiver) {
        return colorize(purifyPrivateMessage(I18n.SOCIAL_SPY_FORMAT, server, sender, receiver, message));
        // return colorize(String.format("&8&l[&eSocialSpy&8&l][&e%s&8&l][&b%s&8&l]&r %s", sender, receiver, message));
    }

    public static String getGlobalListMessage() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, ProxyPlayers> entry : Caching.getNetworkPlayersMap().entrySet()) {
            // sb.append(String.format("&8&l[&b%s&8&l] &r&e%d\n", entry.getKey(), entry.getValue().getPlayersCount()));
            sb.append(StringManager.purifyMessage(I18n.GLOBAL_LIST_SERVERS_FORMAT, entry.getKey(), null, null, entry.getValue().getPlayersCount(), null));
        }
        int x = 1;
        int y = 1;
        sb.append(I18n.GLOBAL_LIST_STAFF_TEXT);
        for (Map.Entry<String, ProxyStaff> entry : Caching.getNetworkStaffMap().entrySet()) {
            for (String playerName : entry.getValue().getStaff()) {
                // sb.append(String.format("&e%s&8(&b%s&8)", playerName, entry.getKey()));
                sb.append(StringManager.purifyMessage(I18n.GLOBAL_LIST_STAFF_FORMAT, entry.getKey(), playerName, null, null, null));
                if (x == Caching.getNetworkStaffMap().entrySet().size()) {
                    if (y == entry.getValue().getStaffCount()) {
                        sb.append(String.format("%s.", I18n.COMMA_AND_DOT_COLOR));
                    } else {
                        sb.append(String.format("%s, ", I18n.COMMA_AND_DOT_COLOR));
                    }
                    y++;
                }
            }
            x++;
        }
        return sb.toString();
    }

    /**
     * Method to purify a message according to its arguments.
     *
     * @param rawMessage The message to @purifyMessage.
     * @param serverName Name of server, if N/A enter null.
     * @param playerName Name of player, if N/A enter null.
     * @param command    Command string, if N/A enter null.
     * @param count      The integer argument, if N/A enter null.
     * @param subMessage The sub-message, if N/A enter null.
     * @return The purified message.
     */
    public static String purifyMessage(String rawMessage, String serverName, String playerName, String command, Integer count, String subMessage) {
        if (serverName == null) {
            serverName = "";
        }
        if (playerName == null) {
            playerName = "";
        }
        if (command == null) {
            command = "";
        }
        if (count == null) {
            count = 0;
        }
        if (subMessage == null) {
            subMessage = "";
        }
        return rawMessage
                .replace("$s$", serverName)
                .replace("$p$", playerName)
                .replace("$c$", command)
                .replace("$d$", String.valueOf(count))
                .replace("$m$", subMessage);

    }

    /**
     * Method to purify a private message.
     *
     * @param rawMessage The message to @purifyMessage.
     * @param sender     The sender of the message.
     * @param receiver   The receiver of the message.
     * @param msg        The sub-message.
     * @return The purified message.
     */
    public static String purifyPrivateMessage(String rawMessage, String server, String sender, String receiver, String msg) {
        if (server == null) {
            server = "";
        }
        if (sender == null) {
            sender = "";
        }
        if (receiver == null) {
            receiver = "";
        }
        if (msg == null) {
            msg = "";
        }
        return rawMessage
                .replace("$server$", server)
                .replace("$sender$", sender)
                .replace("$receiver$", receiver)
                .replace("$message$", msg);
    }
}
