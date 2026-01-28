/**
 * THIS FUCKING UTIL
 * WHY DOESN'T BUKKIT COME WITH ONE?!?!??
 * I don't always want to make a new config section,
 * so I gotta make my own logic?
 * I want Paper API to be complete :(
 */

package com.boyninja1555.mines.lib;

import com.boyninja1555.mines.MinesSMP;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;
import java.util.UUID;

public class MapToConfigurationSection {

    public static ConfigurationSection to(Map<String, Object> map) {
        String emptyId = UUID.randomUUID().toString();
        ConfigurationSection out = MinesSMP.config.createSection("EMPTY-" + emptyId, map);
        MinesSMP.config.set("EMPTY-"  + emptyId, null);
        return out;
    }
}
