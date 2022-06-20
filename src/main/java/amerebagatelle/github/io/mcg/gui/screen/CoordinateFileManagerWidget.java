package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.coordinates.CoordinatesManager;
import amerebagatelle.github.io.mcg.gui.MCGListWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

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

    public boolean isEntrySelected(Entry entry) {
        return this.getSelectedOrNull() == entry;
    }

    public void openFile() {
        CoordinatesManagerScreen screen = new CoordinatesManagerScreen(new File(currentDirectory.toString(), (Objects.requireNonNull(this.getSelectedOrNull()).getName())).toPath());
        client.setScreen(screen);
    }

    public void newFile() {
        client.setScreen(new CoordinateFileCreationScreen("file", currentDirectory));
    }

    public void newFolder() {
        client.setScreen(new CoordinateFileCreationScreen("folder", currentDirectory));
    }

    public void removeFile() {
        //noinspection ResultOfMethodCallIgnored
        new File(currentDirectory.toString(), Objects.requireNonNull(this.getSelectedOrNull()).getName()).delete();
        updateEntries(currentDirectory);
    }

    public boolean hasFileSelected() {
        return this.getSelectedOrNull() instanceof FileEntry;
    }

    public boolean hasSelected() {
        return this.getSelectedOrNull() != null;
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
            drawStringWithShadow(matrices, client.textRenderer, name.replace(".coordinates", ""), x + 5, y + entryHeight / 2 - 4, 16777215);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (CoordinateFileManagerWidget.this.isEntrySelected(this)) {
                openFile();
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

        @Override
        public Text getNarration() {
            return Text.literal(name.replace(".coordinates", ""));
        }
    }

    public class FolderEntry extends CoordinateFileManagerWidget.Entry {

        public FolderEntry(String name) {
            super(name);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            drawStringWithShadow(matrices, client.textRenderer, name + "/", x + 5, y + entryHeight / 2 - 4, 16777215);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (CoordinateFileManagerWidget.this.isEntrySelected(this)) {
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

        @Override
        public Text getNarration() {
            return Text.literal(name);
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
