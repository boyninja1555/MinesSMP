package com.boyninja1555.mines.lib;

import com.boyninja1555.mines.MinesSMP;
import org.bukkit.event.Listener;

public abstract class MinesListener implements Listener {
    public final MinesSMP plugin;

    public MinesListener(MinesSMP plugin) {
        this.plugin = plugin;
    }
}
