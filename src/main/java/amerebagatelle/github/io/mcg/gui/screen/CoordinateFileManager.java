package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.gui.MCGButtonWidget;
import amerebagatelle.github.io.mcg.gui.overlay.CoordinateHudOverlay;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class CoordinateFileManager extends Screen {

    // Widgets/Buttons
    private CoordinateFileManagerWidget coordinateFileManagerWidget;
    private MCGButtonWidget openFile;
    private MCGButtonWidget returnFolder;
    private MCGButtonWidget newFile;
    private MCGButtonWidget newFolder;
    private MCGButtonWidget removeFile;
    private MCGButtonWidget clearOverlay;

    public CoordinateFileManager() {
        super(new LiteralText("CoordinateFileManagerScreen"));
    }

    @Override
    public void init() {
        coordinateFileManagerWidget = new CoordinateFileManagerWidget(this.client, width / 3 * 2, height - 60, 40, this.height - 20, 15, 10);
        this.addSelectableChild(coordinateFileManagerWidget);

        openFile = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, coordinateFileManagerWidget.getTop(), coordinateFileManagerWidget.getButtonWidth(), 20, new TranslatableText("mcg.button.openfile"), press -> coordinateFileManagerWidget.openFile());
        this.addDrawableChild(openFile);
        returnFolder = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, openFile.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, new TranslatableText("mcg.button.return"), press -> coordinateFileManagerWidget.backUpFolder());
        this.addSelectableChild(returnFolder);
        newFile = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, returnFolder.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, new TranslatableText("mcg.file.newfile"), press -> coordinateFileManagerWidget.newFile());
        this.addDrawableChild(newFile);
        newFolder = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, newFile.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, new TranslatableText("mcg.file.newfolder"), press -> coordinateFileManagerWidget.newFolder());
        this.addDrawableChild(newFolder);
        removeFile = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, newFolder.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, new TranslatableText("mcg.button.remove"), press -> coordinateFileManagerWidget.removeFile());
        this.addDrawableChild(removeFile);
        clearOverlay = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, removeFile.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, new TranslatableText("mcg.coordinate.clearoverlay"), press -> CoordinateHudOverlay.INSTANCE.clearCoordinate());
        this.addDrawableChild(clearOverlay);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        updateButtonStates();
        this.renderBackground(matrices);
        drawCenteredText(matrices, textRenderer, I18n.translate("mcg.file.managertitle"), width / 2, 10, 16777215);
        drawStringWithShadow(matrices, textRenderer, String.format("%s" + coordinateFileManagerWidget.getCurrentDirectory().toString().substring(coordinateFileManagerWidget.getCurrentDirectory().toString().indexOf("coordinates")), Formatting.BLUE), coordinateFileManagerWidget.getLeft(), coordinateFileManagerWidget.getTop() - 10, 16777215);
        coordinateFileManagerWidget.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    public void updateButtonStates() {
        openFile.active = coordinateFileManagerWidget.hasFileSelected();
        removeFile.active = coordinateFileManagerWidget.hasSelected();
        returnFolder.active = !coordinateFileManagerWidget.getCurrentDirectory().endsWith("coordinates");
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
