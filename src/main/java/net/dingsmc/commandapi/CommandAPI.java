package net.dingsmc.commandapi;

import org.bukkit.plugin.java.JavaPlugin;

public class CommandAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("CommandAPI has been enabled!");
        new CommandWrapper("test", "This is a test command").setToPluginCommand(this.getCommand("test"))
            .addSubcommands(
                new Command("sas", "This is a sas command")
                    .addSubcommands(new Command("jol", "This is a jol command"))
                    .addSubcommands(new Command("jol", "This is a jol command"))
            )
            .addSubcommands(new Command("eses", "This is a eses command"))
            .addSubcommands(new Command("sos", "This is a sos command"))
            .addSubcommands(new Command("lal", "This is a lal command"));
    }
    
}