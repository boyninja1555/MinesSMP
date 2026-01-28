package com.boyninja1555.mines.lib;

import com.boyninja1555.mines.MinesSMP;
import com.boyninja1555.mines.dialog.DialogTree;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class DialogPlayer {
    // Global list so we can restrict players in dialog
    public static final List<UUID> playersInDialog = new ArrayList<>();

    private final Player player;
    private final UUID playerId;
    private final String dialogName;
    private DialogTree current;

    public DialogPlayer(Player player, DialogTree tree) {
        this.player = player;
        this.playerId = player.getUniqueId();
        this.dialogName = tree.label();

        // I wanted to prevent certain exploits I would utilize myself :sob:
        this.current = new DialogTree(
                tree.label(),
                tree.greeting(),
                tree.pathways()
        );
    }

    public void greet() {
        // Fixing that one bug where you can re-activate a dialog by spamming your options AFTER you've exited the dialog
        if (current == null)
            return;

        if (!playersInDialog.contains(playerId)) {
            Sound enterSound = Sound.sound(
                    Key.key(MinesSMP.config.getString("dialog-enter-sound", "entity.villager.no")),
                    Sound.Source.UI,
                    1f,
                    1f
            );
            player.playSound(enterSound, Sound.Emitter.self());
            playersInDialog.add(playerId);
        }

        // I figure we can clean up the UI for maximum usability
        if (!player.getGameMode().equals(GameMode.SPECTATOR))
            player.setGameMode(GameMode.SPECTATOR);

        Component greeting = MinesSMP.config.getRichMessage("dialog-npc", MinesSMP.DEFAULT_MESSAGE)
                .replaceText(TextReplacementConfig.builder()
                        .match(Pattern.quote("{npc}"))
                        .replacement(dialogName)
                        .build())
                .replaceText(TextReplacementConfig.builder()
                        .match(Pattern.quote("{greeting}"))
                        .replacement(current.greeting())
                        .build());

        List<Component> optionsList = new ArrayList<>();

        for (int i = 0; i < current.pathways().size(); i++) {
            int index = i;
            optionsList.add(MinesSMP.config.getRichMessage("dialog-option", MinesSMP.DEFAULT_MESSAGE)
                    .replaceText(TextReplacementConfig.builder()
                            .match(Pattern.quote("{option}"))
                            .replacement(current.pathways().get(i).label())
                            .build())
                    .clickEvent(ClickEvent.callback(a -> answer(index))));
        }

        optionsList.add(MinesSMP.config.getRichMessage("dialog-option", MinesSMP.DEFAULT_MESSAGE)
                .replaceText(TextReplacementConfig.builder()
                        .match(Pattern.quote("{option}"))
                        .replacement("Close")
                        .build())
                .clickEvent(ClickEvent.callback(a -> exit())));

        Component options = Component.join(JoinConfiguration.separator(Component.newline()), optionsList);
        player.sendMessage(
                Component.empty()
                        .append(greeting)
                        .appendNewline()
                        .append(options)
        );
    }

    public void answer(int i) {
        // Fixing that one bug where you can re-activate a dialog by spamming your options AFTER you've exited the dialog
        if (current == null)
            return;

        // I'm not sure this can happen, but whatEVER
        if (current.pathways().isEmpty()) {
            exit();
            return;
        }

        // I wouldn't try understanding this...
        // Just understand it works :D
        if (i > current.pathways().size() - 1)
            return;

        Sound clickSound = Sound.sound(
                Key.key(MinesSMP.config.getString("dialog-option-sound", "entity.villager.no")),
                Sound.Source.UI,
                1f,
                1f
        );
        player.playSound(clickSound, Sound.Emitter.self());
        current = current.pathways().get(i);
        greet();
    }

    public void exit() {
        // Fixing that one bug where you can re-activate a dialog by spamming your options AFTER you've exited the dialog
        if (current == null)
            return;

        Sound exitSound = Sound.sound(
                Key.key(MinesSMP.config.getString("dialog-exit-sound", "entity.villager.no")),
                Sound.Source.UI,
                1f,
                1f
        );
        player.playSound(exitSound, Sound.Emitter.self());
        player.setGameMode(GameMode.SURVIVAL);
        playersInDialog.remove(playerId);
        current = null;
    }

    // I may never use this, but I like it :(
    public DialogTree current() {
        return new DialogTree(
                current.label(),
                current.greeting(),
                current.pathways()
        );
    }
}
