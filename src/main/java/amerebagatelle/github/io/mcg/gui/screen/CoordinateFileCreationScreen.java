package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.gui.MCGButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CoordinateFileCreationScreen extends Screen {
    private TextFieldWidget fileNameWidget;
    private MCGButtonWidget confirmButton;
    private MCGButtonWidget cancelButton;

    private final String fileType;
    private final Path folderPath;

    public CoordinateFileCreationScreen(String fileType, Path folderPath) {
        super(new LiteralText("CoordinateFileCreationScreen"));
        this.fileType = fileType;
        this.folderPath = folderPath;
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        fileNameWidget = new TextFieldWidget(textRenderer, width/2-100, 100, 200, 20, new LiteralText("Name"));
        this.addChild(fileNameWidget);

        confirmButton = new MCGButtonWidget(width/2-50, height-100, 100, 20, new LiteralText("Confirm"), press -> confirm());
        this.addButton(confirmButton);
        cancelButton = new MCGButtonWidget(width/2-50, height-70, 100, 20, new LiteralText("Cancel"), press -> cancel());
        this.addButton(cancelButton);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        updateButtonStates();
        this.renderBackground(matrices);
        this.drawCenteredString(matrices, textRenderer, "New " + fileType, this.width/2, 50, 16777215);
        fileNameWidget.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void confirm() {
        try {
            if(fileType.equals("Folder")) {
                MCG.coordinatesManager.createFolder(Paths.get(folderPath.toString(), fileNameWidget.getText()));
            } else {
                MCG.coordinatesManager.initNewCoordinatesFile(Paths.get(folderPath.toString(), fileNameWidget.getText().endsWith(".coordinates") ? fileNameWidget.getText() : fileNameWidget.getText() + ".coordinates"));
            }
        } catch (IOException e) {
            MCG.logger.debug("Can't make new coordinates file.");
        } finally {
            client.openScreen(new CoordinateFileManager());
        }
    }

    private void cancel() {
        client.openScreen(new CoordinateFileManager());
    }

    private void updateButtonStates() {
        confirmButton.active = fileNameWidget.getText().length() != 0;
    }
}
