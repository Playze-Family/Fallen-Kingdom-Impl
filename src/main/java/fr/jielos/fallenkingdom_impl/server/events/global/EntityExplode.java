package fr.jielos.fallenkingdom_impl.server.events.global;

import fr.jielos.fallenkingdom_impl.server.controllers.EventsController;
import fr.jielos.fallenkingdom_impl.server.events.ListenerAdapter;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

import javax.annotation.Nonnull;

public class EntityExplode extends ListenerAdapter {

    public EntityExplode(@Nonnull EventsController eventsController) {
        super(eventsController);

        registerAsListener();
    }

    @EventHandler
    public void onEntityExplode(@Nonnull EntityExplodeEvent event) {
        event.blockList().removeIf(block -> block.getType() == Material.CHEST);
    }
}
