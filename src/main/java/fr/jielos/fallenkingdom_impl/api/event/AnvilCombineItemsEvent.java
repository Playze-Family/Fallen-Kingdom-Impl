package fr.jielos.fallenkingdom_impl.api.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class AnvilCombineItemsEvent extends InventoryClickEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private boolean isCancelled;

    private final ItemStack firstItem;
    private final ItemStack secondItem;
    private final ItemStack resultItem;
    public AnvilCombineItemsEvent(@Nonnull InventoryClickEvent event, @Nonnull ItemStack firstItem, @Nonnull ItemStack secondItem, @Nonnull ItemStack resultItem) {
        super(event.getView(), event.getSlotType(), event.getRawSlot(), event.getClick(), event.getAction());

        this.firstItem = firstItem;
        this.secondItem = secondItem;
        this.resultItem = resultItem;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    public ItemStack getFirstItem() {
        return firstItem;
    }

    public ItemStack getSecondItem() {
        return secondItem;
    }

    public ItemStack getResultItem() {
        return resultItem;
    }
}
