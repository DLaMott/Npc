package com.npc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClickNPC implements Listener {
    private final FileConfiguration config;
    private final NpcMain main;

    public ClickNPC(NpcMain main) {
        this.main = main;
        this.config = main.getConfig();

    }

    @EventHandler
    public void theClick(rightClickNPC event) {

        Player player = event.getPlayer();
        player.sendMessage(config.getString("Message2"));
        player.sendMessage(config.getString("Message"));
    }


}
