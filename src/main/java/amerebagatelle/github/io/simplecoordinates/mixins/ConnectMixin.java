package amerebagatelle.github.io.simplecoordinates.mixins;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public abstract class ConnectMixin {

    @Environment(EnvType.CLIENT)
    @Inject(method="onGameJoin", at=@At("TAIL"))
    private void onConnectedToServerEvent(GameJoinS2CPacket packet, CallbackInfo cbi) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if(mc.getServer() != null) {
            CoordinatesManager.initCoordinatesForServer(mc.getServer().getServerName() + "-Local");
        } else {
            CoordinatesManager.initCoordinatesForServer(mc.getCurrentServerEntry().name + "-Server");
        }
    }

    @Environment(EnvType.CLIENT)
    @Inject(method="onDisconnected", at=@At("HEAD"), cancellable=true)
    public void onDisconnectedFromServerEvent(Text reason, CallbackInfo cbi) {

    }
}
