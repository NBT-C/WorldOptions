package me.nbtc.worldoptions.listeners;

import me.nbtc.worldoptions.Main;
import me.nbtc.worldoptions.manager.base.WorldOption;
import org.bukkit.Material;
import org.bukkit.block.Hopper;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.util.Vector;

public class InteractListener implements Listener {
    @EventHandler
    public void interact(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getItem() != null){
            if (event.getItem().getType() == Material.ENDER_PEARL){
                boolean enderpearl = Main.getInstance().getService().checkOption(player.getWorld(), WorldOption.Ender_Pearl);
                if (!enderpearl) {
                    event.setCancelled(true);
                    player.updateInventory();
                }
            }
            if (event.getItem().getType() == Material.ITEM_FRAME){
                boolean frame = Main.getInstance().getService().checkOption(player.getWorld(), WorldOption.Item_Frame);
                if (!frame)
                    event.setCancelled(true);
            }
            if (event.getItem().getType() == Material.LAVA_BUCKET){
                boolean lava = Main.getInstance().getService().checkOption(player.getWorld(), WorldOption.Lava);
                if (!lava)
                    event.setCancelled(true);
            }
            if (event.getItem().getType() == Material.BOAT){
                boolean boat = Main.getInstance().getService().checkOption(player.getWorld(), WorldOption.Boats);
                if (!boat)
                    event.setCancelled(true);
            }
            if (event.getItem().getType().name().contains("MINECART")){
                boolean trains = Main.getInstance().getService().checkOption(player.getWorld(), WorldOption.Trains);
                if (!trains)
                    event.setCancelled(true);
            }
            if (event.getItem().getType() == Material.FLINT_AND_STEEL || event.getItem().getType() == Material.FIREBALL){
                boolean fire = Main.getInstance().getService().checkOption(player.getWorld(), WorldOption.Spawn_Fire);
                if (!fire)
                    event.setCancelled(true);
            }
            if (event.getItem().getType() == Material.BOOK_AND_QUILL || event.getItem().getType() == Material.WRITTEN_BOOK){
                boolean books = Main.getInstance().getService().checkOption(player.getWorld(), WorldOption.Books);
                if (!books) {
                    event.setCancelled(true);
                    player.closeInventory();
                }
            }
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() == Material.NOTE_BLOCK) {
            boolean noteBlock = Main.getInstance().getService().checkOption(player, WorldOption.Note_Block);
            if (!noteBlock)
                event.setCancelled(true);
        }
        if (event.getClickedBlock().getType() == Material.HOPPER) {
            boolean hopper = Main.getInstance().getService().checkOption(player, WorldOption.Hopper);
            if (!hopper)
                event.setCancelled(true);
        }
        if (event.getClickedBlock().getType() == Material.BED_BLOCK) {
            boolean bed = Main.getInstance().getService().checkOption(player, WorldOption.Bed);
            if (!bed)
                event.setCancelled(true);
        }
        if (event.getClickedBlock().getType() == Material.BEACON) {
            boolean beacon = Main.getInstance().getService().checkOption(player, WorldOption.Beacon);
            if (!beacon)
                event.setCancelled(true);
        }
        if (event.getClickedBlock().getType() == Material.ENDER_CHEST) {
            boolean ec = Main.getInstance().getService().checkOption(player, WorldOption.Ender_Chest);
            if (!ec)
                event.setCancelled(true);
        }
        if (event.getClickedBlock().getType() == Material.CHEST) {
            boolean chest = Main.getInstance().getService().checkOption(player, WorldOption.Chest);
            if (!chest)
                event.setCancelled(true);
        }
        if (event.getClickedBlock().getType() == Material.FURNACE) {
            boolean furnace = Main.getInstance().getService().checkOption(player, WorldOption.Furnace);
            if (!furnace)
                event.setCancelled(true);
        }
        if (event.getClickedBlock().getType() == Material.ANVIL) {
            boolean anvil = Main.getInstance().getService().checkOption(player, WorldOption.Anvil);
            if (!anvil)
                event.setCancelled(true);
        }
        if (event.getClickedBlock().getType() == Material.WORKBENCH) {
            boolean bench = Main.getInstance().getService().checkOption(player, WorldOption.Crafting_Table);
            if (!bench)
                event.setCancelled(true);
        }
        if (event.getClickedBlock().getType() == Material.ITEM_FRAME) {
            boolean frame = Main.getInstance().getService().checkOption(player, WorldOption.Item_Frame);
            if (!frame)
                event.setCancelled(true);
        }
        if (event.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
            boolean enchant = Main.getInstance().getService().checkOption(player, WorldOption.Enchantment_Table);
            if (!enchant)
                event.setCancelled(true);
        }
        if (event.getClickedBlock().getType().name().contains("BUTTON")) {
            boolean button = Main.getInstance().getService().checkOption(player, WorldOption.Buttons);
            if (!button)
                event.setCancelled(true);
        }
    }


    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof EnderPearl) {
            boolean enderpearl = Main.getInstance().getService().checkOption(event.getEntity().getWorld(), WorldOption.Ender_Pearl);
            if (!enderpearl)
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryPickupItem(InventoryPickupItemEvent event) {
        if (event.getInventory().getType() == InventoryType.HOPPER) {
            boolean hopper = Main.getInstance().getService().checkOption(event.getItem().getWorld(), WorldOption.Hopper);
            if (!hopper)
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent event) {
        if (event.getVehicle() instanceof Boat && event.getEntered() instanceof Player) {
            Player player = (Player) event.getEntered();
            boolean boat = Main.getInstance().getService().checkOption(player, WorldOption.Boats);
            if (!boat)
                event.setCancelled(true);
        }
        if (event.getVehicle() instanceof Minecart && event.getEntered() instanceof Player) {
            Player player = (Player) event.getEntered();
            boolean trains = Main.getInstance().getService().checkOption(player, WorldOption.Trains);
            if (!trains)
                event.setCancelled(true);
        }
    }
}
