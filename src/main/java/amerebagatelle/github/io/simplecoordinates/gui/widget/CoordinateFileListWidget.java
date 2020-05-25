package amerebagatelle.github.io.simplecoordinates.gui.widget;

import amerebagatelle.github.io.simplecoordinates.SimpleCoordinates;
import amerebagatelle.github.io.simplecoordinates.gui.screen.CoordinatesScreen;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.IOException;

public class CoordinateFileListWidget extends AlwaysSelectedEntryListWidget<CoordinateFileListWidget.Entry> {
    public final Identifier TEXTURE = new Identifier("simplecoordinates", "textures/file_icons.png");

    public String workingDirectory = SimpleCoordinates.coordinatesManager.coordinatesFolder;
    private CoordinatesWidget coordinatesWidget;
    private CoordinatesScreen parent;

    public CoordinateFileListWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight, CoordinatesWidget coordinatesWidget, CoordinatesScreen parent) {
        super(minecraftClient, width, height, top, bottom, itemHeight);
        this.generateFileList();
        this.centerListVertically = false;
        this.coordinatesWidget = coordinatesWidget;
        this.parent = parent;
    }

    public void changeWorkingDirectory(String directory) {
        this.clearEntries();
        workingDirectory = directory;
        this.generateFileList();
    }

    public void generateFileList() {
        File cwdFile = new File(workingDirectory);
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
                this.coordinatesWidget.setCurrentList(SimpleCoordinates.coordinatesManager.loadCoordinates(workingDirectory + "/" + ((CoordinateFileEntry) entry).name), ((CoordinateFileEntry) entry).name);
            } catch (IOException e) {
                SimpleCoordinates.logger.info("Could not load coordinates file.");
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.parent.drawWidgetBackground(this.left, this.top, width, height-top-(height-bottom));
        this.renderList(this.getRowLeft(), this.top + 4 - (int)this.getScrollAmount(), mouseX, mouseY, delta);
        this.renderDecorations(mouseX, mouseY);
        this.drawCenteredString(this.minecraft.textRenderer, "Files", this.left+this.width/2, this.top-30, 16777215);
        this.drawString(this.minecraft.textRenderer, workingDirectory.substring(11), this.left, this.bottom+5, 3553279);
    }

    @Override
    protected int getScrollbarPosition() {
        return this.left+this.width-10;
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
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            CoordinateFileListWidget.this.drawString(CoordinateFileListWidget.this.minecraft.textRenderer, this.name, x+5, y, 16777215);
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
        public void render(int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovering, float delta) {
            //CoordinateFileListWidget.this.minecraft.getTextureManager().bindTexture(TEXTURE);
            //aCoordinateFileListWidget.this.blit(x, y, 256, 256, 256, 256);
            CoordinateFileListWidget.this.drawString(CoordinateFileListWidget.this.minecraft.textRenderer, this.name, x+5, y, 16777215);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            CoordinateFileListWidget.this.selectEntry(this);
            CoordinateFileListWidget.this.changeWorkingDirectory(workingDirectory + "/" + this.name);
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
