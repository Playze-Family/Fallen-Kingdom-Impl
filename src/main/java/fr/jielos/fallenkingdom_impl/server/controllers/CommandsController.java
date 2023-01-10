package fr.jielos.fallenkingdom_impl.server.controllers;

import fr.jielos.fallenkingdom_impl.FallenKingdomImpl;
import fr.jielos.fallenkingdom_impl.server.commands.CommandAdapter;
import fr.jielos.fallenkingdom_impl.server.commands.global.CommandMagicTool;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

import javax.annotation.Nonnull;

public class CommandsController extends ServerController<CommandsController> {

    private final EventsController eventsController;
    public CommandsController(@Nonnull FallenKingdomImpl instance, @Nonnull EventsController eventsController) {
        super(instance);

        this.eventsController = eventsController;
    }

    @Override
    public CommandsController load() {
        loadGlobalCommands();

        return super.load();
    }

    private void loadGlobalCommands() {
        new CommandMagicTool(this, "magictool");
    }

    public void registerCommand(@Nonnull String commandName, @Nonnull CommandAdapter adapter) {
        setExecutor(commandName, adapter);
        setTabCompleter(commandName, adapter);
    }

    public void setExecutor(@Nonnull String commandName, @Nonnull CommandExecutor executor) {
        instance.getCommand(commandName).setExecutor(executor);
    }

    public void setTabCompleter(@Nonnull String commandName, @Nonnull TabCompleter executor) {
        instance.getCommand(commandName).setTabCompleter(executor);
    }

    public EventsController getEventsController() {
        return eventsController;
    }
}
