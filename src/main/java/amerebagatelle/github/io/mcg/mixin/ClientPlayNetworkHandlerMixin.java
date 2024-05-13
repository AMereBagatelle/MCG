package amerebagatelle.github.io.mcg.mixin;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.gui.screen.CoordinateFileManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientConfigurationNetworkHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConfigurationNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Environment(EnvType.CLIENT)
    @Inject(method = "onDisconnected", at = @At("HEAD"))
    public void resetScreenOnDisconnect(Text reason, CallbackInfo ci) {
        MCG.managerScreenInstance = new CoordinateFileManager(MCG.rootCoordinateFolder);
    }
}
