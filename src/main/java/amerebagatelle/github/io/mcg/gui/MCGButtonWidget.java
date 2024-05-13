package amerebagatelle.github.io.mcg.gui;

import amerebagatelle.github.io.mcg.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class MCGButtonWidget extends ButtonWidget {
    public MCGButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, Supplier::get);
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        float darkness = this.isHovered() && active ? 0.4f : 0.5f;
        RenderUtils.drawBox(this.getX(), this.getY(), this.width, this.height, 1f, 1f, 1f, darkness);
        context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, this.getMessage().getString(), this.getX() + (this.width / 2), this.getY() + (this.height / 2) - 5, active ? 0xFFFFFF : 0xAAAAAA);
    }

    public int getBottom() {
        return getY() + height;
    }

    public int getRight() {
        return getX() + width;
    }
}
