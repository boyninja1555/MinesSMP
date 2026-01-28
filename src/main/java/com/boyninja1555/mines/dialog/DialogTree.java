/**
 * Warning! Deserialization may fail at any moment
 * I made this late at night
 */

package com.boyninja1555.mines.dialog;

import com.boyninja1555.mines.MinesSMP;
import com.boyninja1555.mines.lib.MapToConfigurationSection;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record DialogTree(String label, String greeting, List<DialogTree> pathways) {

    public ConfigurationSection serialize(ConfigurationSection empty) {
        // It's mostly just a wrapper for other classes' serialization methods
        ConfigurationSection tree = empty.createSection("fake");
        tree.set("label", label);
        tree.set("greeting", greeting);
        tree.set("pathways", pathways.stream()
                .map(p -> p.serialize(empty))
                .toList());

        return tree;
    }

    public static DialogTree deserialize(MinesSMP plugin, ConfigurationSection tree) {
        String label = tree.getString("label", "Unnamed");
        String greeting = tree.getString("greeting", "Invalid greeting");
        List<?> pathwaysList = tree.getList("pathways");

        if (pathwaysList == null) {
            plugin.getLogger().warning("Could not load dialog tree! Missing \"pathways\" field, which is a list of more trees");
            return null;
        }

        // That mapping util is so funny hahahhahaha
        // ...
        // I HATE BUKKIT
        // GIMME PAPER API BUT COMPLETE
        List<DialogTree> pathways = pathwaysList.stream()
                .map(p -> MapToConfigurationSection.to((Map) p))
                .map(p -> DialogTree.deserialize(plugin, p))
                .toList();

        return new DialogTree(
                label,
                greeting,
                new ArrayList<>(pathways)
        );
    }
}
