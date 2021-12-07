package com.npc;

import net.minecraft.server.level.EntityPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import java.util.ArrayList;

public class DestroyNPC implements CommandExecutor, Listener {
    public static DataManager data;

    public DestroyNPC() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> npcNames = new ArrayList<String>();
        ArrayList<EntityPlayer> npc1 = new ArrayList<EntityPlayer>();

        NPC.getNpcs().stream().forEach(npc -> {
            String n = npc.getBukkitEntity().getName().substring(2);
            npcNames.add(n);
            npc1.add(npc);
        });

        for (int i = 0; i < npc1.size(); i++) {

            if (label.equalsIgnoreCase("destroynpc")) {
                if (!(sender instanceof Player)) {
                    return true;
                }
                Player player = (Player) sender;

                if (args[0].equals(npcNames.get(i))) {
                    //Todo: Erase NPC from data.yml upon command call
                    NPC.removeNPC(player, npc1.get(i));
                    System.out.println(npc1.get(i));
                    player.sendMessage("NPC zooted");
                    return true;
                }
            }
        }
        return false;
    }
}
