package amerebagatelle.github.io.mcg.gui.widget;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.gui.screen.CoordinatesScreen;
import amerebagatelle.github.io.mcg.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class CoordinateFileListWidget extends AlwaysSelectedEntryListWidget<CoordinateFileListWidget.Entry> {
    public static Path workingDirectory = FileSystems.getDefault().getPath("coordinates");
    private final CoordinatesWidget coordinatesWidget;

    public CoordinateFileListWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight, CoordinatesWidget coordinatesWidget, CoordinatesScreen parent) {
        super(minecraftClient, width, height, top, bottom, itemHeight);
        this.generateFileList();
        this.centerListVertically = false;
        this.coordinatesWidget = coordinatesWidget;
    }

    public void changeWorkingDirectory(String directory) {
        this.clearEntries();
        workingDirectory = workingDirectory.resolve(directory);
        this.generateFileList();
    }

    public void moveDirectoryBack() {
        this.clearEntries();
        if(workingDirectory.getParent() != null) workingDirectory = workingDirectory.getParent();
        this.generateFileList();
    }

    public void generateFileList() {
        File cwdFile = new File(workingDirectory.toString());
        File[] filesInside = cwdFile.listFiles();
        if (filesInside != null) {
            for (File file : filesInside) {
                if(file.isDirectory()) {
                    this.addEntry(new CoordinateFolderEntry(file.getName()));
                } else {
                    this.addEntry(new CoordinateFileEntry(file.getName()));
                }
            }
        }
    }

    public void selectEntry(CoordinateFileListWidget.Entry entry) {
        this.setSelected(entry);
        this.ensureVisible(entry);
        if(entry instanceof CoordinateFileEntry) {
            try {
                this.coordinatesWidget.setCurrentList(MCG.coordinatesManager.loadCoordinates(workingDirectory + "/" + ((CoordinateFileEntry) entry).name), ((CoordinateFileEntry) entry).name);
            } catch (IOException e) {
                MCG.logger.info("Could not load coordinates file.");
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        RenderUtils.drawWidgetBackground(this.left, this.top, width, height - top - (height - bottom));
        this.renderList(matrixStack, this.getRowLeft(), this.top + 4 - (int) this.getScrollAmount(), mouseX, mouseY, delta);
        this.renderDecorations(matrixStack, mouseX, mouseY);
        this.drawCenteredString(matrixStack, this.client.textRenderer, "Files", this.left + this.width / 2, this.top - 30, 16777215);
        String visibleDirectory = workingDirectory.toString().length() > 12 ? workingDirectory.toString().substring(11) + "/" : "/";
        this.drawStringWithShadow(matrixStack, this.client.textRenderer, visibleDirectory, this.left, this.top - 10, 3553279);
    }

    @Override
    protected int getScrollbarPositionX() {
        return this.left + this.width - 10;
    }

    @Override
    public int getRowWidth() {
        return this.width-30;
    }

    public class CoordinateFileEntry extends CoordinateFileListWidget.Entry {
        public String name;

        public CoordinateFileEntry(String name) {
            this.name = name;
        }

        @Override
        public void render(MatrixStack matrixStack, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            if (y >= CoordinateFileListWidget.this.top && y + height <= CoordinateFileListWidget.this.bottom) {
                CoordinateFileListWidget.this.drawStringWithShadow(matrixStack, CoordinateFileListWidget.this.client.textRenderer, this.name, x + 5, y, 16777215);
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            CoordinateFileListWidget.this.selectEntry(this);
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return false;
        }
    }

    public class CoordinateFolderEntry extends CoordinateFileListWidget.Entry {
        public String name;

        public CoordinateFolderEntry(String name) {
            this.name = name;
        }

        @Override
        public void render(MatrixStack matrixStack, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            if (y >= CoordinateFileListWidget.this.top && y + height <= CoordinateFileListWidget.this.bottom) {
                CoordinateFileListWidget.this.drawStringWithShadow(matrixStack, CoordinateFileListWidget.this.client.textRenderer, this.name + "/", x + 5, y, 16777215);
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            CoordinateFileListWidget.this.selectEntry(this);
            CoordinateFileListWidget.this.changeWorkingDirectory(this.name);
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return false;
        }
    }

    public abstract class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinateFileListWidget.Entry>{
        public Entry() {
        }
    }
}
