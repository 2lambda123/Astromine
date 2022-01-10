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

package com.github.mixinors.astromine.datagen.provider.tag;

import java.util.List;
import java.util.Map;

import com.github.mixinors.astromine.AMCommon;
import com.github.mixinors.astromine.datagen.AMDatagen;
import com.github.mixinors.astromine.datagen.family.block.AMBlockFamilies;
import com.github.mixinors.astromine.datagen.family.material.MaterialFamilies;
import com.github.mixinors.astromine.datagen.family.material.MaterialFamily;
import com.github.mixinors.astromine.datagen.family.material.variant.ItemVariant;
import com.github.mixinors.astromine.registry.common.AMItems;
import org.jetbrains.annotations.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import com.shnupbups.piglib.Piglib;

public class AMItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public static final Map<List<ItemVariant>, Identifier> GENERIC_TAGS = Map.of(
			AMDatagen.CLUSTER_VARIANTS, AMDatagen.createCommonTagId("clusters"),
			AMDatagen.ARMOR_VARIANTS, AMDatagen.createCommonTagId("armor"),
			AMDatagen.TOOL_VARIANTS, AMDatagen.createCommonTagId("tools")
	);

	public static final List<Identifier> COPY = List.of(
			AMDatagen.createCommonTagId("yellow_sandstones"),
			AMDatagen.createCommonTagId("red_sandstones"),
			AMDatagen.createCommonTagId("sandstones"),
			AMDatagen.createCommonTagId("quartz_blocks"),
			AMDatagen.createCommonTagId("unwaxed_copper_blocks"),
			AMDatagen.createCommonTagId("waxed_copper_blocks"),
			AMDatagen.createCommonTagId("copper_blocks"),
			AMDatagen.createCommonTagId("unwaxed_cut_copper"),
			AMDatagen.createCommonTagId("waxed_cut_copper"),
			AMDatagen.createCommonTagId("cut_copper"),
			AMDatagen.createCommonTagId("purpur_blocks"),
			AMDatagen.createCommonTagId("mushrooms"),
			AMDatagen.createCommonTagId("mushroom_blocks"),
			AMDatagen.createCommonTagId("nether_fungi"),
			AMDatagen.createCommonTagId("nether_roots"),
			AMDatagen.createCommonTagId("nether_vines"),
			AMDatagen.createCommonTagId("pumpkins"),
			AMDatagen.createCommonTagId("gourds"),
			AMDatagen.createCommonTagId("weeping_vines"),
			AMDatagen.createCommonTagId("twisting_vines")
	);

	public static final List<Item> DRILLS = List.of(
			AMItems.PRIMITIVE_DRILL.get(),
			AMItems.BASIC_DRILL.get(),
			AMItems.ADVANCED_DRILL.get(),
			AMItems.ELITE_DRILL.get()
	);

	public static final List<Tag.Identified<Item>> ONE_BIOFUEL_TAGS = List.of(
			ItemTags.SMALL_FLOWERS,
			ItemTags.LEAVES,
			AMDatagen.createCommonItemTag("mushrooms"),
			AMDatagen.createCommonItemTag("nether_fungi"),
			AMDatagen.createCommonItemTag("nether_roots"),
			AMDatagen.createCommonItemTag("vines"),
			AMDatagen.createCommonItemTag("berries"),
			AMDatagen.createCommonItemTag("seeds")
	);

	public static final List<Item> ONE_BIOFUEL_ITEMS = List.of(
			Items.GRASS,
			Items.FERN,
			Items.DEAD_BUSH,
			Items.SEAGRASS,
			Items.SEA_PICKLE,
			Items.NETHER_SPROUTS,
			Items.LILY_PAD,
			Items.MELON_SLICE,
			Items.WHEAT,
			Items.APPLE,
			Items.COOKIE,
			Items.SUGAR_CANE,
			Items.KELP,
			Items.DRIED_KELP,
			Items.BAMBOO,
			Items.CARROT,
			Items.POTATO,
			Items.BAKED_POTATO,
			Items.POISONOUS_POTATO,
			Items.BEETROOT,
			Items.GLOW_LICHEN,
			Items.SUGAR,
			Items.HONEYCOMB
	);

	public static final List<Tag.Identified<Item>> TWO_BIOFUEL_TAGS = List.of(
			ItemTags.TALL_FLOWERS,
			ItemTags.SAPLINGS
	);

	public static final List<Item> TWO_BIOFUEL_ITEMS = List.of(
			Items.PORKCHOP,
			Items.COOKED_PORKCHOP,
			Items.BEEF,
			Items.COOKED_BEEF,
			Items.CHICKEN,
			Items.COOKED_CHICKEN,
			Items.ROTTEN_FLESH,
			Items.SPIDER_EYE,
			Items.FERMENTED_SPIDER_EYE,
			Items.RABBIT,
			Items.COOKED_RABBIT,
			Items.MUTTON,
			Items.COOKED_MUTTON,
			Items.CACTUS,
			Items.TALL_GRASS,
			Items.LARGE_FERN,
			Items.MOSS_CARPET,
			Items.LEATHER,
			Items.EGG,
			Items.CHORUS_FRUIT,
			Items.POPPED_CHORUS_FRUIT,
			Items.CHORUS_FLOWER,
			Items.CHORUS_PLANT
	);

	public static final List<Tag.Identified<Item>> FOUR_BIOFUEL_TAGS = List.of(
			ItemTags.FISHES,
			AMDatagen.createCommonItemTag("metal_apples")
	);

	public static final List<Item> FOUR_BIOFUEL_ITEMS = List.of(
			Items.CAKE,
			Items.SPORE_BLOSSOM
	);

	public static final List<Tag.Identified<Item>> NINE_BIOFUEL_TAGS = List.of(
			AMDatagen.createCommonItemTag("gourds")
	);

	public static final List<Item> NINE_BIOFUEL_ITEMS = List.of(
			Items.HONEY_BLOCK,
			Items.HONEYCOMB_BLOCK,
			Items.PUMPKIN_PIE,
			Items.MOSS_BLOCK
	);

	public AMItemTagProvider(FabricDataGenerator dataGenerator, @Nullable BlockTagProvider blockTagProvider) {
		super(dataGenerator, blockTagProvider);
	}

	@Override
	protected void generateTags() {
		var beaconPaymentTagBuilder = getOrCreateTagBuilder(ItemTags.BEACON_PAYMENT_ITEMS);
		var piglinLovedTagBuilder = getOrCreateTagBuilder(ItemTags.PIGLIN_LOVED);
		var piglinLovedNuggetsTagBuilder = getOrCreateTagBuilder(Piglib.PIGLIN_LOVED_NUGGETS);
		var piglinBarteringItemsTagBuilder = getOrCreateTagBuilder(Piglib.PIGLIN_BARTERING_ITEMS);
		var piglinSafeArmorTagBuilder = getOrCreateTagBuilder(Piglib.PIGLIN_SAFE_ARMOR);

		MaterialFamilies.getFamilies().filter(MaterialFamily::shouldGenerateTags).forEachOrdered(family -> {
			family.getItemTags().forEach((variant, tag) -> {
				getOrCreateTagBuilder(tag).add(family.getVariant(variant));

				if (family.hasAlias()) {
					getOrCreateTagBuilder(family.getAliasTag(variant)).addTag(tag);
				}

				if (family.isPiglinLoved()) {
					if (variant.equals(ItemVariant.NUGGET)) {
						piglinLovedNuggetsTagBuilder.addTag(tag);

						if (family.hasAlias()) {
							piglinLovedNuggetsTagBuilder.addTag(family.getAliasTag(variant));
						}
					} else {
						piglinLovedTagBuilder.addTag(tag);

						if (family.hasAlias()) {
							piglinLovedTagBuilder.addTag(family.getAliasTag(variant));
						}
					}
				}

				if (variant.hasTag()) {
					getOrCreateTagBuilder(variant.getTag()).addTag(tag);

					if (family.hasAlias()) {
						getOrCreateTagBuilder(variant.getTag()).addTag(family.getAliasTag(variant));
					}
				}
			});
			family.getBlockItemTags().forEach((variant, tag) -> {
				var blockTag = family.getTag(variant);

				copy(blockTag, tag);

				if (family.hasAlias()) {
					copy(family.getAliasTag(variant), family.getAliasItemTag(variant));
				}

				if (family.isPiglinLoved()) {
					piglinLovedTagBuilder.addTag(tag);
				}

				if (variant.hasTag()) {
					getOrCreateTagBuilder(variant.getItemTag()).addTag(tag);

					if (family.hasAlias()) {
						getOrCreateTagBuilder(variant.getItemTag()).addTag(family.getAliasItemTag(variant));
					}
				}
			});

			if (family.hasAnyBlockVariants(AMDatagen.ORE_VARIANTS)) {
				var oresBlockTag = family.getBlockTag("ores");
				var oresItemTag = family.getItemTag("ores");
				copy(oresBlockTag, oresItemTag);

				if (family.hasAlias()) {
					copy(family.getAliasBlockTag("ores"), family.getAliasItemTag("ores"));
				}
			}

			if (family.hasAnyItemVariants(AMDatagen.CLUSTER_VARIANTS)) {
				var clustersTag = family.getItemTag("clusters");
				var clustersTagBuilder = getOrCreateTagBuilder(clustersTag);
				AMDatagen.CLUSTER_VARIANTS.forEach((variant) -> {
					if (family.hasVariant(variant)) {
						clustersTagBuilder.addTag(family.getTag(variant));
					}
				});
				if (family.hasAlias()) {
					getOrCreateTagBuilder(family.getAliasItemTag("clusters")).addTag(clustersTag);
				}
			}

			if (family.hasAnyItemVariants(AMDatagen.EQUIPMENT_VARIANTS)) {
				var armorTag = family.getItemTag("armor");
				var toolsTag = family.getItemTag("tools");
				
				var salvageablesTag = family.getItemTag("salvageables");
				var salvageablesTagBuilder = getOrCreateTagBuilder(salvageablesTag);

				if (family.hasAnyItemVariants(AMDatagen.ARMOR_VARIANTS)) {
					var armorTagBuilder = getOrCreateTagBuilder(armorTag);
					AMDatagen.ARMOR_VARIANTS.forEach((variant) -> {
						if (family.hasVariant(variant)) {
							armorTagBuilder.addTag(family.getTag(variant));
						}
					});

					if (family.isPiglinLoved()) {
						piglinSafeArmorTagBuilder.addTag(armorTag);

						if (family.hasAlias()) {
							piglinSafeArmorTagBuilder.addTag(family.getAliasItemTag("armor"));
						}
					}

					salvageablesTagBuilder.addTag(armorTag);

					if (family.hasAlias()) {
						getOrCreateTagBuilder(family.getAliasItemTag("armor")).addTag(armorTag);
					}
				}

				if (family.hasAnyItemVariants(AMDatagen.TOOL_VARIANTS)) {
					var toolsTagBuilder = getOrCreateTagBuilder(toolsTag);
					AMDatagen.TOOL_VARIANTS.forEach((variant) -> {
						if (family.hasVariant(variant)) {
							toolsTagBuilder.addTag(family.getTag(variant));
						}
					});

					salvageablesTagBuilder.addTag(toolsTag);

					if (family.hasAlias()) {
						getOrCreateTagBuilder(family.getAliasItemTag("tools")).addTag(toolsTag);
					}
				}

				if (family.hasVariant(ItemVariant.HORSE_ARMOR)) {
					salvageablesTagBuilder.add(family.getVariant(ItemVariant.HORSE_ARMOR));
				}

				if (family.hasAlias()) {
					getOrCreateTagBuilder(family.getAliasItemTag("salvageables")).addTag(salvageablesTag);
				}
			}

			if (family.isValidForBeacon()) {
				beaconPaymentTagBuilder.addTag(family.getBaseTag());

				if (family.hasAlias()) {
					beaconPaymentTagBuilder.addTag(family.getAliasBaseTag());
				}
			}

			if (family.isPiglinLoved()) {
				piglinBarteringItemsTagBuilder.addTag(family.getBaseTag());

				if (family.hasAlias()) {
					piglinBarteringItemsTagBuilder.addTag(family.getAliasBaseTag());
				}
			}
		});

		AMBlockFamilies.getFamilies().forEachOrdered(family -> family.getVariants().forEach((variant, block) -> {
			if (AMDatagen.VANILLA_ITEM_TAG_VARIANTS.containsKey(variant)) {
				getOrCreateTagBuilder(AMDatagen.createItemTag(AMDatagen.VANILLA_ITEM_TAG_VARIANTS.get(variant))).add(block.asItem());
			}
		}));

		AMDatagen.FLUIDS.forEach((fluid) -> {
			var bucketTagBuilder = getOrCreateTagBuilder(AMDatagen.createCommonItemTag(Registry.FLUID.getId(fluid.getStill()).getPath() + "_buckets"));
			bucketTagBuilder.add(fluid.getBucketItem());
		});

		copy(AMDatagen.createCommonBlockTag("ores"), AMDatagen.createCommonItemTag("ores"));

		GENERIC_TAGS.forEach((variantSet, id) -> {
			var tag = getOrCreateTagBuilder(AMDatagen.createItemTag(id));
			variantSet.forEach((variant) -> {
				if (variant.hasTag()) tag.addTag(variant.getTag());
			});
		});

		AMDatagen.TOOL_VARIANTS.forEach((variant) -> getOrCreateTagBuilder(AMDatagen.createItemTag(new Identifier("fabric", variant.getTagPath()))).addTag(variant.getTag()));
		
		var drillsTagBuilder = getOrCreateTagBuilder(AMDatagen.createItemTag(AMCommon.id("drills")));
		DRILLS.forEach(drillsTagBuilder::add);

		COPY.forEach((id -> copy(AMDatagen.createBlockTag(id), AMDatagen.createItemTag(id))));

		getOrCreateTagBuilder(AMDatagen.createCommonItemTag("gold_apples"))
				.add(Items.ENCHANTED_GOLDEN_APPLE);

		getOrCreateTagBuilder(AMDatagen.createCommonItemTag("seeds"))
				.add(Items.WHEAT_SEEDS)
				.add(Items.BEETROOT_SEEDS)
				.add(Items.MELON_SEEDS)
				.add(Items.PUMPKIN_SEEDS);

		getOrCreateTagBuilder(AMDatagen.createCommonItemTag("berries"))
				.add(Items.SWEET_BERRIES)
				.add(Items.GLOW_BERRIES);

		getOrCreateTagBuilder(AMDatagen.createCommonItemTag("vines"))
				.addTag(AMDatagen.createCommonItemTag("nether_vines"))
				.add(Items.VINE);

		getOrCreateTagBuilder(AMDatagen.createCommonItemTag("biofuel"))
				.add(AMItems.BIOFUEL.get());

		getOrCreateTagBuilder(AMDatagen.createCommonItemTag("carbon_dusts"))
				.addTag(AMDatagen.createCommonItemTag("coal_dusts"))
				.addTag(AMDatagen.createCommonItemTag("charcoal_dusts"));
		
		var oneBiofuelTagBuilder = getOrCreateTagBuilder(AMDatagen.createCommonItemTag("one_biofuel"));
		ONE_BIOFUEL_ITEMS.forEach(oneBiofuelTagBuilder::add);
		ONE_BIOFUEL_TAGS.forEach(oneBiofuelTagBuilder::addTag);
		
		var twoBiofuelTagBuilder = getOrCreateTagBuilder(AMDatagen.createCommonItemTag("two_biofuel"));
		TWO_BIOFUEL_ITEMS.forEach(twoBiofuelTagBuilder::add);
		TWO_BIOFUEL_TAGS.forEach(twoBiofuelTagBuilder::addTag);
		
		var fourBiofuelTagBuilder = getOrCreateTagBuilder(AMDatagen.createCommonItemTag("four_biofuel"));
		FOUR_BIOFUEL_ITEMS.forEach(fourBiofuelTagBuilder::add);
		FOUR_BIOFUEL_TAGS.forEach(fourBiofuelTagBuilder::addTag);
		
		var nineBiofuelTagBuilder = getOrCreateTagBuilder(AMDatagen.createCommonItemTag("nine_biofuel"));
		NINE_BIOFUEL_ITEMS.forEach(nineBiofuelTagBuilder::add);
		NINE_BIOFUEL_TAGS.forEach(nineBiofuelTagBuilder::addTag);
	}
}
