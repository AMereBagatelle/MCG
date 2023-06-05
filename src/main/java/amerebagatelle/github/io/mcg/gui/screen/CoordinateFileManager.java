package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.Constants;
import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.coordinates.CoordinateRoot;
import amerebagatelle.github.io.mcg.gui.MCGButtonWidget;
import amerebagatelle.github.io.mcg.gui.overlay.CoordinateHudOverlay;
import amerebagatelle.github.io.mcg.gui.overlay.ErrorDisplayOverlay;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class CoordinateFileManager extends Screen {

    // Widgets/Buttons
    private CoordinateFileManagerWidget coordinateFileManagerWidget;
    private MCGButtonWidget openFile;
    private MCGButtonWidget returnFolder;
    private MCGButtonWidget newFile;
    private MCGButtonWidget newFolder;
    private MCGButtonWidget removeFile;
    private MCGButtonWidget clearOverlay;
    private MCGButtonWidget config;

    public CoordinateFileManager() {
        super(Text.literal("CoordinateFileManagerScreen"));
    }

    @Override
    public void init() {
        coordinateFileManagerWidget = new CoordinateFileManagerWidget(this.client, width / 3 * 2, height - 60, 40, this.height - 20, 15, 10, this, MCG.rootCoordinateFolder);
        this.addSelectableChild(coordinateFileManagerWidget);

        openFile = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, coordinateFileManagerWidget.getTop(), coordinateFileManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.button.openfile"), press -> coordinateFileManagerWidget.openFile());
        this.addDrawableChild(openFile);
        returnFolder = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, openFile.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.button.return"), press -> coordinateFileManagerWidget.backUpFolder());
        this.addDrawableChild(returnFolder);
        newFile = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, returnFolder.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.file.newfile"), press -> coordinateFileManagerWidget.newFile());
        this.addDrawableChild(newFile);
        newFolder = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, newFile.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.file.newfolder"), press -> coordinateFileManagerWidget.newFolder());
        this.addDrawableChild(newFolder);
        removeFile = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, newFolder.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.button.remove"), press -> coordinateFileManagerWidget.removeFile());
        this.addDrawableChild(removeFile);
        clearOverlay = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, removeFile.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.coordinate.clearoverlay"), press -> CoordinateHudOverlay.INSTANCE.clearCoordinate());
        this.addDrawableChild(clearOverlay);
        config = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, coordinateFileManagerWidget.getBottom() - 20, coordinateFileManagerWidget.getButtonWidth(), 20, Text.translatable("mcg.button.config"), press -> Objects.requireNonNull(client).setScreen(new ConfigScreen(this)));
        this.addDrawableChild(config);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        updateButtonStates();
        this.renderBackground(matrices);
        drawCenteredTextWithShadow(matrices, textRenderer, I18n.translate("mcg.file.managertitle"), width / 2, 10, 0xFFFFFF);
        drawTextWithShadow(matrices, textRenderer, String.format("%s" + MCG.rootCoordinateFolder.getRootRelativePath(coordinateFileManagerWidget.getCurrentFolder()), Formatting.BLUE), coordinateFileManagerWidget.getLeft(), coordinateFileManagerWidget.getTop() - 10, 0xFFFFFF);
        coordinateFileManagerWidget.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    public void updateButtonStates() {
        openFile.active = coordinateFileManagerWidget.hasFileSelected();
        removeFile.active = coordinateFileManagerWidget.hasSelected();
        returnFolder.active = !(coordinateFileManagerWidget.getCurrentFolder() instanceof CoordinateRoot);
    }

    @Override
    public void close() {
        MCG.managerScreenInstance = this;
        super.close();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    public void reportError(String error) {
        Constants.LOGGER.warn(error);
        ErrorDisplayOverlay.INSTANCE.addError(error);
    }
}
