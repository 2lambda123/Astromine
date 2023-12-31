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

package com.github.mixinors.astromine.registry.common;

import com.github.mixinors.astromine.AMCommon;
import com.github.mixinors.astromine.common.item.*;
import com.github.mixinors.astromine.common.item.base.EnergyItem;
import com.github.mixinors.astromine.common.item.base.FluidItem;
import com.github.mixinors.astromine.mixin.common.common.AxeItemAccessor;
import com.github.mixinors.astromine.mixin.common.common.HoeItemAccessor;
import com.github.mixinors.astromine.mixin.common.common.PickaxeItemAccessor;
import me.shedaniel.architectury.annotations.ExpectPlatform;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class AMItems {
	public static RegistrySupplier<Item> ENERGY = register("energy", () -> new Item(new Item.Settings()));
	public static RegistrySupplier<Item> FLUID = register("fluid", () -> new Item(new Item.Settings()));
	public static RegistrySupplier<Item> ITEM = register("item", () -> new Item(new Item.Settings()));

	public static RegistrySupplier<Item> MANUAL = register("manual", () -> new ManualItem(getBasicSettings().maxCount(1)));
	
	public static RegistrySupplier<Item> SPACE_SLIME_SPAWN_EGG = null;
	
	public static RegistrySupplier<Item> SPACE_SLIME_BALL = null;
	
	public static RegistrySupplier<Item> ASTEROID_METITE_CLUSTER = register("asteroid_metite_cluster", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTEROID_ASTERITE_CLUSTER = register("asteroid_asterite_cluster", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTEROID_STELLUM_CLUSTER = register("asteroid_stellum_cluster", () -> new Item(AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> ASTEROID_GALAXIUM_CLUSTER = register("asteroid_galaxium_cluster", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> ASTEROID_COPPER_CLUSTER = register("asteroid_copper_cluster", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTEROID_TIN_CLUSTER = register("asteroid_tin_cluster", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTEROID_SILVER_CLUSTER = register("asteroid_silver_cluster", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTEROID_LEAD_CLUSTER = register("asteroid_lead_cluster", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> ASTEROID_COAL_CLUSTER = register("asteroid_coal_cluster", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTEROID_IRON_CLUSTER = register("asteroid_iron_cluster", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTEROID_GOLD_CLUSTER = register("asteroid_gold_cluster", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTEROID_REDSTONE_CLUSTER = register("asteroid_redstone_cluster", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTEROID_LAPIS_CLUSTER = register("asteroid_lapis_cluster", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTEROID_DIAMOND_CLUSTER = register("asteroid_diamond_cluster", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTEROID_EMERALD_CLUSTER = register("asteroid_emerald_cluster", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> PRIMITIVE_ROCKET_FUEL_TANK = register("primitive_rocket_fuel_tank", () -> new Item(AMItems.getBasicSettings().maxCount(1)));
	public static RegistrySupplier<Item> PRIMITIVE_ROCKET_PLATING = register("primitive_rocket_plating", () -> new Item(AMItems.getBasicSettings().maxCount(1)));
	public static RegistrySupplier<Item> PRIMITIVE_ROCKET_HULL = register("primitive_rocket_hull", () -> new Item(AMItems.getBasicSettings().maxCount(1)));
	public static RegistrySupplier<Item> PRIMITIVE_ROCKET_BOOSTER = register("primitive_rocket_booster", () -> new Item(AMItems.getBasicSettings().maxCount(1)));
	
	public static RegistrySupplier<Item> SPACE_SUIT_HELMET = register("space_suit_helmet", () -> new SpaceSuitItem(AMArmorMaterials.SPACE_SUIT, EquipmentSlot.HEAD, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> SPACE_SUIT_CHESTPLATE = register("space_suit_chestplate", () -> new SpaceSuitItem(AMArmorMaterials.SPACE_SUIT, EquipmentSlot.CHEST, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> SPACE_SUIT_LEGGINGS = register("space_suit_leggings", () -> new SpaceSuitItem(AMArmorMaterials.SPACE_SUIT, EquipmentSlot.LEGS, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> SPACE_SUIT_BOOTS = register("space_suit_boots", () -> new SpaceSuitItem(AMArmorMaterials.SPACE_SUIT, EquipmentSlot.FEET, AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> PRIMITIVE_ROCKET;
	
	public static RegistrySupplier<Item> METITE_NUGGET = register("metite_nugget", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STELLUM_NUGGET = register("stellum_nugget", () -> new Item(AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> UNIVITE_NUGGET = register("univite_nugget", () -> new Item(AMItems.getBasicSettings().fireproof()));
	
	
	public static RegistrySupplier<Item> COPPER_NUGGET = register("copper_nugget", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> TIN_NUGGET = register("tin_nugget", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> SILVER_NUGGET = register("silver_nugget", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> LEAD_NUGGET = register("lead_nugget", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> STEEL_NUGGET = register("steel_nugget", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> BRONZE_NUGGET = register("bronze_nugget", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ELECTRUM_NUGGET = register("electrum_nugget", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> FOOLS_GOLD_NUGGET = register("fools_gold_nugget", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METEORIC_STEEL_NUGGET = register("meteoric_steel_nugget", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> NETHERITE_NUGGET = register("netherite_nugget", () -> new Item(AMItems.getBasicSettings().fireproof()));
	
	public static RegistrySupplier<Item> ASTERITE_FRAGMENT = register("asterite_fragment", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GALAXIUM_FRAGMENT = register("galaxium_fragment", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> DIAMOND_FRAGMENT = register("diamond_fragment", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> EMERALD_FRAGMENT = register("emerald_fragment", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> QUARTZ_FRAGMENT = register("quartz_fragment", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> COPPER_WIRE = register("copper_wire", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> TIN_WIRE = register("tin_wire", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> SILVER_WIRE = register("silver_wire", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ELECTRUM_WIRE = register("electrum_wire", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GOLD_WIRE = register("gold_wire", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> ASTERITE = register("asterite", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GALAXIUM = register("galaxium", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> METITE_INGOT = register("metite_ingot", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STELLUM_INGOT = register("stellum_ingot", () -> new Item(AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> UNIVITE_INGOT = register("univite_ingot", () -> new Item(AMItems.getBasicSettings().fireproof()));
	
	
	public static RegistrySupplier<Item> COPPER_INGOT = register("copper_ingot", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> TIN_INGOT = register("tin_ingot", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> SILVER_INGOT = register("silver_ingot", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> LEAD_INGOT = register("lead_ingot", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> BRONZE_INGOT = register("bronze_ingot", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STEEL_INGOT = register("steel_ingot", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ELECTRUM_INGOT = register("electrum_ingot", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> FOOLS_GOLD_INGOT = register("fools_gold_ingot", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METEORIC_STEEL_INGOT = register("meteoric_steel_ingot", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> METITE_DUST = register("metite_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTERITE_DUST = register("asterite_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STELLUM_DUST = register("stellum_dust", () -> new Item(AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> GALAXIUM_DUST = register("galaxium_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> UNIVITE_DUST = register("univite_dust", () -> new Item(AMItems.getBasicSettings().fireproof()));
	
	
	public static RegistrySupplier<Item> COPPER_DUST = register("copper_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> TIN_DUST = register("tin_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> SILVER_DUST = register("silver_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> LEAD_DUST = register("lead_dust", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> STEEL_DUST = register("steel_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> BRONZE_DUST = register("bronze_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ELECTRUM_DUST = register("electrum_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> FOOLS_GOLD_DUST = register("fools_gold_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METEORIC_STEEL_DUST = register("meteoric_steel_dust", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> IRON_DUST = register("iron_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GOLD_DUST = register("gold_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> LAPIS_DUST = register("lapis_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> DIAMOND_DUST = register("diamond_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> EMERALD_DUST = register("emerald_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> NETHERITE_DUST = register("netherite_dust", () -> new Item(AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> COAL_DUST = register("coal_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> CHARCOAL_DUST = register("charcoal_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> QUARTZ_DUST = register("quartz_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> RAW_NETHERITE_DUST = register("raw_netherite_dust", () -> new Item(AMItems.getBasicSettings().fireproof()));
	
	public static RegistrySupplier<Item> METITE_TINY_DUST = register("metite_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTERITE_TINY_DUST = register("asterite_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STELLUM_TINY_DUST = register("stellum_tiny_dust", () -> new Item(AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> GALAXIUM_TINY_DUST = register("galaxium_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> UNIVITE_TINY_DUST = register("univite_tiny_dust", () -> new Item(AMItems.getBasicSettings().fireproof()));
	
	
	public static RegistrySupplier<Item> COPPER_TINY_DUST = register("copper_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> TIN_TINY_DUST = register("tin_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> SILVER_TINY_DUST = register("silver_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> LEAD_TINY_DUST = register("lead_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> STEEL_TINY_DUST = register("steel_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> BRONZE_TINY_DUST = register("bronze_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ELECTRUM_TINY_DUST = register("electrum_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> FOOLS_GOLD_TINY_DUST = register("fools_gold_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METEORIC_STEEL_TINY_DUST = register("meteoric_steel_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> REDSTONE_TINY_DUST = register("redstone_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> IRON_TINY_DUST = register("iron_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GOLD_TINY_DUST = register("gold_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> LAPIS_TINY_DUST = register("lapis_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> DIAMOND_TINY_DUST = register("diamond_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> EMERALD_TINY_DUST = register("emerald_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> NETHERITE_TINY_DUST = register("netherite_tiny_dust", () -> new Item(AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> COAL_TINY_DUST = register("coal_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> CHARCOAL_TINY_DUST = register("charcoal_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> QUARTZ_TINY_DUST = register("quartz_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> RAW_NETHERITE_TINY_DUST = register("raw_netherite_tiny_dust", () -> new Item(AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> GLOWSTONE_TINY_DUST = register("glowstone_tiny_dust", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> METITE_PLATE = register("metite_plate", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STELLUM_PLATE = register("stellum_plate", () -> new Item(AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> UNIVITE_PLATE = register("univite_plate", () -> new Item(AMItems.getBasicSettings().fireproof()));
	
	
	public static RegistrySupplier<Item> COPPER_PLATE = register("copper_plate", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> TIN_PLATE = register("tin_plate", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> SILVER_PLATE = register("silver_plate", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> LEAD_PLATE = register("lead_plate", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> STEEL_PLATE = register("steel_plate", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> BRONZE_PLATE = register("bronze_plate", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ELECTRUM_PLATE = register("electrum_plate", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> FOOLS_GOLD_PLATE = register("fools_gold_plate", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METEORIC_STEEL_PLATE = register("meteoric_steel_plate", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> IRON_PLATE = register("iron_plate", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GOLD_PLATE = register("gold_plate", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> NETHERITE_PLATE = register("netherite_plate", () -> new Item(AMItems.getBasicSettings().fireproof()));

	public static RegistrySupplier<Item> METITE_GEAR = register("metite_gear", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STELLUM_GEAR = register("stellum_gear", () -> new Item(AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> UNIVITE_GEAR = register("univite_gear", () -> new Item(AMItems.getBasicSettings().fireproof()));
	
	
	public static RegistrySupplier<Item> COPPER_GEAR = register("copper_gear", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> TIN_GEAR = register("tin_gear", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> SILVER_GEAR = register("silver_gear", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> LEAD_GEAR = register("lead_gear", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> STEEL_GEAR = register("steel_gear", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> BRONZE_GEAR = register("bronze_gear", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ELECTRUM_GEAR = register("electrum_gear", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> FOOLS_GOLD_GEAR = register("fools_gold_gear", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METEORIC_STEEL_GEAR = register("meteoric_steel_gear", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> IRON_GEAR = register("iron_gear", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GOLD_GEAR = register("gold_gear", () -> new Item(AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> NETHERITE_GEAR = register("netherite_gear", () -> new Item(AMItems.getBasicSettings().fireproof()));
	
	public static RegistrySupplier<Item> WOODEN_MINING_TOOL = register("wooden_mining_tool", () -> new DynamicToolItem((MiningToolItem) Items.WOODEN_SHOVEL, (MiningToolItem) Items.WOODEN_PICKAXE, ToolMaterials.WOOD, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> WOODEN_MATTOCK = register("wooden_mattock", () -> new DynamicToolItem((MiningToolItem) Items.WOODEN_HOE, (MiningToolItem) Items.WOODEN_AXE, ToolMaterials.WOOD, AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> STONE_MINING_TOOL = register("stone_mining_tool", () -> new DynamicToolItem((MiningToolItem) Items.STONE_SHOVEL, (MiningToolItem) Items.STONE_PICKAXE, ToolMaterials.STONE, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STONE_MATTOCK = register("stone_mattock", () -> new DynamicToolItem((MiningToolItem) Items.STONE_HOE, (MiningToolItem) Items.STONE_AXE, ToolMaterials.STONE, AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> IRON_MINING_TOOL = register("iron_mining_tool", () -> new DynamicToolItem((MiningToolItem) Items.IRON_SHOVEL, (MiningToolItem) Items.IRON_PICKAXE, ToolMaterials.IRON, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> IRON_MATTOCK = register("iron_mattock", () -> new DynamicToolItem((MiningToolItem) Items.IRON_HOE, (MiningToolItem) Items.IRON_AXE, ToolMaterials.IRON, AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> GOLDEN_MINING_TOOL = register("golden_mining_tool", () -> new DynamicToolItem((MiningToolItem) Items.GOLDEN_SHOVEL, (MiningToolItem) Items.GOLDEN_PICKAXE, ToolMaterials.GOLD, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GOLDEN_MATTOCK = register("golden_mattock", () -> new DynamicToolItem((MiningToolItem) Items.GOLDEN_HOE, (MiningToolItem) Items.GOLDEN_AXE, ToolMaterials.GOLD, AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> DIAMOND_MINING_TOOL = register("diamond_mining_tool", () -> new DynamicToolItem((MiningToolItem) Items.DIAMOND_SHOVEL, (MiningToolItem) Items.DIAMOND_PICKAXE, ToolMaterials.DIAMOND, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> DIAMOND_MATTOCK = register("diamond_mattock", () -> new DynamicToolItem((MiningToolItem) Items.DIAMOND_HOE, (MiningToolItem) Items.DIAMOND_AXE, ToolMaterials.DIAMOND, AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> NETHERITE_MINING_TOOL = register("netherite_mining_tool", () -> new DynamicToolItem((MiningToolItem) Items.NETHERITE_SHOVEL, (MiningToolItem) Items.NETHERITE_PICKAXE, ToolMaterials.NETHERITE, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> NETHERITE_MATTOCK = register("netherite_mattock", () -> new DynamicToolItem((MiningToolItem) Items.NETHERITE_HOE, (MiningToolItem) Items.NETHERITE_AXE, ToolMaterials.NETHERITE, AMItems.getBasicSettings().fireproof()));
	
	public static RegistrySupplier<PickaxeItem> BRONZE_PICKAXE = register("bronze_pickaxe", () -> PickaxeItemAccessor.init(AMToolMaterials.BRONZE, 1, -2.8f, AMItems.getBasicSettings()));
	public static RegistrySupplier<AxeItem> BRONZE_AXE = register("bronze_axe", () -> AxeItemAccessor.init(AMToolMaterials.BRONZE, 5f, -3.0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<ShovelItem> BRONZE_SHOVEL = register("bronze_shovel", () -> new ShovelItem(AMToolMaterials.BRONZE, 1.5f, -3.0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<HoeItem> BRONZE_HOE = register("bronze_hoe", () -> HoeItemAccessor.init(AMToolMaterials.BRONZE, -2, 0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> BRONZE_SWORD = register("bronze_sword", () -> new SwordItem(AMToolMaterials.BRONZE, 3, -2.4f, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> BRONZE_MINING_TOOL = register("bronze_mining_tool", () -> new DynamicToolItem(BRONZE_SHOVEL.get(), BRONZE_PICKAXE.get(), AMToolMaterials.BRONZE, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> BRONZE_MATTOCK = register("bronze_mattock", () -> new DynamicToolItem(BRONZE_HOE.get(), BRONZE_AXE.get(), AMToolMaterials.BRONZE, AMItems.getBasicSettings()));
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> BRONZE_HAMMER = null;
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> BRONZE_EXCAVATOR = null;
	
	public static RegistrySupplier<PickaxeItem> STEEL_PICKAXE = register("steel_pickaxe", () -> PickaxeItemAccessor.init(AMToolMaterials.STEEL, 1, -2.8f, AMItems.getBasicSettings()));
	public static RegistrySupplier<AxeItem> STEEL_AXE = register("steel_axe", () -> AxeItemAccessor.init(AMToolMaterials.STEEL, 5f, -3.0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<ShovelItem> STEEL_SHOVEL = register("steel_shovel", () -> new ShovelItem(AMToolMaterials.STEEL, 1.5f, -3.0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<HoeItem> STEEL_HOE = register("steel_hoe", () -> HoeItemAccessor.init(AMToolMaterials.STEEL, -3, 0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STEEL_SWORD = register("steel_sword", () -> new SwordItem(AMToolMaterials.STEEL, 3, -2.4f, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STEEL_MINING_TOOL = register("steel_mining_tool", () -> new DynamicToolItem(STEEL_SHOVEL.get(), STEEL_PICKAXE.get(), AMToolMaterials.STEEL, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STEEL_MATTOCK = register("steel_mattock", () -> new DynamicToolItem(STEEL_HOE.get(), STEEL_AXE.get(), AMToolMaterials.STEEL, AMItems.getBasicSettings()));
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> STEEL_HAMMER = null;
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> STEEL_EXCAVATOR = null;
	
	public static RegistrySupplier<PickaxeItem> METITE_PICKAXE = register("metite_pickaxe", () -> PickaxeItemAccessor.init(AMToolMaterials.METITE, 1, -2.8f, AMItems.getBasicSettings()));
	public static RegistrySupplier<AxeItem> METITE_AXE = register("metite_axe", () -> AxeItemAccessor.init(AMToolMaterials.METITE, 5f, -3.0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<ShovelItem> METITE_SHOVEL = register("metite_shovel", () -> new ShovelItem(AMToolMaterials.METITE, 1.5f, -3.0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<HoeItem> METITE_HOE = register("metite_hoe", () -> HoeItemAccessor.init(AMToolMaterials.METITE, -4, 0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METITE_SWORD = register("metite_sword", () -> new SwordItem(AMToolMaterials.METITE, 3, -2.4f, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METITE_MINING_TOOL = register("metite_mining_tool", () -> new DynamicToolItem(METITE_SHOVEL.get(), METITE_PICKAXE.get(), AMToolMaterials.METITE, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METITE_MATTOCK = register("metite_mattock", () -> new DynamicToolItem(METITE_HOE.get(), METITE_AXE.get(), AMToolMaterials.METITE, AMItems.getBasicSettings()));
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> METITE_HAMMER = null;
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> METITE_EXCAVATOR = null;
	
	public static RegistrySupplier<PickaxeItem> ASTERITE_PICKAXE = register("asterite_pickaxe", () -> PickaxeItemAccessor.init(AMToolMaterials.ASTERITE, 1, -2.8f, AMItems.getBasicSettings()));
	public static RegistrySupplier<AxeItem> ASTERITE_AXE = register("asterite_axe", () -> AxeItemAccessor.init(AMToolMaterials.ASTERITE, 5f, -3.0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<ShovelItem> ASTERITE_SHOVEL = register("asterite_shovel", () -> new ShovelItem(AMToolMaterials.ASTERITE, 1.5f, -3.0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<HoeItem> ASTERITE_HOE = register("asterite_hoe", () -> HoeItemAccessor.init(AMToolMaterials.ASTERITE, -5, 0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTERITE_SWORD = register("asterite_sword", () -> new SwordItem(AMToolMaterials.ASTERITE, 3, -2.4f, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTERITE_MINING_TOOL = register("asterite_mining_tool", () -> new DynamicToolItem(ASTERITE_SHOVEL.get(), ASTERITE_PICKAXE.get(), AMToolMaterials.ASTERITE, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTERITE_MATTOCK = register("asterite_mattock", () -> new DynamicToolItem(ASTERITE_HOE.get(), ASTERITE_AXE.get(), AMToolMaterials.ASTERITE, AMItems.getBasicSettings()));
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> ASTERITE_HAMMER = null;
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> ASTERITE_EXCAVATOR = null;
	
	public static RegistrySupplier<PickaxeItem> STELLUM_PICKAXE = register("stellum_pickaxe", () -> PickaxeItemAccessor.init(AMToolMaterials.STELLUM, 1, -2.8f, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<AxeItem> STELLUM_AXE = register("stellum_axe", () -> AxeItemAccessor.init(AMToolMaterials.STELLUM, 5f, -3.0f, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<ShovelItem> STELLUM_SHOVEL = register("stellum_shovel", () -> new ShovelItem(AMToolMaterials.STELLUM, 1.5f, -3.0f, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<HoeItem> STELLUM_HOE = register("stellum_hoe", () -> HoeItemAccessor.init(AMToolMaterials.STELLUM, -6, 0f, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> STELLUM_SWORD = register("stellum_sword", () -> new SwordItem(AMToolMaterials.STELLUM, 3, -2.4f, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> STELLUM_MINING_TOOL = register("stellum_mining_tool", () -> new DynamicToolItem(STELLUM_SHOVEL.get(), STELLUM_PICKAXE.get(), AMToolMaterials.STELLUM, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> STELLUM_MATTOCK = register("stellum_mattock", () -> new DynamicToolItem(STELLUM_HOE.get(), STELLUM_AXE.get(), AMToolMaterials.STELLUM, AMItems.getBasicSettings().fireproof()));
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> STELLUM_HAMMER = null;
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> STELLUM_EXCAVATOR = null;
	
	public static RegistrySupplier<PickaxeItem> GALAXIUM_PICKAXE = register("galaxium_pickaxe", () -> PickaxeItemAccessor.init(AMToolMaterials.GALAXIUM, 1, -2.8f, AMItems.getBasicSettings()));
	public static RegistrySupplier<AxeItem> GALAXIUM_AXE = register("galaxium_axe", () -> AxeItemAccessor.init(AMToolMaterials.GALAXIUM, 5f, -3.0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<ShovelItem> GALAXIUM_SHOVEL = register("galaxium_shovel", () -> new ShovelItem(AMToolMaterials.GALAXIUM, 1.5f, -3.0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<HoeItem> GALAXIUM_HOE = register("galaxium_hoe", () -> HoeItemAccessor.init(AMToolMaterials.GALAXIUM, -5, 0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GALAXIUM_SWORD = register("galaxium_sword", () -> new SwordItem(AMToolMaterials.GALAXIUM, 3, -2.4f, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GALAXIUM_MINING_TOOL = register("galaxium_mining_tool", () -> new DynamicToolItem(GALAXIUM_SHOVEL.get(), GALAXIUM_PICKAXE.get(), AMToolMaterials.GALAXIUM, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GALAXIUM_MATTOCK = register("galaxium_mattock", () -> new DynamicToolItem(GALAXIUM_HOE.get(), GALAXIUM_AXE.get(), AMToolMaterials.GALAXIUM, AMItems.getBasicSettings()));
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> GALAXIUM_HAMMER = null;
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> GALAXIUM_EXCAVATOR = null;
	
	public static RegistrySupplier<PickaxeItem> UNIVITE_PICKAXE = register("univite_pickaxe", () -> PickaxeItemAccessor.init(AMToolMaterials.UNIVITE, 1, -2.8f, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<AxeItem> UNIVITE_AXE = register("univite_axe", () -> AxeItemAccessor.init(AMToolMaterials.UNIVITE, 5f, -3.0f, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<ShovelItem> UNIVITE_SHOVEL = register("univite_shovel", () -> new ShovelItem(AMToolMaterials.UNIVITE, 1.5f, -3.0f, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<HoeItem> UNIVITE_HOE = register("univite_hoe", () -> HoeItemAccessor.init(AMToolMaterials.UNIVITE, -6, 0f, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> UNIVITE_SWORD = register("univite_sword", () -> new SwordItem(AMToolMaterials.UNIVITE, 3, -2.4f, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> UNIVITE_MINING_TOOL = register("univite_mining_tool", () -> new DynamicToolItem(UNIVITE_SHOVEL.get(), UNIVITE_PICKAXE.get(), AMToolMaterials.UNIVITE, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> UNIVITE_MATTOCK = register("univite_mattock", () -> new DynamicToolItem(UNIVITE_HOE.get(), UNIVITE_AXE.get(), AMToolMaterials.UNIVITE, AMItems.getBasicSettings().fireproof()));
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> UNIVITE_HAMMER = null;
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> UNIVITE_EXCAVATOR = null;
	
	public static RegistrySupplier<PickaxeItem> METEORIC_STEEL_PICKAXE = register("meteoric_steel_pickaxe", () -> PickaxeItemAccessor.init(AMToolMaterials.METEORIC_STEEL, 1, -2.8f, AMItems.getBasicSettings()));
	public static RegistrySupplier<AxeItem> METEORIC_STEEL_AXE = register("meteoric_steel_axe", () -> AxeItemAccessor.init(AMToolMaterials.METEORIC_STEEL, 5f, -3.0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<ShovelItem> METEORIC_STEEL_SHOVEL = register("meteoric_steel_shovel", () -> new ShovelItem(AMToolMaterials.METEORIC_STEEL, 1.5f, -3.0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<HoeItem> METEORIC_STEEL_HOE = register("meteoric_steel_hoe", () -> HoeItemAccessor.init(AMToolMaterials.METEORIC_STEEL, -3, 0f, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METEORIC_STEEL_SWORD = register("meteoric_steel_sword", () -> new SwordItem(AMToolMaterials.METEORIC_STEEL, 3, -2.4f, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METEORIC_STEEL_MINING_TOOL = register("meteoric_steel_mining_tool", () -> new DynamicToolItem(METEORIC_STEEL_SHOVEL.get(), METEORIC_STEEL_PICKAXE.get(), AMToolMaterials.METEORIC_STEEL, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METEORIC_STEEL_MATTOCK = register("meteoric_steel_mattock", () -> new DynamicToolItem(METEORIC_STEEL_HOE.get(), METEORIC_STEEL_AXE.get(), AMToolMaterials.METEORIC_STEEL, AMItems.getBasicSettings()));
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> METEORIC_STEEL_HAMMER = null;
	// TODO: Reimplement this on Forge & Fabric modules!
	public static RegistrySupplier<Item> METEORIC_STEEL_EXCAVATOR = null;
	
	public static RegistrySupplier<Item> BRONZE_HELMET = register("bronze_helmet", () -> new ArmorItem(AMArmorMaterials.BRONZE, EquipmentSlot.HEAD, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> BRONZE_CHESTPLATE = register("bronze_chestplate", () -> new ArmorItem(AMArmorMaterials.BRONZE, EquipmentSlot.CHEST, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> BRONZE_LEGGINGS = register("bronze_leggings", () -> new ArmorItem(AMArmorMaterials.BRONZE, EquipmentSlot.LEGS, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> BRONZE_BOOTS = register("bronze_boots", () -> new ArmorItem(AMArmorMaterials.BRONZE, EquipmentSlot.FEET, AMItems.getBasicSettings().fireproof()));
	
	public static RegistrySupplier<Item> STEEL_HELMET = register("steel_helmet", () -> new ArmorItem(AMArmorMaterials.STEEL, EquipmentSlot.HEAD, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STEEL_CHESTPLATE = register("steel_chestplate", () -> new ArmorItem(AMArmorMaterials.STEEL, EquipmentSlot.CHEST, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STEEL_LEGGINGS = register("steel_leggings", () -> new ArmorItem(AMArmorMaterials.STEEL, EquipmentSlot.LEGS, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> STEEL_BOOTS = register("steel_boots", () -> new ArmorItem(AMArmorMaterials.STEEL, EquipmentSlot.FEET, AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> FOOLS_GOLD_HELMET = register("fools_gold_helmet", () -> new ArmorItem(AMArmorMaterials.FOOLS_GOLD, EquipmentSlot.HEAD, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> FOOLS_GOLD_CHESTPLATE = register("fools_gold_chestplate", () -> new ArmorItem(AMArmorMaterials.FOOLS_GOLD, EquipmentSlot.CHEST, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> FOOLS_GOLD_LEGGINGS = register("fools_gold_leggings", () -> new ArmorItem(AMArmorMaterials.FOOLS_GOLD, EquipmentSlot.LEGS, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> FOOLS_GOLD_BOOTS = register("fools_gold_boots", () -> new ArmorItem(AMArmorMaterials.FOOLS_GOLD, EquipmentSlot.FEET, AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> METITE_HELMET = register("metite_helmet", () -> new ArmorItem(AMArmorMaterials.METITE, EquipmentSlot.HEAD, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METITE_CHESTPLATE = register("metite_chestplate", () -> new ArmorItem(AMArmorMaterials.METITE, EquipmentSlot.CHEST, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METITE_LEGGINGS = register("metite_leggings", () -> new ArmorItem(AMArmorMaterials.METITE, EquipmentSlot.LEGS, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METITE_BOOTS = register("metite_boots", () -> new ArmorItem(AMArmorMaterials.METITE, EquipmentSlot.FEET, AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> ASTERITE_HELMET = register("asterite_helmet", () -> new ArmorItem(AMArmorMaterials.ASTERITE, EquipmentSlot.HEAD, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTERITE_CHESTPLATE = register("asterite_chestplate", () -> new ArmorItem(AMArmorMaterials.ASTERITE, EquipmentSlot.CHEST, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTERITE_LEGGINGS = register("asterite_leggings", () -> new ArmorItem(AMArmorMaterials.ASTERITE, EquipmentSlot.LEGS, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> ASTERITE_BOOTS = register("asterite_boots", () -> new ArmorItem(AMArmorMaterials.ASTERITE, EquipmentSlot.FEET, AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> STELLUM_HELMET = register("stellum_helmet", () -> new ArmorItem(AMArmorMaterials.STELLUM, EquipmentSlot.HEAD, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> STELLUM_CHESTPLATE = register("stellum_chestplate", () -> new ArmorItem(AMArmorMaterials.STELLUM, EquipmentSlot.CHEST, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> STELLUM_LEGGINGS = register("stellum_leggings", () -> new ArmorItem(AMArmorMaterials.STELLUM, EquipmentSlot.LEGS, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> STELLUM_BOOTS = register("stellum_boots", () -> new ArmorItem(AMArmorMaterials.STELLUM, EquipmentSlot.FEET, AMItems.getBasicSettings().fireproof()));
	
	public static RegistrySupplier<Item> GALAXIUM_HELMET = register("galaxium_helmet", () -> new ArmorItem(AMArmorMaterials.GALAXIUM, EquipmentSlot.HEAD, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GALAXIUM_CHESTPLATE = register("galaxium_chestplate", () -> new ArmorItem(AMArmorMaterials.GALAXIUM, EquipmentSlot.CHEST, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GALAXIUM_LEGGINGS = register("galaxium_leggings", () -> new ArmorItem(AMArmorMaterials.GALAXIUM, EquipmentSlot.LEGS, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> GALAXIUM_BOOTS = register("galaxium_boots", () -> new ArmorItem(AMArmorMaterials.GALAXIUM, EquipmentSlot.FEET, AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> UNIVITE_HELMET = register("univite_helmet", () -> new AnimatedArmorItem(AMArmorMaterials.UNIVITE, EquipmentSlot.HEAD, AMItems.getBasicSettings().fireproof(), 18));
	public static RegistrySupplier<Item> UNIVITE_CHESTPLATE = register("univite_chestplate", () -> new AnimatedArmorItem(AMArmorMaterials.UNIVITE, EquipmentSlot.CHEST, AMItems.getBasicSettings().fireproof(), 18));
	public static RegistrySupplier<Item> UNIVITE_LEGGINGS = register("univite_leggings", () -> new ArmorItem(AMArmorMaterials.UNIVITE, EquipmentSlot.LEGS, AMItems.getBasicSettings().fireproof()));
	public static RegistrySupplier<Item> UNIVITE_BOOTS = register("univite_boots", () -> new AnimatedArmorItem(AMArmorMaterials.UNIVITE, EquipmentSlot.FEET, AMItems.getBasicSettings().fireproof(), 18));
	
	
	public static RegistrySupplier<Item> METEORIC_STEEL_HELMET = register("meteoric_steel_helmet", () -> new ArmorItem(AMArmorMaterials.METEORIC_STEEL, EquipmentSlot.HEAD, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METEORIC_STEEL_CHESTPLATE = register("meteoric_steel_chestplate", () -> new ArmorItem(AMArmorMaterials.METEORIC_STEEL, EquipmentSlot.CHEST, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METEORIC_STEEL_LEGGINGS = register("meteoric_steel_leggings", () -> new ArmorItem(AMArmorMaterials.METEORIC_STEEL, EquipmentSlot.LEGS, AMItems.getBasicSettings()));
	public static RegistrySupplier<Item> METEORIC_STEEL_BOOTS = register("meteoric_steel_boots", () -> new ArmorItem(AMArmorMaterials.METEORIC_STEEL, EquipmentSlot.FEET, AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> METEOR_METITE_CLUSTER = register("meteor_metite_cluster", () -> new Item(AMItems.getBasicSettings()));
	
	public static RegistrySupplier<Item> FIRE_EXTINGUISHER = register("fire_extinguisher", () -> new FireExtinguisherItem(AMItems.getBasicSettings().maxCount(1)));
	
	public static FoodComponent FOOLS_GOLD_APPLE_COMPONENT = new FoodComponent.Builder().hunger(2).saturationModifier(0.4F).statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 400, 1), 1.0F).statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 4800, 0), 1.0F).alwaysEdible().build();
	public static RegistrySupplier<Item> FOOLS_GOLD_APPLE = register("fools_gold_apple", () -> new Item(AMItems.getBasicSettings().food(FOOLS_GOLD_APPLE_COMPONENT).rarity(Rarity.RARE)));
	public static FoodComponent LEAD_APPLE_COMPONENT = new FoodComponent.Builder().hunger(2).saturationModifier(0.4F).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 40, 1), 1.0F).statusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 1000, 2), 1.0F).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 500, 1), 1.0F).alwaysEdible().build();
	public static RegistrySupplier<Item> LEAD_APPLE = register("lead_apple", () -> new Item(AMItems.getBasicSettings().food(LEAD_APPLE_COMPONENT).rarity(Rarity.RARE)));
	
	public static RegistrySupplier<Item> BLADES = register("blades", () -> new Item(getBasicSettings()));
	
	public static RegistrySupplier<Item> BIOFUEL = register("biofuel", () -> new Item(getBasicSettings()));
	
	public static RegistrySupplier<Item> MACHINE_CHASSIS = register("machine_chassis", () -> new Item(getBasicSettings()));
	
	public static RegistrySupplier<Item> BASIC_MACHINE_UPGRADE_KIT = register("basic_machine_upgrade_kit", () -> new Item(getBasicSettings()));
	public static RegistrySupplier<Item> ADVANCED_MACHINE_UPGRADE_KIT = register("advanced_machine_upgrade_kit", () -> new Item(getBasicSettings()));
	public static RegistrySupplier<Item> ELITE_MACHINE_UPGRADE_KIT = register("elite_machine_upgrade_kit", () -> new Item(getBasicSettings()));
	
	public static RegistrySupplier<Item> PRIMITIVE_PLATING = register("primitive_plating", () -> new Item(getBasicSettings()));
	public static RegistrySupplier<Item> BASIC_PLATING = register("basic_plating", () -> new Item(getBasicSettings()));
	public static RegistrySupplier<Item> ADVANCED_PLATING = register("advanced_plating", () -> new Item(getBasicSettings()));
	public static RegistrySupplier<Item> ELITE_PLATING = register("elite_plating", () -> new Item(getBasicSettings()));
	
	public static RegistrySupplier<Item> PORTABLE_TANK = register("portable_tank", () -> FluidItem.of(getBasicSettings().maxCount(1), AMConfig.get().portableTankFluid));
	public static RegistrySupplier<Item> LARGE_PORTABLE_TANK = register("large_portable_tank", () -> FluidItem.of(getBasicSettings().maxCount(1), AMConfig.get().largePortableTankFluid));
	
	public static RegistrySupplier<Item> PRIMITIVE_CIRCUIT = register("primitive_circuit", () -> new Item(getBasicSettings()));
	public static RegistrySupplier<Item> BASIC_CIRCUIT = register("basic_circuit", () -> new Item(getBasicSettings()));
	public static RegistrySupplier<Item> ADVANCED_CIRCUIT = register("advanced_circuit", () -> new Item(getBasicSettings()));
	public static RegistrySupplier<Item> ELITE_CIRCUIT = register("elite_circuit", () -> new Item(getBasicSettings()));
	
	public static RegistrySupplier<Item> PRIMITIVE_BATTERY = register("primitive_battery", () -> EnergyItem.of(getBasicSettings().maxCount(1), AMConfig.get().primitiveBatteryEnergy));
	public static RegistrySupplier<Item> BASIC_BATTERY = register("basic_battery", () -> EnergyItem.of(getBasicSettings().maxCount(1), AMConfig.get().basicBatteryEnergy));
	public static RegistrySupplier<Item> ADVANCED_BATTERY = register("advanced_battery", () -> EnergyItem.of(getBasicSettings().maxCount(1), AMConfig.get().advancedBatteryEnergy));
	public static RegistrySupplier<Item> ELITE_BATTERY = register("elite_battery", () -> EnergyItem.of(getBasicSettings().maxCount(1), AMConfig.get().eliteBatteryEnergy));
	public static RegistrySupplier<Item> CREATIVE_BATTERY = register("creative_battery", () -> EnergyItem.ofCreative(getBasicSettings().maxCount(1)));
	
	public static RegistrySupplier<Item> PRIMITIVE_BATTERY_PACK = register("primitive_battery_pack", () -> EnergyItem.of(getBasicSettings().maxCount(1), AMConfig.get().primitiveBatteryPackEnergy));
	public static RegistrySupplier<Item> BASIC_BATTERY_PACK = register("basic_battery_pack", () -> EnergyItem.of(getBasicSettings().maxCount(1), AMConfig.get().basicBatteryPackEnergy));
	public static RegistrySupplier<Item> ADVANCED_BATTERY_PACK = register("advanced_battery_pack", () -> EnergyItem.of(getBasicSettings().maxCount(1), AMConfig.get().advancedBatteryPackEnergy));
	public static RegistrySupplier<Item> ELITE_BATTERY_PACK = register("elite_battery_pack", () -> EnergyItem.of(getBasicSettings().maxCount(1), AMConfig.get().eliteBatteryPackEnergy));
	public static RegistrySupplier<Item> CREATIVE_BATTERY_PACK = register("creative_battery_pack", () -> EnergyItem.ofCreative(getBasicSettings().maxCount(1)));
	
	public static RegistrySupplier<Item> PRIMITIVE_DRILL = register("primitive_drill", () -> new DrillItem(AMToolMaterials.PRIMITIVE_DRILL, 1, -2.8F, AMConfig.get().primitiveDrillEnergy, getBasicSettings().maxCount(1)));
	public static RegistrySupplier<Item> BASIC_DRILL = register("basic_drill", () -> new DrillItem(AMToolMaterials.BASIC_DRILL, 1, -2.8F, AMConfig.get().basicDrillEnergy, getBasicSettings().maxCount(1)));
	public static RegistrySupplier<Item> ADVANCED_DRILL = register("advanced_drill", () -> new DrillItem(AMToolMaterials.ADVANCED_DRILL, 1, -2.8F, AMConfig.get().advancedDrillEnergy, getBasicSettings().maxCount(1)));
	public static RegistrySupplier<Item> ELITE_DRILL = register("elite_drill", () -> new DrillItem(AMToolMaterials.ELITE_DRILL, 1, -2.8F, AMConfig.get().eliteDrillEnergy, getBasicSettings().maxCount(1)));
	
	public static RegistrySupplier<Item> DRILL_HEAD = register("drill_head", () -> new Item(getBasicSettings()));
	
	public static RegistrySupplier<Item> PRIMITIVE_DRILL_BASE = register("primitive_drill_base", () -> new Item(getBasicSettings()));
	public static RegistrySupplier<Item> BASIC_DRILL_BASE = register("basic_drill_base", () -> new Item(getBasicSettings()));
	public static RegistrySupplier<Item> ADVANCED_DRILL_BASE = register("advanced_drill_base", () -> new Item(getBasicSettings()));
	public static RegistrySupplier<Item> ELITE_DRILL_BASE = register("elite_drill_base", () -> new Item(getBasicSettings()));
	
	public static RegistrySupplier<Item> HOLOGRAPHIC_CONNECTOR = register("holographic_connector", () -> new HolographicConnectorItem(getBasicSettings().maxCount(1)));
	
	public static RegistrySupplier<Item> GRAVITY_GAUNTLET = register("gravity_gauntlet", () -> new GravityGauntletItem(getBasicSettings().maxCount(1), AMConfig.get().gravityGauntletEnergy));
	
	public static void init() {
		postInit();
	}
	
	@ExpectPlatform
	public static void postInit() {
		throw new AssertionError();
	}

	/**
	 * @param name
	 *        Name of item instance to be registered
	 * @param item
	 *        Item instance to be registered
	 *
	 * @return Item instance registered
	 */
	public static <T extends Item> RegistrySupplier<T> register(String name, Supplier<T> item) {
		return register(AMCommon.id(name), item);
	}

	/**
	 * @param name
	 *        Identifier of item instance to be registered
	 * @param item
	 *        Item instance to be registered
	 *
	 * @return Item instance registered
	 */
	public static <T extends Item> RegistrySupplier<T> register(Identifier name, Supplier<T> item) {
		return AMCommon.registry(Registry.ITEM_KEY).registerSupplied(name, item);
	}

	public static Item.Settings getBasicSettings() {
		return new Item.Settings().group(AMItemGroups.ASTROMINE);
	}
}
