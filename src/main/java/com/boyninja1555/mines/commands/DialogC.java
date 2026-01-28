package com.boyninja1555.mines.commands;

import com.boyninja1555.mines.MinesSMP;
import com.boyninja1555.mines.dialog.DialogTree;
import com.boyninja1555.mines.lib.DialogPlayer;
import com.boyninja1555.mines.lib.MinesCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.suggestion.Suggestions;

import java.util.List;
import java.util.regex.Pattern;

public class DialogC extends MinesCommand {

    public DialogC(MinesSMP plugin) {
        super(plugin);
    }

    @Command("dialog <label>")
    @CommandDescription("Tests a dialog by its label")
    @Permission("minesmp.commands.dialog")
    public void dialog(
            CommandSourceStack source,
            @Argument(value = "label", suggestions = "dialog-labels") String rawLabel
    ) {
        // Fuck consoles
        if (!(source.getSender() instanceof Player player)) {
            source.getSender().sendMessage(MinesSMP.config.getRichMessage("not-player", MinesSMP.DEFAULT_MESSAGE)
                    .replaceText(TextReplacementConfig.builder()
                            .match(Pattern.quote("{action}"))
                            .replacement("to test a dialog")
                            .build()));
            return;
        }

        // I would implement multi-argument selection, but I'm too lazy to figure out how Cloud does that
        String label = rawLabel.replaceAll("__", " ");
        DialogTree tree = MinesSMP.services.dialog.getRootByLabel(label);

        // Add an error message if you feel like it, I don't friggin' care
        if (tree == null)
            return;

        // W REUSABLE CODEEE :SPARKLES:
        DialogPlayer dialog = new DialogPlayer(player, tree);
        dialog.greet();
    }

    @Suggestions("dialog-labels")
    public List<String> dialogLabels(CommandSourceStack source, String input) {
        String[] args = input.split(" ");
        List<String> labels = MinesSMP.services.dialog.getAll().stream()
                .map(DialogTree::label)
                .map(l -> l.replaceAll(" ", "__"))
                .toList();

        if (args.length == 0)
            return labels;

        if (args.length == 1)
            return labels.stream()
                    .filter(a -> a.startsWith(args[0]))
                    .toList();

        return List.of();
    }
}
