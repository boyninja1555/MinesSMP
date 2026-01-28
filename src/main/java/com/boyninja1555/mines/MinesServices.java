/**
 * MinesSMP Services
 * This class controls most features of the MinesSMP plugin
 */

package com.boyninja1555.mines;

import com.boyninja1555.mines.commands.*;
import com.boyninja1555.mines.listeners.DialogPlayerRestrictL;
import com.boyninja1555.mines.listeners.JoinLeaveL;

import java.io.File;

public class MinesServices {
    public final MinesCommands commands;
    public final MinesListeners listeners;
    public final MinesDialog dialog;

    public MinesServices(MinesSMP plugin) {
        commands = new MinesCommands(plugin);
        commands.register(PingC.class);
        commands.register(CoreC.class);
        commands.register(OpenC.class);
        commands.register(CloseC.class);
        commands.register(DialogC.class);

        listeners = new MinesListeners(plugin);
        listeners.register(JoinLeaveL.class);
        listeners.register(DialogPlayerRestrictL.class);

        dialog = new MinesDialog(
                plugin,
                new File(plugin.getDataFolder(), "dialog.yml")
        );
        dialog.load();
    }
}
