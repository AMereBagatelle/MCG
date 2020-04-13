package amerebagatelle.github.io.simplecoordinates.gui.widget;

import amerebagatelle.github.io.simplecoordinates.SimpleCoordinates;
import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import amerebagatelle.github.io.simplecoordinates.gui.screen.CoordinatesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.options.ControlsListWidget;
import net.minecraft.client.gui.widget.ElementListWidget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoordinatesWidget extends ElementListWidget<CoordinatesWidget.Entry> {
    private ArrayList<ArrayList<String>> coordinatesList;

    public CoordinatesWidget(CoordinatesScreen gui, MinecraftClient client, ArrayList<ArrayList<String>> coordinateList) {
        super(client, gui.width, gui.height/2, 40, gui.height, 20);
        this.coordinatesList = coordinateList;
        coordinatesList.forEach(coordinate -> {
            this.addEntry(new CoordinatesWidget.CoordinateListEntry(coordinate.get(0), coordinate.get(1), coordinate.get(2), coordinate.get(3)));
        });
    }

    public class CoordinateListEntry extends CoordinatesWidget.Entry {
        public String name;
        public String x, y, z;

        public CoordinateListEntry(final String name, final String x, final String y, final String z) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            TextRenderer textRenderer = CoordinatesWidget.this.minecraft.textRenderer;
            int color = 16777215;
            textRenderer.draw(this.name, x, y, color);
            textRenderer.draw(this.x, x+50, y, color);
            textRenderer.draw(this.y, x+100, y, color);
            textRenderer.draw(this.z, x+150, y, color);
        }

        @Override
        public List<? extends Element> children() {
            return null;
        }
    }

    public abstract class Entry extends ElementListWidget.Entry<CoordinatesWidget.Entry>{
        public Entry() {
        }
    }
}
