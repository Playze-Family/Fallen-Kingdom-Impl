package fr.jielos.fallenkingdom_impl.server.ui;

import fr.jielos.fallenkingdom_impl.server.controllers.InterfacesController;
import fr.jielos.fallenkingdom_impl.server.events.ListenerAdapter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public abstract class PlayerInterface extends ListenerAdapter {

    protected final Player player;
    protected final Inventory inventory;
    protected final boolean cancelled;
    public PlayerInterface(@Nonnull InterfacesController interfacesController, @Nonnull Player player, @Nonnull String displayName, int size, boolean cancelled) {
        super(interfacesController.getEventsController());

        this.player = player;
        this.inventory = server.createInventory(player, size, displayName);
        this.cancelled = cancelled;

        registerAsListener();

        interfacesController.registerPlayerInterface(player, this);
    }

    public void open() {
        update();

        player.openInventory(inventory);
    }

    public void update() {
        player.updateInventory();
    }

    public void addItem(ItemInterface itemInterface) {
        inventory.setItem(itemInterface.getSlot(), itemInterface.getItem());
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    @EventHandler
    public void onInventoryClick(@Nonnull InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        if(this.player != player) return;

        final Inventory inventory = event.getClickedInventory();
        if(!this.inventory.equals(inventory)) return;

        event.setCancelled(cancelled);

        onInventoryClicked(event.getCurrentItem(), event.getRawSlot(), event.getAction(), event.getClick());
    }

    public abstract void onInventoryClicked(@Nonnull ItemStack item, int slot, @Nonnull InventoryAction action, @Nonnull ClickType clickType);
}
