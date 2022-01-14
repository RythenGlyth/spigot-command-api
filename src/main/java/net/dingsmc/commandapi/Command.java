package net.dingsmc.commandapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class Command implements TabCompleter {

    public List<Command> subcommands = new ArrayList<Command>();
    public String name;
    public Command parent;

    public Command(String name, Command parent, Command... subcommands) {
        this.name = name;
        this.subcommands = Arrays.asList(subcommands);
        this.parent = parent;
    }

    public final boolean callIntern(CommandSender sender, List<String> args) {
        if(args.size() > 0) {
            for(Command command : subcommands) {
                if(command.name.equalsIgnoreCase(args.get(0))) {
                    args.remove(0);
                    return command.callIntern(sender, args);
                }
            }
        }
        return this.call(sender, args);
    }

    public boolean call(CommandSender sender, List<String> args) {
        this.sendHelp(sender);
        return true;
    }

    public void sendHelp(CommandSender sender) {
        if(parent != null) parent.sendHelp(sender);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias,
            String[] args) {
        // TODO Auto-generated method stub
        return null;
    }
}
