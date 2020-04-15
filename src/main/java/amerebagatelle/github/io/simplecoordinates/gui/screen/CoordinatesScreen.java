package amerebagatelle.github.io.simplecoordinates.gui.screen;

import amerebagatelle.github.io.simplecoordinates.SimpleCoordinates;
import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import amerebagatelle.github.io.simplecoordinates.gui.widget.CoordinatesWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoordinatesScreen extends Screen {
    private MinecraftClient client;
    public CoordinatesWidget coordinatesWidget;

    public CoordinatesScreen(MinecraftClient client) {
        super(new TranslatableText("coordinates.title"));
        this.client = client;
    }

    @Override
    public void init() {
        super.init();
        ArrayList<ArrayList<String>> coordinatesList;
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
        this.renderBackground();
        this.coordinatesWidget.render(mouseX, mouseY, delta);
        this.drawCenteredString(this.font, this.title.asFormattedString(), this.width / 2, 20, 16777215);
        super.render(mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void select(CoordinatesWidget.Entry entry) {
        this.coordinatesWidget.setSelected(entry);
    }
}
