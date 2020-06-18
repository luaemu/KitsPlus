package me.local.kitsplus.commands;

import me.local.kitsplus.kit.KitsGuiManager;
import me.local.kitsplus.kit.KitsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                KitsGuiManager.updateGui();
                KitsGuiManager.openGui(((Player) sender));
                System.out.println("OPEN GUI");
            }
        }
        return true;
    }
}
