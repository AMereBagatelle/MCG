package amerebagatelle.github.io.mcg.mixin;

import amerebagatelle.github.io.mcg.gui.screen.CoordinatesScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"))
    public void onKey(long window, int key, int scancode, int i, int j, CallbackInfo ci) {
        if (key == GLFW.GLFW_KEY_Y) {
            MinecraftClient.getInstance().openScreen(new CoordinatesScreen());
        }
    }
}