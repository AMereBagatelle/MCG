package amerebagatelle.github.io.mcg.mixin;

import amerebagatelle.github.io.mcg.MCG;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    private boolean keybindPressed;

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if (MCG.binding.isPressed() && !keybindPressed) {
            MinecraftClient.getInstance().setScreen(MCG.managerScreenInstance);
        }
        keybindPressed = MCG.binding.isPressed();
    }
}
