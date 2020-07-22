package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.gui.MCGListWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;

public class CoordinateFileManagerWidget extends MCGListWidget<CoordinateFileManagerWidget.Entry> {
    public CoordinateFileManagerWidget(MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
        super(minecraftClient, i, j, k, l, m);
    }

    public class FileEntry extends CoordinateFileManagerWidget.Entry {

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {

        }
    }

    public class FolderEntry extends CoordinateFileManagerWidget.Entry {

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {

        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinateFileManagerWidget.Entry> {
    }
}
