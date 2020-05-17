package amerebagatelle.github.io.simplecoordinates.gui.widget;

import amerebagatelle.github.io.simplecoordinates.gui.screen.CoordinatesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.render.Tessellator;

import java.util.ArrayList;

public class CoordinatesWidget extends AlwaysSelectedEntryListWidget<CoordinatesWidget.Entry> {
    private final CoordinatesScreen gui;

    public CoordinatesWidget(CoordinatesScreen gui, MinecraftClient client, ArrayList<ArrayList<String>> coordinateList) {
        super(client, gui.width, gui.height, 40, gui.height-64, 20);
        this.gui = gui;
    }

    public void setSelected(CoordinatesWidget.Entry entry) {
        super.setSelected(entry);
        this.ensureVisible(entry);
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 100;
    }

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 100;
    }

    @Override
    protected boolean isFocused() {
        return this.gui.getFocused() == this;
    }

    public class CoordinateListEntry extends CoordinatesWidget.Entry {
        public String name;
        public String x, y, z;
        private final CoordinatesScreen gui;

        public CoordinateListEntry(CoordinatesScreen gui) {
            this.gui = gui;
        }

        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            TextRenderer textRenderer = CoordinatesWidget.this.minecraft.textRenderer;
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            //this.gui.select(this, this.coordinateList);
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return false;
        }
    }

    public abstract class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinatesWidget.Entry>{
        public Entry() {
        }
    }
}
