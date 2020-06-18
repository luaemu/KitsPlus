package me.local.kitsplus.listeners;

import me.local.kitsplus.KitsPlus;
import me.local.kitsplus.kit.KitsManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiOptionSelectEvent implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(ChatColor.DARK_BLUE + "PhaseCraft Kits")) {
            String kitName = KitsPlus.getNbtWrapper().getNBTTag("kitName", e.getCurrentItem());
            KitsManager.giveKitToPlayer(((Player) e.getWhoClicked()), kitName);
            e.setCancelled(true);
        }
    }
}
