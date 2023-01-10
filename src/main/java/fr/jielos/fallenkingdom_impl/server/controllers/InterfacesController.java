package fr.jielos.fallenkingdom_impl.server.controllers;

import fr.jielos.fallenkingdom_impl.FallenKingdomImpl;
import fr.jielos.fallenkingdom_impl.server.ui.PlayerInterface;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterfacesController extends ServerController<InterfacesController> {

    private final Map<Player, List<PlayerInterface>> interfaces = new HashMap<>();
    private final EventsController eventsController;
    public InterfacesController(@Nonnull FallenKingdomImpl instance, @Nonnull EventsController eventsController) {
        super(instance);

        this.eventsController = eventsController;
    }

    public List<PlayerInterface> getPlayerInterfaces(@Nonnull Player player) {
        return interfaces.getOrDefault(player, new ArrayList<>());
    }

    public void registerPlayerInterface(@Nonnull Player player, @Nonnull PlayerInterface playerInterface) {
        final List<PlayerInterface> playerInterfaces = getPlayerInterfaces(player);

        playerInterfaces.add(playerInterface);
        interfaces.put(player, playerInterfaces);
    }

    @SuppressWarnings("unchecked")
    public <I extends PlayerInterface> I getPlayerInterface(@Nonnull Player player, @Nonnull Class<? extends PlayerInterface> type) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final I playerInterface = (I) getPlayerInterfaces(player).stream().filter(pInterface -> pInterface.getClass() == type).findFirst().orElse(null);
        return playerInterface != null ? playerInterface : (I) type.getConstructor(InterfacesController.class, Player.class).newInstance(this, player);
    }

    public EventsController getEventsController() {
        return eventsController;
    }
}
