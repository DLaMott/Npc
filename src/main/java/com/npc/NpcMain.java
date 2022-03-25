package com.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.npc.Data.DataManager;
import com.npc.Data.Metrics;
import com.npc.Data.MovementListener;
import com.npc.Destroy.DestroyNPC;
import com.npc.Destroy.DestroyNpcTab;
import com.npc.NpcConfiguration.AddNPC;
import com.npc.NpcConfiguration.ClickNPC;
import com.npc.NpcConfiguration.NPC;
import com.npc.NpcConfiguration.SkinTab;
import com.npc.Packets.OnJoin;
import com.npc.Packets.OnQuit;
import com.npc.Packets.PacketReader;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.UUID;

public class NpcMain extends JavaPlugin {
    public static DataManager data;

    public static FileConfiguration getData() {
        return data.getConfig();
    }

    public static void saveData() {
        data.saveConfig();
    }

    public static void reload(){
        data.reloadConfig();
    }

    public static PacketReader reader = new PacketReader();

    @Override
    public void onEnable() {

        data = new DataManager(this);

        if (data.getConfig().contains("data")) {
            loadNPC();
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            reader.inject(player);
        }

        this.getServer().getPluginManager().registerEvents(new OnJoin(), this);
        this.getServer().getPluginManager().registerEvents(new ClickNPC(this), this);
        this.getCommand("createnpc").setExecutor(new AddNPC());
        this.getCommand("destroynpc").setExecutor(new DestroyNPC(this));
        this.getServer().getPluginManager().registerEvents(new MovementListener(), this);
        this.getCommand("destroynpc").setTabCompleter(new DestroyNpcTab(this));
        this.getCommand("createnpc").setTabCompleter(new SkinTab());
        this.getServer().getPluginManager().registerEvents(new OnQuit(this), this);

        int pluginId = 14744;
        Metrics metrics = new Metrics(this, pluginId);

        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
    }

    @Override
    public void onDisable() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            reader.unInject(player);

            for (EntityPlayer npc : NPC.getNpcs()){
               try{

                   NPC.removeNPC(npc);

               }catch (Exception cx){
                   cx.getCause();
               }
            }
        }
    }

    public void loadNPC() {
        FileConfiguration file = data.getConfig();
        if (file.getConfigurationSection("data") != null) {
            file.getConfigurationSection("data").getKeys(false).forEach(npc -> {

                Location location = new Location(Bukkit.getWorld(file.getString("data." + npc + ".world")),
                        file.getInt("data." + npc + ".x"),
                        file.getInt("data." + npc + ".y"),
                        file.getInt("data." + npc + ".z"));
                location.setPitch((float) file.getDouble("data." + npc + ".p"));
                location.setYaw((float) file.getDouble("data." + npc + ".yaw"));

                String name = file.getString("data." + npc + ".name");
                GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.DARK_AQUA + "" + name);
                gameProfile.getProperties().put("textures", new Property("textures",
                        file.getString("data." + npc + ".text"),
                        file.getString("data." + npc + ".signature")));

                NPC.loadNPC(location, gameProfile);
            });
        }
    }
}
