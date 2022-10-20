/*
 * MCG
 * Copyright © 2022 George Pronyuk <https://vk.com/gpronyuk>
 *
 * MCG is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MCG is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MCG. If not, see <https://www.gnu.org/licenses/>
 * and navigate to version 3 of the GNU Lesser General Public License.
 */
package amerebagatelle.github.io.mcg.mixin;

import amerebagatelle.github.io.mcg.gui.overlay.CoordinateHudOverlay;
import com.mojang.logging.LogUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.world.ClientWorld$ClientEntityHandler")
public class ClientEntityHandlerMixin {
    @Inject(method = "startTracking(Lnet/minecraft/entity/Entity;)V", at = @At("TAIL"))
    private void worldChange(Entity entity, CallbackInfo ci) {
        if(entity instanceof ClientPlayerEntity) {
            CoordinateHudOverlay.INSTANCE.current().ifPresent(CoordinateHudOverlay.INSTANCE::setCurrentCoordinate); // update the world
        }
    }
}
