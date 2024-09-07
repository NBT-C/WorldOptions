package me.nbtc.worldoptions.manager.base;

import me.nbtc.worldoptions.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;

public class Service {
    public final HashMap<String, HashMap<WorldOption, Boolean>> worldOptionValues = new HashMap<>();
    public final List<WorldOption> worldOptions = new ArrayList<>(Arrays.asList(WorldOption.values()));

    public void loadValues(){
        for (World world : Bukkit.getWorlds()) {
            HashMap<WorldOption, Boolean> values = new HashMap<>();
            for (WorldOption option : WorldOption.values()) {
                String key = option.name();
                if (Main.getInstance().getSettings().getConfig().get(world.getName() + "." + key) == null){
                    boolean isTrue = !key.contains("Join");
                    Main.getInstance().getSettings().getConfig().set(world.getName() + "." + key, isTrue);
                    Main.getInstance().getSettings().save();
                }
                boolean value = Main.getInstance().getSettings().getConfig().getBoolean(world.getName() + "." + key, false);
                values.put(option, value);
            }
            worldOptionValues.put(world.getName(), values);
        }
    }
    public void saveValues(){
        for (Map.Entry<String, HashMap<WorldOption, Boolean>> value : worldOptionValues.entrySet()){
            String world = value.getKey();

            for (Map.Entry<WorldOption, Boolean> options : value.getValue().entrySet()){
                Main.getInstance().getSettings().getConfig().set(world + "." + options.getKey().name(), options.getValue());
                Main.getInstance().getSettings().save();
            }
        }
    }
    public boolean checkOption(Player player, WorldOption wo){
        return worldOptionValues.get(player.getWorld().getName()).get(wo);
    }
    public boolean checkOption(World world, WorldOption wo){
        return worldOptionValues.get(world.getName()).get(wo);
    }
}
