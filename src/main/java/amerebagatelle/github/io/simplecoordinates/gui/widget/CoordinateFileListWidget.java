package amerebagatelle.github.io.simplecoordinates.gui.widget;

import amerebagatelle.github.io.simplecoordinates.SimpleCoordinates;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.util.Identifier;

import java.io.File;

public class CoordinateFileListWidget extends AlwaysSelectedEntryListWidget<CoordinateFileListWidget.Entry> {
    public final Identifier TEXTURE = new Identifier("afkpeace/textures/file_icons.png");

    public String workingDirectory = SimpleCoordinates.coordinatesManager.coordinatesFolder;

    public CoordinateFileListWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight) {
        super(minecraftClient, width, height, top, bottom, itemHeight);
        this.generateFileList();
        this.centerListVertically = false;
    }

    public void changeWorkingDirectory(String directory) {
        this.clearEntries();
        workingDirectory = directory;
        this.generateFileList();
    }

    public void generateFileList() {
        File cwdFile = new File(workingDirectory);
        File[] filesInside = cwdFile.listFiles();
        for (File file : filesInside) {
            if(file.isDirectory()) {
                this.addEntry(new CoordinateFolderEntry(file.getName()));
            } else {
                this.addEntry(new CoordinateFileEntry(file.getName()));
            }
        }
    }

    public class CoordinateFileEntry extends CoordinateFileListWidget.Entry {
        public String name;

        public CoordinateFileEntry(String name) {
            this.name = name;
        }

        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            CoordinateFileListWidget.this.drawCenteredString(CoordinateFileListWidget.this.minecraft.textRenderer, this.name, x, y, 16777215);
        }
    }

    public class CoordinateFolderEntry extends CoordinateFileListWidget.Entry {
        public String name;

        public CoordinateFolderEntry(String name) {
            this.name = name;
        }

        @Override
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            CoordinateFileListWidget.this.drawCenteredString(CoordinateFileListWidget.this.minecraft.textRenderer, this.name, x+width/2, y, 16777215);

        }
    }

    public abstract class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinateFileListWidget.Entry>{
        public Entry() {
        }
    }
}
