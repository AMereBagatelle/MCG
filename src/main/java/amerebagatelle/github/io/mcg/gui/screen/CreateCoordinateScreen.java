package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.coordinates.CoordinatesSet;
import amerebagatelle.github.io.mcg.gui.widget.CoordinateFileListWidget;
import amerebagatelle.github.io.mcg.gui.widget.CoordinatesWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.function.Predicate;

public class CreateCoordinateScreen extends Screen {
    private final MinecraftClient client;
    private final CoordinatesScreen parent;
    private final Predicate<String> coordinateFilter = (string) -> {
        System.out.println(string);
        if(string.length() == 0 || string.equals("-")) {
            return true;
        } else {
            try {
                Integer.parseInt(string);
                return true;
            } catch(Exception e) {
                return false;
            }
        }
    };

    public TextFieldWidget coordinateNameField;
    public TextFieldWidget coordinateXField;
    public TextFieldWidget coordinateYField;
    public TextFieldWidget coordinateZField;
    public TextFieldWidget coordinateDetailsField;

    public ButtonWidget addButton;

    private CoordinatesSet editCoordinates;

    public CreateCoordinateScreen(MinecraftClient client, CoordinatesScreen parent, @Nullable CoordinatesWidget.CoordinateListEntry editCoordinates) {
        super(new TranslatableText("screen.writecoordinate.title"));
        this.client = client;
        this.parent = parent;
        if(editCoordinates != null) {
            this.editCoordinates = editCoordinates.getCoordinates();
        }
    }

    @Override
    protected void init() {
        client.keyboard.enableRepeatEvents(true);
        super.init();
        this.coordinateNameField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, 66, 200, 20, new TranslatableText("writecoordinate.enterName"));
        this.coordinateNameField.setSelected(true);
        this.children.add(coordinateNameField);
        this.coordinateXField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, 106, 200, 20, new TranslatableText("writecoordinate.enterX"));
        this.coordinateXField.setTextPredicate(coordinateFilter);
        this.children.add(coordinateXField);
        this.coordinateYField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, 146, 200, 20, new TranslatableText("writecoordinate.enterY"));
        this.coordinateYField.setTextPredicate(coordinateFilter);
        this.children.add(coordinateYField);
        this.coordinateZField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, 186, 200, 20, new TranslatableText("writecoordinate.enterZ"));
        this.coordinateZField.setTextPredicate(coordinateFilter);
        this.children.add(coordinateZField);
        this.coordinateDetailsField = new TextFieldWidget(this.textRenderer, this.width / 2 - 200, 226, 400, 20, new TranslatableText("writecoordinate.enterDetails"));
        this.children.add(coordinateDetailsField);
        this.addButton = this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 120, 200, 20, new TranslatableText("writecoordinate.addCoordinate"), ctx -> this.andAndClose()));
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 80, 200, 20, new TranslatableText("writecoordinate.cancelAdd"), ctx -> this.onClose()));
        this.updateButtonActivationStates();
        // checks for what we can just toss into the boxes for players
        BlockPos playerPos = client.player.getBlockPos();
        if (playerPos != null) {
            coordinateXField.setText(Integer.toString(playerPos.getX()));
            coordinateYField.setText(Integer.toString(playerPos.getY()));
            coordinateZField.setText(Integer.toString(playerPos.getZ()));
        }
        if (editCoordinates != null) {
            coordinateNameField.setText(editCoordinates.name);
            coordinateXField.setText(Integer.toString(editCoordinates.x));
            coordinateYField.setText(Integer.toString(editCoordinates.y));
            coordinateZField.setText(Integer.toString(editCoordinates.z));
            coordinateDetailsField.setText(editCoordinates.description);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrixStack);
        int textColor = 16777215;
        this.drawCenteredString(matrixStack, this.textRenderer, I18n.translate("writecoordinate.title"), this.width / 2, 20, textColor);
        this.drawCenteredString(matrixStack, this.textRenderer, I18n.translate("writecoordinate.enterName"), this.width / 2, coordinateNameField.y - 15, textColor);
        this.coordinateNameField.render(matrixStack, mouseX, mouseY, delta);
        this.drawCenteredString(matrixStack, this.textRenderer, I18n.translate("writecoordinate.enterX"), this.width / 2, coordinateXField.y - 15, textColor);
        this.coordinateXField.render(matrixStack, mouseX, mouseY, delta);
        this.drawCenteredString(matrixStack, this.textRenderer, I18n.translate("writecoordinate.enterY"), this.width / 2, coordinateYField.y - 15, textColor);
        this.coordinateYField.render(matrixStack, mouseX, mouseY, delta);
        this.drawCenteredString(matrixStack, this.textRenderer, I18n.translate("writecoordinate.enterZ"), this.width / 2, coordinateZField.y - 15, textColor);
        this.coordinateZField.render(matrixStack, mouseX, mouseY, delta);
        this.drawCenteredString(matrixStack, this.textRenderer, I18n.translate("writecoordinate.enterDetails"), this.width / 2, coordinateDetailsField.y - 15, textColor);
        this.coordinateDetailsField.render(matrixStack, mouseX, mouseY, delta);
        super.render(matrixStack, mouseX, mouseY, delta);
        this.updateButtonActivationStates();
    }

    public void andAndClose() {
        try {
            MCG.coordinatesManager.writeToCoordinates(CoordinateFileListWidget.workingDirectory.toString() + "/" + CoordinatesWidget.coordinatesListName, new CoordinatesSet(coordinateNameField.getText(), Integer.parseInt(coordinateXField.getText()), Integer.parseInt(coordinateYField.getText()), Integer.parseInt(coordinateZField.getText()), coordinateDetailsField.getText()));
        } catch (IOException e) {
            MCG.logger.error("Could not write coordinate.");
        }
        this.onClose();
    }

    @Override
    public void onClose() {
        client.openScreen(this.parent);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void removed() {
        client.keyboard.enableRepeatEvents(false);
    }

    private void updateButtonActivationStates() {
        addButton.active = coordinateNameField.getText().length() != 0 && coordinateXField.getText().length() != 0 && coordinateYField.getText().length() != 0 && coordinateZField.getText().length() != 0;
    }
}
