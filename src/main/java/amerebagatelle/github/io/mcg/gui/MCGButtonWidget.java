package amerebagatelle.github.io.mcg.gui;

import amerebagatelle.github.io.mcg.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class MCGButtonWidget extends ButtonWidget {
    public MCGButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderUtils.drawBox(this.x, this.y, this.width, this.height, 1f, 0.5f, 0.5f, 0.9f);
        this.drawCenteredString(matrices, MinecraftClient.getInstance().textRenderer, this.getMessage().asString(), this.x+(this.width/2), this.y+(this.height/2)-5, 16777215);
    }
}
