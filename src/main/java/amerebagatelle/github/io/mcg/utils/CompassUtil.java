package amerebagatelle.github.io.mcg.utils;

import amerebagatelle.github.io.mcg.coordinates.CoordinatesSet;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public final class CompassUtil {
    public static void setPosition(ItemStack compass, CoordinatesSet set) {
        ((Compass) Items.COMPASS).write(compass, set);
    }
}
