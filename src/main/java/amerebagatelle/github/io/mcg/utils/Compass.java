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
package amerebagatelle.github.io.mcg.utils;

import amerebagatelle.github.io.mcg.coordinates.CoordinatesSet;
import net.minecraft.item.ItemStack;

public interface Compass {
    void write(ItemStack compass, CoordinatesSet set);
}
