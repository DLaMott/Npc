package com.npc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class AddNPC implements CommandExecutor, Listener {

    public AddNPC() {

    }

    /***
     * Takes a string argument and determines if was sent by a player or else. If command
     * is from a player and is the correct format {@link NPC} is called to create a npc.
     * @param sender The sender of the string command.
     * @param cmd The command.
     * @param label String text for the command.
     * @param args The amount of strings sent
     * @return True upon valid string
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("createnpc")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 0) {
                NPC.createNPC(player, player.getName());
                player.sendMessage("NPC created");
                return true;
            }
            NPC.createNPC(player, args[0]);
            player.sendMessage("NPC created");
            return true;
        }
        return false;
    }
}
