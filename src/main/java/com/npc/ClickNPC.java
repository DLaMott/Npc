package com.npc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClickNPC implements Listener {
    private final FileConfiguration config;
    private final NpcMain main;
    public static DataManager data;

    public ClickNPC(NpcMain main) {
        this.main = main;
        this.config = main.getConfig();



    }

    @EventHandler
    public void theClick(rightClickNPC event) {

        data = new DataManager(main);
        FileConfiguration file = data.getConfig();

        Player player = event.getPlayer();
        player.sendMessage(config.getString("Message2"));
        player.sendMessage(config.getString("Message"));

        // will send message but is blank?
        //player.sendMessage(file.getString("1.message"));

        // sends each message for each npc in data config but how to limit to npc clicked?
        if (file.getConfigurationSection("data") != null) {
            file.getConfigurationSection("data").getKeys(false).forEach(npc -> {

                String message = file.getString("data." + npc + ".message");
                player.sendMessage(message);
            });}
        }


    }



