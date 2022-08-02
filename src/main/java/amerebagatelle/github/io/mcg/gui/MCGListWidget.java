package amerebagatelle.github.io.mcg.gui;

import amerebagatelle.github.io.mcg.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Objects;

public class MCGListWidget<E extends AlwaysSelectedEntryListWidget.Entry<E>> extends AlwaysSelectedEntryListWidget<E> {
    public MCGListWidget(MinecraftClient minecraftClient, int width, int height, int top, int bottom, int itemHeight, int left) {
        super(minecraftClient, width, height, top, bottom, itemHeight);
        this.left = left;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderUtils.drawBox(left, top, width, height, 0.1f, 0.1f, 0.1f, 0.30f);
        this.renderList(matrices, this.getRowLeft(), this.top + 4 - (int) this.getScrollAmount(), delta);
        this.renderDecorations(matrices, mouseX, mouseY);
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
