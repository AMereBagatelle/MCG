package amerebagatelle.github.io.simplecoordinates.commands;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.TranslatableText;

import java.io.IOException;

import static io.github.cottonmc.clientcommands.ArgumentBuilders.*;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.StringArgumentType.string;

public class RemoveCoordinateCommand implements ClientCommandPlugin {
    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> commandDispatcher) {
        LiteralArgumentBuilder<CottonClientCommandSource> removeCoordinate = literal("removecoordinate").
                then(argument("coordinateKey", string()).
                    executes(RemoveCoordinateCommand::run));
        commandDispatcher.register(removeCoordinate);
    }

    private static int run(CommandContext<CottonClientCommandSource> ctx) {
        MinecraftClient mc = MinecraftClient.getInstance();
        try {
            CoordinatesManager.removeCoordinate(StringArgumentType.getString(ctx, "coordinateKey"));
            mc.inGameHud.addChatMessage(MessageType.SYSTEM, new TranslatableText("return.simplecoordinates.coordinateremovesuccess"));
            return 1;
        } catch (IOException e) {
            mc.inGameHud.addChatMessage(MessageType.SYSTEM, new TranslatableText("return.simplecoordinates.coordinateremovefail"));
            return 0;
        }
    }
}
