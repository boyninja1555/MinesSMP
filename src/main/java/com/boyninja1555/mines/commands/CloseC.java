package com.boyninja1555.mines.commands;

import com.boyninja1555.mines.MinesSMP;
import com.boyninja1555.mines.lib.MinesCommand;
import com.boyninja1555.mines.lib.MinesPerms;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;

public class CloseC extends MinesCommand {

    public CloseC(MinesSMP plugin) {
        super(plugin);
    }

    @Command("close")
    @CommandDescription("Closes the server")
    @Permission("minesmp.commands.close")
    public void close(CommandSourceStack source) {
        CommandSender sender = source.getSender();

        // I'm not sure how this is useful, really! I always whitelist my staff
        // But keep it for fun
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (!player.hasPermission(MinesPerms.FLAG_BYPASS_SERVER_CLOSED.value) || plugin().getServer().getWhitelistedPlayers().contains(player))
                return;

            plugin().getServer().getWhitelistedPlayers().add(player);
        });

        plugin().getServer().setWhitelist(true);
        sender.sendMessage(MinesSMP.config.getRichMessage("server-closed", MinesSMP.DEFAULT_MESSAGE));
    }
}
