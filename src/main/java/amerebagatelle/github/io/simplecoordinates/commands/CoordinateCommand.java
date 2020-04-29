package amerebagatelle.github.io.simplecoordinates.commands;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinateSet;
import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;

import static io.github.cottonmc.clientcommands.ArgumentBuilders.*;
import static com.mojang.brigadier.arguments.StringArgumentType.string;

public class CoordinateCommand implements ClientCommandPlugin {

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> commandDispatcher) {
        LiteralArgumentBuilder<CottonClientCommandSource> coordinateCommand = literal("coord").
            then(literal("add").
                then(argument("name", string()).
                        then(argument("details", string()).
                                executes(CoordinateCommand::addCoordDetails)).
                        executes(CoordinateCommand::addCoord))).
            then(literal("del").
                then(argument("coordinateKey", string()).
                        executes(CoordinateCommand::removeCoord)));
        commandDispatcher.register(coordinateCommand);
    }

    @Environment(EnvType.CLIENT)
    private static int addCoordDetails(CommandContext<CottonClientCommandSource> ctx) {
        MinecraftClient mc = MinecraftClient.getInstance();
        BlockPos coordinate = mc.player.getBlockPos();
        try {
            CoordinatesManager.writeToCoordinates(new CoordinateSet(StringArgumentType.getString(ctx, "name"), coordinate.getX(), coordinate.getY(), coordinate.getZ(), StringArgumentType.getString(ctx, "details")));
            mc.inGameHud.addChatMessage(MessageType.SYSTEM, new TranslatableText("return.simplecoordinates.coordinatewritesuccess"));
            return 1;
        } catch (IOException e) {
            mc.inGameHud.addChatMessage(MessageType.SYSTEM, new TranslatableText("return.simplecoordinates.coordinatewritefail"));
            return 0;
        }
    }

    @Environment(EnvType.CLIENT)
    private static int addCoord(CommandContext<CottonClientCommandSource> ctx) {
        MinecraftClient mc = MinecraftClient.getInstance();
        BlockPos coordinate = mc.player.getBlockPos();
        try {
            CoordinatesManager.writeToCoordinates(new CoordinateSet(StringArgumentType.getString(ctx, "name"), coordinate.getX(), coordinate.getY(), coordinate.getZ(), ""));
            mc.inGameHud.addChatMessage(MessageType.SYSTEM, new TranslatableText("return.simplecoordinates.coordinatewritesuccess"));
            return 1;
        } catch (IOException e) {
            mc.inGameHud.addChatMessage(MessageType.SYSTEM, new TranslatableText("return.simplecoordinates.coordinatewritefail"));
            return 0;
        }
    }

    @Environment(EnvType.CLIENT)
    private static int removeCoord(CommandContext<CottonClientCommandSource> ctx) {
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
