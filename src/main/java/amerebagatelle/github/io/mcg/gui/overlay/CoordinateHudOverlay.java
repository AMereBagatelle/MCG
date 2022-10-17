package amerebagatelle.github.io.mcg.gui.overlay;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.coordinates.CoordinatesSet;
import amerebagatelle.github.io.mcg.utils.CompassUtil;
import com.google.common.base.Strings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CoordinateHudOverlay {
    public static final CoordinateHudOverlay INSTANCE = new CoordinateHudOverlay();
    private final MinecraftClient client = MinecraftClient.getInstance();
    private String currentCoordinate;
    private final ItemStack compass = new ItemStack(Items.COMPASS);

    public void render(MatrixStack matrixStack) {
        DrawableHelper.drawStringWithShadow(matrixStack, client.textRenderer, currentCoordinate, MCG.config.overlayX, MCG.config.overlayY, 16777215);
        if(MCG.config.showCompass && !Strings.isNullOrEmpty(currentCoordinate)) {
            client.getItemRenderer().renderInGuiWithOverrides(compass, MCG.config.overlayX - 20, MCG.config.overlayY - 5);
        }
    }

    public void setCurrentCoordinate(CoordinatesSet set) {
        if(MCG.config.showCompass) {
            CompassUtil.setPosition(compass, set);
        }
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
