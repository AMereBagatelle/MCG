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
        if(string.length() == 0) {
            return false;
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

    public CreateCoordinateScreen(MinecraftClient client, CoordinatesScreen parent) {
        super(new TranslatableText("screen.writecoordinate.title"));
        this.client = client;
        this.parent = parent;
    }

    @Override
    protected void init() {
        client.keyboard.enableRepeatEvents(true);
        super.init();
        this.coordinateNameField = new TextFieldWidget(this.font, this.width/2-100, 66, 200, 20, I18n.translate("writecoordinate.enterName"));
        this.coordinateNameField.setSelected(true);
        this.children.add(coordinateNameField);
        this.coordinateXField = new TextFieldWidget(this.font, this.width/2-100, 106, 200, 20, I18n.translate("writecoordinate.enterX"));
        this.coordinateXField.setTextPredicate(coordinateFilter);
        this.children.add(coordinateXField);
        this.coordinateYField = new TextFieldWidget(this.font, this.width/2-100, 146, 200, 20, I18n.translate("writecoordinate.enterY"));
        this.coordinateYField.setTextPredicate(coordinateFilter);
        this.children.add(coordinateYField);
        this.coordinateZField = new TextFieldWidget(this.font, this.width/2-100, 186, 200, 20, I18n.translate("writecoordinate.enterZ"));
        this.coordinateZField.setTextPredicate(coordinateFilter);
        this.children.add(coordinateZField);
        this.coordinateDetailsField = new TextFieldWidget(this.font, this.width/2-200, 226, 400, 20, I18n.translate("writecoordinate.enterDetails"));
        this.children.add(coordinateDetailsField);
        this.addButton = this.addButton(new ButtonWidget(this.width/2-100, this.height-140, 200, 20, I18n.translate("writecoordinate.addCoordinate"), ctx -> this.andAndClose()));
        this.addButton(new ButtonWidget(this.width/2-100, this.height-100, 200, 20, I18n.translate("writecoordinate.cancelAdd"), ctx -> this.onClose()));
        if(this.parent.selectedCoordinates != null) {
            ArrayList<String> selectedCoordinates = this.parent.selectedCoordinates;
            this.coordinateNameField.setText(selectedCoordinates.get(0));
            this.coordinateXField.setText(selectedCoordinates.get(1));
            this.coordinateYField.setText(selectedCoordinates.get(2));
            this.coordinateZField.setText(selectedCoordinates.get(3));
            this.coordinateDetailsField.setText(selectedCoordinates.get(4));
        }
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
        try {
            CoordinatesManager.writeToCoordinates(coordinateNameField.getText(), Integer.parseInt(coordinateXField.getText()), Integer.parseInt(coordinateYField.getText()), Integer.parseInt(coordinateZField.getText()), coordinateDetailsField.getText());
        } catch (IOException e) {
            SimpleCoordinates.logger.error("Could not write coordinate.");
        }
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
