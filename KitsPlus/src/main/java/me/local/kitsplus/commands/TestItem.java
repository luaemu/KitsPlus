package me.local.kitsplus.commands;

import me.local.kitsplus.kit.KitsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestItem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("testitem")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("only player may execute this command!");
            } else {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    builder.append(args[i]).append(" ");
                }
                System.out.println(builder.toString());
                ItemStack stack = KitsManager.getItemStackFromString(builder.toString());
                Player p = ((Player) sender);
                p.getInventory().addItem(stack);
            }
        }
        return false;
    }
}
