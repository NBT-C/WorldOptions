package me.nbtc.worldoptions.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private ItemMeta itemMeta;

    private ItemStack itemStack;

    public ItemBuilder(Material mat, int size, int theByte) {
        this.itemStack = new ItemStack(mat, size, (short)(byte)theByte);
        this.itemMeta = this.itemStack.getItemMeta();
    }


    public ItemBuilder setDisplayname(String s) {
        this.itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',s));
        return this;
    }

    public ItemBuilder setChargeColor(Color color){
        ItemMeta meta = this.itemMeta;
        FireworkEffectMeta metaFw = (FireworkEffectMeta) meta;
        FireworkEffect aa = FireworkEffect.builder().withColor(color).build();
        metaFw.setEffect(aa);
        return this;
    }
    public ItemBuilder addBookEnchant(Enchantment enchantment, int level){
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemMeta;
        meta.addStoredEnchant(enchantment, level, true);
        this.itemStack.setItemMeta(meta);
        return this;


    }

    public ItemBuilder addOwner(String name) {
        SkullMeta smeta = (SkullMeta)this.itemMeta;
        smeta.setOwner(name);
        this.itemStack.setItemMeta((ItemMeta)smeta);
        return this;
    }
    public ItemBuilder addLeatherColor(Color color) {
        if (color == null){
            return this;
        }
        LeatherArmorMeta lam = (LeatherArmorMeta)this.itemMeta;
        lam.setColor(color);
        this.itemStack.setItemMeta((ItemMeta)lam);
        return this;
    }

    public ItemBuilder addHiddenEnchantment(){
        this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.itemMeta.addEnchant(Enchantment.DURABILITY, 1,true);
        return this;
    }
    public ItemBuilder addHiddenEnchantment(boolean yes){
        if (yes) {
            this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            this.itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        }
        return this;
    }
    public ItemBuilder setLore(String... s) {
        ArrayList<String> list = new ArrayList<>();
        for (String sd : s){
            list.add(ChatColor.translateAlternateColorCodes('&',sd));
        }
        this.itemMeta.setLore(list);
        return this;
    }
    public ItemBuilder setLore(List<String> s) {
        ArrayList<String> list = new ArrayList<>();
        for (String sd : s){
            list.add(ChatColor.translateAlternateColorCodes('&',sd));
        }
        this.itemMeta.setLore(list);
        return this;
    }
    public ItemBuilder addItemFlags(ItemFlag... s) {
        this.itemMeta.addItemFlags(s);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchant, int level) {
        this.itemMeta.addEnchant(enchant, level, true);
        return this;
    }
    public ItemBuilder setDurability(int durability) {
        this.itemStack.setDurability((short) durability);
        return this;
    }



    public String toString() {
        return "ItemBuilder{itemMeta=" + this.itemMeta + ", itemStack=" + this.itemStack + '}';
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}