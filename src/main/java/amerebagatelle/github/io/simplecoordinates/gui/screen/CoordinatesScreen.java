package amerebagatelle.github.io.simplecoordinates.gui.screen;

import amerebagatelle.github.io.simplecoordinates.SimpleCoordinates;
import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
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
    private final MinecraftClient client;
    public CoordinatesWidget coordinatesWidget;
    public ArrayList<String> selectedCoordinates;
    private final TextRenderer textRenderer;
    private final int textColor = 16777215;

    private ButtonWidget buttonWrite;
    private ButtonWidget buttonRefresh;
    private ButtonWidget buttonDelete;

    public CoordinatesScreen(MinecraftClient client) {
        super(new TranslatableText("screen.coordinates.title"));
        this.client = client;
        this.textRenderer = client.textRenderer;
    }

    @Override
    public void init() {
        super.init();
        ArrayList<ArrayList<String>> coordinatesList;
        int buttonY = this.height-36;
        int buttonStartX = this.width-500;
        this.buttonWrite = this.addButton(new ButtonWidget(buttonStartX, buttonY, 150, 20, I18n.translate("button.simplecoordinates.writecoordinate"), buttonWidget -> client.openScreen(new CreateCoordinateScreen(client, this))));
        this.buttonDelete = this.addButton(new ButtonWidget(buttonStartX+160, buttonY, 150, 20, I18n.translate("button.simplecoordinates.removecoordinate"), buttonWidget -> {
            try {
                CoordinatesManager.removeCoordinate(selectedCoordinates.get(0));
                this.refresh();
                this.selectedCoordinates = null;
            } catch (IOException e) {
                SimpleCoordinates.logger.error("Could not remove coordinate");
            }
        }));
        this.buttonRefresh = this.addButton(new ButtonWidget(buttonStartX+320, buttonY, 150, 20, I18n.translate("button.simplecoordinates.refresh"), buttonWidget -> this.refresh()));
        this.updateButtonStates();
        try {
            coordinatesList = CoordinatesManager.loadCoordinates();
            coordinatesWidget = new CoordinatesWidget(this, client, coordinatesList);
        } catch (IOException e) {
            SimpleCoordinates.logger.error(I18n.translate("return.simplecoordinates.coordinateloadfail"));
        }
        this.children.add(coordinatesWidget);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        if(coordinatesWidget != null) {
            this.renderDirtBackground(0);
            this.coordinatesWidget.render(mouseX, mouseY, delta);
            this.drawCenteredString(this.font, this.title.asFormattedString(), this.width / 2, 20, 16777215);
            super.render(mouseX, mouseY, delta);
            if (selectedCoordinates != null) {
                int coordinatesDrawY = this.height-36;
                int coordinatesDrawX = 10;
                String x = selectedCoordinates.get(1);
                String y = selectedCoordinates.get(2);
                String z = selectedCoordinates.get(3);
                this.drawString(textRenderer, "Name: " + selectedCoordinates.get(0), coordinatesDrawX, coordinatesDrawY - 20, textColor);
                this.drawString(textRenderer, "Coordinates:  " + x, coordinatesDrawX, coordinatesDrawY, textColor);
                this.drawString(textRenderer, y, coordinatesDrawX + textRenderer.getStringWidth("Coordinates:  ") + textRenderer.getStringWidth(x) + 20, coordinatesDrawY, textColor);
                this.drawString(textRenderer, z, coordinatesDrawX + textRenderer.getStringWidth("Coordinates:  ") + textRenderer.getStringWidth(x) + textRenderer.getStringWidth(y) + 40, coordinatesDrawY, textColor);
                this.drawString(textRenderer, "Details: " + selectedCoordinates.get(4), coordinatesDrawX, coordinatesDrawY + 20, textColor);
            }
        } else {
            client.openScreen(null);
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

    public void select(CoordinatesWidget.Entry entry, ArrayList<String> selectedCoordinates) {
        this.coordinatesWidget.setSelected(entry);
        this.selectedCoordinates = selectedCoordinates;
        this.updateButtonStates();
    }

    public void refresh() {
        client.openScreen(this);
    }

    public void updateButtonStates() {
        this.buttonDelete.active = selectedCoordinates != null;
    }
}
