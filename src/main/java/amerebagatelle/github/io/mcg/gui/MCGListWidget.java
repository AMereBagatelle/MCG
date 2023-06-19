package amerebagatelle.github.io.mcg.gui;

import amerebagatelle.github.io.mcg.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

import java.util.Objects;

public class MCGListWidget<E extends AlwaysSelectedEntryListWidget.Entry<E>> extends AlwaysSelectedEntryListWidget<E> {
    public MCGListWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight, int left) {
        super(minecraftClient, width, height, top, bottom, itemHeight);
        this.left = left;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderUtils.drawBox(left, top, width, height, 0.1f, 0.1f, 0.1f, 0.30f);
        this.renderList(context, mouseX, mouseY, delta);
        this.renderDecorations(context, mouseX, mouseY);
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return left + width;
    }

    public int getTop() {
        return top;
    }

    public int getWidth() {
        return width;
    }

    public int getBottom() {
        return top + height;
    }

    public int getButtonWidth() {
        return Objects.requireNonNull(client.currentScreen).width - left - width - 10;
    }
}
