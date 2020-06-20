package amerebagatelle.github.io.simplecoordinates.gui.screen;

import amerebagatelle.github.io.simplecoordinates.SimpleCoordinates;
import amerebagatelle.github.io.simplecoordinates.gui.widget.CoordinateFileListWidget;
import amerebagatelle.github.io.simplecoordinates.gui.widget.CoordinatesWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.TranslatableText;

import java.io.File;
import java.io.IOException;

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
                new File(CoordinateFileListWidget.workingDirectory.toString() + "/" + CoordinatesWidget.coordinatesListName).delete();
            }
            this.refresh();
        }));
        newCoordinateButton = this.addButton(new ButtonWidget(this.width/2+10, this.height-45, 110, 20, "New/Edit Coordinate", onPress -> {
            this.minecraft.openScreen(new CreateCoordinateScreen(minecraft, this, this.coordinatesWidget.getSelected()));
        }));
        deleteCoordinateButton = this.addButton(new ButtonWidget(this.width/2+125, this.height-45, 100, 20, "Delete Coordinate", onPress -> {
            try {
                SimpleCoordinates.coordinatesManager.removeCoordinate(CoordinateFileListWidget.workingDirectory.toString() + "/" + CoordinatesWidget.coordinatesListName, coordinatesWidget.getSelected().getCoordinates());
            } catch (IOException | NullPointerException ignored) {
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
        deleteFileButton.active = coordinateFileListWidget.getSelected() != null;
        newCoordinateButton.active = coordinateFileListWidget.getSelected() != null && coordinateFileListWidget.getSelected() instanceof CoordinateFileListWidget.CoordinateFileEntry;
        deleteCoordinateButton.active = coordinatesWidget.getSelected() != null;
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
