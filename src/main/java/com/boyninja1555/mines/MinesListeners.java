/**
 * MinesSMP Service for event listeners
 */

package com.boyninja1555.mines;

import com.boyninja1555.mines.lib.MinesListener;

import java.lang.reflect.InvocationTargetException;

public class MinesListeners {
    private final MinesSMP plugin;

    public MinesListeners(MinesSMP plugin) {
        this.plugin = plugin;
    }

    public void register(Class<? extends MinesListener> listener) {
        try {
            plugin.getServer().getPluginManager().registerEvents(
                    listener.getConstructor(MinesSMP.class).newInstance(plugin),
                    plugin
            );
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ex) {
            plugin.getLogger().warning("Could not register listener! " + ex.getMessage());
        }
    }
}
