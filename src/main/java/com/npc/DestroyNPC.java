package com.npc;

import net.minecraft.server.level.EntityPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import java.util.ArrayList;

public class DestroyNPC implements CommandExecutor, Listener {

    private final NpcMain main;
    public static DataManager data;


    public DestroyNPC(NpcMain main) {
        this.main = main;
    }

    /***
     *  Takes in a destroynpc label and a string for the npc in need of deletion.
     *  Proper validation will call {@link NPC} to remove the NPC from the server.
     *  Proper name validation will remove the NPC from the data.yml file.
     * @param sender Sender of the command
     * @param cmd Command being sent
     * @param label String label
     * @param args String text
     * @return True upon a valid command string
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        data = new DataManager(main);
        FileConfiguration file = data.getConfig();

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
                try {
                    if (args[0].equals(npcNames.get(i))) {

                        NPC.removeNPC(player, npc1.get(i));
                        player.sendMessage("NPC zooted");

                                if (file.getConfigurationSection("data") != null) {
                                    file.getConfigurationSection("data").getKeys(false).forEach(npc ->{

                                        if(file.getString("data." + npc + ".name").equals(args[0])){

                                            String path = ("data." + npc);

                                            try{
                                                NpcMain.getData().set(path, null);
                                                NpcMain.saveData();
                                                NpcMain.reload();

                                                }catch (Exception e){
                                                System.out.println("Cant find path!");
                                            }
                                        }
                                    });
                                }
                        return true;
                    }
                } catch (Exception e) {
                    player.sendMessage("Enter a valid npc name!!!!");
                }
            }
        }
        return false;
    }
}
