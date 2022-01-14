package net.rythenglyth.commandapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

public class CommandWrapper extends Command implements TabExecutor {

    public CommandWrapper(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return this.callIntern(sender, new ArrayList<>(Arrays.asList(args)));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command arg1, String arg2, String[] args) {
        return this.complete(sender, new ArrayList<>(Arrays.asList(args)));
    }

    public CommandWrapper setToPluginCommand(PluginCommand pc) {
        pc.setExecutor(this);
        pc.setTabCompleter(this);
        return this;
    }
    
}
