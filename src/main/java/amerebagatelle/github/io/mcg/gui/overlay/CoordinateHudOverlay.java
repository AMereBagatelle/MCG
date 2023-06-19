package amerebagatelle.github.io.mcg.gui.overlay;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.coordinates.Coordinate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class CoordinateHudOverlay {
    public static final CoordinateHudOverlay INSTANCE = new CoordinateHudOverlay();
    private final MinecraftClient client = MinecraftClient.getInstance();
    private String currentCoordinate;

    public void render(DrawContext context) {
        context.drawTextWithShadow(client.textRenderer, currentCoordinate, MCG.config.overlayX, MCG.config.overlayY, 0xFFFFFF);
    }

    public void setCurrentCoordinate(Coordinate set) {
        this.currentCoordinate = MCG.config.overlayFormat
                .replace("%name", set.name)
                .replace("%x", Integer.toString(set.x))
                .replace("%y", Integer.toString(set.y))
                .replace("%z", Integer.toString(set.z))
                .replace("%desc", set.description);
    }

    public void clearCoordinate() {
        this.currentCoordinate = "";
    }
}
