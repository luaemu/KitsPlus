package me.local.kitsplus.commands;

import me.local.kitsplus.KitsPlus;
import me.local.kitsplus.kit.KitsCooldownManager;
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
            } else {
                if ("reload".equalsIgnoreCase(args[0])) {
                    if (sender.hasPermission("kitsplus.admin")) {
                        KitsPlus.getInstance().reloadConfig();
                    } else {
                        sender.sendMessage(KitsPlus.getInstance().getConfig().getString("permission-message"));
                    }
                } else if ("bypass".equalsIgnoreCase(args[0])) {
                    if (sender.hasPermission("kitsplus.admin") || sender.hasPermission("kitsplus.bypass")) {
                        KitsManager.getCooldownManager().toggleBypassed(((Player) sender));
                    } else {

                    }
                }
            }
        }
        return true;
    }
}
