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

    public CoordinatesScreen() {
        super(new TranslatableText("screen.coordinates.title"));
    }

    @Override
    public void init() {
        super.init();
        coordinatesWidget = new CoordinatesWidget(this.minecraft, this.width/2-20, this.height, 40, this.height - 10, 15);
        this.children.add(coordinatesWidget);
        this.coordinatesWidget.setLeftPos(this.width/2+10);
        coordinateFileListWidget = new CoordinateFileListWidget(this.minecraft, this.width/2-10, this.height, 40, this.height-10, 15, coordinatesWidget);
        this.children.add(coordinateFileListWidget);
        this.coordinateFileListWidget.setLeftPos(10);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        coordinateFileListWidget.render(mouseX, mouseY, delta);
        coordinatesWidget.render(mouseX, mouseY, delta);
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
