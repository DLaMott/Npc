package com.npc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        PacketReader reader = new PacketReader();
        reader.inject(event.getPlayer());
        NPC.addJoinPacket(event.getPlayer());
    }
}
