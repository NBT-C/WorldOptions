package me.nbtc.worldoptions.listeners;

import com.ultimismc.serversync.Server;
import com.ultimismc.serversync.communication.ServerChannelConstants;
import me.nbtc.worldoptions.Main;
import me.nbtc.worldoptions.manager.base.WorldOption;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class OtherListeners implements Listener {
    @EventHandler
    public void weather(WeatherChangeEvent event){
        boolean weather = Main.getInstance().getService().checkOption(event.getWorld(), WorldOption.Weather_Change);
        if (!weather)
            event.setCancelled(true);
    }
    @EventHandler
    public void hunger(FoodLevelChangeEvent event){
        boolean hunger = Main.getInstance().getService().checkOption(event.getEntity().getWorld(), WorldOption.Hunger);
        if (!hunger)
            event.setCancelled(true);
    }
    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        boolean fish = Main.getInstance().getService().checkOption(event.getPlayer(), WorldOption.Fishing);
        if (!fish)
            event.setCancelled(true);
    }
    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        boolean exp = Main.getInstance().getService().checkOption(event.getEntity().getWorld(), WorldOption.Explosions);
        if (!exp)
            event.setCancelled(true);
    }
    @EventHandler
    public void onExplosionPrime(BlockExplodeEvent event) {
        boolean exp = Main.getInstance().getService().checkOption(event.getBlock().getWorld(), WorldOption.Explosions);
        if (!exp)
            event.setCancelled(true);
    }
    @EventHandler
    public void onExplosionPrime(EntityExplodeEvent event) {
        boolean exp = Main.getInstance().getService().checkOption(event.getEntity().getWorld(), WorldOption.Explosions);
        if (!exp)
            event.setCancelled(true);
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        boolean msg = Main.getInstance().getService().checkOption(event.getPlayer(), WorldOption.Join_Message);
        boolean heal = Main.getInstance().getService().checkOption(event.getPlayer(), WorldOption.Heal_On_Join);
        boolean feed = Main.getInstance().getService().checkOption(event.getPlayer(), WorldOption.Feed_On_Join);
        boolean fly = Main.getInstance().getService().checkOption(event.getPlayer(), WorldOption.Flight_On_Join);
        boolean clearInv = Main.getInstance().getService().checkOption(event.getPlayer(), WorldOption.Clear_Inventory_On_Join);
        boolean clearArmor = Main.getInstance().getService().checkOption(event.getPlayer(), WorldOption.Clear_Armor_On_Join);
        if (clearInv)
            event.getPlayer().getInventory().clear();
        if (clearArmor)
            event.getPlayer().getInventory().setArmorContents(null);
        if (heal)
            event.getPlayer().setHealth(20);
        if (fly) {
            event.getPlayer().setAllowFlight(true);
            event.getPlayer().setFlying(true);
        }
        if (feed)
            event.getPlayer().setFoodLevel(20);
        if (!msg)
            event.setJoinMessage(null);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        boolean msg = Main.getInstance().getService().checkOption(event.getPlayer(), WorldOption.Quit_Message);
        if (!msg)
            event.setQuitMessage(null);
    }
    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        boolean burn = Main.getInstance().getService().checkOption(event.getBlock().getWorld(), WorldOption.Block_Burn);
        if (!burn)
            event.setCancelled(true);
    }
    @EventHandler
    public void onBlockBurn(BlockSpreadEvent event) {
        boolean burn = Main.getInstance().getService().checkOption(event.getBlock().getWorld(), WorldOption.Block_Burn);
        if (!burn)
            event.setCancelled(true);
    }
    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        boolean piston = Main.getInstance().getService().checkOption(event.getBlock().getWorld(), WorldOption.Pistons);
        if (!piston)
            event.setCancelled(true);
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        boolean piston = Main.getInstance().getService().checkOption(event.getBlock().getWorld(), WorldOption.Pistons);
        if (!piston)
            event.setCancelled(true);
    }
    @EventHandler
    public void onprejoin(AsyncPlayerPreLoginEvent event){
        Server server = Main.getInstance().getServerManager().getServer();
        if (server.isFull()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, "Full server!");
        }
    }
    @EventHandler
    public void join(PlayerJoinEvent event){
        Server server = Main.getInstance().getServerManager().getServer();
        server.setOnlinePlayers(Bukkit.getOnlinePlayers().size());

        Main.getInstance().getServerManager().sendServerMessage(ServerChannelConstants.SERVER_PLAYERS_UPDATED);
    }
    @EventHandler
    public void join(PlayerQuitEvent event){
        System.out.println("listened");
        Server server = Main.getInstance().getServerManager().getServer();
        server.setOnlinePlayers(server.getOnlinePlayers() - 1);

        Main.getInstance().getServerManager().sendServerMessage(ServerChannelConstants.SERVER_PLAYERS_UPDATED);
    }
}
