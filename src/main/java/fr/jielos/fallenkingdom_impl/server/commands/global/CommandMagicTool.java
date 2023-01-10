package fr.jielos.fallenkingdom_impl.server.commands.global;

import fr.jielos.fallenkingdom_impl.api.components.ItemBuilder;
import fr.jielos.fallenkingdom_impl.server.commands.CommandAdapter;
import fr.jielos.fallenkingdom_impl.server.controllers.CommandsController;
import fr.jielos.fallenkingdom_impl.server.controllers.InterfacesController;
import fr.jielos.fallenkingdom_impl.server.ui.global.MagicToolInterface;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;

public class CommandMagicTool extends CommandAdapter {

    private final ItemStack MAGIC_TOOL = new ItemBuilder(Material.FEATHER).setName("§6L'outil magique").toItemStack();

    public CommandMagicTool(@Nonnull CommandsController commandsController, @Nonnull String commandName) {
        super(commandsController, commandName);

        registerAsCommand();
        registerAsListener();
    }

    @Override
    public boolean onCommand(@Nonnull Player player, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        player.getInventory().setItem(0, MAGIC_TOOL);
        player.getInventory().setHeldItemSlot(0);
        player.sendMessage("§7§oVous avez reçu l'outil magique dans votre inventaire.");

        return super.onCommand(player, command, label, args);
    }

    @EventHandler
    public void onInventoryClick(@Nonnull PlayerInteractEvent event) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        final Player player = event.getPlayer();
        final ItemStack item = event.getItem();
        if(item == null || item.getType() == Material.AIR) return;

        if(item.equals(MAGIC_TOOL)) {
            event.setCancelled(true);

            final InterfacesController interfacesController = instance.getInterfacesControllers();
            final MagicToolInterface magicToolInterface = interfacesController.getPlayerInterface(player, MagicToolInterface.class);

            magicToolInterface.open();
        }
    }

}
