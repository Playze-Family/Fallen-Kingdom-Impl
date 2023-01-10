package fr.jielos.fallenkingdom_impl.api.components;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Collection;

public class TitleAPI {

    public static void sendTitle(@Nonnull Player player, @Nonnull String title, @Nonnull String subTitle, int fadeIn, int stay, int fadeOut) {
        final PlayerConnection pConnection = ((CraftPlayer) player).getHandle().playerConnection;
        final PacketPlayOutTitle packet1 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);

        final IChatBaseComponent i1 = IChatBaseComponent.ChatSerializer.a("{\"text\": \""+subTitle+"\"}");
        final PacketPlayOutTitle packet2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, i1);

        final IChatBaseComponent i2 = IChatBaseComponent.ChatSerializer.a("{\"text\": \""+title+"\"}");
        final PacketPlayOutTitle packet3 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, i2);

        pConnection.sendPacket(packet1);
        pConnection.sendPacket(packet2);
        pConnection.sendPacket(packet3);
    }

    public static void sendTitle(@Nonnull Collection<? extends Player> players, @Nonnull String title, @Nonnull String subTitle, int fadeIn, int stay, int fadeOut) {
        players.forEach(player -> sendTitle(player, title, subTitle, fadeIn, stay, fadeOut));
    }

}
