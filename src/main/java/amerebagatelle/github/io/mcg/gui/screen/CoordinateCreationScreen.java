package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.coordinates.Coordinate;
import amerebagatelle.github.io.mcg.gui.MCGButtonWidget;
import amerebagatelle.github.io.mcg.gui.overlay.ErrorDisplayOverlay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Predicate;

public class CoordinateCreationScreen extends Screen {
    private final Predicate<String> coordinateFilter = (string) -> string.matches("-?\\d+") || string.equals("-") || string.equals("~") || string.isEmpty();
    private TextFieldWidget nameField;
    private TextFieldWidget xField;
    private TextFieldWidget yField;
    private TextFieldWidget zField;
    private MCGButtonWidget setAtPos;
    private TextFieldWidget descriptionField;
    private MCGButtonWidget confirmButton;
    private MCGButtonWidget cancelButton;
    private final Coordinate coordinate;

    private final CoordinatesManagerScreen parent;

    public CoordinateCreationScreen(Coordinate coordinate, CoordinatesManagerScreen coordinateScreen) {
        super(Text.literal("CoordinateCreationScreen"));
        this.coordinate = coordinate;
        this.parent = coordinateScreen;
    }

    @Override
    public void init() {
        Objects.requireNonNull(client);

        nameField = new TextFieldWidget(textRenderer, 20, 40, 200, 20, Text.translatable("mcg.button.name"));
        this.addSelectableChild(nameField);
        xField = new TextFieldWidget(textRenderer, 20, 80, 50, 20, Text.literal("X"));
        xField.setTextPredicate(coordinateFilter);
        this.addSelectableChild(xField);
        yField = new TextFieldWidget(textRenderer, 80, 80, 50, 20, Text.literal("Y"));
        yField.setTextPredicate(coordinateFilter);
        this.addSelectableChild(yField);
        zField = new TextFieldWidget(textRenderer, 140, 80, 50, 20, Text.literal("Z"));
        zField.setTextPredicate(coordinateFilter);
        this.addSelectableChild(zField);

        setAtPos = new MCGButtonWidget(200, 78, 100, 25, Text.translatable("mcg.button.setcurrent"), onPress -> {
            BlockPos pos = Objects.requireNonNull(client.player).getBlockPos();
            xField.setText(Integer.toString(pos.getX()));
            yField.setText(Integer.toString(pos.getY()));
            zField.setText(Integer.toString(pos.getZ()));
        });
        this.addDrawableChild(setAtPos);

        descriptionField = new TextFieldWidget(textRenderer, 20, 120, 200, 20, Text.translatable("mcg.button.description"));
        this.addSelectableChild(descriptionField);

        if (coordinate != null) {
            nameField.setText(coordinate.name);
            xField.setText(Integer.toString(coordinate.x));
            yField.setText(Integer.toString(coordinate.y));
            zField.setText(Integer.toString(coordinate.z));
            descriptionField.setText(coordinate.description);
        } else {
            yField.setText(Integer.toString(Objects.requireNonNull(client.player).getBlockPos().getY()));
        }

        confirmButton = new MCGButtonWidget(width - 105, height - 25, 100, 20, Text.translatable("mcg.button.confirm"), press -> confirm());
        this.addDrawableChild(confirmButton);
        cancelButton = new MCGButtonWidget(width - 210, height - 25, 100, 20, Text.translatable("mcg.button.cancel"), press -> cancel());
        this.addDrawableChild(cancelButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        updateButtonStates();
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, I18n.translate("mcg.coordinate.creationtitle"), width / 2, 10, 0xFFFFFF);
        context.drawTextWithShadow(textRenderer, I18n.translate("mcg.button.name"), nameField.getX(), nameField.getY() - 10, 0xFFFFFF);
        nameField.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(textRenderer, "X", xField.getX(), xField.getY() - 10, 0xFFFFFF);
        xField.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(textRenderer, "Y", yField.getX(), yField.getY() - 10, 0xFFFFFF);
        yField.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(textRenderer, "Z", zField.getX(), zField.getY() - 10, 0xFFFFFF);
        zField.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(textRenderer, I18n.translate("mcg.button.description"), descriptionField.getX(), descriptionField.getY() - 10, 0xFFFFFF);
        descriptionField.render(context, mouseX, mouseY, delta);
        ErrorDisplayOverlay.INSTANCE.render(context, height);
    }

    private void updateButtonStates() {
        confirmButton.active = !nameField.getText().isEmpty() && !xField.getText().isEmpty() && !yField.getText().isEmpty() && !zField.getText().isEmpty();
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private void confirm() {
        try {
            Coordinate set = parseCoordinate(nameField.getText(), xField.getText(), yField.getText(), zField.getText(), descriptionField.getText());
            parent.getFile().addCoordinate(set);
            parent.getFile().save();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
            parent.reportError(I18n.translate("mcg.coordinate.creationfail"));
        }
        parent.refresh();
        Objects.requireNonNull(client).setScreen(parent);
    }

    private Coordinate parseCoordinate(String name, String x, String y, String z, String desc) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(client.player);
        Coordinate result = new Coordinate(name, 0, 0, 0, desc);
        if (x.equals("~")) {
            result.x = client.player.getBlockPos().getX();
        } else {
            result.x = Integer.parseInt(x);
        }
        if (y.equals("~")) {
            result.y = client.player.getBlockPos().getY();
        } else {
            result.y = Integer.parseInt(y);
        }
        if (z.equals("~")) {
            result.z = client.player.getBlockPos().getZ();
        } else {
            result.z = Integer.parseInt(z);
        }
        return result;
    }

    private void cancel() {
        Objects.requireNonNull(client).setScreen(parent);
    }
}
