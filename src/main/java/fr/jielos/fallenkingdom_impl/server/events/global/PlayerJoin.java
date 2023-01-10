package fr.jielos.fallenkingdom_impl.server.events.global;

import fr.jielos.fallenkingdom_impl.api.components.TabAPI;
import fr.jielos.fallenkingdom_impl.api.components.TitleAPI;
import fr.jielos.fallenkingdom_impl.server.controllers.EventsController;
import fr.jielos.fallenkingdom_impl.server.events.ListenerAdapter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.annotation.Nonnull;

public class PlayerJoin extends ListenerAdapter {

    public PlayerJoin(@Nonnull EventsController eventsController) {
        super(eventsController);

        registerAsListener();
    }

    @EventHandler
    public void onPlayerJoin(@Nonnull PlayerJoinEvent event) throws Exception {
        final Player player = event.getPlayer();

        TabAPI.setTabHeaderFooter(player, "§f§lFallen Kingdom\n§7Family Edition 2\n", "\n§efk.playze.me\n§ediscord.playze.me");
        TitleAPI.sendTitle(player, "§f§lFallen Kingdom", "§7Family Edition 2", 20, 4*20, 20);
    }
}
