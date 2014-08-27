package com.aaomidi.dev.lilybook.engine.configuration;


import com.aaomidi.dev.lilybook.engine.objects.LilyConfig;
import org.bukkit.configuration.file.YamlConfiguration;

public class I18n {
    public static String STAFF_JOIN;
    public static String STAFF_LEAVE;
    public static String STAFF_CHAT_ENABLE;
    public static String STAFF_CHAT_DISABLE;
    public static String ALERT_CONFIRMATION;
    public static String DISPATCH_CONFIRMATION;
    public static String FIND_POSITIVE;
    public static String FIND_FORMAT;
    public static String SEND_ALL_CONFIRMATION;
    public static String SEND_CONFIRMATION;
    public static String SERVER_COMMAND;
    public static String SERVER_FORMAT;
    public static String COMMA_AND_DOT_COLOR;
    public static String DISPATCH_NOTIFY;
    public static String STAFF_MESSAGE_FORMAT;
    public static String PRIVATE_MESSAGE_FORMAT;
    public static String SOCIAL_SPY_FORMAT;
    public static String GLOBAL_LIST_SERVERS_FORMAT;
    public static String GLOBAL_LIST_STAFF_FORMAT;
    public static String GLOBAL_LIST_STAFF_TEXT;
    public static String ERROR_NO_REPLY;
    public static String ERROR_PLAYER_NOT_ONLINE;
    public static String ERROR_PLAYER_NOT_FOUND;
    public static String ERROR_NO_SERVER_ARGUMENT;
    public static String ERROR_NO_COMMAND_ARGUMENT;
    public static String ERROR_NOT_PLAYER;
    public static String ERROR_NO_PERMISSION;
    public static String ERROR_SERVER_OFFLINE;
    public static String COMMAND_ADMIN_CHAT;
    public static String COMMAND_ALERT_CLEAR;
    public static String COMMAND_ALERT;
    public static String COMMAND_DISPATCH;
    public static String COMMAND_FIND;
    public static String COMMAND_GLIST;
    public static String COMMAND_MESSAGE;
    public static String COMMAND_REPLY;
    public static String COMMAND_SEND_ALL;
    public static String COMMAND_SEND;
    public static String COMMAND_SERVER;


    public I18n() {
        this.setupStrings();
    }

    private void setupStrings() {
        LilyConfig lilyConfig = ConfigWriter.getLanguagesConfig();
        YamlConfiguration config = lilyConfig.getConfig();
        //Start general output messages.
        STAFF_JOIN = config.getString("StaffJoin");
        STAFF_LEAVE = config.getString("StaffLeave");
        STAFF_CHAT_ENABLE = config.getString("StaffChatEnable");
        STAFF_CHAT_DISABLE = config.getString("StaffChatDisable");
        ALERT_CONFIRMATION = config.getString("AlertConfirmation");
        DISPATCH_CONFIRMATION = config.getString("DispatchConfirmation");
        FIND_POSITIVE = config.getString("FindPositive");
        FIND_FORMAT = config.getString("FindFormat");
        SEND_ALL_CONFIRMATION = config.getString("SendAllConfirmation");
        SEND_CONFIRMATION = config.getString("SendConfirmation");
        SERVER_COMMAND = config.getString("ServerCommand");
        SERVER_FORMAT = config.getString("ServerFormat");
        DISPATCH_NOTIFY = config.getString("DispatchNotify");
        SOCIAL_SPY_FORMAT = config.getString("SocialSpyFormat");
        GLOBAL_LIST_SERVERS_FORMAT = config.getString("GlobalListServerFormat");
        GLOBAL_LIST_STAFF_TEXT = config.getString("GlobalListStaffText");
        GLOBAL_LIST_STAFF_FORMAT = config.getString("GlobalListStaffFormat");
        // End general output messages.
        COMMA_AND_DOT_COLOR = config.getString("CommaAndDotColor");
        // Start error messages.
        ERROR_NO_REPLY = config.getString("ErrorNoReply");
        ERROR_PLAYER_NOT_ONLINE = config.getString("ErrorPlayerNotOnline");
        ERROR_PLAYER_NOT_FOUND = config.getString("ErrorPlayerNotFound");
        ERROR_NO_SERVER_ARGUMENT = config.getString("ErrorNoServerArgument");
        ERROR_NO_COMMAND_ARGUMENT = config.getString("ErrorNoCommandArgument");
        ERROR_NOT_PLAYER = config.getString("ErrorNotPlayer");
        ERROR_NO_PERMISSION = config.getString("ErrorNoPermission");
        ERROR_SERVER_OFFLINE = config.getString("ErrorServerOffline");
        // End error messages.
        // Start different argument messages.
        STAFF_MESSAGE_FORMAT = config.getString("StaffMessageFormat");
        PRIVATE_MESSAGE_FORMAT = config.getString("PrivateMessageFormat");
        // End different argument messages.
        // Start command usages.
        COMMAND_ADMIN_CHAT = config.getString("CommandAdminChat");
        COMMAND_ALERT = config.getString("CommandAlertClear");
        COMMAND_ALERT = config.getString("CommandAlert");
        COMMAND_DISPATCH = config.getString("CommandDispatch");
        COMMAND_FIND = config.getString("CommandFind");
        COMMAND_GLIST = config.getString("CommandGList");
        COMMAND_MESSAGE = config.getString("CommandMessage");
        COMMAND_REPLY = config.getString("CommandReply");
        COMMAND_SEND_ALL = config.getString("CommandSendAll");
        COMMAND_SEND = config.getString("CommandSend");
        COMMAND_SERVER = config.getString("CommandServer");
        // End command usages.
    }
}
