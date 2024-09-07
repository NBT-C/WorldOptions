package me.nbtc.worldoptions.manager;

import me.nbtc.worldoptions.Main;
import me.nbtc.worldoptions.manager.base.WorldOption;
import me.nbtc.worldoptions.smartinv.ClickableItem;
import me.nbtc.worldoptions.smartinv.SmartInventory;
import me.nbtc.worldoptions.smartinv.content.InventoryContents;
import me.nbtc.worldoptions.smartinv.content.InventoryProvider;
import me.nbtc.worldoptions.smartinv.content.Pagination;
import me.nbtc.worldoptions.smartinv.content.SlotIterator;
import me.nbtc.worldoptions.utils.ItemBuilder;
import me.nbtc.worldoptions.utils.TextUtil;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WorldOptionsMenu implements InventoryProvider {
    public final SmartInventory INVENTORY;
    private final String world;
    private String search;

    public WorldOptionsMenu(Player player, int page, String search) {
        this.world = player.getWorld().getName();
        this.search = search;
        INVENTORY = SmartInventory.builder()
                .id("WO")
                .provider(this)
                .size(6, 9)
                .title("Options: " + world + (!search.isEmpty() ? " | Searching: " + search.toLowerCase() : ""))
                .build();
        INVENTORY.open(player, page);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        List<ClickableItem> items = new ArrayList<>();
        HashMap<WorldOption, Boolean> bob = new HashMap<>();
        for (WorldOption worldOption : Main.getInstance().getService().worldOptions){
            if (!search.isEmpty()){
                if (!worldOption.name().toLowerCase().contains(search.toLowerCase())) continue;
            }
            boolean isEnabled = Main.getInstance().getService().worldOptionValues.get(world).get(worldOption);
            String c = isEnabled ? "&a" : "&c";
            String oppositeC = isEnabled ? "&c" : "&a";
            String status = isEnabled ? "Enabled" : "Disabled";
            String oppositeStatus = isEnabled ? "disable" : "enable";

            ClickableItem clickableItem = ClickableItem.of(
                    new ItemBuilder(worldOption.getMaterial(), 1, worldOption.getAByte())
                            .setDisplayname(worldOption.getDisplayName())
                            .addHiddenEnchantment(isEnabled)
                            .setLore("", c+"● " + status, "", "&7Interact to " +oppositeC+ oppositeStatus)
                            .build(),
                    e ->{
                        Player clicker = (Player) e.getWhoClicked();
                        clicker.playSound(clicker.getLocation(), Sound.CLICK, 1L, 3L);
                        Main.getInstance().getService().worldOptionValues.get(world).put(worldOption, !isEnabled);

                        new WorldOptionsMenu(clicker, pagination.getPage(), search);
                    });
            items.add(clickableItem);
            bob.put(worldOption, isEnabled);
        }

        if (items.isEmpty()){
            new ErrorMenu(player, "&cWorld Options you've searched not found");
            return;
        }

        List<String> lore = new ArrayList<>();
        for (WorldOption option : bob.keySet()){
            if (bob.get(option)) {
                lore.add(TextUtil.format("&a◆ " + ChatColor.stripColor(option.name().replace("_", " "))));
            }
        }
        for (WorldOption option : bob.keySet()){
            if (!bob.get(option)) {
                lore.add(TextUtil.format("&c◆ " + ChatColor.stripColor(option.name().replace("_", " "))));
            }
        }
        contents.set(0, 1, ClickableItem.empty(new ItemBuilder(Material.PAINTING, 1, 0)
                        .setLore(lore.toArray(new String[0]))
                        .setDisplayname("&eOption List")
                        .build()));

        List<String> enableLore = new ArrayList<>();
        for (WorldOption option : bob.keySet()){
            if (!bob.get(option)) {
                enableLore.add(TextUtil.format("&c◆ " + ChatColor.stripColor(option.name().replace("_", " ")) + "&8 -> &aEnable"));
            }
        }
        List<String> disableLore = new ArrayList<>();
        for (WorldOption option : bob.keySet()){
            if (bob.get(option)) {
                disableLore.add(TextUtil.format("&a◆ " + ChatColor.stripColor(option.name().replace("_", " ")) + "&8 -> &cDisable"));
            }
        }

        if (enableLore.isEmpty())
            enableLore.add(TextUtil.format("&cNon to enable :)"));
        if (disableLore.isEmpty())
            disableLore.add(TextUtil.format("&cNon to disable :)"));

        contents.set(0, 7, ClickableItem.of(new ItemBuilder(Material.EMERALD_BLOCK, 1, 0)
                        .setDisplayname("&aEnable All")
                        .setLore(enableLore)
                        .build(),
                e -> {
                    for (Map.Entry<WorldOption, Boolean> option : bob.entrySet()){
                        if (!option.getValue()){
                            Main.getInstance().getService().worldOptionValues.get(world).put(option.getKey(), true);
                        }
                    }
                    player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
                    new WorldOptionsMenu(player, pagination.getPage(), search);
                }));
        contents.set(0, 8, ClickableItem.of(new ItemBuilder(Material.REDSTONE_BLOCK, 1, 0)
                        .setDisplayname("&cDisable All")
                        .setLore(disableLore)
                        .build(),
                e -> {
                    for (Map.Entry<WorldOption, Boolean> option : bob.entrySet()){
                        if (option.getValue()){
                            Main.getInstance().getService().worldOptionValues.get(world).put(option.getKey(), false);
                        }
                    }
                    player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
                    new WorldOptionsMenu(player, pagination.getPage(), search);
                }));

        if (search.isEmpty()) {
            contents.set(0, 0, ClickableItem.of(new ItemBuilder(Material.SIGN, 1, 0)
                            .setDisplayname("&cSearch for options")
                            .build(), e -> {
                player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
                new AnvilGUI.Builder()
                        .onClick((slot, stateSnapshot) -> {
                            if (slot != AnvilGUI.Slot.OUTPUT) {
                                return Collections.emptyList();
                            }
                            return Arrays.asList(
                                    AnvilGUI.ResponseAction.close(),
                                    AnvilGUI.ResponseAction.run(() -> {
                                        player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
                                        new WorldOptionsMenu(player, pagination.getPage(), stateSnapshot.getText());
                                    })
                            );
                        })
                        .text("Search string")
                        .itemOutput(new ItemStack(Material.EMERALD_BLOCK))
                        .plugin(Main.getInstance())
                        .open(player);
                    }));
        } else {
            contents.set(0, 0, ClickableItem.of(new ItemBuilder(Material.BARRIER, 1, 0)
                            .setDisplayname("&cCancel search")
                            .build(),
                    e -> {
                        player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
                        new WorldOptionsMenu(player, pagination.getPage(), "");
                    }));
        }



        ItemStack itemStack = (Main.getInstance().createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzdhZWU5YTc1YmYwZGY3ODk3MTgzMDE1Y2NhMGIyYTdkNzU1YzYzMzg4ZmYwMTc1MmQ1ZjQ0MTlmYzY0NSJ9fX0=", ""));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(TextUtil.format("&e&lPrevious page"));
        itemStack.setItemMeta(itemMeta);

        contents.set(5, 0, ClickableItem.of(itemStack,
                e -> {
                    if (!pagination.isFirst()) {
                        INVENTORY.open(player, pagination.previous().getPage());
                        player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
                    }
                }));

        ItemStack itemStack2 = (Main.getInstance().createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyYWQxYjljYjRkZDIxMjU5YzBkNzVhYTMxNWZmMzg5YzNjZWY3NTJiZTM5NDkzMzgxNjRiYWM4NGE5NmUifX19", ""));
        ItemMeta itemMeta2 = itemStack2.getItemMeta();
        itemMeta2.setDisplayName(TextUtil.format("&e&lNext page"));
        itemStack2.setItemMeta(itemMeta2);

        contents.set(5, 8, ClickableItem.of(itemStack2,
                e -> {
                    if (!pagination.isLast()) {
                        INVENTORY.open(player, pagination.next().getPage());
                        player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
                    }
                }));

        pagination.setItems(items.toArray(new ClickableItem[0]));
        pagination.setItemsPerPage(14);
        contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1);

        int[][] layout = {
                {2, 1}, {2, 2}, {2, 3}, {2, 4}, {2, 5}, {2, 6}, {2, 7},
                {3, 1}, {3, 2}, {3, 3}, {3, 4}, {3, 5}, {3, 6}, {3, 7},
        };

        int itemIndex = 0;
        for (int[] slot : layout) {
            if (itemIndex >= pagination.getPageItems().length) break;
            contents.set(slot[0], slot[1], pagination.getPageItems()[itemIndex]);
            itemIndex++;
        }
    }

}
