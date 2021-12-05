package com.npc;

import net.minecraft.network.protocol.game.PacketPlayOutEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
//Todo: Limit NPC tracking to 5*5*5 block radius
public class MovementListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){

        NPC.getNpcs().stream().forEach(npc -> {

            Location location = npc.getBukkitEntity().getLocation();
            location.setDirection(e.getPlayer().getLocation().subtract(location).toVector());
            float yaw = location.getYaw();
            float pitch = location.getPitch();

            PlayerConnection connection = ((CraftPlayer) e.getPlayer()).getHandle().b;
            connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.getId(), (byte) (int) ((yaw % 360.0F) * 256.0F / 360.0F), (byte) (int) ((pitch % 360.0F) * 256.0F / 360.0F), false));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) ((yaw % 360.0F) * 256.0F / 360.0F)));

        });
    }
}
