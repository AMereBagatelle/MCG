package amerebagatelle.github.io.simplecoordinates.gui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class CreateCoordinateScreen extends Screen {
    private final MinecraftClient client;
    private final TextRenderer textRenderer;

    public TextFieldWidget coordinateName;

    public CreateCoordinateScreen(MinecraftClient client) {
        super(new TranslatableText("screen.writecoordinate.title"));
        this.client = client;
        this.textRenderer = client.textRenderer;
    }

    @Override
    protected void init() {
        super.init();

    }
}
