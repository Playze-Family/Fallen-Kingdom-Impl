package fr.jielos.fallenkingdom_impl.api.components;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public class TabAPI {

    public static void setTabHeaderFooter(@Nonnull Player player, @Nonnull String header, @Nonnull String footer) throws Exception {
        final PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

        final PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

        final IChatBaseComponent i1 = IChatBaseComponent.ChatSerializer.a("{\"text\": \""+header+"\"}");
        final IChatBaseComponent i2 = IChatBaseComponent.ChatSerializer.a("{\"text\": \""+footer+"\"}");

        final Field field1 = packet.getClass().getDeclaredField("a");
        final Field field2 = packet.getClass().getDeclaredField("b");

        field1.setAccessible(true);
        field1.set(packet, i1);
        field1.setAccessible(!field1.isAccessible());

        field2.setAccessible(true);
        field2.set(packet, i2);
        field2.setAccessible(!field2.isAccessible());

        playerConnection.sendPacket(packet);
    }

}
