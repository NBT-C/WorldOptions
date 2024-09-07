package me.nbtc.worldoptions.utils;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextUtil {
    public static String format(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public static void msg(Player player, String msg){
        player.sendMessage(format("&8┃ " + msg));
    }
    public static void msg(CommandSender player, String msg){
        player.sendMessage(format("&8┃ " + msg));
    }

}
