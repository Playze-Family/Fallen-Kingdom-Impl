package fr.jielos.fallenkingdom_impl.server.ui.global;

import fr.jielos.fallenkingdom_impl.api.components.ItemBuilder;
import fr.jielos.fallenkingdom_impl.server.controllers.InterfacesController;
import fr.jielos.fallenkingdom_impl.server.controllers.VanishController;
import fr.jielos.fallenkingdom_impl.server.ui.ItemInterface;
import fr.jielos.fallenkingdom_impl.server.ui.PlayerInterface;
import fr.jielos.fallenkingdom_impl.utils.ServerUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nonnull;
import java.util.List;

public class MagicToolInterface extends PlayerInterface {

    private final ItemInterface SWITCH_SURVIVAL_MODE = new ItemInterface(48, new ItemBuilder(Material.GRASS).setName("§cSurvie").toItemStack());
    private final ItemInterface SWITCH_CREATIVE_MODE = new ItemInterface(49, new ItemBuilder(Material.BEDROCK).setName("§aCréatif").toItemStack());
    private final ItemInterface SWITCH_SPECTATOR_MODE = new ItemInterface(50, new ItemBuilder(Material.FEATHER).setName("§7Spectateur").toItemStack());

    private final ItemInterface RANDOM_TELEPORTATION = new ItemInterface(39, new ItemBuilder(Material.BARRIER).setName("§cTéléportation aléatoire").setLore("§7Permet de se téléporter", "§7aléatoirement à un joueur.").toItemStack());

    private final ItemInterface VANISH_OFF = new ItemInterface(40, new ItemBuilder(new ItemStack(Material.INK_SACK, 1, (short) 8)).setName("§fInvisibilité - §cDésactivé").setLore("§7- Clic pour §aactiver").toItemStack());
    private final ItemInterface VANISH_ON = new ItemInterface(40, new ItemBuilder(new ItemStack(Material.INK_SACK, 1, (short) 10)).setName("§fInvisibilité - §aActivé").setLore("§7- Clic pour §cdésactiver").toItemStack());

    public MagicToolInterface(@Nonnull InterfacesController interfacesController, @Nonnull Player player) {
        super(interfacesController, player, ChatColor.GRAY + "Outils magiques", 9*6, true);
    }

    @Override
    public void update() {
        inventory.clear();

        for(Player onlinePlayer : ServerUtils.getOnlinePlayers(server, player)) {
            inventory.addItem(new ItemBuilder(new ItemStack(Material.SKULL_ITEM, 1, (short) 3)).setSkullOwner(onlinePlayer.getName()).setName(onlinePlayer.getDisplayName()).setLore("§7- Clic-gauche pour ouvrir son inventaire", "§7- Clic-droit pour se téléporter").toItemStack());
        }

        addItem(RANDOM_TELEPORTATION);
        addItem(SWITCH_SURVIVAL_MODE);
        addItem(SWITCH_CREATIVE_MODE);
        addItem(SWITCH_SPECTATOR_MODE);

        addItem(instance.getVanishController().isVanish(player) ? VANISH_ON : VANISH_OFF);

        super.update();
    }

    @Override
    public void onInventoryClicked(@Nonnull ItemStack item, int slot, @Nonnull InventoryAction action, @Nonnull ClickType clickType) {
        switch (item.getType()) {
            case SKULL_ITEM: {
                final SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
                if(skullMeta == null) return;

                final Player targetPlayer = server.getPlayer(skullMeta.getOwner());
                if(targetPlayer == null) return;

                switch (clickType) {
                    case LEFT: player.openInventory(targetPlayer.getInventory()); break;
                    case RIGHT: player.teleport(targetPlayer); break;
                }

                break;
            }

            case BARRIER: {
                final List<Player> onlinePlayers = ServerUtils.getOnlinePlayers(server, player);
                final Player randomPlayer = onlinePlayers.get(ServerUtils.SPLITTABLE_RANDOM.nextInt(onlinePlayers.size()));

                player.teleport(randomPlayer);
                player.sendMessage(String.format("§7Vous avez été téléporté aléatoirement à §r%s§7.", randomPlayer.getDisplayName()));

                break;
            }

            case INK_SACK: {
                final VanishController vanishController = instance.getVanishController();
                vanishController.setVanish(player, !vanishController.isVanish(player));

                update();
            }
        }

        switch (slot) {
            case 48: player.setGameMode(GameMode.SURVIVAL); break;
            case 49: player.setGameMode(GameMode.CREATIVE); break;
            case 50: player.setGameMode(GameMode.SPECTATOR); break;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(@Nonnull PlayerJoinEvent event) {
        update();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(@Nonnull PlayerQuitEvent event) {
        server.getScheduler().runTaskLater(instance, this::update, 0);
    }

}
