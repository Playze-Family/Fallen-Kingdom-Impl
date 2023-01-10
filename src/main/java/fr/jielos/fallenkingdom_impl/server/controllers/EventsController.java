package fr.jielos.fallenkingdom_impl.server.controllers;

import fr.jielos.fallenkingdom_impl.FallenKingdomImpl;
import fr.jielos.fallenkingdom_impl.server.events.global.PlayerJoin;
import fr.jielos.fallenkingdom_impl.server.events.global.bans.ForbiddenEnchantments;
import fr.jielos.fallenkingdom_impl.server.events.global.bans.ForbiddenItems;
import fr.jielos.fallenkingdom_impl.server.events.triggers.TriggerAnvilCombineItems;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import javax.annotation.Nonnull;

public class EventsController extends ServerController<EventsController> {

    public EventsController(@Nonnull FallenKingdomImpl instance) {
        super(instance);
    }

    @Override
    public EventsController load() {
        loadTriggersEvents();
        loadGlobalEvents();

        return super.load();
    }

    private void loadTriggersEvents() {
        new TriggerAnvilCombineItems(this);
    }

    private void loadGlobalEvents() {
        new PlayerJoin(this);

        new ForbiddenItems(this);
        new ForbiddenEnchantments(this);
    }

    public void registerEvent(@Nonnull Listener listener) {
        pluginManager.registerEvents(listener, instance);
    }

    public void callEvent(@Nonnull Event event) {
        pluginManager.callEvent(event);
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }
}
