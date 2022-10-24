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

import amerebagatelle.github.io.mcg.coordinates.CoordinatesSet;
import amerebagatelle.github.io.mcg.utils.Compass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.CompassItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CompassItem.class)
public abstract class CompassItemMixin implements Compass {

    @Shadow
    protected abstract void writeNbt(RegistryKey<World> worldKey, BlockPos pos, NbtCompound nbt);
    @Override
    public void write(ItemStack stack, CoordinatesSet set) {
        NbtCompound compound = stack.getOrCreateNbt();
        writeNbt(MinecraftClient.getInstance().player.getWorld().getRegistryKey(), new BlockPos(set.x, set.y, set.z), compound);
        stack.setNbt(compound);
    }
}
