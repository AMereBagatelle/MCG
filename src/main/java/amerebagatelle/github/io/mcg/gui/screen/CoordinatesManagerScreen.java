package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.coordinates.CoordinatesSet;
import amerebagatelle.github.io.mcg.gui.MCGButtonWidget;
import amerebagatelle.github.io.mcg.gui.overlay.ErrorDisplayOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.nio.file.Path;
import java.util.Objects;

public class CoordinatesManagerScreen extends Screen {
    private CoordinateManagerWidget coordinateManagerWidget;

    private final Path filepath;

    private MCGButtonWidget newCoordinate;
    private MCGButtonWidget removeCoordinate;
    private MCGButtonWidget teleportToCoordinate;
    private MCGButtonWidget back;

    public CoordinatesManagerScreen(Path filepath) {
        super(new LiteralText("CoordinateManagerScreen"));
        this.filepath = filepath;
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        coordinateManagerWidget = new CoordinateManagerWidget(client, this, width / 3 * 2, height - 60, 40, this.height - 20, 15, 10);
        coordinateManagerWidget.setFile(filepath);
        this.addChild(coordinateManagerWidget);
        newCoordinate = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, coordinateManagerWidget.getTop(), coordinateManagerWidget.getButtonWidth(), 20, new TranslatableText("mcg.coordinate.newcoordinate"), press -> {
            coordinateManagerWidget.newCoordinate(this);
        });
        this.addButton(newCoordinate);
        removeCoordinate = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, newCoordinate.getBottom() + 5, coordinateManagerWidget.getButtonWidth(), 20, new TranslatableText("mcg.coordinate.removecoordinate"), press -> {
            coordinateManagerWidget.removeCoordinate();
        });
        this.addButton(removeCoordinate);
        teleportToCoordinate = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, removeCoordinate.getBottom() + 5, coordinateManagerWidget.getButtonWidth(), 20, new TranslatableText("mcg.coordinate.tp"), press -> {
            coordinateManagerWidget.teleportToCoordinate();
        });
        this.addButton(teleportToCoordinate);
        back = new MCGButtonWidget(coordinateManagerWidget.getRight() + 5, teleportToCoordinate.getBottom() + 5, coordinateManagerWidget.getButtonWidth(), 20, new LiteralText("Back"), press -> {
            client.openScreen(new CoordinateFileManager());
        });
        this.addButton(back);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        updateButtonStates();
        this.renderBackground(matrices);
        drawCenteredString(matrices, textRenderer, I18n.translate("mcg.coordinate.coordinatesof") + filepath.getFileName().toString().replace(".coordinates", ""), width / 2, 10, 16777215);
        coordinateManagerWidget.render(matrices, mouseX, mouseY, delta);

        // selected coordinate view
        if (coordinateManagerWidget.getSelected() != null) {
            CoordinatesSet set = ((CoordinateManagerWidget.CoordinateEntry) coordinateManagerWidget.getSelected()).coordinate;
            int drawY = back.getBottom() + 20;
            drawStringWithShadow(matrices, textRenderer, set.name, coordinateManagerWidget.getRight() + 5, drawY, 16777215);
            drawStringWithShadow(matrices, textRenderer, Integer.toString(set.x), coordinateManagerWidget.getRight() + 5, drawY + 15, 16777215);
            drawStringWithShadow(matrices, textRenderer, Integer.toString(set.y), coordinateManagerWidget.getRight() + 5, drawY + 25, 16777215);
            drawStringWithShadow(matrices, textRenderer, Integer.toString(set.z), coordinateManagerWidget.getRight() + 5, drawY + 35, 16777215);
            drawStringWithShadow(matrices, textRenderer, set.description, coordinateManagerWidget.getRight() + 5, drawY + 50, 16777215);
        }
        super.render(matrices, mouseX, mouseY, delta);
        ErrorDisplayOverlay.INSTANCE.render(matrices, height);
    }

    public void refresh() {
        coordinateManagerWidget.refreshEntries();
    }

    public void updateButtonStates() {
        removeCoordinate.active = coordinateManagerWidget.getSelected() != null;
        teleportToCoordinate.active = coordinateManagerWidget.getSelected() != null && Objects.requireNonNull(Objects.requireNonNull(client).player).isCreativeLevelTwoOp();
    }

    public Path getFilepath() {
        return filepath;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void reportError(String error) {
        MCG.logger.warn(error);
        ErrorDisplayOverlay.INSTANCE.addError(error);
    }
}
