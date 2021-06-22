package amerebagatelle.github.io.mcg.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;

@Environment(EnvType.CLIENT)
public class RenderUtils {
    public static void drawBox(float x, float y, float width, float height, float red, float blue, float green, float alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        GlStateManager._enableBlend();
        GlStateManager._disableTexture();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        GlStateManager.glBlendFuncSeparate(GlStateManager.SrcFactor.SRC_COLOR.value, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA.value, GlStateManager.SrcFactor.ONE.value, GlStateManager.DstFactor.ZERO.value);
        RenderSystem.setShaderColor(red, green, blue, alpha);

        builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        builder.vertex(x, y, 0.0).next();
        builder.vertex(x, y + height, 0.0).next();
        builder.vertex(x + width, y + height, 0.0).next();
        builder.vertex(x + width, y, 0.0).next();

        tessellator.draw();

        GlStateManager._enableTexture();
        GlStateManager._disableBlend();
    }
}
