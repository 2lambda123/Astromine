/*
 * MIT License
 *
 * Copyright (c) 2020, 2021 Mixinors
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

package com.github.chainmailstudios.astromine.technologies.registry;

import net.minecraft.item.Item;

import com.github.chainmailstudios.astromine.common.item.base.EnergyVolumeItem;
import com.github.chainmailstudios.astromine.common.item.base.FluidVolumeItem;
import com.github.chainmailstudios.astromine.registry.AstromineConfig;
import com.github.chainmailstudios.astromine.registry.AstromineItems;
import com.github.chainmailstudios.astromine.technologies.common.item.DrillItem;
import com.github.chainmailstudios.astromine.technologies.common.item.GravityGauntletItem;
import com.github.chainmailstudios.astromine.technologies.common.item.HolographicConnectorItem;

public class AstromineTechnologiesItems extends AstromineItems {
	public static final Item MACHINE_CHASSIS = register("machine_chassis", new Item(getBasicSettings()));

	public static final Item BASIC_MACHINE_UPGRADE_KIT = register("basic_machine_upgrade_kit", new Item(getBasicSettings()));
	public static final Item ADVANCED_MACHINE_UPGRADE_KIT = register("advanced_machine_upgrade_kit", new Item(getBasicSettings()));
	public static final Item ELITE_MACHINE_UPGRADE_KIT = register("elite_machine_upgrade_kit", new Item(getBasicSettings()));
	
	public static final Item PRIMITIVE_PLATING = register("primitive_plating", new Item(getBasicSettings()));
	public static final Item BASIC_PLATING = register("basic_plating", new Item(getBasicSettings()));
	public static final Item ADVANCED_PLATING = register("advanced_plating", new Item(getBasicSettings()));
	public static final Item ELITE_PLATING = register("elite_plating", new Item(getBasicSettings()));

	public static final Item PORTABLE_TANK = register("portable_tank", FluidVolumeItem.of(getBasicSettings().maxCount(1), AstromineConfig.get().portableTankFluid));
	public static final Item LARGE_PORTABLE_TANK = register("large_portable_tank", FluidVolumeItem.of(getBasicSettings().maxCount(1), AstromineConfig.get().largePortableTankFluid));

	public static final Item PRIMITIVE_CIRCUIT = register("primitive_circuit", new Item(getBasicSettings()));
	public static final Item BASIC_CIRCUIT = register("basic_circuit", new Item(getBasicSettings()));
	public static final Item ADVANCED_CIRCUIT = register("advanced_circuit", new Item(getBasicSettings()));
	public static final Item ELITE_CIRCUIT = register("elite_circuit", new Item(getBasicSettings()));

	public static final Item PRIMITIVE_BATTERY = register("primitive_battery", EnergyVolumeItem.of(getBasicSettings().maxCount(1), AstromineConfig.get().primitiveBatteryEnergy));
	public static final Item BASIC_BATTERY = register("basic_battery", EnergyVolumeItem.of(getBasicSettings().maxCount(1), AstromineConfig.get().basicBatteryEnergy));
	public static final Item ADVANCED_BATTERY = register("advanced_battery", EnergyVolumeItem.of(getBasicSettings().maxCount(1), AstromineConfig.get().advancedBatteryEnergy));
	public static final Item ELITE_BATTERY = register("elite_battery", EnergyVolumeItem.of(getBasicSettings().maxCount(1), AstromineConfig.get().eliteBatteryEnergy));
	public static final Item CREATIVE_BATTERY = register("creative_battery", EnergyVolumeItem.ofCreative(getBasicSettings().maxCount(1)));

	public static final Item PRIMITIVE_BATTERY_PACK = register("primitive_battery_pack", EnergyVolumeItem.of(getBasicSettings().maxCount(1), AstromineConfig.get().primitiveBatteryPackEnergy));
	public static final Item BASIC_BATTERY_PACK = register("basic_battery_pack", EnergyVolumeItem.of(getBasicSettings().maxCount(1), AstromineConfig.get().basicBatteryPackEnergy));
	public static final Item ADVANCED_BATTERY_PACK = register("advanced_battery_pack", EnergyVolumeItem.of(getBasicSettings().maxCount(1), AstromineConfig.get().advancedBatteryPackEnergy));
	public static final Item ELITE_BATTERY_PACK = register("elite_battery_pack", EnergyVolumeItem.of(getBasicSettings().maxCount(1), AstromineConfig.get().eliteBatteryPackEnergy));
	public static final Item CREATIVE_BATTERY_PACK = register("creative_battery_pack", EnergyVolumeItem.ofCreative(getBasicSettings().maxCount(1)));

	public static final Item PRIMITIVE_DRILL = register("primitive_drill", new DrillItem(AstromineTechnologiesToolMaterials.PRIMITIVE_DRILL, 1, -2.8F, 1, AstromineConfig.get().primitiveDrillEnergy, getBasicSettings().maxCount(1)));
	public static final Item BASIC_DRILL = register("basic_drill", new DrillItem(AstromineTechnologiesToolMaterials.BASIC_DRILL, 1, -2.8F, 1, AstromineConfig.get().basicDrillEnergy, getBasicSettings().maxCount(1)));
	public static final Item ADVANCED_DRILL = register("advanced_drill", new DrillItem(AstromineTechnologiesToolMaterials.ADVANCED_DRILL, 1, -2.8F, 1, AstromineConfig.get().advancedDrillEnergy, getBasicSettings().maxCount(1)));
	public static final Item ELITE_DRILL = register("elite_drill", new DrillItem(AstromineTechnologiesToolMaterials.ELITE_DRILL, 1, -2.8F, 1, AstromineConfig.get().eliteDrillEnergy, getBasicSettings().maxCount(1)));

	public static final Item DRILL_HEAD = register("drill_head", new Item(getBasicSettings()));

	public static final Item PRIMITIVE_DRILL_BASE = register("primitive_drill_base", new Item(getBasicSettings()));
	public static final Item BASIC_DRILL_BASE = register("basic_drill_base", new Item(getBasicSettings()));
	public static final Item ADVANCED_DRILL_BASE = register("advanced_drill_base", new Item(getBasicSettings()));
	public static final Item ELITE_DRILL_BASE = register("elite_drill_base", new Item(getBasicSettings()));

	public static final Item HOLOGRAPHIC_CONNECTOR = register("holographic_connector", new HolographicConnectorItem(getBasicSettings().maxCount(1)));

	public static final Item GRAVITY_GAUNTLET = register("gravity_gauntlet", new GravityGauntletItem(getBasicSettings().maxCount(1), AstromineConfig.get().gravityGauntletEnergy));

	public static void initialize() {

	}

	public static Item.Settings getBasicSettings() {
		return new Item.Settings().group(AstromineTechnologiesItemGroups.TECHNOLOGIES);
	}
}
