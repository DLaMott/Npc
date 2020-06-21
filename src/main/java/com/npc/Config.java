package com.npc;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private final FileConfiguration config;

    public Config(NpcMain plugin) {
        this.config = plugin.getConfig();
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }
}
