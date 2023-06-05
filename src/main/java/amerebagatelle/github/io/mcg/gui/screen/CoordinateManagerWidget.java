package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.coordinates.Coordinate;
import amerebagatelle.github.io.mcg.coordinates.CoordinateFile;
import amerebagatelle.github.io.mcg.gui.MCGListWidget;
import amerebagatelle.github.io.mcg.gui.overlay.CoordinateHudOverlay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class CoordinateManagerWidget extends MCGListWidget<CoordinateManagerWidget.Entry> {
    private CoordinateFile file;
    private final CoordinatesManagerScreen parent;

    public CoordinateManagerWidget(MinecraftClient minecraftClient, CoordinatesManagerScreen parent, int width, int height, int top, int bottom, int itemHeight, int left) {
        super(minecraftClient, width, height, top, bottom, itemHeight, left);
        this.parent = parent;
    }

    public void setFile(CoordinateFile file) {
        this.file = file;
        refreshEntries();
    }

    public void refreshEntries() {
        clearEntries();
        List<Coordinate> list = file.getCoordinates();
        if (list != null) {
            for (Coordinate set : list) {
                this.addEntry(new CoordinateEntry(set));
            }
        }
    }

    public void newCoordinate(CoordinatesManagerScreen screen) {
        client.setScreen(new CoordinateCreationScreen(this.getSelectedOrNull() != null ? ((CoordinateEntry) this.getSelectedOrNull()).coordinate : null, screen));
    }

    public void removeCoordinate() {
        if (file.removeCoordinate(((CoordinateEntry) Objects.requireNonNull(this.getSelectedOrNull())).coordinate)) {
            try {
                file.save();
            } catch (IOException e) {
                parent.reportError(I18n.translate("mcg.coordinate.removefail"));
            }
        } else {
            parent.reportError(I18n.translate("mcg.coordinate.removefail"));
        }
        refreshEntries();
        this.setSelected(null);
    }

    public void copyCoordinate() {
        try {
            Coordinate coordinate = ((CoordinateEntry) Objects.requireNonNull(this.getSelectedOrNull())).coordinate.copy();
            coordinate.name += " Copy";
            file.addCoordinate(coordinate);
            file.save();
        } catch (IOException e) {
            parent.reportError(I18n.translate("mcg.coordinate.copyfail"));
        }
        refreshEntries();
    }

    public boolean isEntrySelected(Entry entry) {
        return this.getSelectedOrNull() == entry;
    }

    public void setOverlayToSelected() {
        CoordinateHudOverlay.INSTANCE.setCurrentCoordinate(((CoordinateEntry) Objects.requireNonNull(this.getSelectedOrNull())).coordinate);
    }

    public void teleportToCoordinate() {
        Objects.requireNonNull(client.player);
        if (client.player.isCreativeLevelTwoOp()) {
            Coordinate coordinateSet = ((CoordinateEntry) Objects.requireNonNull(this.getSelectedOrNull())).coordinate;
            client.player.networkHandler.sendChatCommand("tp %s %s %s".formatted(coordinateSet.x, coordinateSet.y, coordinateSet.z));
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
        public Coordinate coordinate;

        public CoordinateEntry(Coordinate coordinate) {
            this.coordinate = coordinate;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            int drawY = y + entryHeight / 2 - 5;
            drawTextWithShadow(matrices, client.textRenderer, coordinate.name, x + 5, drawY, 0xFFFFFF);
            int drawX = x + entryWidth / 2;
            drawTextWithShadow(matrices, client.textRenderer, Integer.toString(coordinate.x), drawX - 50, drawY, 0xFFFFFF);
            drawTextWithShadow(matrices, client.textRenderer, Integer.toString(coordinate.y), drawX + 30, drawY, 0xFFFFFF);
            drawTextWithShadow(matrices, client.textRenderer, Integer.toString(coordinate.z), drawX + 110, drawY, 0xFFFFFF);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (CoordinateManagerWidget.this.isEntrySelected(this)) {
                CoordinateManagerWidget.this.setOverlayToSelected();
            } else {
                CoordinateManagerWidget.this.select(this);
            }
            return false;
        }

        @Override
        public Text getNarration() {
            return Text.translatable("mcg.narration.coordinateentry", coordinate.name, coordinate.x, coordinate.y, coordinate.z, coordinate.description);
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<CoordinateManagerWidget.Entry> {
    }
}
