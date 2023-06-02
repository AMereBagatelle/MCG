package amerebagatelle.github.io.mcg.gui;

import amerebagatelle.github.io.mcg.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class MCGButtonWidget extends ButtonWidget {
    public MCGButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, Supplier::get);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        float darkness = this.isHovered() && active ? 0.3f : 0.1f;
        RenderUtils.drawBox(this.getX(), this.getY(), this.width, this.height, darkness, darkness, darkness, 0.3f);
        drawCenteredTextWithShadow(matrices, MinecraftClient.getInstance().textRenderer, this.getMessage().getString(), this.getX() + (this.width / 2), this.getY() + (this.height / 2) - 5, active ? 0xFFFFFF : 0xAAAAAA);
    }

    public int getBottom() {
        return getY() + height;
    }

    public int getRight() {
        return getX() + width;
    }
}
