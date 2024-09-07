package me.nbtc.worldoptions;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.ultimismc.serversync.*;
import com.ultimismc.serversync.communication.JedisConnection;
import lombok.Getter;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.nbtc.worldoptions.commands.WorldOptionsCommand;
import me.nbtc.worldoptions.listeners.*;
import me.nbtc.worldoptions.manager.base.Service;
import me.nbtc.worldoptions.manager.base.WorldOption;
import me.nbtc.worldoptions.smartinv.InventoryManager;
import me.nbtc.worldoptions.utils.TextUtil;
import me.nbtc.worldoptions.utils.config.SimpleConfig;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;

@Getter
public final class Main extends JavaPlugin {
    private static @Getter Main instance;
    private SimpleConfig settings,bob;
    private Service service;
    private InventoryManager inventoryManager;
    HeadDatabaseAPI api = new HeadDatabaseAPI();


    @Override
    public void onEnable() {
        // Plugin startup logic
        api.
        instance = this;
        settings = new SimpleConfig("settings.yml", getDataFolder().getPath());
        bob = new SimpleConfig("bob.yml", getDataFolder().getPath());
        service = new Service();

        service.loadValues();

        inventoryManager = new InventoryManager(this);
        inventoryManager.init();

        getCommand("worldoptions").setExecutor(new WorldOptionsCommand());
        listen();

        monstersAndAnimalCheck();

        Bukkit.getOnlinePlayers().forEach(player -> {
            CraftPlayer bPlayer = ((CraftPlayer) player);
            Collection<Property> properties = bPlayer.getProfile().getProperties().get("textures");
            String url = "";
            for (Property property : properties) {
                url = property.getValue();
                break;
            }
            System.out.println(url);
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        service.saveValues();
    }

    public void listen(){
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new OtherListeners(), this);
        getServer().getPluginManager().registerEvents(new CreatureListener(), this);
    }

    public ItemStack createSkull(String url, String name) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        if (url.isEmpty())
            return head;
        SkullMeta headMeta = (SkullMeta)head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (IllegalArgumentException|NoSuchFieldException|SecurityException|IllegalAccessException error) {
            error.printStackTrace();
        }
        head.setItemMeta((ItemMeta)headMeta);
        return head;
    }

    private void monstersAndAnimalCheck(){
        for (World world : Bukkit.getWorlds()){
            boolean monster = Main.getInstance().getService().checkOption(world, WorldOption.Spawn_Monsters);
            if (!monster) {
                for (Entity entity : world.getEntities()){
                    if (CreatureListener.isMonster(entity.getType())){
                        entity.remove();
                    }
                }
            }
            boolean animal = Main.getInstance().getService().checkOption(world, WorldOption.Spawn_Animal);
            if (!animal) {
                for (Entity entity : world.getEntities()){
                    if (CreatureListener.isAnimal(entity.getType())){
                        entity.remove();
                    }
                }
            }
        }
    }
}
