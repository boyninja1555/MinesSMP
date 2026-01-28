/**
 * MinesSMP Service for Dialog
 */

package com.boyninja1555.mines;

import com.boyninja1555.mines.dialog.DialogTree;
import com.boyninja1555.mines.lib.MapToConfigurationSection;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MinesDialog {
    private final MinesSMP plugin;
    private final List<DialogTree> trees;
    private final File file;

    public MinesDialog(MinesSMP plugin, File file) {
        this.plugin = plugin;
        this.trees = new ArrayList<>();
        this.file = file;
    }

    // Used by dialog triggers (debug command, npc click, yk yk)
    // to get a dialog tree by its label (if it's the root, label is just the dialog/npc name)
    public DialogTree getRootByLabel(String label) {
        List<DialogTree> results = trees.stream()
                .filter(t -> t.label().equals(label))
                .toList();

        if (results.isEmpty())
            return null;

        return results.getFirst();
    }

    public List<DialogTree> getAll() {
        return new ArrayList<>(trees);
    }

    public void load() {
        if (!file.exists()) {
            save();
            return;
        }

        YamlConfiguration dialogConfig = YamlConfiguration.loadConfiguration(file);
        List<?> dialogList = dialogConfig.getList("dialog");

        // Who's dumb enough to make this mistake... lol
        if (dialogList == null) {
            plugin.getLogger().severe("Could not load dialog! Missing or invalid \"dialog\" field");
            return;
        }

        // I hate that map to section util...
        // I just hate it
        // UPDATE YOUR API, PAPER, SO I CAN DITCH BUKKIT :SOB:
        dialogList.stream()
                .map(d -> {
                    if (d instanceof Map)
                        return MapToConfigurationSection.to((Map) d);

                    return null;
                })
                .forEach(t -> trees.add(DialogTree.deserialize(plugin, t)));
    }

    public void save() {
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (!created)
                    throw new IOException("Please ensure you can access the " + file.getParentFile() + " directory");
            }

            // Just serialize and jam shit into it... :)
            YamlConfiguration dialogConfig = YamlConfiguration.loadConfiguration(file);
            ConfigurationSection emptySection = dialogConfig.createSection("empty");
            List<ConfigurationSection> dialogList = new ArrayList<>();
            trees.forEach(t -> dialogList.add(t.serialize(emptySection)));
            dialogConfig.set("dialog", dialogList);
            dialogConfig.set("empty", null);
        } catch (IOException ex) {
            plugin.getLogger().severe("Could not save dialog! " + ex.getMessage());
        }
    }
}
