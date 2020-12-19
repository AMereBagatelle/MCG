package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.coordinates.CoordinatesSet;
import amerebagatelle.github.io.mcg.gui.MCGListWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class CoordinateManagerWidget extends MCGListWidget<CoordinateManagerWidget.Entry> {
    private Path filepath;
    private final CoordinatesManagerScreen parent;

    public CoordinateManagerWidget(MinecraftClient minecraftClient, CoordinatesManagerScreen parent, int width, int height, int top, int bottom, int itemHeight, int left) {
        super(minecraftClient, width, height, top, bottom, itemHeight, left);
        this.parent = parent;
    }

    public void setFile(Path filepath) {
        this.filepath = filepath;
        refreshEntries();
    }

    public void refreshEntries() {
        clearEntries();
        try {
            List<CoordinatesSet> list = MCG.coordinatesManager.loadCoordinates(filepath);
            if (list != null) {
                for (CoordinatesSet set : list) {
                    this.addEntry(new CoordinateEntry(set));
                }
            }
        } catch (IOException e) {
            parent.reportError(I18n.translate("mcg.coordinate.readfail"));
        }
    }

    public void newCoordinate(CoordinatesManagerScreen screen) {
        client.openScreen(new CoordinateCreationScreen(this.getSelected() != null ? ((CoordinateEntry) this.getSelected()).coordinate : null, screen));
    }

    public void removeCoordinate() {
        try {
            MCG.coordinatesManager.removeCoordinate(filepath, ((CoordinateEntry) Objects.requireNonNull(this.getSelected())).coordinate);
        } catch (IOException e) {
            parent.reportError(I18n.translate("mcg.coordinate.removefail"));
        }
        refreshEntries();
        this.setSelected(null);
    }

    public void teleportToCoordinate() {
        Objects.requireNonNull(client.player);
        if (client.player.isCreativeLevelTwoOp()) {
            CoordinatesSet coordinateSet = ((CoordinateEntry) Objects.requireNonNull(this.getSelected())).coordinate;
            client.player.sendChatMessage(String.format("/tp %s %s %s", coordinateSet.x, coordinateSet.y, coordinateSet.z));
        } else {
            parent.reportError(I18n.translate("commands.help.failed"));
        }
    }

    public void select(CoordinateManagerWidget.Entry entry) {
        this.setSelected(entry);
        this.ensureVisible(entry);
    }

    @Override
    public int getRowWidth() {
        return width;
    }

    public class CoordinateEntry extends CoordinateManagerWidget.Entry {
        public CoordinatesSet coordinate;

        public CoordinateEntry(CoordinatesSet coordinate) {
            this.coordinate = coordinate;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            int drawY = y + entryHeight / 2 - 5;
            drawStringWithShadow(matrices, client.textRenderer, coordinate.name, x + 5, drawY, 16777215);
            int drawX = x + entryWidth / 2;
            drawStringWithShadow(matrices, client.textRenderer, Integer.toString(coordinate.x), drawX - 50, drawY, 16777215);
            drawStringWithShadow(matrices, client.textRenderer, Integer.toString(coordinate.y), drawX + 30, drawY, 16777215);
            drawStringWithShadow(matrices, client.textRenderer, Integer.toString(coordinate.z), drawX + 110, drawY, 16777215);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            CoordinateManagerWidget.this.select(this);
            return false;
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int button) {
            return false;
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinateManagerWidget.Entry> {
    }
}
