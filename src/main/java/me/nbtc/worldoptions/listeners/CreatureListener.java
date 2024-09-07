package me.nbtc.worldoptions.listeners;

import me.nbtc.worldoptions.Main;
import me.nbtc.worldoptions.manager.base.WorldOption;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureListener implements Listener {
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        EntityType entityType = event.getEntityType();

        if (isMonster(entityType)){
            boolean monster = Main.getInstance().getService().checkOption(event.getEntity().getWorld(), WorldOption.Spawn_Monsters);
            if (!monster)
                event.setCancelled(true);
        }
        if (isAnimal(entityType)){
            boolean animal = Main.getInstance().getService().checkOption(event.getEntity().getWorld(), WorldOption.Spawn_Animal);
            if (!animal)
                event.setCancelled(true);
        }
    }

    public static boolean isMonster(EntityType entityType) {
        switch (entityType) {
            case CREEPER:
            case ZOMBIE:
            case SKELETON:
            case SPIDER:
            case ENDERMAN:
            case WITCH:
            case SLIME:
            case MAGMA_CUBE:
            case GHAST:
            case BLAZE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isAnimal(EntityType entityType) {
        switch (entityType) {
            case COW:
            case SHEEP:
            case PIG:
            case CHICKEN:
            case HORSE:
            case RABBIT:
            case WOLF:
            case OCELOT:
                return true;
            default:
                return false;
        }
    }
}
