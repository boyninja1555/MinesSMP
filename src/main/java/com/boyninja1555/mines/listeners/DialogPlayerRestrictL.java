/**
 * I'm sure this can be exploited... :D
 */

package com.boyninja1555.mines.listeners;

import com.boyninja1555.mines.MinesSMP;
import com.boyninja1555.mines.lib.CancelEventRef;
import com.boyninja1555.mines.lib.DialogPlayer;
import com.boyninja1555.mines.lib.MinesListener;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class DialogPlayerRestrictL extends MinesListener {

    public DialogPlayerRestrictL(MinesSMP plugin) {
        super(plugin);
    }

    private void inDialogCheck(UUID playerId, CancelEventRef canceller) {
        if (!DialogPlayer.playersInDialog.contains(playerId))
            return;

        canceller.cancelEvent();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        inDialogCheck(
                event.getPlayer().getUniqueId(),
                () -> event.setCancelled(true)
        );
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        inDialogCheck(
                event.getPlayer().getUniqueId(),
                () -> event.setCancelled(true)
        );
    }
}
