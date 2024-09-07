package me.nbtc.worldoptions.listeners;

import me.nbtc.worldoptions.Main;
import me.nbtc.worldoptions.manager.base.WorldOption;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockListener implements Listener {
    @EventHandler
    public void place(BlockPlaceEvent event){
        Player player = event.getPlayer();
        if (isRedstoneRelated(event.getBlockPlaced().getType())){
            boolean redstone = Main.getInstance().getService().checkOption(player, WorldOption.Redstone);
            if (!redstone)
                event.setCancelled(true);
        }
        if (event.getBlockPlaced().getType() == Material.ITEM_FRAME){
            boolean frame = Main.getInstance().getService().checkOption(player, WorldOption.Item_Frame);
            if (!frame)
                event.setCancelled(true);
        }
        if (event.getBlockPlaced().getType().name().contains("LAVA")){
            boolean lava = Main.getInstance().getService().checkOption(player, WorldOption.Lava);
            if (!lava)
                event.setCancelled(true);
        }
        if (event.getBlockPlaced().getType() == Material.SIGN_POST){
            boolean sign = Main.getInstance().getService().checkOption(player, WorldOption.Signs);
            if (!sign){
                event.setCancelled(true);
                player.closeInventory();
            }
        }
    }
    @EventHandler
    public void onBlockRedstone(BlockRedstoneEvent event) {
        if (event.getBlock().getType().name().contains("_PLATE")){
            boolean plate = Main.getInstance().getService().checkOption(event.getBlock().getWorld(), WorldOption.Pressure_Plate);
            if (!plate)
                event.setNewCurrent(0);
        }
        boolean redstone = Main.getInstance().getService().checkOption(event.getBlock().getWorld(), WorldOption.Redstone);
        if (!redstone) {
            Material type = event.getBlock().getType();
            if (isRedstoneRelated(type)) {
                event.setNewCurrent(0);
            }
        }
    }


    private boolean isRedstoneRelated(Material type) {
        return type == Material.REDSTONE_WIRE ||
                type == Material.DIODE_BLOCK_ON ||
                type == Material.DIODE_BLOCK_OFF ||
                type == Material.REDSTONE_COMPARATOR_ON ||
                type == Material.REDSTONE_COMPARATOR_OFF;
    }

}
