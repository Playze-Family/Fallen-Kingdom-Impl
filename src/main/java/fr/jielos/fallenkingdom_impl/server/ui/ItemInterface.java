package fr.jielos.fallenkingdom_impl.server.ui;

import org.bukkit.inventory.ItemStack;

public class ItemInterface {

    private final int slot;
    private final ItemStack item;

    public ItemInterface(int slot, ItemStack item) {
        this.slot = slot;
        this.item = item;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItem() {
        return item;
    }
}
