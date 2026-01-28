/**
 * MinesSMP Service for Commands
 */

package com.boyninja1555.mines;

import com.boyninja1555.mines.lib.MinesCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;

import java.lang.reflect.InvocationTargetException;

public class MinesCommands {
    private final MinesSMP plugin;
    private final PaperCommandManager<CommandSourceStack> manager;
    private final AnnotationParser<CommandSourceStack> parser;

    public MinesCommands(MinesSMP plugin) {
        this.plugin = plugin;
        this.manager = PaperCommandManager.builder()
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(plugin);
        this.parser = new AnnotationParser<>(manager, CommandSourceStack.class);
    }

    public void register(Class<? extends MinesCommand> command) {
        try {
            parser.parse(command.getConstructor(MinesSMP.class).newInstance(plugin));
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            plugin.getLogger().severe("Could not register command! " + ex.getMessage());
        }
    }
}
