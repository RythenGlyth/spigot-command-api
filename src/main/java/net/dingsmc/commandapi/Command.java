package net.dingsmc.commandapi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class Command {

    public List<Command> subcommands = new ArrayList<Command>();
    public String name;
    public List<String> aliases = new ArrayList<String>();
    public String description;
    public Command parent;

    public String neededPermission;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Command addSubcommands(Command... subcommands) {
        for (Command command : subcommands) {
            command.parent = this;
            this.subcommands.add(command);
        }
        return this;
    }

    public Command addAliases(String... aliases) {
        for (String alias : aliases) {
            this.aliases.add(alias);
        }
        return this;
    }

    public final boolean callIntern(CommandSender sender, List<String> args) {
        if(args.size() > 0) {
            for(Command command : subcommands) {
                if(command.name.equalsIgnoreCase(args.get(0)) || command.aliases.contains(args.get(0))) {
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

    public final List<String> complete(CommandSender sender, List<String> args) {
        if(args.size() > 1) {
            for(Command command : subcommands) {
                if(command.name.equalsIgnoreCase(args.get(0)) || command.aliases.contains(args.get(0))) {
                    args.remove(0);
                    return command.complete(sender, args);
                }
            }
        }
        return subcommands.stream().map(command -> command.name).filter(n -> args.size() == 0 || n.startsWith(args.get(0))).collect(Collectors.toList());
    }

    public void sendHelp(CommandSender sender) {
        sender.sendMessage("§e---------------------------------");
        for(Command command : subcommands) {
            String s = "§e/" + command.getNameWithParent() + " §7- §f" + command.description;
            if(sender instanceof Player) {
                ((Player)sender).spigot().sendMessage(new ComponentBuilder(s).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("übernehmen").create())).event(new ClickEvent(Action.SUGGEST_COMMAND, "/" + command.getNameWithParent())).create());
            } else {
                sender.sendMessage(s);
            }
        }
        sender.sendMessage("§e---------------------------------");
    }

    public String getNameWithParent() {
        if(parent != null) return parent.getNameWithParent() + " " + name;
        return name;
    }

    public boolean hasPermission(CommandSender sender) {
        return neededPermission == null || neededPermission == "" || sender.hasPermission(neededPermission);
    }

    public Command setNeededPermission(String neededPermission) {
        this.neededPermission = neededPermission;
        return this;
    }
}
