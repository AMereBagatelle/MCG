package amerebagatelle.github.io.mcg;

import amerebagatelle.github.io.mcg.coordinates.CoordinateRoot;
import amerebagatelle.github.io.mcg.gui.screen.CoordinateFileManager;
import amerebagatelle.github.io.mcg.utils.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;

@Environment(EnvType.CLIENT)
public class MCG implements ClientModInitializer {
    public static CoordinateRoot rootCoordinateFolder;

    public static final Config config = new Config();

    public static KeyBinding binding;
    public static Screen managerScreenInstance;

    @Override
    public void onInitializeClient() {
        Constants.LOGGER.info("Gathering your coordinates...");

        try {
            rootCoordinateFolder = new CoordinateRoot(FabricLoader.getInstance().getGameDir().resolve("coordinates"));
        } catch (IOException e) {
            Constants.LOGGER.error("Failed to create root coordinates folder.");
            throw new RuntimeException(e);
        }

        managerScreenInstance = new CoordinateFileManager(rootCoordinateFolder);

        binding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "mcg.keybinding.main",
                GLFW.GLFW_KEY_Y,
                "MCG"
        ));
    }
}
