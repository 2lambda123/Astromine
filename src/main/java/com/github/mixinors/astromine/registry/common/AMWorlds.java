/*
 * MIT License
 *
 * Copyright (c) 2020 - 2022 Mixinors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.mixinors.astromine.registry.common;

import com.github.mixinors.astromine.AMCommon;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

import java.util.HashSet;
import java.util.Set;

public class AMWorlds {
	private static final Set<RegistryKey<?>> KEYS = new HashSet<>();
	
	public static final Identifier EARTH_ORBIT_ID = AMCommon.id("earth_orbit");
	public static final RegistryKey<DimensionOptions> EARTH_ORBIT_OPTIONS = register(Registry.DIMENSION_KEY, EARTH_ORBIT_ID);
	public static final RegistryKey<DimensionType> EARTH_ORBIT_TYPE_KEY = register(Registry.DIMENSION_TYPE_KEY, EARTH_ORBIT_ID);
	public static final RegistryKey<World> EARTH_ORBIT_WORLD = register(Registry.WORLD_KEY, EARTH_ORBIT_ID);
	
	public static final Identifier MOON_ORBIT_ID = AMCommon.id("moon_orbit");
	public static final RegistryKey<DimensionOptions> MOON_ORBIT_OPTIONS = register(Registry.DIMENSION_KEY, MOON_ORBIT_ID);
	public static final RegistryKey<DimensionType> MOON_ORBIT_TYPE_KEY = register(Registry.DIMENSION_TYPE_KEY, MOON_ORBIT_ID);
	public static final RegistryKey<World> MOON_ORBIT_WORLD = register(Registry.WORLD_KEY, MOON_ORBIT_ID);
	
	public static final Identifier MOON_ID = AMCommon.id("moon");
	public static final RegistryKey<DimensionOptions> MOON_OPTIONS = register(Registry.DIMENSION_KEY, MOON_ID);
	public static final RegistryKey<DimensionType> MOON_TYPE_KEY = register(Registry.DIMENSION_TYPE_KEY, MOON_ID);
	public static final RegistryKey<World> MOON_WORLD = register(Registry.WORLD_KEY, MOON_ID);
	
	public static void init() {
	}
	
	public static <T> RegistryKey<T> register(RegistryKey<Registry<T>> registry, Identifier identifier) {
		var key = RegistryKey.of(registry, identifier);
		KEYS.add(key);
		return key;
	}
	
	public static boolean isSpace(RegistryEntry<DimensionType> dimensionType) {
		return dimensionType.isIn(AMTagKeys.DimensionTypeTags.IS_SPACE);
	}
	
	public static boolean isAtmospheric(RegistryEntry<DimensionType> dimensionType) {
		return dimensionType.isIn(AMTagKeys.DimensionTypeTags.IS_ATMOSPHERIC);
	}
	
	public static boolean isAstromine(RegistryKey<?> key) {
		return KEYS.contains(key);
	}
}
