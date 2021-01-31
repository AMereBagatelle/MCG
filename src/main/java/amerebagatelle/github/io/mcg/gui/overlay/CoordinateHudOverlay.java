package amerebagatelle.github.io.mcg.gui.overlay;

import amerebagatelle.github.io.mcg.coordinates.CoordinatesSet;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class CoordinateHudOverlay {
    public static final CoordinateHudOverlay INSTANCE = new CoordinateHudOverlay();
    private final MinecraftClient client = MinecraftClient.getInstance();
    private String currentCoordinate;

    public void render(MatrixStack matrixStack, int windowHeight) {
        DrawableHelper.drawStringWithShadow(matrixStack, client.textRenderer, currentCoordinate, 10, windowHeight - 30, 16777215);
    }

    public void setCurrentCoordinate(CoordinatesSet set) {
        this.currentCoordinate = String.format("%s @ %s %s %s", set.name, set.x, set.y, set.z);
    }

    public void clearCoordinate() {
        this.currentCoordinate = "";
    }
}
