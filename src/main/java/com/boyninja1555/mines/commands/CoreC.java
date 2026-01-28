/**
 * Bruh I wouldn't recommend working here unless you make a new resource/file that needs to be reloaded
 * It's SERIOUSLY annoying :sob:
 */

package com.boyninja1555.mines.commands;

import com.boyninja1555.mines.MinesSMP;
import com.boyninja1555.mines.lib.CoreAction;
import com.boyninja1555.mines.lib.MinesCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.CommandDescription;
import org.incendo.cloud.annotations.Permission;
import org.incendo.cloud.annotations.suggestion.Suggestions;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CoreC extends MinesCommand {

    public CoreC(MinesSMP plugin) {
        super(plugin);
    }

    @Command("core <action>")
    @CommandDescription("Manages the MinesSMP plugin")
    @Permission("minesmp.commands.core")
    public void core(
            CommandSourceStack source,
            @Argument(value = "action", suggestions = "core-actions") CoreAction action
    ) {
        CommandSender sender = source.getSender();

        if (action.equals(CoreAction.RELOAD)) {
            plugin().rReloadConfig();
            MinesSMP.services.dialog.load();
            sender.sendMessage(MinesSMP.config.getRichMessage("plugin-reloaded", MinesSMP.DEFAULT_MESSAGE));
        } else if (action.equals(CoreAction.REGENERATE_CONFIG)) {
            File configFile = new File(plugin().getDataFolder(), "config.yml");

            if (configFile.exists()) {
                boolean deleted = configFile.delete();
                if (!deleted) {
                    plugin().getLogger().severe("Could not regenerate config.yml! Please ensure you can access the " + configFile.getParentFile() + " directory");
                    return;
                }
            }

            plugin().saveDefaultConfig();
            plugin().rReloadConfig();
            sender.sendMessage(MinesSMP.config.getRichMessage("config-regenerated", MinesSMP.DEFAULT_MESSAGE));
        }
    }

    @Suggestions("core-actions")
    public List<String> coreActions(CommandSourceStack source, String input) {
        String[] args = input.split(" ");
        List<String> actions = Arrays.stream(CoreAction.values())
                .map(CoreAction::literal)
                .toList();

        if (args.length == 0)
            return actions;

        if (args.length == 1)
            return actions.stream()
                    .filter(a -> a.startsWith(args[0]))
                    .toList();

        return List.of();
    }
}
