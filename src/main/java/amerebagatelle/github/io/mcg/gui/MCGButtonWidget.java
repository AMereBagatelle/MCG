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
        float darkness = this.isHovered() && active ? 0.3f : 0.1f;
        RenderUtils.drawBox(this.x, this.y, this.width, this.height, darkness, darkness, darkness, 0.3f);
        drawCenteredText(matrices, MinecraftClient.getInstance().textRenderer, this.getMessage().getString(), this.x + (this.width / 2), this.y + (this.height / 2) - 5, active ? 16777215 : 10526880);
    }

    public int getBottom() {
        return y + height;
    }

    public int getRight() {
        return x + width;
    }
}
