package amerebagatelle.github.io.simplecoordinates;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesSet;
import amerebagatelle.github.io.simplecoordinates.gui.screen.CoordinatesScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class SimpleCoordinates implements ClientModInitializer {
    private static FabricKeyBinding debug;
    public static final Logger logger = LogManager.getLogger();
    public static final CoordinatesManager coordinatesManager = new CoordinatesManager();

    @Override
    public void onInitializeClient() {
        logger.info("Gathering your coordinates...");

        coordinatesManager.initCoordinatesFolder();

        FabricKeyBinding debug = FabricKeyBinding.Builder.create(
                new Identifier("test"),
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                "Miscellaneous"
        ).build();

        KeyBindingRegistry.INSTANCE.register(debug);

        ClientTickCallback.EVENT.register(e -> {
            if(debug.wasPressed()) MinecraftClient.getInstance().openScreen(new CoordinatesScreen());
        });
    }
}
