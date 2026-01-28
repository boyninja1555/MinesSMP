/**
 * First command I make in this plugin AND with cloud :sparkles:
 * Treat it with respect D:<
 */

package com.boyninja1555.mines.commands;

import com.boyninja1555.mines.MinesSMP;
import com.boyninja1555.mines.lib.MinesCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.suggestion.Suggestions;
import org.incendo.cloud.context.CommandContext;

import java.util.List;
import java.util.regex.Pattern;

public class PingC extends MinesCommand {

    public PingC(MinesSMP plugin) {
        super(plugin);
    }

    @Command("ping [player]")
    @CommandDescription("Views your ping or another player's")
    public void ping(
            CommandSourceStack source,
            @Argument(value = "player", suggestions = "online-players") String playerName
    ) {
        CommandSender sender = source.getSender();
        Player player;

        if (playerName == null && sender instanceof Player)
            player = (Player) sender;
        else if (playerName == null) {
            sender.sendMessage(MinesSMP.config.getRichMessage("not-player", MinesSMP.DEFAULT_MESSAGE)
                    .replaceText(TextReplacementConfig.builder()
                            .match(Pattern.quote("{action}"))
                            .replacement("to view your ping")
                            .build()));
            return;
        } else
            player = Bukkit.getPlayer(playerName);

        if (player == null) {
            sender.sendMessage(MinesSMP.config.getRichMessage("invalid-player", MinesSMP.DEFAULT_MESSAGE)
                    .replaceText(TextReplacementConfig.builder()
                            .match(Pattern.quote("{player}"))
                            .replacement(playerName)
                            .build()));
            return;
        }

        // This time around, I'm not making a mini chat formatter
        sender.sendMessage(MinesSMP.config.getRichMessage("ping", MinesSMP.DEFAULT_MESSAGE)
                .replaceText(TextReplacementConfig.builder()
                        .match(Pattern.quote("{player}"))
                        .replacement(player.getName())
                        .build())
                .replaceText(TextReplacementConfig.builder()
                        .match(Pattern.quote("{ping}"))
                        .replacement(String.valueOf(player.getPing()))
                        .build()));
    }

    @Suggestions("online-players")
    public List<String> onlinePlayers(CommandContext<CommandSourceStack> source, String input) {
        String[] args = input.split(" ");
        List<String> players = Bukkit.getOnlinePlayers()
                .stream()
                .map(Player::getName)
                .toList();

        if (args.length == 0)
            return players;

        // I lowk kinda wish Cloud or Brigadier did this automatically
        // Basically it makes your command input filter for tab completion shit
        if (args.length == 1)
            return players.stream()
                    .filter(p -> p.startsWith(args[0]))
                    .toList();

        return List.of();
    }
}
