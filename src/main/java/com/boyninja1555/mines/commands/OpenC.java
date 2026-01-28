package com.boyninja1555.mines.commands;

import com.boyninja1555.mines.MinesSMP;
import com.boyninja1555.mines.lib.MinesCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

public class OpenC extends MinesCommand {

    public OpenC(MinesSMP plugin) {
        super(plugin);
    }

    @Command("open")
    @CommandDescription("Opens the server")
    @Permission("minesmp.commands.open")
    public void open(CommandSourceStack source) {
        // Honestly, it's just a shorter whitelist command
        CommandSender sender = source.getSender();
        plugin().getServer().setWhitelist(false);
        sender.sendMessage(MinesSMP.config.getRichMessage("server-opened", MinesSMP.DEFAULT_MESSAGE));
    }
}
