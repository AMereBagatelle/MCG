package amerebagatelle.github.io.simplecoordinates.commands;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;
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
    }

    private static int addCoordDetails(CommandContext<CottonClientCommandSource> ctx) {
        return 0;
    }

    private static int addCoord(CommandContext<CottonClientCommandSource> ctx) {
        return 0;
    }

    private static int removeCoord(CommandContext<CottonClientCommandSource> ctx) {
        return 0;
    }
}
