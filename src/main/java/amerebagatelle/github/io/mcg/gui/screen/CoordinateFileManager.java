package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.gui.MCGButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class CoordinateFileManager extends Screen {

    // Widgets/Buttons
    private CoordinateFileManagerWidget coordinateFileManagerWidget;
    private MCGButtonWidget openFile;
    private MCGButtonWidget returnFolder;
    private MCGButtonWidget newFile;
    private MCGButtonWidget newFolder;
    private MCGButtonWidget removeFile;

    public CoordinateFileManager() {
        super(new LiteralText("CoordinateFileManagerScreen"));
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        coordinateFileManagerWidget = new CoordinateFileManagerWidget(this.client, width / 3 * 2, height - 60, 40, this.height - 20, 15, 10);
        this.addChild(coordinateFileManagerWidget);

        openFile = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, coordinateFileManagerWidget.getTop(), coordinateFileManagerWidget.getButtonWidth(), 20, new LiteralText("Open File"), press -> {
            coordinateFileManagerWidget.openFile();
        });
        this.addButton(openFile);
        returnFolder = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, openFile.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, new LiteralText("Return"), press -> {
            coordinateFileManagerWidget.backUpFolder();
        });
        this.addButton(returnFolder);
        newFile = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, returnFolder.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, new LiteralText("New File"), press -> {
            coordinateFileManagerWidget.newFile();
        });
        this.addButton(newFile);
        newFolder = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, newFile.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, new LiteralText("New Folder"), press -> {
            coordinateFileManagerWidget.newFolder();
        });
        this.addButton(newFolder);
        removeFile = new MCGButtonWidget(coordinateFileManagerWidget.getRight() + 5, newFolder.getBottom() + 5, coordinateFileManagerWidget.getButtonWidth(), 20, new LiteralText("Remove File/Folder"), press -> {
            coordinateFileManagerWidget.removeFile();
        });
        this.addButton(removeFile);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        updateButtonStates();
        this.renderBackground(matrices);
        drawCenteredString(matrices, textRenderer, "Coordinate File Manager", width / 2, 10, 16777215);
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
