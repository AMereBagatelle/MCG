package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.coordinates.CoordinatesSet;
import amerebagatelle.github.io.mcg.gui.MCGButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.function.Predicate;

public class CoordinateCreationScreen extends Screen {
    private final Predicate<String> coordinateFilter = (string) -> {
        if (string.length() == 0 || string.equals("-")) {
            return true;
        } else {
            try {
                Integer.parseInt(string);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    };
    private TextFieldWidget nameField;
    private TextFieldWidget xField;
    private TextFieldWidget yField;
    private TextFieldWidget zField;
    private TextFieldWidget descriptionField;
    private MCGButtonWidget confirmButton;
    private MCGButtonWidget cancelButton;
    private final CoordinatesSet coordinate;

    private final CoordinatesManagerScreen parent;

    public CoordinateCreationScreen(@Nullable CoordinatesSet coordinate, CoordinatesManagerScreen coordinateScreen) {
        super(new LiteralText("CoordinateCreationScreen"));
        this.coordinate = coordinate;
        this.parent = coordinateScreen;
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        nameField = new TextFieldWidget(textRenderer, 20, 40, 200, 20, new LiteralText("Name"));
        this.addChild(nameField);
        xField = new TextFieldWidget(textRenderer, 20, 80, 50, 20, new LiteralText("X"));
        xField.setTextPredicate(coordinateFilter);
        this.addChild(xField);
        yField = new TextFieldWidget(textRenderer, 80, 80, 50, 20, new LiteralText("Y"));
        yField.setTextPredicate(coordinateFilter);
        this.addChild(yField);
        zField = new TextFieldWidget(textRenderer, 140, 80, 50, 20, new LiteralText("Z"));
        zField.setTextPredicate(coordinateFilter);
        this.addChild(zField);
        descriptionField = new TextFieldWidget(textRenderer, 20, 120, 200, 20, new LiteralText("Description"));
        this.addChild(descriptionField);

        if (coordinate != null) {
            nameField.setText(coordinate.name);
            xField.setText(Integer.toString(coordinate.x));
            yField.setText(Integer.toString(coordinate.y));
            zField.setText(Integer.toString(coordinate.z));
            descriptionField.setText(coordinate.description);
        } else {
            yField.setText(Integer.toString(client.player.getBlockPos().getY()));
        }

        confirmButton = new MCGButtonWidget(width - 105, height - 25, 100, 20, new LiteralText("Confirm"), press -> confirm());
        this.addButton(confirmButton);
        cancelButton = new MCGButtonWidget(width - 210, height - 25, 100, 20, new LiteralText("Cancel"), press -> cancel());
        this.addButton(cancelButton);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        updateButtonStates();
        this.renderBackground(matrices);
        drawCenteredString(matrices, textRenderer, "Create Coordinate", width / 2, 10, 16777215);
        drawStringWithShadow(matrices, textRenderer, "Name", nameField.x, nameField.y - 10, 16777215);
        nameField.render(matrices, mouseX, mouseY, delta);
        drawStringWithShadow(matrices, textRenderer, "X", xField.x, xField.y - 10, 16777215);
        xField.render(matrices, mouseX, mouseY, delta);
        drawStringWithShadow(matrices, textRenderer, "Y", yField.x, yField.y - 10, 16777215);
        yField.render(matrices, mouseX, mouseY, delta);
        drawStringWithShadow(matrices, textRenderer, "Z", zField.x, zField.y - 10, 16777215);
        zField.render(matrices, mouseX, mouseY, delta);
        drawStringWithShadow(matrices, textRenderer, "Description", descriptionField.x, descriptionField.y - 10, 16777215);
        descriptionField.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    private void updateButtonStates() {
        confirmButton.active = nameField.getText().length() != 0 && xField.getText().length() != 0 && yField.getText().length() != 0 && zField.getText().length() != 0;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void confirm() {
        try {
            MCG.coordinatesManager.writeToCoordinates(parent.getFilepath(), new CoordinatesSet(nameField.getText(), Integer.parseInt(xField.getText()), Integer.parseInt(zField.getText()), Integer.parseInt(yField.getText()), descriptionField.getText()));
        } catch (IOException e) {
            MCG.logger.warn("Could not write coordinate.");
        }
        parent.refresh();
        client.openScreen(parent);
    }

    private void cancel() {
        client.openScreen(parent);
    }
}
