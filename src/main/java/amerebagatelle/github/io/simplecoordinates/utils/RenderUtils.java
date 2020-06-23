package amerebagatelle.github.io.simplecoordinates.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;

@Environment(EnvType.CLIENT)
public class RenderUtils {
    public static void drawBox(float x, float y, float width, float height, float red, float blue, float green, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SrcFactor.SRC_COLOR.field_22545, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA.field_22528, GlStateManager.SrcFactor.ONE.field_22545, GlStateManager.DstFactor.ZERO.field_22528);
        RenderSystem.color4f(red, green, blue, alpha);

        builder.begin(7, VertexFormats.POSITION);
        builder.vertex(x, y, 0.0).next();
        builder.vertex(x, y + height, 0.0).next();
        builder.vertex(x + width, y + height, 0.0).next();
        builder.vertex(x + width, y, 0.0).next();

        tessellator.draw();

        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }

    public static void drawWidgetBackground(float x, float y, float width, float height) {
        drawBox(x, y, width, height, 0.1f, 0.1f, 0.1f, 0.30f);
    }
}
