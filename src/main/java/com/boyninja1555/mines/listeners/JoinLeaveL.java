/**
 * Fuck CJM
 */

package com.boyninja1555.mines.listeners;

import com.boyninja1555.mines.MinesSMP;
import com.boyninja1555.mines.lib.MinesListener;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.regex.Pattern;

public class JoinLeaveL extends MinesListener {

    public JoinLeaveL(MinesSMP plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            event.joinMessage(MinesSMP.config.getRichMessage("first-join", MinesSMP.DEFAULT_MESSAGE)
                    .replaceText(TextReplacementConfig.builder()
                            .match(Pattern.quote("{player}"))
                            .replacement(player.getName())
                            .build()));

            Sound firstJoinSound = Sound.sound(
                    Key.key(MinesSMP.config.getString("first-join-sound", "entity.villager.no")),
                    Sound.Source.UI,
                    1f,
                    1f
            );
            Bukkit.getOnlinePlayers().forEach(p -> p.playSound(firstJoinSound, Sound.Emitter.self()));
            return;
        }

        event.joinMessage(MinesSMP.config.getRichMessage("joined", MinesSMP.DEFAULT_MESSAGE)
                .replaceText(TextReplacementConfig.builder()
                        .match(Pattern.quote("{player}"))
                        .replacement(player.getName())
                        .build()));

        Sound joinSound = Sound.sound(
                Key.key(MinesSMP.config.getString("joined-sound", "entity.villager.no")),
                Sound.Source.UI,
                1f,
                1f
        );
        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(joinSound, Sound.Emitter.self()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.quitMessage(MinesSMP.config.getRichMessage("left", MinesSMP.DEFAULT_MESSAGE)
                .replaceText(TextReplacementConfig.builder()
                        .match(Pattern.quote("{player}"))
                        .replacement(event.getPlayer().getName())
                        .build()));

        Sound leftSound = Sound.sound(
                Key.key(MinesSMP.config.getString("left-sound", "entity.villager.no")),
                Sound.Source.UI,
                1f,
                1f
        );
        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(leftSound, Sound.Emitter.self()));
    }
}
