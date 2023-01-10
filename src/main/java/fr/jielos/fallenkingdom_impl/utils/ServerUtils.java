package fr.jielos.fallenkingdom_impl.utils;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;

public class ServerUtils {

    public static final SplittableRandom SPLITTABLE_RANDOM = new SplittableRandom();

    public static List<Player> getOnlinePlayers(@Nonnull Server server, @Nonnull Player player) {
        return server.getOnlinePlayers().stream().filter(onlinePlayer -> !onlinePlayer.equals(player)).collect(Collectors.toList());
    }

}
