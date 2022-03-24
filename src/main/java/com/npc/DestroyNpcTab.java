package com.npc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class DestroyNpcTab implements TabCompleter {

    List<String> arguments = new ArrayList<>();

    private final NpcMain main;
    public static DataManager data;


    public DestroyNpcTab(NpcMain main) {
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {

        data = new DataManager(main);
        FileConfiguration file = data.getConfig();

        if (file.getConfigurationSection("data") != null) {
            file.getConfigurationSection("data").getKeys(false).forEach(npc ->{
                arguments.add(file.getString("data." + npc + ".name"));
            });
        }

        List<String> result = new ArrayList<String>();
        if (args.length == 1) {
            for (String a : arguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) result.add(a);
            }
            return result;
        }
        return arguments;
    }
}


