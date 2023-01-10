package fr.jielos.fallenkingdom_impl.server.controllers;

import fr.jielos.fallenkingdom_impl.FallenKingdomImpl;
import fr.jielos.fallenkingdom_impl.IComponent;
import org.bukkit.plugin.PluginManager;

import javax.annotation.Nonnull;

public abstract class ServerController<T extends ServerController<T>> extends IComponent {

    protected final PluginManager pluginManager;
    public ServerController(@Nonnull FallenKingdomImpl instance) {
        super(instance);

        this.pluginManager = server.getPluginManager();
    }

    @SuppressWarnings("unchecked")
    public T load() {
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T unload() {
        return (T) this;
    }

}
