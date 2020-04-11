package amerebagatelle.github.io.simplecoordinates;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.security.Key;

@Environment(EnvType.CLIENT)
public class SimpleCoordinates implements ClientModInitializer {
    public static final Logger logger = LogManager.getLogger();

    @Override
    public void onInitializeClient() {
        logger.info("Gathering your coordinates...");

        CoordinatesManager.initCoordinates();
        KeybindHandler.initKeybinds();
        KeybindHandler.registerKeybindActions();
    }
}
