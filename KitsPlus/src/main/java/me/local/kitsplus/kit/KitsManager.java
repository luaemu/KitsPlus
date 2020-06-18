package me.local.kitsplus.kit;

import me.local.kitsplus.KitsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* KitsManager manages creating, deleting, and modifying kits. It also stores information for the /kit GUI and cooldowns */
public class KitsManager {
    private static KitsCooldownManager cooldownManager = new KitsCooldownManager();

    public static void giveKitToPlayer(Player player, String kitName) {
        if (KitsPlus.getInstance().getKitsConfig().getConfigurationSection("kits." + kitName) != null) {
            long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(player, kitName);
            int cooldown = KitsPlus.getInstance().getKitsConfig().getInt("kits."+kitName+".cooldown");
            if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= cooldown) {
                String effect = KitsPlus.getInstance().getKitsConfig().getConfigurationSection("kits." + kitName).getString("potion-effect");
                int amplifier = KitsPlus.getInstance().getKitsConfig().getConfigurationSection("kits." + kitName).getInt("amplifier");
                System.out.println("effect = " + effect);
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(KitsPlus.getInstance(), new Runnable()
                {
                    public void run()
                    {
                        player.addPotionEffect(PotionEffectType.getByName(effect).createEffect(Integer.MAX_VALUE, amplifier));
                    }

                }, (1));
                List<ItemStack> kitItems = new ArrayList<>();
                KitsPlus.getInstance().getKitsConfig().getConfigurationSection("kits." + kitName).getStringList("contents").forEach(e -> kitItems.add(KitsManager.getItemStackFromString(e)));
                player.getInventory().clear();
                player.getActivePotionEffects().forEach(e -> player.removePotionEffect(e.getType()));
                kitItems.forEach(System.out::println);
                kitItems.forEach(player.getInventory()::addItem);
                player.sendMessage(ChatColor.GREEN + "You have successfully redeemed this kit!");
                cooldownManager.setCooldown(player, kitName, System.currentTimeMillis());
            } else {
                player.sendMessage(ChatColor.RED.toString() + "You are on cooldown for " + (cooldown - TimeUnit.MILLISECONDS.toSeconds(timeLeft)) + " seconds on this kit!");
            }
        }
    }

    public static ItemStack getItemStackFromString(String str) {
        List<String> tokens = Arrays.asList(str.split(" "));
        Material mat = Material.matchMaterial(tokens.get(0)) != null ? Material.matchMaterial(tokens.get(0)) : Material.AIR;
        int amount = Integer.parseInt(tokens.get(1));
        short data = Short.parseShort(tokens.get(2));
        ItemStack stack = new ItemStack(mat, amount, data);
        if (tokens.size() > 3) {
            for (int i = 3; i < tokens.size() - 1; i++) {
                Enchantment enchantment;
                int level;
                enchantment = Enchantment.getByKey(NamespacedKey.minecraft(tokens.get(i)));
                level = Integer.parseInt(tokens.get(++i));
                stack.addEnchantment(enchantment, level);
            }
        }
        return stack;
    }

    public static String getStringFromItemStack(ItemStack stack) {
        StringBuilder builder = new StringBuilder();
        builder.append(stack.getType().getKey().getKey()).append(" ");
        builder.append(stack.getAmount()).append(" ");
        builder.append(stack.getData().getData()).append(" ");
        stack.getEnchantments().forEach((i, j) -> builder.append(i.getKey().getKey()).append(" ").append(j).append(" "));
        return builder.toString();
    }
}
