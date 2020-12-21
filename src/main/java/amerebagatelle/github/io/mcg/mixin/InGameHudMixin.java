package amerebagatelle.github.io.mcg.mixin;

import amerebagatelle.github.io.mcg.gui.overlay.CoordinateHudOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "render", at = @At("TAIL"))
    private void renderHud(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        CoordinateHudOverlay.INSTANCE.render(matrices, client.getWindow().getScaledHeight());
    }
}
