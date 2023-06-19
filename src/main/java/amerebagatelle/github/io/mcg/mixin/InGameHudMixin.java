package amerebagatelle.github.io.mcg.mixin;

import amerebagatelle.github.io.mcg.gui.overlay.CoordinateHudOverlay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void renderHud(DrawContext context, float tickDelta, CallbackInfo ci) {
        CoordinateHudOverlay.INSTANCE.render(context);
    }
}
