package com.boyninja1555.mines.lib;

import com.boyninja1555.mines.MinesSMP;

public class MinesCommand {
    private final MinesSMP plugin;

    public MinesCommand(MinesSMP plugin) {
        this.plugin = plugin;
    }

    public MinesSMP plugin() {
        return plugin;
    }
}
