package me.nbtc.worldoptions.manager;

import me.nbtc.worldoptions.smartinv.ClickableItem;
import me.nbtc.worldoptions.smartinv.SmartInventory;
import me.nbtc.worldoptions.smartinv.content.InventoryContents;
import me.nbtc.worldoptions.smartinv.content.InventoryProvider;
import me.nbtc.worldoptions.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ErrorMenu implements InventoryProvider {
    private final String error;
    public final SmartInventory INVENTORY;
    public ErrorMenu(Player player, String error){
        player.playSound(player.getLocation(), Sound.VILLAGER_DEATH, 1L, 2L);
        this.error = error;
        INVENTORY = SmartInventory.builder()
                .id("WOW")
                .provider(this)
                .size(3, 9)
                .title("Error")
                .build();
        INVENTORY.open(player);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(1, 4, ClickableItem.empty(new ItemBuilder(Material.BARRIER, 1,0).setDisplayname("&c&lERROR").setLore(error).build()));
        contents.set(2, 0, ClickableItem.of(new ItemBuilder(Material.ARROW, 1, 0).setDisplayname("&eGo back").build(), e -> {
            player.playSound(player.getLocation(), Sound.CLICK, 1L,1L);
            new WorldOptionsMenu(player, 0, "");
        }));
    }
}
