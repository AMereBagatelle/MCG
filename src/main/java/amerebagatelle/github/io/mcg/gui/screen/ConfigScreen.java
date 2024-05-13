package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.gui.MCGButtonWidget;
import amerebagatelle.github.io.mcg.utils.Config;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

import java.util.Objects;
import java.util.function.Predicate;

public class ConfigScreen extends Screen {
    private final Predicate<String> numberPredicate = (string) -> string.matches("-?\\d+") || string.equals("-") || string.isEmpty();
    private TextFieldWidget overlayXField, overlayYField, overlayFormatField;
    private MCGButtonWidget confirmButton, cancelButton;

    private final CoordinateFileManager parent;

    public ConfigScreen(CoordinateFileManager parent) {
        super(Text.literal("ConfigScreen"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        overlayXField = new TextFieldWidget(textRenderer, 20, 40, 200, 20, Text.translatable("mcg.button.overlayX"));
        overlayXField.setTextPredicate(numberPredicate);
        overlayXField.setText(Integer.toString(MCG.config.overlayX));
        this.addSelectableChild(overlayXField);

        overlayYField = new TextFieldWidget(textRenderer, 20, 80, 200, 20, Text.translatable("mcg.button.overlayY"));
        overlayYField.setTextPredicate(numberPredicate);
        overlayYField.setText(Integer.toString(MCG.config.overlayY));
        this.addSelectableChild(overlayYField);

        overlayFormatField = new TextFieldWidget(textRenderer, 20, 120, 200, 20, Text.translatable("mcg.button.overlayField"));
        overlayFormatField.setText(MCG.config.overlayFormat);
        this.addSelectableChild(overlayFormatField);

        confirmButton = new MCGButtonWidget(width - 105, height - 25, 100, 20, Text.translatable("mcg.button.confirm"), press -> confirm());
        this.addDrawableChild(confirmButton);
        cancelButton = new MCGButtonWidget(width - 210, height - 25, 100, 20, Text.translatable("mcg.button.cancel"), press -> cancel());
        this.addDrawableChild(cancelButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        confirmButton.active = !overlayXField.getText().isEmpty() && !overlayYField.getText().isEmpty();
        this.renderBackground(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(textRenderer, I18n.translate("mcg.config.configTitle"), width / 2, 10, 0xFFFFFF);

        context.drawTextWithShadow(textRenderer, I18n.translate("mcg.button.overlayX"), overlayXField.getX(), overlayXField.getY() - 10, 0xFFFFFF);
        overlayXField.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(textRenderer, I18n.translate("mcg.button.overlayY"), overlayYField.getX(), overlayYField.getY() - 10, 0xFFFFFF);
        overlayYField.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(textRenderer, I18n.translate("mcg.button.overlayField"), overlayFormatField.getX(), overlayFormatField.getY() - 10, 0xFFFFFF);
        overlayFormatField.render(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);
    }

    private void confirm() {
        Config config = MCG.config;
        config.writeSetting("overlayX", overlayXField.getText());
        config.writeSetting("overlayY", overlayYField.getText());
        config.writeSetting("overlayFormat", overlayFormatField.getText());
        config.loadSettings();
        Objects.requireNonNull(client).setScreen(parent);
    }

    private void cancel() {
        Objects.requireNonNull(client).setScreen(parent);
    }
}
