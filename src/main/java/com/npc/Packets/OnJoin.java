package com.npc.Packets;

import com.npc.NpcConfiguration.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class OnJoin implements Listener {

    /***
     * Upon a player join event inject the player into the Channel
     * @param event The player joined
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PacketReader reader = new PacketReader();
        try{
        reader.inject(event.getPlayer());}catch (Exception e){
            System.out.println("Player not injected!!!!!!");
        }
        NPC.addJoinPacket(event.getPlayer());
    }
}
