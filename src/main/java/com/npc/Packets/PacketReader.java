package com.npc.Packets;

import com.npc.NpcConfiguration.NPC;
import com.npc.NpcMain;
import com.npc.NpcConfiguration.rightClickNPC;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.lang.reflect.Field;
import java.util.*;

public class PacketReader {

    public static Map<UUID, Channel> channels = new HashMap<UUID, Channel>();
    private int count = 0;
    Channel channel;

    /***
     * Adds a {@link Player} to the packet reader pipeline. Allows the reading of player packet information.
     * @param player The player to be injected to the {@link Channel} pipeline.
     */
    public void inject(Player player) {

        CraftPlayer craftPlayer = (CraftPlayer) player;
        //Entity Player playerconnection, playerConnection network manager, Network manager channel
        channel = craftPlayer.getHandle().b.a.m;
        channels.put(player.getUniqueId(), channel);
        System.out.println(player.getDisplayName() + " Added to pipeline");

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

    /***
     * Removes a player from a Channel pipeline. Can be used on plugin disable or player quit event.
     * @param player the {@link Player} to be removed from the pipeline
     */
    public void unInject(Player player) {

        try{

            channel = channels.get(player.getUniqueId());

            try{

            channel.pipeline().remove("PacketInjector");

            }catch (Exception ex){

                System.out.println(ex.getMessage());
            }

            channels.remove(player.getUniqueId());
            System.out.println("Removed " + player.getDisplayName() + " from injector");

        }catch (Exception ex){

            System.out.println(Arrays.toString(ex.getStackTrace()));
            System.out.println("Player not removed");
        }
    }

    /***
     * Reads a {@link Packet} from a {@link Player}
     * @param player The player
     * @param packet The Packet
     */
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
