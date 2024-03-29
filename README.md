# Npc loader for Minecraft Spigot Servers

## Hello!

This plugin allows a user to create an NPC that clones the user's skin. If users would like to use an entity skin simply type the skin name after the npc name upon creation. The NPC's location is then saved in a data.yml file to be reused after a server is shut down. The NPC will also display a message when the player clicks on the NPC. The messages are changeable within the given data.yml. Each spawned NPC can have their own custom message.

Please refer to version history to find the correct version for your spigot server! This plugin at this time is not backwards compatible. You must download the correct plugin version per your spigot server version! Refer to below for correct versions:

For multiple versions go to the main spigot repo for this plugin.

The current version supported is 1.18.2 servers only as this plugin is not backwards compatible!

### Spigot repo location: https://www.spigotmc.org/resources/npc.81087/

### Statistics?
In the recent update metrics have been added to give an overall view of the 
current users for this plugin. Sadly, this will not show the previous version
statistics but we can see the current version here:

https://bstats.org/plugin/bukkit/NpcMain/14744


Current GitHub version is for Spigot 1.18.2!

Update 4.0 NPC fix/1.17.1 support : NPC head/body will rotate tracking a player when a player is within radius to the NPC, custom messages, various bugs.

Update 5.0 NPC can now be delete via command, custom skins by naming added, custom skin tab added, performances issues resolved.


## Todo:
Add admin in game user menu for NPC editing
Have one version to support all spigot versions.

Suggestions are more than welcome also please feel free to reach out if issues arise.

I will be re-submitting older versions to support the upgrades and minor bug fixes.

### Thank you,

### GothamsJoker

## Installation:

For just the jar? Go to spigot: https://www.spigotmc.org/resources/npc.81087/

Download jar per your spigot version.
Place jar in plugin folder for your server.
Upon server load data.yml will be created and stored in your server plugin directory under a new folder named: NpcMain.

Want the whole code? Sure, simply clone this repo and you should be good to go! To aquire jar run 'mvn package' within this directory.

## Usage:
In game admin must enter /createnpc to create a NPC. NPC will mirror the current player skin you are wearing. The NPC will spawn at your location.

## Set up NPC:
Navigate to data.yml and change the text area for (name: rename) and (message: changeme)
Close and restart server to see your changes!

## Delete NPC:

Option one:
In game admins can simply type /deletenpc and select the npc name they wish to delete. Simply restart the server to see the changes.

Option two:
Navigate to data.yml and delete the NPC fields for the NPC you wish to delete.
Field example to delete:
'1':
x: -157
y: 64
z: -118
p: 15.899899
yaw: 153.44925
world: world
name: changeme
text: ewogICJ0aW1lc3RhbXAiIDogMTYzODY4OTI2OTI2OSwKICAicHJvZml=
signature: V27ZEnsBjrzYDm+0lctbZJKDOwVsrKQbaleVhzt775D45JPf17lsWw1rDFV8Eq/Aqu5A6x0=
message: changeme

## NPC example with command:
![npcshowcase](https://user-images.githubusercontent.com/66742430/144964825-0edca4cf-b2e7-4cbd-a899-7570363ba75e.png)

