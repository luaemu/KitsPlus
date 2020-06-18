package me.local.kitsplus.kit;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class KitsCooldownManager {

    private final Map<String, Long> cooldowns = new HashMap<>();

    private String makeCooldownKey(String uuid, String kitName) {
        StringBuilder sb = new StringBuilder();
        sb.append(uuid).append(':').append(kitName);
        return sb.toString();
    }

    public void setCooldown(Player player, String kitName, long time) {
        if (time < 1) {
            cooldowns.remove(makeCooldownKey(player.getUniqueId().toString(), kitName), time);
        } else {
            cooldowns.put(makeCooldownKey(player.getUniqueId().toString(), kitName), time);
        }
    }

    public long getCooldown(Player player, String kitName) {
        return cooldowns.getOrDefault(makeCooldownKey(player.getUniqueId().toString(),kitName), 0L);
    }
}