package com.npc;

import net.minecraft.server.level.EntityPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;


public class ClickNPC implements Listener {

    private final NpcMain main;
    public static DataManager data;

    public ClickNPC(NpcMain main) {
        this.main = main;
    }

    @EventHandler
    public void theClick(rightClickNPC event) {

        data = new DataManager(main);
        FileConfiguration file = data.getConfig();

        Player player = event.getPlayer();
        EntityPlayer npc1 = event.getNpc();

        String npc2 = npc1.getBukkitEntity().getName().substring(2);
        ArrayList<String> messageC = new ArrayList<String>();
        ArrayList<String> name = new ArrayList<String>();

        if (file.getConfigurationSection("data") != null) {

            file.getConfigurationSection("data").getKeys(false).forEach( npc ->{
                String message = file.getString("data."+ npc +".message");
                String Cname = file.getString("data." + npc +".name");
                    messageC.add(message);
                    name.add(Cname);

            });

            for(int i = 0; i < name.size(); i++){
                if(npc2.equals(String.valueOf(name.get(i)))){
                    player.sendMessage(String.valueOf(messageC.get(i)));
                }
            }
        }
    }}



