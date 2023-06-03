package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.coordinates.CoordinateFile;
import amerebagatelle.github.io.mcg.coordinates.CoordinateFolder;
import amerebagatelle.github.io.mcg.gui.MCGListWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class CoordinateFileManagerWidget extends MCGListWidget<CoordinateFileManagerWidget.Entry> {
    private CoordinateFileManager parent;
    private CoordinateFolder folder;

    public CoordinateFileManagerWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight, int left, CoordinateFileManager parent, CoordinateFolder folder) {
        super(minecraftClient, width, height, top, bottom, itemHeight, left);
        this.parent = parent;
        this.folder = folder;
        this.updateEntries(folder);
    }

    private void updateEntries(CoordinateFolder folder) {
        this.clearEntries();
        this.setSelected(null);
        for (CoordinateFolder listFolder : folder.listFolders()) {
            this.addEntry(new FolderEntry(listFolder.getName()));
        }
        for (CoordinateFile file : folder.listFiles()) {
            this.addEntry(new FileEntry(file.getName()));
        }
    }

    public CoordinateFolder getCurrentFolder() {
        return folder;
    }

    public void select(Entry entry) {
        this.setSelected(entry);
        this.ensureVisible(entry);
    }

    public boolean isEntrySelected(Entry entry) {
        return this.getSelectedOrNull() == entry;
    }

    public void openFile() {
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        CoordinatesManagerScreen screen = new CoordinatesManagerScreen(folder.getFile(Objects.requireNonNull(this.getSelectedOrNull()).getName()).get());
        client.setScreen(screen);
    }

    public void newFile() {
        client.setScreen(new CoordinateFileCreationScreen(CoordinateFileCreationScreen.FileType.FILE, folder));
    }

    public void newFolder() {
        client.setScreen(new CoordinateFileCreationScreen(CoordinateFileCreationScreen.FileType.FOLDER, folder));
    }

    public void removeFile() {
        try {
            folder.getFile(Objects.requireNonNull(this.getSelectedOrNull()).getName()).get().delete();
        } catch (IOException e) {
            parent.reportError("Could not delete file %s.".formatted(Objects.requireNonNull(this.getSelectedOrNull()).getName()));
        }
        updateEntries(folder);
    }

    public boolean hasFileSelected() {
        return this.getSelectedOrNull() instanceof FileEntry;
    }

    public boolean hasSelected() {
        return this.getSelectedOrNull() != null;
    }

    public void enterFolder(String folderName) {
        this.folder = folder.getFolder(folderName).orElse(MCG.rootCoordinateFolder);
    }

    public void backUpFolder() {
        folder = folder.getParent();
        updateEntries(folder);
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
            drawTextWithShadow(matrices, client.textRenderer, name.replace(".coordinates", ""), x + 5, y + entryHeight / 2 - 4, 0xFFFFFF);
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
            drawTextWithShadow(matrices, client.textRenderer, name + "/", x + 5, y + entryHeight / 2 - 4, 0xFFFFFF);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (CoordinateFileManagerWidget.this.isEntrySelected(this)) {
                CoordinateFileManagerWidget.this.enterFolder(name);
            } else {
                CoordinateFileManagerWidget.this.select(this);
            }
            return false;
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
