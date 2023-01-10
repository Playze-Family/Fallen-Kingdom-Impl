package fr.jielos.fallenkingdom_impl.api.components;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Collection;

public class ActionBarAPI {

    public static void sendActionBar(@Nonnull Player player, @Nonnull String message) {
        final PlayerConnection pConnection = ((CraftPlayer) player).getHandle().playerConnection;
        final IChatBaseComponent i1 = IChatBaseComponent.ChatSerializer.a("{\"text\": \""+message+"\"}");
        final PacketPlayOutChat packet1 = new PacketPlayOutChat(i1, (byte) 2);
        pConnection.sendPacket(packet1);
    }

    public static void sendActionBar(@Nonnull Collection<? extends Player> players, @Nonnull String message) {
        players.forEach(player -> sendActionBar(player, message));
    }

}
