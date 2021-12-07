package com.npc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;

public class DestroyNpcTab implements TabCompleter {

    List<String> arguments = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {

        NPC.getNpcs().stream().forEach(npc -> {
            String n = npc.getBukkitEntity().getName().substring(2);
            arguments.add(n);
        });
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


