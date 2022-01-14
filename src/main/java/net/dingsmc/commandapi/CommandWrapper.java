package net.dingsmc.commandapi;

import java.util.Arrays;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandWrapper extends Command implements CommandExecutor {

    public CommandWrapper(String name, Command[] subcommands) {
        super(name, null, subcommands);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return this.callIntern(sender, Arrays.asList(args));
    }
    
}
