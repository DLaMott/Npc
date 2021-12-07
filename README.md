# Npc loader for Minecraft Spigot Servers

## Hello!

This plugin allows a user to create an NPC that clones the user's skin. The NPC's location is then saved in a data.yml file to be reused after a server is shut down. The NPC will also display a message when the player clicks on the NPC. The messages are changeable within the given data.yml. Each spawned NPC can have their own custom message.

Please refer to version history to find the correct version for your spigot server! This plugin at this time is not backwards compatible. You must download the correct plugin version per your spigot server version! Refer to below for correct versions:

Current Github version is for Spigot 1.18!

Update 4.0 NPC fix/1.17.1 support : NPC head/body will rotate tracking a player when a player is within radius to the NPC, custom messages, various bugs.


## Todo:
Add admin in game user menu for NPC editing
Have one version to support all spigot versions.
Complete NPC deletion command

This plugin is ever growing!

Suggestions are more than welcome also please feel free to reach out if issues arise.

I will be re submitting older versions to support the upgrades and minor bug fixes.

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

There is a /deletenpc npc command included with this build but currently only destroys npc in game and does not yet remove the NPC from your data.yml. This means the NPC will reload upon server restart. 

## NPC example with command:
npcshowcase.png
