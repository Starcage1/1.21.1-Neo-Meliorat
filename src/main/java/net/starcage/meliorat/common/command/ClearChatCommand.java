package net.starcage.meliorat.common.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.starcage.meliorat.network.ClearChatPayload;

public class ClearChatCommand {

    public static void register(
            CommandDispatcher<CommandSourceStack> dispatcher
    ) {

        dispatcher.register(
                Commands.literal("clearchat")
                        .executes(context -> {

                            ServerPlayer player =
                                    context.getSource()
                                            .getPlayerOrException();

                            PacketDistributor.sendToPlayer(
                                    player,
                                    new ClearChatPayload()
                            );

                            return 1;
                        })
        );
    }
}