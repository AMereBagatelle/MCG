package amerebagatelle.github.io.mcg.utils;

import amerebagatelle.github.io.mcg.coordinates.CoordinatesSet;
import com.mojang.logging.LogUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.CompassItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public final class CompassUtil {
    public static void setPosition(ItemStack compass, CoordinatesSet set) {
        NbtCompound nbt = compass.getOrCreateNbt();
        nbt.put(CompassItem.LODESTONE_POS_KEY, NbtHelper.fromBlockPos(new BlockPos(set.x, set.y, set.z)));
        World.CODEC.encodeStart(NbtOps.INSTANCE, Objects.requireNonNull(MinecraftClient.getInstance().player).world.getRegistryKey())
                .resultOrPartial(LogUtils.getLogger()::error).ifPresent(e -> nbt.put(CompassItem.LODESTONE_DIMENSION_KEY, e));
        nbt.putBoolean(CompassItem.LODESTONE_TRACKED_KEY, true);
        compass.setNbt(nbt);
    }
}
