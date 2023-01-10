package fr.jielos.fallenkingdom_impl;

import org.bukkit.Server;

import javax.annotation.Nonnull;

public abstract class IComponent {

    protected final FallenKingdomImpl instance;
    protected final Server server;
    public IComponent(@Nonnull FallenKingdomImpl instance) {
        this.instance = instance;
        this.server = instance.getServer();
    }

    public FallenKingdomImpl getInstance() {
        return instance;
    }

    public Server getServer() {
        return server;
    }

}
