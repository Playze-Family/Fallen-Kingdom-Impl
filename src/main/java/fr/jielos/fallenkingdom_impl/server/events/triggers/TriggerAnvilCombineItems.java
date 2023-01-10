package fr.jielos.fallenkingdom_impl.server.events.triggers;

import fr.jielos.fallenkingdom_impl.api.event.AnvilCombineItemsEvent;
import fr.jielos.fallenkingdom_impl.server.controllers.EventsController;
import fr.jielos.fallenkingdom_impl.server.events.ListenerAdapter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class TriggerAnvilCombineItems extends ListenerAdapter {

    public TriggerAnvilCombineItems(@Nonnull EventsController eventsController) {
        super(eventsController);

        registerAsListener();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(@Nonnull InventoryClickEvent event) {
        final Inventory inventory = event.getInventory();

        if(!(inventory instanceof AnvilInventory)) return;
        if(event.getRawSlot() != 2) return;

        final ItemStack firstItem = inventory.getItem(0);
        final ItemStack secondItem = inventory.getItem(1);
        final ItemStack resultItem = inventory.getItem(2);
        if(firstItem == null || secondItem == null || resultItem == null) return;

        final AnvilCombineItemsEvent newEvent = new AnvilCombineItemsEvent(event, firstItem, secondItem, resultItem);

        eventsController.callEvent(newEvent);
        if(newEvent.isCancelled()) event.setCancelled(true);
    }
}
