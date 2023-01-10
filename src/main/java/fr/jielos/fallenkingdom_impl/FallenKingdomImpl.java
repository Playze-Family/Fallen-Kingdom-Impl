package fr.jielos.fallenkingdom_impl;

import fr.jielos.fallenkingdom_impl.server.controllers.CommandsController;
import fr.jielos.fallenkingdom_impl.server.controllers.EventsController;
import fr.jielos.fallenkingdom_impl.server.controllers.InterfacesController;
import fr.jielos.fallenkingdom_impl.server.controllers.VanishController;
import org.bukkit.plugin.java.JavaPlugin;

public final class FallenKingdomImpl extends JavaPlugin {

    private static FallenKingdomImpl instance;

    private EventsController eventsController;
    private CommandsController commandsController;
    private InterfacesController interfacesController;
    private VanishController vanishController;

    @Override
    public void onEnable() {
        instance = this;

        eventsController = new EventsController(this).load();
        commandsController = new CommandsController(this, eventsController).load();
        interfacesController = new InterfacesController(this, eventsController).load();
        vanishController = new VanishController(this, eventsController);
    }

    @Override
    public void onDisable() {
        vanishController.unload();
    }

    public static FallenKingdomImpl getInstance() {
        return instance;
    }

    public EventsController getEventsController() {
        return eventsController;
    }

    public CommandsController getCommandsController() {
        return commandsController;
    }

    public InterfacesController getInterfacesControllers() {
        return interfacesController;
    }

    public VanishController getVanishController() {
        return vanishController;
    }
}
