package me.nbtc.worldoptions.manager.base;

import lombok.Getter;
import org.bukkit.Material;

public enum WorldOption {
    PvP(Material.DIAMOND_SWORD, (byte) 0, "&cPvP"),

    Note_Block(Material.NOTE_BLOCK, (byte) 0, "&eNote Block"),
    Hopper(Material.HOPPER, (byte) 0, "&7Hopper"),
    Redstone(Material.REDSTONE, (byte) 0, "&cRedstone"),
    Pistons(Material.PISTON_BASE, (byte) 0, "&ePistons"),
    Bed(Material.BED, (byte) 0, "&dBed"),
    Beacon(Material.BEACON, (byte) 0, "&bBeacon"),
    Ender_Chest(Material.ENDER_CHEST, (byte) 0, "&5Ender Chest"),
    Ender_Pearl(Material.ENDER_PEARL, (byte) 0, "&aEnder Pearl"),
    Chest(Material.CHEST, (byte) 0, "&6Chest"),
    Furnace(Material.FURNACE, (byte) 0, "&8Furnace"),
    Item_Frame(Material.ITEM_FRAME, (byte) 0, "&fItem Frame"),
    Pressure_Plate(Material.GOLD_PLATE, (byte) 0, "&ePressure Plate"),
    Anvil(Material.ANVIL, (byte) 0, "&8Anvil"),
    Crafting_Table(Material.WORKBENCH, (byte) 0, "&7Crafting Table"),
    Enchantment_Table(Material.ENCHANTMENT_TABLE, (byte) 0, "&9Enchantment Table"),
    Buttons(Material.STONE_BUTTON, (byte) 0, "&7Buttons"),
    Trains(Material.MINECART, (byte) 0, "&7Trains"),
    Lava(Material.LAVA_BUCKET, (byte) 0, "&6Lava"),
    Signs(Material.SIGN, (byte) 0, "&6Signs"),
    Boats(Material.BOAT, (byte) 0, "&7Boats"),
    Books(Material.BOOK, (byte) 0, "&eBooks"),

    Spawn_Monsters(Material.MONSTER_EGG, (byte) 0, "&cSpawn Monsters"),
    Spawn_Animal(Material.EGG, (byte) 0, "&aSpawn Animals"),

    Weather_Change(Material.GHAST_TEAR, (byte) 0, "&bWeather Change"),
    Fishing(Material.FISHING_ROD, (byte) 0, "&3Fishing"),
    Explosions(Material.TNT, (byte) 0, "&cExplosions"),
    Join_Message(Material.INK_SACK, (byte) 10, "&eJoin Message"),
    Quit_Message(Material.INK_SACK, (byte) 1, "&cQuit Message"),
    Hunger(Material.COOKED_CHICKEN, (byte) 0, "&6Hunger"),
    Fall_Damage(Material.IRON_PLATE, (byte) 0, "&7Fall Damage"),
    Block_Burn(Material.BLAZE_ROD, (byte) 0, "&6Block Burn"),
    Spawn_Fire(Material.BLAZE_POWDER, (byte) 0, "&eSpawn Fire"),

    Heal_On_Join(Material.GOLDEN_APPLE, (byte) 0, "&6Heal On Join"),
    Feed_On_Join(Material.COOKED_BEEF, (byte) 0, "&6Feed On Join"),
    Clear_Inventory_On_Join(Material.IRON_PICKAXE, (byte) 0, "&7Clear Inventory On Join"),
    Clear_Armor_On_Join(Material.IRON_CHESTPLATE, (byte) 0, "&7Clear Armor On Join"),
    Flight_On_Join(Material.FEATHER, (byte) 0, "&aFlight On Join");

    final @Getter Material material;
    final @Getter byte aByte;
    final @Getter String displayName;
    WorldOption(Material material, byte aByte, String displayName){
        this.material = material;
        this.aByte = aByte;
        this.displayName = displayName;
    }
}
