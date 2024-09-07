package me.nbtc.worldoptions.listeners;

import me.nbtc.worldoptions.Main;
import me.nbtc.worldoptions.manager.base.WorldOption;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            boolean fallDamage = Main.getInstance().getService().checkOption(player, WorldOption.Fall_Damage);
            if (!fallDamage)
                event.setCancelled(true);
        }
    }
    @EventHandler
    public void pvp(EntityDamageByEntityEvent event){
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDamager() instanceof Player)) return;

        Player attacker = (Player) event.getDamager();
        boolean pvp = Main.getInstance().getService().checkOption(attacker, WorldOption.PvP);
        if (!pvp)
            event.setCancelled(true);

    }
}
