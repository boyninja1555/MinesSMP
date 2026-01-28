/**
 * Good news! We NEVER have to edit this file
 * All the components/services are registered in the MinesServices class
 */

package com.boyninja1555.mines;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class MinesSMP extends JavaPlugin {
    public static final Component DEFAULT_MESSAGE = Component.text("Please regenerate the MinesSMP config.yml", NamedTextColor.RED);
    public static FileConfiguration config;
    public static MinesServices services;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        services = new MinesServices(this);
    }

    public void rReloadConfig() {
        reloadConfig();
        config = getConfig();
    }
}
