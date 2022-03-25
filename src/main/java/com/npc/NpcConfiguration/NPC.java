package com.npc.NpcConfiguration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.npc.Data.DataManager;
import com.npc.NpcMain;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class NPC {

    private static final List<EntityPlayer> NPC = new ArrayList<EntityPlayer>();
    private final NpcMain main;
    public static DataManager data;

    public NPC(NpcMain main) {
        this.main = main;

    }

    /***
     *
     * @param player Player sending the request
     * @param skin Skin chosen
     */
    public static void createNPC(Player player,String name, String skin) {

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Objects.requireNonNull(Bukkit.getWorld(player.getWorld().getName()))).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.DARK_AQUA + name);
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile);
        npc.b(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(),
                player.getLocation().getYaw(), player.getLocation().getPitch());


        String[] skin1 = getSkin(player, skin);
        gameProfile.getProperties().put("textures", new Property("textures", skin1[0], skin1[1]));
        addNPCPacket(npc);
        NPC.add(npc);

        String message = "Type a new message in the data.yml. Exit/Restart/Reload Server";

        int var = 1;
        if (NpcMain.getData().contains("data")) {
            var = NpcMain.getData().getConfigurationSection("data").getKeys(false).size() + 1;
        }

        NpcMain.getData().set("data." + var + ".x", (int) player.getLocation().getX());
        NpcMain.getData().set("data." + var + ".y", (int) player.getLocation().getY());
        NpcMain.getData().set("data." + var + ".z", (int) player.getLocation().getZ());
        NpcMain.getData().set("data." + var + ".p", player.getLocation().getPitch());
        NpcMain.getData().set("data." + var + ".yaw", player.getLocation().getYaw());
        NpcMain.getData().set("data." + var + ".world", player.getLocation().getWorld().getName());
        NpcMain.getData().set("data." + var + ".name", name);
        NpcMain.getData().set("data." + var + ".text", skin1[0]);
        NpcMain.getData().set("data." + var + ".signature", skin1[1]);
        NpcMain.getData().set("data." + var + ".message", message);
        NpcMain.saveData();

    }

    /***
     * Adds NPC packet to the server.
     *
     * @param location Location in the game
     * @param profile Load game profile
     */
    public static void loadNPC(Location location, GameProfile profile) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        GameProfile gameProfile = profile;
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile);

        npc.b(location.getX(), location.getY(), location.getZ(),
                location.getYaw(), location.getPitch());

        addNPCPacket(npc);
        NPC.add(npc);

    }

    /***
     *
     * @param player Valid player
     * @param name The name of the skin
     * @return The texture and signature of a skin
     */
    private static String[] getSkin(Player player, String name) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();

            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader2 = new InputStreamReader(url2.openStream());
            JsonObject property = new JsonParser().parse(reader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();
            return new String[]{texture, signature};
        } catch (Exception e) {
            EntityPlayer p = ((CraftPlayer) player).getHandle();
            GameProfile profile = p.getBukkitEntity().getProfile();
            Property property = profile.getProperties().get("textures").iterator().next();
            String texture = property.getValue();
            String signature = property.getSignature();
            return new String[]{texture, signature};

        }
    }

    /***
     * Destroy the packet for an NPC
     * @param npc a valid npc
     */
    public static void removeNPC(EntityPlayer npc) {
        for(Player player : Bukkit.getOnlinePlayers()){
        PlayerConnection connection = ((CraftPlayer) player).getHandle().b;
        connection.a(new PacketPlayOutEntityDestroy(npc.getBukkitEntity().getEntityId()));
        }
    }

    /***
     *
     * @param npc The NPC being added
     */
    public static void addNPCPacket(EntityPlayer npc) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().b;
            connection.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
            connection.a(new PacketPlayOutNamedEntitySpawn(npc));
        }
    }

    /***
     *
     * @param player player connecting to the server
     */
    public static void addJoinPacket(Player player) {

        for (EntityPlayer npc : NPC) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().b;
            connection.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
            connection.a(new PacketPlayOutNamedEntitySpawn(npc));
        }
    }

    /***
     *
     * @return A list of NPCs
     */
    public static List<EntityPlayer> getNpcs(){
        return NPC;
    }
}
