package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.coordinates.CoordinatesSet;
import amerebagatelle.github.io.mcg.gui.widget.CoordinateFileListWidget;
import amerebagatelle.github.io.mcg.gui.widget.CoordinatesWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.io.File;
import java.io.IOException;

public class CoordinatesScreen extends Screen {
    public CoordinateFileListWidget coordinateFileListWidget;
    public CoordinatesWidget coordinatesWidget;

    private ButtonWidget newCoordinateButton;
    private ButtonWidget deleteCoordinateButton;
    private ButtonWidget deleteFileButton;
    private ButtonWidget tpToCoordsButton;

    public CoordinatesScreen() {
        super(new TranslatableText("screen.coordinates.title"));
    }

    @Override
    public void init() {
        super.init();
        this.addButton(new ButtonWidget(10, this.height - 45, 20, 20, new LiteralText("/"), onPress -> {
            this.coordinateFileListWidget.moveDirectoryBack();
        }));
        this.addButton(new ButtonWidget(35, this.height - 45, 70, 20, new LiteralText("New File"), onPress -> {
            this.client.openScreen(new CreateFileScreen(this));
        }));
        this.addButton(new ButtonWidget(110, this.height - 45, 70, 20, new LiteralText("New Folder"), onPress -> {
            this.client.openScreen(new CreateFolderScreen(this));
        }));
        deleteFileButton = this.addButton(new ButtonWidget(185, this.height - 45, 100, 20, new LiteralText("Delete File"), onPress -> {
            if (coordinateFileListWidget.getSelected() != null) {
                new File(CoordinateFileListWidget.workingDirectory.toString() + "/" + CoordinatesWidget.coordinatesListName).delete();
            }
            this.refresh();
        }));
        newCoordinateButton = this.addButton(new ButtonWidget(this.width / 2 + 10, this.height - 45, 110, 20, new LiteralText("New/Edit Coordinate"), onPress -> {
            this.client.openScreen(new CreateCoordinateScreen(client, this, this.coordinatesWidget.getSelected()));
        }));
        deleteCoordinateButton = this.addButton(new ButtonWidget(this.width / 2 + 120, this.height - 45, 100, 20, new LiteralText("Delete Coordinate"), onPress -> {
            try {
                MCG.coordinatesManager.removeCoordinate(CoordinateFileListWidget.workingDirectory.toString() + "/" + CoordinatesWidget.coordinatesListName, coordinatesWidget.getSelected().getCoordinates());
            } catch (IOException | NullPointerException ignored) {
            }
            this.refresh();
        }));
        if (MinecraftClient.getInstance().player.isCreative() || MinecraftClient.getInstance().player.isSpectator()) {
            tpToCoordsButton = this.addButton(new ButtonWidget(this.width / 2 + 220, this.height - 45, 85, 20, new LiteralText("TP To Selected"), onPress -> {
                MinecraftClient mc = MinecraftClient.getInstance();
                if (mc.player.isCreative() || mc.player.isSpectator()) {
                    CoordinatesSet coordinateSet = coordinatesWidget.getSelected().getCoordinates();
                    mc.player.sendChatMessage(String.format("/tp %s %s %s", coordinateSet.x, coordinateSet.y, coordinateSet.z));
                }
            }));
        }
        coordinatesWidget = new CoordinatesWidget(this.client, this.width / 2 - 50, this.height, 40, this.height - 50, 15, this);
        this.children.add(coordinatesWidget);
        this.coordinatesWidget.setLeftPos(this.width / 2 + 10);
        coordinateFileListWidget = new CoordinateFileListWidget(this.client, this.width / 2 - 40, this.height, 40, this.height - 50, 15, coordinatesWidget, this);
        this.children.add(coordinateFileListWidget);
        this.coordinateFileListWidget.setLeftPos(10);
        updateButtonStates();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrixStack);
        coordinateFileListWidget.render(matrixStack, mouseX, mouseY, delta);
        coordinatesWidget.render(matrixStack, mouseX, mouseY, delta);
        super.render(matrixStack, mouseX, mouseY, delta);
        updateButtonStates();
    }

    public void refresh() {
        this.client.openScreen(new CoordinatesScreen());
    }

    public void updateButtonStates() {
        deleteFileButton.active = coordinateFileListWidget.getSelected() != null;
        newCoordinateButton.active = coordinateFileListWidget.getSelected() != null && coordinateFileListWidget.getSelected() instanceof CoordinateFileListWidget.CoordinateFileEntry;
        deleteCoordinateButton.active = coordinatesWidget.getSelected() != null;
        if (tpToCoordsButton != null) tpToCoordsButton.active = coordinatesWidget.getSelected() != null;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}