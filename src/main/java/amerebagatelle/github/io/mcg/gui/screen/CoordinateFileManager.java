package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.gui.MCGButtonWidget;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CoordinateFileManager extends Screen {
    public static Path currentDirectory = getCoordinateDirectory();

    // Widgets/Buttons
    private CoordinateFileManagerWidget coordinateFileManagerWidget;
    private MCGButtonWidget openFile;
    private MCGButtonWidget newFile;
    private MCGButtonWidget newFolder;
    private MCGButtonWidget removeFile;

    public CoordinateFileManager() {
        super(new LiteralText("CoordinateFileManagerScreen"));
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        coordinateFileManagerWidget = new CoordinateFileManagerWidget(this.client, width/3*2, height-60, 40, this.height-20, 15, 10);
        this.addChild(coordinateFileManagerWidget);
        coordinateFileManagerWidget.updateEntries(currentDirectory);
        openFile = new MCGButtonWidget(coordinateFileManagerWidget.getLeft()+coordinateFileManagerWidget.getWidth()+5, coordinateFileManagerWidget.getTop(), coordinateFileManagerWidget.getButtonWidth(), 30, new LiteralText("Open File"), press -> {

        });
        this.addButton(openFile);
        newFile = new MCGButtonWidget(coordinateFileManagerWidget.getLeft()+coordinateFileManagerWidget.getWidth()+5, openFile.y+openFile.getHeight()+5, coordinateFileManagerWidget.getButtonWidth(), 30, new LiteralText("New File"), press -> {

        });
        this.addButton(newFile);
        newFolder = new MCGButtonWidget(coordinateFileManagerWidget.getLeft()+coordinateFileManagerWidget.getWidth()+5, newFile.y+newFile.getHeight()+5, coordinateFileManagerWidget.getButtonWidth(), 30, new LiteralText("New Folder"), press -> {

        });
        this.addButton(newFolder);
        removeFile = new MCGButtonWidget(coordinateFileManagerWidget.getLeft()+coordinateFileManagerWidget.getWidth()+5, newFolder.y+newFolder.getHeight()+5, coordinateFileManagerWidget.getButtonWidth(), 30, new LiteralText("Remove File/Folder"), press -> {

        });
        this.addButton(removeFile);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.drawCenteredString(matrices, textRenderer, "Coordinate File Manager", width/2, 10, 16777215);
        this.drawStringWithShadow(matrices, textRenderer, String.format("%s" + currentDirectory.toString().substring(currentDirectory.toString().indexOf("coordinates")), Formatting.BLUE), coordinateFileManagerWidget.getLeft(), coordinateFileManagerWidget.getTop()-10, 16777215);
        coordinateFileManagerWidget.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    public static Path getCoordinateDirectory() {
        return Paths.get(FabricLoader.getInstance().getGameDir().toString(), "coordinates");
    }

    public void refreshEntries() {
        coordinateFileManagerWidget.updateEntries(currentDirectory);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
