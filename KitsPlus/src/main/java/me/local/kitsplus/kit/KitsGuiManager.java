package me.local.kitsplus.kit;

import me.local.kitsplus.KitsPlus;
import me.local.kitsplus.nbt.NbtWrapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

public class KitsGuiManager {
    private static Inventory gui;
    private static ArrayList<ItemStack> kitChoices = new ArrayList<>();

    public static boolean addChoice(ItemStack itemStack) {
        if (kitChoices.contains(itemStack)) {
            return false;
        } else {
            kitChoices.add(itemStack);
            return true;
        }
    }

    public static void openGui(Player p) {
        String title = ChatColor.DARK_BLUE + "PhaseCraft Kits";
        gui = Bukkit.createInventory(p, 27, title);
        kitChoices.forEach(gui::addItem);
        p.openInventory(gui);
    }

    public static void updateGui() {
        kitChoices.removeAll(kitChoices);
        FileConfiguration kitsConfig = KitsPlus.getInstance().getKitsConfig();
        ConfigurationSection section = kitsConfig.getConfigurationSection("kits");
        section.getKeys(false).forEach((key) -> {
            String iconItem = section.getString(key + ".kit-icon");
            String iconLore = section.getString(key+".description");
            String iconName = section.getString(key+".name-formatted");
            ItemStack kitIcon = new ItemStack(Material.matchMaterial(iconItem));
            kitIcon = KitsPlus.getNbtWrapper().setNBTTag("kitName", key, kitIcon);
            ItemMeta meta = kitIcon.getItemMeta();
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&',iconLore)));
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', iconName));
            kitIcon.setItemMeta(meta);
            if (!kitChoices.contains(kitIcon)) {
                kitChoices.add(kitIcon);
            }
        });
    }
}
