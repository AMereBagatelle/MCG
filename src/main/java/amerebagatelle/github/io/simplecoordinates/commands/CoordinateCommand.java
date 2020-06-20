package amerebagatelle.github.io.simplecoordinates.commands;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import amerebagatelle.github.io.simplecoordinates.gui.screen.CoordinatesScreen;
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

@Environment(EnvType.CLIENT)
public class CoordinateCommand implements ClientCommandPlugin {

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> commandDispatcher) {
        LiteralArgumentBuilder<CottonClientCommandSource> coord = literal("coord")
                .executes(ctx -> {
                    MinecraftClient.getInstance().openScreen(new CoordinatesScreen());
                   return 1;
                });

        commandDispatcher.register(coord);
    }
}
