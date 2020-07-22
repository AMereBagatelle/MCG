package amerebagatelle.github.io.mcg.gui.screen;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CoordinateFileManager extends Screen {
    public static Path currentPath = getCoordinateDirectory();

    public CoordinateFileManager() {
        super(new LiteralText("CoordinateFileManagerScreen"));
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
    }

    public static Path getCoordinateDirectory() {
        return Paths.get(FabricLoader.getInstance().getGameDir().toString(), "coordinates");
    }
}
