package fr.jielos.fallenkingdom_impl.server.events;

import fr.jielos.fallenkingdom_impl.IComponent;
import fr.jielos.fallenkingdom_impl.server.controllers.EventsController;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;

public abstract class ListenerAdapter extends IComponent implements Listener {

    protected EventsController eventsController;
    public ListenerAdapter(@Nonnull EventsController eventsController) {
        super(eventsController.getInstance());

        this.eventsController = eventsController;
    }

    public void registerAsListener() {
        eventsController.registerEvent(this);
    }

    public EventsController getEventsController() {
        return eventsController;
    }
}
