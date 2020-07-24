package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.coordinates.CoordinatesManager;
import amerebagatelle.github.io.mcg.gui.MCGListWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.io.File;
import java.nio.file.Path;

public class CoordinateFileManagerWidget extends MCGListWidget<CoordinateFileManagerWidget.Entry> {
    private static Path currentDirectory = CoordinatesManager.getCoordinateDirectory();

    public CoordinateFileManagerWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight, int left) {
        super(minecraftClient, width, height, top, bottom, itemHeight, left);
        this.updateEntries(currentDirectory);
    }

    public void updateEntries(Path path) {
        File[] files = new File(path.toString()).listFiles();
        if(files != null) {
            for(File file : files) {
                if(file.isDirectory()) {
                    this.addEntry(new FolderEntry(file.getName()));
                } else {
                    if(file.getName().endsWith(".coordinates")) this.addEntry(new FileEntry(file.getName()));
                }
            }
        }
    }

    public void newFile() {
        client.openScreen(new CoordinateFileCreationScreen("File"));
    }

    public void newFolder() {
        client.openScreen(new CoordinateFileCreationScreen("Folder"));
    }

    public boolean hasFileSelected() {
        return this.getSelected() instanceof FileEntry;
    }

    public boolean hasSelected() {
        return this.getSelected() != null;
    }

    public Path getCurrentDirectory() {
        return currentDirectory;
    }

    @Override
    public int getRowWidth() {
        return this.width;
    }

    public class FileEntry extends CoordinateFileManagerWidget.Entry {
        private final String name;

        public FileEntry(String name) {
            this.name = name;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            CoordinateFileManagerWidget.this.drawStringWithShadow(matrices, client.textRenderer, name.replace(".coordinates", ""), x + 5, y + entryHeight/2, 16777215);
        }
    }

    public class FolderEntry extends CoordinateFileManagerWidget.Entry {
        private final String name;

        public FolderEntry(String name) {
            this.name = name;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            CoordinateFileManagerWidget.this.drawStringWithShadow(matrices, client.textRenderer, name + "/", x + 5, y + entryHeight/2, 16777215);
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinateFileManagerWidget.Entry> {
    }
}
