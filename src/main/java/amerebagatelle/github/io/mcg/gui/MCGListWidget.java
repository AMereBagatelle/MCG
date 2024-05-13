package amerebagatelle.github.io.mcg.gui;

import amerebagatelle.github.io.mcg.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

import java.util.Objects;

public class MCGListWidget<E extends AlwaysSelectedEntryListWidget.Entry<E>> extends AlwaysSelectedEntryListWidget<E> {
    public MCGListWidget(MinecraftClient minecraftClient, int width, int height, int y, int itemHeight) {
        super(minecraftClient, width, height, y, itemHeight);
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderUtils.drawBox(getX(), getY(), width, height, 0.1f, 0.1f, 0.1f, 0.50f);
        this.renderList(context, mouseX, mouseY, delta);
        this.renderDecorations(context, mouseX, mouseY);
    }

    public int getLeft() {
        return getX();
    }

    public int getRight() {
        return getX() + width;
    }

    public int getTop() {
        return getY();
    }

    public int getBottom() {
        return getY() + height;
    }

    public int getButtonWidth() {
        return Objects.requireNonNull(client.currentScreen).width - getX() - width - 10;
    }
}
