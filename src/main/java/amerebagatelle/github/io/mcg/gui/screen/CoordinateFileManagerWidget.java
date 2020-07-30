package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.coordinates.CoordinatesManager;
import amerebagatelle.github.io.mcg.gui.MCGListWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CoordinateFileManagerWidget extends MCGListWidget<CoordinateFileManagerWidget.Entry> {
    private static Path currentDirectory = CoordinatesManager.getCoordinateDirectory();

    public CoordinateFileManagerWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight, int left) {
        super(minecraftClient, width, height, top, bottom, itemHeight, left);
        this.updateEntries(currentDirectory);
    }

    public void updateEntries(Path path) {
        this.clearEntries();
        this.setSelected(null);
        File[] files = new File(path.toString()).listFiles();
        if(files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    this.addEntry(new FolderEntry(file.getName()));
                } else {
                    if (file.getName().endsWith(".coordinates")) this.addEntry(new FileEntry(file.getName()));
                }
            }
        }
    }

    public void select(Entry entry) {
        this.setSelected(entry);
        this.ensureVisible(entry);
    }

    public boolean getSelectedEntry(Entry entry) {
        for (Entry possibleEntry : this.children()) {
            if (this.getSelected() == entry) {
                return true;
            }
        }
        return false;
    }

    public void openFile() {
        CoordinatesManagerScreen screen = new CoordinatesManagerScreen(new File(currentDirectory.toString(), (this.getSelected().getName())).toPath());
        client.openScreen(screen);
    }

    public void newFile() {
        client.openScreen(new CoordinateFileCreationScreen("File", currentDirectory));
    }

    public void newFolder() {
        client.openScreen(new CoordinateFileCreationScreen("Folder", currentDirectory));
    }

    public void removeFile() {
        new File(currentDirectory.toString(), this.getSelected().getName()).delete();
        updateEntries(currentDirectory);
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

    public void addToDirectory(String toAdd) {
        currentDirectory = Paths.get(currentDirectory.toString(), toAdd);
        updateEntries(currentDirectory);
    }

    public void backUpFolder() {
        currentDirectory = currentDirectory.getParent();
        updateEntries(currentDirectory);
    }

    @Override
    public int getRowWidth() {
        return this.width;
    }

    public class FileEntry extends CoordinateFileManagerWidget.Entry {

        public FileEntry(String name) {
            super(name);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            CoordinateFileManagerWidget.this.drawStringWithShadow(matrices, client.textRenderer, name.replace(".coordinates", ""), x + 5, y + entryHeight / 2 - 4, 16777215);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            CoordinateFileManagerWidget.this.select(this);
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return false;
        }

        public String getName() {
            return name;
        }
    }

    public class FolderEntry extends CoordinateFileManagerWidget.Entry {

        public FolderEntry(String name) {
            super(name);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            CoordinateFileManagerWidget.this.drawStringWithShadow(matrices, client.textRenderer, name + "/", x + 5, y + entryHeight / 2 - 4, 16777215);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (CoordinateFileManagerWidget.this.getSelectedEntry(this)) {
                CoordinateFileManagerWidget.this.addToDirectory(name);
            } else {
                CoordinateFileManagerWidget.this.select(this);
            }
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return false;
        }

        public String getName() {
            return name;
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinateFileManagerWidget.Entry> {
        public final String name;

        public Entry(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
