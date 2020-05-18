package amerebagatelle.github.io.simplecoordinates.gui.screen;

import amerebagatelle.github.io.simplecoordinates.SimpleCoordinates;
import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import amerebagatelle.github.io.simplecoordinates.gui.widget.CoordinateFileListWidget;
import amerebagatelle.github.io.simplecoordinates.gui.widget.CoordinatesWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableText;

import java.io.IOException;
import java.util.ArrayList;

public class CoordinatesScreen extends Screen {
    public CoordinateFileListWidget coordinateFileListWidget;
    public CoordinatesWidget coordinatesWidget;
    public ArrayList<String> selectedCoordinates;
    private final int textColor = 16777215;

    private ButtonWidget buttonWrite;
    private ButtonWidget buttonRefresh;
    private ButtonWidget buttonDelete;

    public CoordinatesScreen() {
        super(new TranslatableText("screen.coordinates.title"));
    }

    @Override
    public void init() {
        super.init();
        coordinateFileListWidget = new CoordinateFileListWidget(this.minecraft, this.width, this.height, 40, this.height-10, 20);
        this.children.add(coordinateFileListWidget);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderDirtBackground(0);
        coordinateFileListWidget.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void select(CoordinatesWidget.Entry entry, ArrayList<String> selectedCoordinates) {
        this.coordinatesWidget.setSelected(entry);
        this.selectedCoordinates = selectedCoordinates;
        this.updateButtonStates();
    }

    public void refresh() {
        minecraft.openScreen(this);
    }

    public void updateButtonStates() {
        this.buttonDelete.active = selectedCoordinates != null;
    }
}
