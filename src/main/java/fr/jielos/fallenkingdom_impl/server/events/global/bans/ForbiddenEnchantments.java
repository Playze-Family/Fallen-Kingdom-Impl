package fr.jielos.fallenkingdom_impl.server.events.global.bans;

import com.google.common.collect.ImmutableMap;
import fr.jielos.fallenkingdom_impl.api.event.AnvilCombineItemsEvent;
import fr.jielos.fallenkingdom_impl.server.controllers.EventsController;
import fr.jielos.fallenkingdom_impl.server.events.ListenerAdapter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ForbiddenEnchantments extends ListenerAdapter {

    public static final Map<Enchantment, Integer> FORBIDDEN_ENCHANTMENTS = ImmutableMap.of(
            Enchantment.FIRE_ASPECT, 0,
            Enchantment.ARROW_FIRE, 0,
            Enchantment.DAMAGE_ALL, 4,
            Enchantment.ARROW_DAMAGE, 3
    );

    public static final Map<Map<Enchantment, Integer>, List<Material>> FORBIDDEN_SPECIALS_ENCHANTMENTS = ImmutableMap.of(
            ImmutableMap.of(Enchantment.PROTECTION_ENVIRONMENTAL, 4), Arrays.asList(Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS),
            ImmutableMap.of(Enchantment.PROTECTION_ENVIRONMENTAL, 3), Arrays.asList(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS)
    );

    private static final String FORBIDDEN_ENCHANTMENTS_ERROR = "Les enchantements que vous essayez d'appliquer sur votre objet sont trop haut/interdits.";

    public ForbiddenEnchantments(@Nonnull EventsController eventsController) {
        super(eventsController);

        registerAsListener();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEnchantItem(@Nonnull EnchantItemEvent event) {
        final Player player = event.getEnchanter();
        final ItemStack itemStack = event.getItem();
        if(itemStack == null) return;

        try {
            final Map<Enchantment, Integer> enchantments = event.getEnchantsToAdd();

            if(hasForbiddenEnchantments(itemStack, enchantments)) throw new IllegalStateException(FORBIDDEN_ENCHANTMENTS_ERROR);
        } catch (IllegalStateException exception) {
            event.setCancelled(true);

            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
            player.sendMessage(ChatColor.DARK_RED + exception.getMessage());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(@Nonnull AnvilCombineItemsEvent event) {
        final Player player = (Player) event.getWhoClicked();

        try {
            final ItemStack resultItem = event.getResultItem();
            if(hasForbiddenEnchantments(resultItem)) throw new IllegalStateException(FORBIDDEN_ENCHANTMENTS_ERROR);
        } catch (IllegalStateException exception) {
            event.setCancelled(true);

            player.closeInventory();
            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
            player.sendMessage(ChatColor.DARK_RED + exception.getMessage());
        }
    }

    public boolean hasForbiddenEnchantments(@Nonnull ItemStack itemStack, @Nonnull Map<Enchantment, Integer> enchantments) {
        return (
                FORBIDDEN_ENCHANTMENTS.entrySet().stream().anyMatch(fEnchantments -> enchantments.getOrDefault(fEnchantments.getKey(), 0) > fEnchantments.getValue()) ||
                FORBIDDEN_SPECIALS_ENCHANTMENTS.entrySet().stream().anyMatch(fsEnchantments -> fsEnchantments.getValue().contains(itemStack.getType()) && fsEnchantments.getKey().entrySet().stream().anyMatch(fEnchantments -> enchantments.getOrDefault(fEnchantments.getKey(), 0) > fEnchantments.getValue()))
        );
    }

    public boolean hasForbiddenEnchantments(@Nonnull ItemStack itemStack) {
        return hasForbiddenEnchantments(itemStack, itemStack.getEnchantments());
    }

}
