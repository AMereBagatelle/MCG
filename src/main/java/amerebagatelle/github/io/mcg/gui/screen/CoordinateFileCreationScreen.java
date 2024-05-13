package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.Constants;
import amerebagatelle.github.io.mcg.coordinates.CoordinateFolder;
import amerebagatelle.github.io.mcg.gui.MCGButtonWidget;
import amerebagatelle.github.io.mcg.gui.overlay.ErrorDisplayOverlay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

import java.nio.file.InvalidPathException;
import java.util.Objects;

public class CoordinateFileCreationScreen extends Screen {
    private TextFieldWidget fileNameWidget;
    private MCGButtonWidget confirmButton;
    private MCGButtonWidget cancelButton;

    private final FileType fileType;
    private final CoordinateFolder parentFolder;

    enum FileType {
        FILE,
        FOLDER
    }

    public CoordinateFileCreationScreen(FileType fileType, CoordinateFolder parentFolder) {
        super(Text.literal("CoordinateFileCreationScreen"));
        this.fileType = fileType;
        this.parentFolder = parentFolder;
    }

    @Override
    public void init() {
        fileNameWidget = new TextFieldWidget(textRenderer, width / 2 - 100, 100, 200, 20, Text.translatable("mcg.button.name"));
        this.addSelectableChild(fileNameWidget);

        confirmButton = new MCGButtonWidget(width / 2 - 50, height - 100, 100, 20, Text.translatable("mcg.button.confirm"), press -> confirm());
        this.addDrawableChild(confirmButton);
        cancelButton = new MCGButtonWidget(width / 2 - 50, height - 70, 100, 20, Text.translatable("mcg.button.cancel"), press -> cancel());
        this.addDrawableChild(cancelButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        updateButtonStates();
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, I18n.translate("mcg.file.new" + fileType.name().toLowerCase()), this.width / 2, 50, 0xFFFFFF);
        fileNameWidget.render(context, mouseX, mouseY, delta);
        ErrorDisplayOverlay.INSTANCE.render(context, height);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private void confirm() {
        try {
            if (fileType == FileType.FOLDER) {
                var folder = parentFolder.getFolder(fileNameWidget.getText());
                if (folder.isEmpty()) ErrorDisplayOverlay.INSTANCE.addError(I18n.translate("mcg.file.creationfail"));
            } else {
                var file = parentFolder.getFile(fileNameWidget.getText().endsWith(".json") ? fileNameWidget.getText() : fileNameWidget.getText() + ".json");
                if (file.isEmpty()) ErrorDisplayOverlay.INSTANCE.addError(I18n.translate("mcg.file.creationfail"));
            }
        } catch (InvalidPathException e) {
            Constants.LOGGER.error("Invalid name for new coordinates file " + fileNameWidget.getText());
            ErrorDisplayOverlay.INSTANCE.addError(I18n.translate("mcg.file.creationfail") + ": " + I18n.translate("mcg.file.invalidpath"));
        }
        Objects.requireNonNull(client).setScreen(new CoordinateFileManager(parentFolder));
    }

    private void cancel() {
        Objects.requireNonNull(client).setScreen(new CoordinateFileManager(parentFolder));
    }

    private void updateButtonStates() {
        confirmButton.active = !fileNameWidget.getText().isEmpty();
    }
}
