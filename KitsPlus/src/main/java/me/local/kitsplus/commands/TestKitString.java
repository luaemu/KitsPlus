package me.local.kitsplus.commands;

import me.local.kitsplus.kit.KitsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestKitString implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("teststring")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players may execute this command!");
            } else {
                Player p = ((Player) sender);
                String serializedItem = KitsManager.getStringFromItemStack(p.getInventory().getItemInMainHand());
                p.sendMessage(serializedItem);
            }
        }
        return false;
    }
}
