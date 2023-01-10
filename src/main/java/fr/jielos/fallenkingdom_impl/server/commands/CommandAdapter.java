package fr.jielos.fallenkingdom_impl.server.commands;

import fr.jielos.fallenkingdom_impl.server.controllers.CommandsController;
import fr.jielos.fallenkingdom_impl.server.events.ListenerAdapter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public abstract class CommandAdapter extends ListenerAdapter implements CommandExecutor, TabCompleter {

    protected final CommandsController commandsController;
    protected final String commandName;
    public CommandAdapter(@Nonnull CommandsController commandsController, String commandName) {
        super(commandsController.getEventsController());

        this.commandsController = commandsController;
        this.commandName = commandName;
    }

    public void registerAsCommand() {
        commandsController.registerCommand(commandName, this);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if(sender instanceof Player) {
            return onCommand((Player) sender, command, label, args);
        }

        return false;
    }

    public boolean onCommand(@Nonnull Player player, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
