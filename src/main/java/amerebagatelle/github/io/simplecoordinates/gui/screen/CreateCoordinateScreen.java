package amerebagatelle.github.io.simplecoordinates.gui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ChatUtil;

import java.util.function.Predicate;

public class CreateCoordinateScreen extends Screen {
    private final MinecraftClient client;
    private final Screen parent;
    private final int textColor = 16777215;
    private final Predicate<String> coordinateFilter = (string) -> {
        if(ChatUtil.isEmpty(string)) {
            return true;
        } else {
            try {
                Integer.parseInt(string);
                return false;
            } catch(Exception e) {
                return true;
            }
        }
    };

    public TextFieldWidget coordinateNameField;
    public TextFieldWidget coordinateXField;
    public TextFieldWidget coordinateYField;
    public TextFieldWidget coordinateZField;
    public TextFieldWidget coordinateDetailsField;

    public CreateCoordinateScreen(MinecraftClient client, Screen parent) {
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
}
