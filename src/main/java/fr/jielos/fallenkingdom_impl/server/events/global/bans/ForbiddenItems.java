package fr.jielos.fallenkingdom_impl.server.events.global.bans;

import fr.jielos.fallenkingdom_impl.server.controllers.EventsController;
import fr.jielos.fallenkingdom_impl.server.events.ListenerAdapter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class ForbiddenItems extends ListenerAdapter {

    public static final List<ItemStack> FORBIDDEN_ITEMS = Arrays.asList(
            new ItemStack(Material.TRAPPED_CHEST),
            new ItemStack(Material.FISHING_ROD),
            new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1)
    );

    private static final String FORBIDDEN_ITEM_ERROR = "Cet objet est interdit dans les règles du jeu.";

    public ForbiddenItems(@Nonnull EventsController eventsController) {
        super(eventsController);

        registerAsListener();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCraftItem(@Nonnull CraftItemEvent event) {
        final Player player = (Player) event.getWhoClicked();

        try {
            final ItemStack itemStack = event.getRecipe().getResult();
            if(isForbiddenItem(itemStack)) throw new IllegalStateException(FORBIDDEN_ITEM_ERROR);
        } catch (IllegalStateException exception) {
            event.setCancelled(true);

            player.closeInventory();
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
            player.sendMessage(ChatColor.DARK_RED + exception.getMessage());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(@Nonnull PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemStack = event.getItem();
        if(itemStack == null) return;

        try {
            if(isForbiddenItem(itemStack)) throw new IllegalStateException(FORBIDDEN_ITEM_ERROR);
        } catch (IllegalStateException exception) {
            event.setCancelled(true);

            player.getInventory().remove(itemStack);
            player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
            player.sendMessage(ChatColor.DARK_RED + exception.getMessage());
        }
    }

    public boolean isForbiddenItem(@Nonnull ItemStack itemStack) {
        return FORBIDDEN_ITEMS.stream().anyMatch(itemStack::isSimilar);
    }

}
