package amerebagatelle.github.io.mcg.gui.screen;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.gui.MCGButtonWidget;
import amerebagatelle.github.io.mcg.utils.Config;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Language;

import java.util.Objects;
import java.util.function.Predicate;

public class ConfigScreen extends Screen {
    private final Predicate<String> numberPredicate = (string) -> string.matches("-?\\d+") || string.equals("-") || string.isEmpty();
    private TextFieldWidget overlayXField, overlayYField, overlayFormatField;
    private CyclingButtonWidget<Boolean> showCompass;

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

        overlayFormatField = new TextFieldWidget(textRenderer, 20, 120, 200, 20, Text.translatable("mcg.button.overlayFormat"));
        overlayFormatField.setText(MCG.config.overlayFormat);
        this.addSelectableChild(overlayFormatField);

        showCompass = new CyclingButtonWidget.Builder<Boolean>(bool -> bool ? Text.translatable("options.on") : Text.translatable("options.off")).values(true, false)
                .build(20, 160, 200, 20, Text.translatable("mcg.button.showCompass"));

        this.addSelectableChild(showCompass);

        confirmButton = new MCGButtonWidget(width - 105, height - 25, 100, 20, Text.translatable("mcg.button.confirm"), press -> confirm());
        this.addDrawableChild(confirmButton);
        cancelButton = new MCGButtonWidget(width - 210, height - 25, 100, 20, Text.translatable("mcg.button.cancel"), press -> cancel());
        this.addDrawableChild(cancelButton);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        confirmButton.active = overlayXField.getText().length() > 0 && overlayYField.getText().length() > 0;
        this.renderBackground(matrices);

        drawCenteredText(matrices, textRenderer, I18n.translate("mcg.config.configTitle"), width / 2, 10, 16777215);

        drawStringWithShadow(matrices, textRenderer, I18n.translate("mcg.button.overlayX"), overlayXField.x, overlayXField.y - 10, 16777215);
        overlayXField.render(matrices, mouseX, mouseY, delta);
        drawStringWithShadow(matrices, textRenderer, I18n.translate("mcg.button.overlayY"), overlayYField.x, overlayYField.y - 10, 16777215);
        overlayYField.render(matrices, mouseX, mouseY, delta);
        drawStringWithShadow(matrices, textRenderer, I18n.translate("mcg.button.overlayFormat"), overlayFormatField.x, overlayFormatField.y - 10, 16777215);
        overlayFormatField.render(matrices, mouseX, mouseY, delta);
        showCompass.render(matrices, mouseX, mouseY, delta);

        super.render(matrices, mouseX, mouseY, delta);
    }

    private void confirm() {
        Config config = MCG.config;
        config.writeSetting("overlayX", overlayXField.getText());
        config.writeSetting("overlayY", overlayYField.getText());
        config.writeSetting("overlayFormat", overlayFormatField.getText());
        config.writeSetting("showCompass", showCompass.getValue().toString());
        config.loadSettings();
        Objects.requireNonNull(client).setScreen(parent);
    }

    private void cancel() {
        Objects.requireNonNull(client).setScreen(parent);
    }
}
