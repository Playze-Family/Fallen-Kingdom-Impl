package fr.jielos.fallenkingdom_impl.server.controllers;

import fr.jielos.fallenkingdom_impl.FallenKingdomImpl;
import fr.jielos.fallenkingdom_impl.utils.ServerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class VanishController extends ServerController<VanishController> implements Listener {

    private final List<UUID> vanishedUUIDs = new ArrayList<>();
    public VanishController(@Nonnull FallenKingdomImpl instance, @Nonnull EventsController eventsController) {
        super(instance);

        eventsController.registerEvent(this);
    }

    @Override
    public VanishController unload() {
        for(Player vanishedPlayer : getVanishedPlayers()) {
            setVanish(vanishedPlayer, false);
        }

        return super.unload();
    }

    public void setVanish(@Nonnull Player player, boolean status) {
        if(status) {
            hidePlayer(player, ServerUtils.getOnlinePlayers(server, player));

            vanishedUUIDs.add(player.getUniqueId());
            player.sendMessage("§7Vous êtes désormais §ainvisible par tous les joueurs§7.");
        } else {
            showPlayer(player, ServerUtils.getOnlinePlayers(server, player));

            vanishedUUIDs.remove(player.getUniqueId());
            player.sendMessage("§7Vous êtes désormais §fvisible par tous les joueurs§7.");
        }
    }

    public boolean isVanish(@Nonnull Player player) {
        return vanishedUUIDs.contains(player.getUniqueId());
    }

    public void showPlayer(@Nonnull Player player, @Nonnull Collection<? extends Player> players) {
        for(Player aPlayer : players) {
            aPlayer.showPlayer(player);
        }
    }

    public void hidePlayer(@Nonnull Player player, @Nonnull Collection<? extends Player> players) {
        for(Player aPlayer : players) {
            aPlayer.hidePlayer(player);
        }
    }

    public final List<Player> getVanishedPlayers() {
        return vanishedUUIDs.stream().map(server::getPlayer).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @EventHandler
    public void onPlayerJoin(@Nonnull PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        for(Player vanishedPlayer : getVanishedPlayers()) {
            player.hidePlayer(vanishedPlayer);
        }
    }

    @EventHandler
    public void onPlayerQuit(@Nonnull PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        for(Player vanishedPlayer : getVanishedPlayers()) {
            player.showPlayer(vanishedPlayer);
        }
    }

}
