package com.npc.NpcConfiguration;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import java.util.ArrayList;
import java.util.List;

public class SkinTab implements TabCompleter {

    List<String> arguments = new ArrayList<>();

    /***
     * Creates a tab completer for all possible {@link org.bukkit.entity.Entity} types.
     * @param commandSender Entity sending command
     * @param command the command
     * @param label the label
     * @param args the text
     * @return
     */
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {

        EntityType[] m = EntityType.values();
        for (int i = 0; i < m.length; i++){
            arguments.add(m[i].name());
        }
        List<String> results = new ArrayList<String>();
        if (args.length == 1) {
            for (String a : arguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) results.add(a);
            }
            return results;
        }
        return arguments;
    }
}
