
# LilyBook

![LilyBook Logo][1]

A big thanks to these people for donating and supporting the development of the plugin!

**CraftShark** - **play.craftshark.net**

**CowCraft** - **mc.cowcraft.net**

Thanks to these people for reviewing the code and helping me find the bugs!

@L33m4n123 - Reviewing the code.

@DarkSeraphim - Reviewing the code.

@edyyy - Testing the plugin. - mc.cowcraft.net

@roastnewt - Testing the plugin. - minepure.com

@shawshark - Testing the plugin. - play.craftshark.net 

@Wobbolino1 - Creating the beautiful logo! - mc.cowcraft.net

----------

## Commands:
 - /adminchat - Toggles the adminchat to active.
 - /adminchat [message] - Sends a message to the admin channel.
 - /alert - Sends a message to one/all servers. To specify a server use -s (servername)
 - /dispatch - Dispatches a command to one/all servers. To specify a server use -s (servername).
 - /find [playerName] - Looks for a player on the network, the name can be partially correct and it will still find the player.
 - /glist - Shows the global list of the server.
 - /message - Sends a network wide message.
 - /reply - Replies to a message sent to you.
 - /sendall [serverName] - Sends all players to the specified server.
 - /send [playerName] [serverName] - Sends the player to the specified server.
 - /server [serverName] - Shows the server the player can connect to and teleports him there.

Features:

+ /glist will not show vanished players (Essentials).
+ /message will not send a message to a player if that player has ignored the sender (Essentials).
+ Teleports players to a specified server when the server is shutting down or reloading.
+ When a staff members joins or leaves, other staff members on the server will be notified.

 ----------
 
## Permissions:

 - lilybook.adminchat - Allows the use of /adminchat & users with this permission string will receive messages from the staff chat.
 - lilybook.dispatch - Allows the use of /dispatch. Becareful, people with this permission can op themselves.
 - lilybook.find - Allows the use of /find.
 - lilybook.glist - Allows the use of /glist.
 - lilybook.message - Allows the use of /message and /reply
 - lilybook.sendall - Allows the use of /sendall.
 - lilybook.send - Allows the use of /send.
 - lilybook.server - Allows the use of /server.
 - lilybook.notify - People with this permission will be notified when someone does /dispatch and/or a staff join/leaves.
 - lilybook.stafflist - People with this permission will be considered staff in /glist and will send join/leave notifications.
 - lilybook.socialspy - People with this permission will be able to read the messages sent in /msg.
 
----------

## Configuration:

The configuration is very straight forward.
Please read the comments in LilyBook's default config.yml.

----------

  [1]: https://raw.githubusercontent.com/aaomidi/LilyBook/master/logo/logo.png
