package amerebagatelle.github.io.mcg.mixin;

import amerebagatelle.github.io.mcg.MCG;
import amerebagatelle.github.io.mcg.gui.screen.CoordinateFileManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Environment(EnvType.CLIENT)
    @Inject(method = "onDisconnect", at = @At("HEAD"))
    public void resetScreenOnDisconnect(DisconnectS2CPacket packet, CallbackInfo ci) {
        MCG.managerScreenInstance = new CoordinateFileManager(MCG.rootCoordinateFolder);
    }
}
