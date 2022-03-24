package com.npc.Packets;

import com.npc.NpcMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener {
    private NpcMain main;
    public OnQuit(NpcMain main){
        this.main = main;
    }

    /***
     * Upon a player quitting uninject the player from the channel
     * @param event Player quits
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        NpcMain.reader.unInject(event.getPlayer());
    }
}
