package me.nbtc.worldoptions.commands;

import me.nbtc.worldoptions.Main;
import me.nbtc.worldoptions.manager.WorldOptionsMenu;
import me.nbtc.worldoptions.utils.TextUtil;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WorldOptionsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (!sender.hasPermission("worldoptions.admin")){
            TextUtil.msg(sender, "&cSorry, but you don't have enough permissions.");
            return false;
        }
        if (!(sender instanceof Player)){
            TextUtil.msg(sender, "&cSorry, you need to be a player to do that.");
            return false;
        }
        if (args.length == 0){
            Player player = ((Player) sender).getPlayer();
            player.playSound(player.getLocation(), Sound.CLICK, 1L, 1L);
            new WorldOptionsMenu(player, 0, "");
            return false;
        } else {
            TextUtil.msg(sender, "&cIncorrect usage of the command.");
        }
        return false;
    }
}
