package amerebagatelle.github.io.simplecoordinates.gui.screen;

import amerebagatelle.github.io.simplecoordinates.SimpleCoordinates;
import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class CreateCoordinateScreen extends Screen {
    private final MinecraftClient client;
    private final CoordinatesScreen parent;
    private final int textColor = 16777215;
    private final Predicate<String> coordinateFilter = (string) -> {
        return true;
    };

    public TextFieldWidget coordinateNameField;
    public TextFieldWidget coordinateXField;
    public TextFieldWidget coordinateYField;
    public TextFieldWidget coordinateZField;
    public TextFieldWidget coordinateDetailsField;

    public ButtonWidget addButton;

    public CreateCoordinateScreen(MinecraftClient client, CoordinatesScreen parent) {
        super(new TranslatableText("screen.writecoordinate.title"));
        this.client = client;
        this.parent = parent;
    }

    @Override
    protected void init() {
        client.keyboard.enableRepeatEvents(true);
        super.init();
        this.updateButtonActivationStates();
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderDirtBackground(0);
        this.drawCenteredString(this.font, I18n.translate("writecoordinate.enterName"), this.width/2, coordinateNameField.y-15, textColor);
        this.coordinateNameField.render(mouseX, mouseY, delta);
        this.drawCenteredString(this.font, I18n.translate("writecoordinate.enterX"), this.width/2, coordinateXField.y-15, textColor);
        this.coordinateXField.render(mouseX, mouseY, delta);
        this.drawCenteredString(this.font, I18n.translate("writecoordinate.enterY"), this.width/2, coordinateYField.y-15, textColor);
        this.coordinateYField.render(mouseX, mouseY, delta);
        this.drawCenteredString(this.font, I18n.translate("writecoordinate.enterZ"), this.width/2, coordinateZField.y-15, textColor);
        this.coordinateZField.render(mouseX, mouseY, delta);
        this.drawCenteredString(this.font, I18n.translate("writecoordinate.enterDetails"), this.width/2, coordinateDetailsField.y-15, textColor);
        this.coordinateDetailsField.render(mouseX, mouseY, delta);
        super.render(mouseX, mouseY, delta);
        this.updateButtonActivationStates();
    }

    public void andAndClose() {
        // Here goes code to save the values
        this.onClose();
    }

    @Override
    public void onClose() {
        this.updateButtonActivationStates();
        this.parent.selectedCoordinates = null;
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
