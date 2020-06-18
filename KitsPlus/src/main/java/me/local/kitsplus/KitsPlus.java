package me.local.kitsplus;

import me.local.kitsplus.commands.KitCommand;
import me.local.kitsplus.commands.TestItem;
import me.local.kitsplus.commands.TestKitString;
import me.local.kitsplus.listeners.GuiOptionSelectEvent;
import me.local.kitsplus.nbt.NbtWrapper;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class KitsPlus extends JavaPlugin {

    private File kitsFile;
    private FileConfiguration kitsConfig;
    private static NbtWrapper nbtWrapper;

    private static KitsPlus instance;

    @Override
    public void onEnable() {
        createKitsConfig();
        saveDefaultConfig();
        registerCommands();
        registerListeners();
        nbtWrapper = new NbtWrapper();
        instance = this;
    }

    private void createKitsConfig() {
        kitsFile = new File(getDataFolder(), "kits.yml");
        if (!(kitsFile.exists())) {
            kitsFile.getParentFile().mkdirs();
            saveResource("kits.yml", false);
        }

        kitsConfig = new YamlConfiguration();
        try {
            kitsConfig.load(kitsFile);
        } catch (IOException | InvalidConfigurationException e)  {
            e.printStackTrace();
        }
    }

    private void registerCommands() {
        getCommand("kit").setExecutor(new KitCommand());
        getCommand("teststring").setExecutor(new TestKitString());
        getCommand("testitem").setExecutor(new TestItem());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new GuiOptionSelectEvent(), this);
    }

    public FileConfiguration getKitsConfig() {
        return kitsConfig;
    }

    public static KitsPlus getInstance() {
        return instance;
    }

    public static NbtWrapper getNbtWrapper() {
        return nbtWrapper;
    }
}
