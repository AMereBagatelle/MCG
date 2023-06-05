package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.Constants;
import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.coordinates.Coordinate;
import amerebagatelle.github.io.mcg.coordinates.CoordinateFile;
import amerebagatelle.github.io.mcg.gui.MCGButtonWidget;
import amerebagatelle.github.io.mcg.gui.overlay.CoordinateHudOverlay;
import amerebagatelle.github.io.mcg.gui.overlay.ErrorDisplayOverlay;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.Objects;

public class CoordinatesManagerScreen extends Screen {
    private CoordinateManagerWidget coordinateManagerWidget;

    private final CoordinateFile file;

    private MCGButtonWidget newCoordinate;
    private MCGButtonWidget removeCoordinate;
    private MCGButtonWidget copyCoordinate;
    private MCGButtonWidget teleportToCoordinate;
    private MCGButtonWidget overlayCoordinate;
    private MCGButtonWidget clearOverlay;
    private MCGButtonWidget back;

    public CoordinatesManagerScreen(CoordinateFile file) {
        super(Text.literal("CoordinateManagerScreen"));
        this.file = file;
    }

    @Override
    public void init() {
        Objects.requireNonNull(client);

        coordinateManagerWidget = new CoordinateManagerWidget(client, this, width / 3 * 2, height - 60, 40, this.height - 20, 15, 10);
        coordinateManagerWidget.setFile(file);
        this.addSelectableChild(coordinateManagerWidget);
        newCoordinate = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, coordinateManagerWidget.getTop(), coordinateManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.coordinate.newcoordinate"), press -> coordinateManagerWidget.newCoordinate(this));
        this.addDrawableChild(newCoordinate);
        removeCoordinate = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, newCoordinate.getBottom() + 5, coordinateManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.coordinate.removecoordinate"), press -> coordinateManagerWidget.removeCoordinate());
        this.addDrawableChild(removeCoordinate);
        copyCoordinate = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, removeCoordinate.getBottom() + 5, coordinateManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.coordinate.copycoordinate"), press -> coordinateManagerWidget.copyCoordinate());
        this.addDrawableChild(copyCoordinate);
        teleportToCoordinate = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, copyCoordinate.getBottom() + 5, coordinateManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.coordinate.tp"), press -> coordinateManagerWidget.teleportToCoordinate());
        this.addDrawableChild(teleportToCoordinate);
        overlayCoordinate = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, teleportToCoordinate.getBottom() + 5, coordinateManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.coordinate.setoverlay"), press -> coordinateManagerWidget.setOverlayToSelected());
        this.addDrawableChild(overlayCoordinate);
        clearOverlay = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, overlayCoordinate.getBottom() + 5, coordinateManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.coordinate.clearoverlay"), press -> CoordinateHudOverlay.INSTANCE.clearCoordinate());
        this.addDrawableChild(clearOverlay);
        back = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, clearOverlay.getBottom() + 5, coordinateManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.button.back"), press -> client.setScreen(new CoordinateFileManager(MCG.rootCoordinateFolder)));
        this.addDrawableChild(back);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        updateButtonStates();
        this.renderBackground(matrices);
        drawCenteredTextWithShadow(matrices, textRenderer, I18n.translate("mcg.coordinate.coordinatesof") + file.getName().substring(0, file.getName().lastIndexOf(".")), width / 2, 10, 0xFFFFFF);
        coordinateManagerWidget.render(matrices, mouseX, mouseY, delta);

        // selected coordinate view
        if (coordinateManagerWidget.getSelectedOrNull() != null) {
            Coordinate set = ((CoordinateManagerWidget.CoordinateEntry) coordinateManagerWidget.getSelectedOrNull()).coordinate;
            int drawY = back.getBottom() + 20;
            drawTextWithShadow(matrices, textRenderer, set.name, coordinateManagerWidget.getRight() + 5, drawY, 0xFFFFFF);
            drawTextWithShadow(matrices, textRenderer, "X: " + set.x, coordinateManagerWidget.getRight() + 5, drawY + 15, 0xFFFFFF);
            drawTextWithShadow(matrices, textRenderer, "Y: " + set.y, coordinateManagerWidget.getRight() + 5, drawY + 25, 0xFFFFFF);
            drawTextWithShadow(matrices, textRenderer, "Z: " + set.z, coordinateManagerWidget.getRight() + 5, drawY + 35, 0xFFFFFF);
            drawTextWithShadow(matrices, textRenderer, set.description, coordinateManagerWidget.getRight() + 5, drawY + 50, 0xFFFFFF);

            Coordinate netherCoords = set.toNetherCoordinateSet();
            drawTextWithShadow(matrices, textRenderer, I18n.translate("mcg.coordinate.nethercoords"), coordinateManagerWidget.getRight() + 5, drawY + 85, 0xFFFFFF);
            drawTextWithShadow(matrices, textRenderer, "X: " + netherCoords.x, coordinateManagerWidget.getRight() + 5, drawY + 100, 0xFFFFFF);
            drawTextWithShadow(matrices, textRenderer, "Y: " + netherCoords.y, coordinateManagerWidget.getRight() + 5, drawY + 110, 0xFFFFFF);
            drawTextWithShadow(matrices, textRenderer, "Z: " + netherCoords.z, coordinateManagerWidget.getRight() + 5, drawY + 120, 0xFFFFFF);
        }
        super.render(matrices, mouseX, mouseY, delta);
        ErrorDisplayOverlay.INSTANCE.render(matrices, height);
    }

    public void refresh() {
        coordinateManagerWidget.refreshEntries();
    }

    public void updateButtonStates() {
        removeCoordinate.active = coordinateManagerWidget.getSelectedOrNull() != null;
        teleportToCoordinate.active = coordinateManagerWidget.getSelectedOrNull() != null && Objects.requireNonNull(Objects.requireNonNull(client).player).isCreativeLevelTwoOp();
        overlayCoordinate.active = coordinateManagerWidget.getSelectedOrNull() != null;
        copyCoordinate.active = coordinateManagerWidget.getSelectedOrNull() != null;
    }

    public CoordinateFile getFile() {
        return file;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void close() {
        MCG.managerScreenInstance = this;
        super.close();
    }

    public void reportError(String error) {
        Constants.LOGGER.warn(error);
        ErrorDisplayOverlay.INSTANCE.addError(error);
    }
}
