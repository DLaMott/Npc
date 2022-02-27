package com.npc;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PacketReader {

    public Map<UUID, Channel> channels = new HashMap<UUID, Channel>();
    private int count = 0;
    Channel channel;

    public void inject(Player player) {

        CraftPlayer craftPlayer = (CraftPlayer) player;
        channel = craftPlayer.getHandle().b.a.k;
        channels.put(player.getUniqueId(), channel);

        if (channel.pipeline().get("PacketInjector") != null) {
            return;
        }

        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<PacketPlayInUseEntity>() {

            @Override
            protected void decode(ChannelHandlerContext channel, PacketPlayInUseEntity packet, List<Object> arg) throws Exception {
                arg.add(packet);
                readPacket(player, packet);
            }
        });
    }

    public void unInject(Player player) {
        channel = channels.get(player.getUniqueId());
        channel.pipeline().remove("PacketInjector");
        channels.remove(player.getUniqueId());
    }

    public void readPacket(Player player, Packet<?> packet) {
        if (packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
            // Counts all actions
            count++;
            if (count == 4) {
                count = 0;
                int id = (int) getValue(packet, "a");
                for (EntityPlayer npc : NPC.getNpcs()) {
                    if (npc.getBukkitEntity().getEntityId() == id) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(NpcMain.getPlugin(NpcMain.class),
                                () -> Bukkit.getPluginManager().callEvent(new rightClickNPC(player, npc)), 0);
                    }
                }
            }
        }
        // Action for packet readers currently not operational
        /* System.out.println("Packet >> " + packet);
        if (packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {

            if (getValue(packet, "action").toString().equalsIgnoreCase("ATTACK"))
                return;
            if (getValue(packet, "d").toString().equalsIgnoreCase("OFF_HAND"))
                return;
            if (getValue(packet, "action").toString().equalsIgnoreCase("INTERACT_AT"))
                return;
            int id = (int) getValue(packet, "a");
            if (getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")) {

                for (EntityPlayer npc : NPC.getNpcs()) {
                    if (npc.getId() == id) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(NpcMain.getPlugin(NpcMain.class),
                         () -> Bukkit.getPluginManager().callEvent(new rightClickNPC(player, npc)), 0);
                    }
                }
            }
        }*/
    }

    private Object getValue(Object instance, String name) {
        Object result = null;

        try {
            Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            result = field.get(instance);
            field.setAccessible(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
