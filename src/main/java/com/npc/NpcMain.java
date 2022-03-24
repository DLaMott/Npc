package com.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
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
