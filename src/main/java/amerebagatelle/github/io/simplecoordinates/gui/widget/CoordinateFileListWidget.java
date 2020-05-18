package amerebagatelle.github.io.simplecoordinates.gui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

public class CoordinateFileListWidget extends AlwaysSelectedEntryListWidget<CoordinateFileListWidget.Entry> {

    public CoordinateFileListWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight) {
        super(minecraftClient, width, height, top, bottom, itemHeight);
    }

    public class CoordinateFileEntry extends CoordinateFileListWidget.Entry {
        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
        }
    }

    public class CoordinateFolderEntry extends CoordinateFileListWidget.Entry {

        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {

        }
    }

    public abstract class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinateFileListWidget.Entry>{
        public Entry() {
        }
    }
}
