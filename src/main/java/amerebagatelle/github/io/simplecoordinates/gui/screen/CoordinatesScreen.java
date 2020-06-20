package amerebagatelle.github.io.simplecoordinates.gui.screen;

import amerebagatelle.github.io.simplecoordinates.SimpleCoordinates;
import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import amerebagatelle.github.io.simplecoordinates.gui.widget.CoordinateFileListWidget;
import amerebagatelle.github.io.simplecoordinates.gui.widget.CoordinatesWidget;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableText;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class CoordinatesScreen extends Screen {
    public CoordinateFileListWidget coordinateFileListWidget;
    public CoordinatesWidget coordinatesWidget;

    private ButtonWidget newCoordinateButton;
    private ButtonWidget deleteCoordinateButton;
    private ButtonWidget deleteFileButton;

    public CoordinatesScreen() {
        super(new TranslatableText("screen.coordinates.title"));
    }

    @Override
    public void init() {
        super.init();
        this.addButton(new ButtonWidget(10, this.height-45, 20, 20, "/", onPress -> {
            this.coordinateFileListWidget.moveDirectoryBack();
        }));
        this.addButton(new ButtonWidget(35, this.height-45, 70, 20, "New File", onPress -> {
            this.minecraft.openScreen(new CreateFileScreen(this));
        }));
        this.addButton(new ButtonWidget(110, this.height-45, 70, 20, "New Folder", onPress -> {
            this.minecraft.openScreen(new CreateFolderScreen(this));
        }));
        deleteFileButton = this.addButton(new ButtonWidget(185, this.height-45, 100, 20, "Delete File", onPress -> {
            if(coordinateFileListWidget.getSelected() != null) {
                new File(coordinateFileListWidget.workingDirectory.toString() + "/" + coordinatesWidget.coordinatesListName).delete();
            }
            this.refresh();
        }));
        newCoordinateButton = this.addButton(new ButtonWidget(this.width/2+10, this.height-45, 110, 20, "New/Edit Coordinate", onPress -> {
            this.minecraft.openScreen(new CreateCoordinateScreen(minecraft, this, this.coordinatesWidget.getSelected()));
        }));
        deleteCoordinateButton = this.addButton(new ButtonWidget(this.width/2+125, this.height-45, 100, 20, "Delete Coordinate", onPress -> {
            try {
                SimpleCoordinates.coordinatesManager.removeCoordinate(coordinateFileListWidget.workingDirectory.toString() + "/" + coordinatesWidget.coordinatesListName, coordinatesWidget.getSelected().getCoordinates());
            } catch (IOException ignored) {
            }
            this.refresh();
        }));
        // TODO Add button for TP to coordinate if in creative
        coordinatesWidget = new CoordinatesWidget(this.minecraft, this.width/2-50, this.height, 40, this.height - 50, 15, this);
        this.children.add(coordinatesWidget);
        this.coordinatesWidget.setLeftPos(this.width/2+10);
        coordinateFileListWidget = new CoordinateFileListWidget(this.minecraft, this.width/2-40, this.height, 40, this.height-50, 15, coordinatesWidget, this);
        this.children.add(coordinateFileListWidget);
        this.coordinateFileListWidget.setLeftPos(10);
        updateButtonStates();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        coordinateFileListWidget.render(mouseX, mouseY, delta);
        coordinatesWidget.render(mouseX, mouseY, delta);
        super.render(mouseX, mouseY, delta);
        updateButtonStates();
    }

    public void refresh() {
        this.minecraft.openScreen(new CoordinatesScreen());
    }

    public void updateButtonStates() {
        if(coordinateFileListWidget.getSelected() != null) {
            deleteFileButton.active = true;
        } else {
            deleteFileButton.active = false;
        }
        if(coordinateFileListWidget.getSelected() != null && coordinateFileListWidget.getSelected() instanceof CoordinateFileListWidget.CoordinateFileEntry) {
            newCoordinateButton.active = true;
        } else {
            newCoordinateButton.active = false;
        }
        if(coordinatesWidget.getSelected() != null) {
            deleteCoordinateButton.active = true;
        } else {
            deleteCoordinateButton.active = false;
        }
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
