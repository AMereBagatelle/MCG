package amerebagatelle.github.io.simplecoordinates.gui.screen;

import amerebagatelle.github.io.simplecoordinates.SimpleCoordinates;
import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import amerebagatelle.github.io.simplecoordinates.gui.widget.CoordinateFileListWidget;
import amerebagatelle.github.io.simplecoordinates.gui.widget.CoordinatesWidget;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableText;

import java.io.IOException;
import java.util.ArrayList;

public class CoordinatesScreen extends Screen {
    public CoordinateFileListWidget coordinateFileListWidget;
    public CoordinatesWidget coordinatesWidget;

    public CoordinatesScreen() {
        super(new TranslatableText("screen.coordinates.title"));
    }

    @Override
    public void init() {
        super.init();
        this.addButton(new ButtonWidget(10, this.height-45, 20, 20, "/", onPress -> {
            this.coordinateFileListWidget.moveDirectoryBack();
        }));
        this.addButton(new ButtonWidget(35, this.height-45, 100, 20, "New File", onPress -> {
            this.minecraft.openScreen(new CreateFileScreen(this));
        }));
        this.addButton(new ButtonWidget(140, this.height-45, 100, 20, "New Folder", onPress -> {

        }));
        coordinatesWidget = new CoordinatesWidget(this.minecraft, this.width/2-50, this.height, 40, this.height - 50, 15, this);
        this.children.add(coordinatesWidget);
        this.coordinatesWidget.setLeftPos(this.width/2+10);
        coordinateFileListWidget = new CoordinateFileListWidget(this.minecraft, this.width/2-40, this.height, 40, this.height-50, 15, coordinatesWidget, this);
        this.children.add(coordinateFileListWidget);
        this.coordinateFileListWidget.setLeftPos(10);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        coordinateFileListWidget.render(mouseX, mouseY, delta);
        coordinatesWidget.render(mouseX, mouseY, delta);
        super.render(mouseX, mouseY, delta);
    }

    public void drawWidgetBackground(float x, float y, float width, float height) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SrcFactor.SRC_COLOR.value, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA.value, GlStateManager.SrcFactor.ONE.value, GlStateManager.DstFactor.ZERO.value);
        GlStateManager.color4f(0.1f, 0.1f, 0.1f, 0.30f);

        builder.begin(7, VertexFormats.POSITION);
        builder.vertex(x, y, 0.0).next();
        builder.vertex(x, y + height, 0.0).next();
        builder.vertex(x + width, y + height, 0.0).next();
        builder.vertex(x + width, y, 0.0).next();

        tessellator.draw();

        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
