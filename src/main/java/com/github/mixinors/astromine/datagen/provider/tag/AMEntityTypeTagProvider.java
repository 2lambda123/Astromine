package com.github.mixinors.astromine.datagen.provider.tag;

import com.github.mixinors.astromine.datagen.DatagenLists;
import com.github.mixinors.astromine.registry.common.AMTagKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

public class AMEntityTypeTagProvider extends FabricTagProvider.EntityTypeTagProvider {
	public AMEntityTypeTagProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}
	
	@Override
	protected void generateTags() {
		var fishTag = getOrCreateTagBuilder(AMTagKeys.EntityTypeTags.FISH);
		for (var entityType : DatagenLists.EntityTypeLists.FISH) {
			fishTag.add(entityType);
		}
		
		var squidsTag = getOrCreateTagBuilder(AMTagKeys.EntityTypeTags.SQUIDS);
		for (var entityType : DatagenLists.EntityTypeLists.SQUIDS) {
			squidsTag.add(entityType);
		}
		
		var guardiansTag = getOrCreateTagBuilder(AMTagKeys.EntityTypeTags.GUARDIANS);
		for (var entityType : DatagenLists.EntityTypeLists.GUARDIANS) {
			guardiansTag.add(entityType);
		}
		
		var skeletonsTag = getOrCreateTagBuilder(AMTagKeys.EntityTypeTags.SKELETONS);
		for (var entityType : DatagenLists.EntityTypeLists.SKELETONS) {
			skeletonsTag.add(entityType);
		}
		
		var zombiesTag = getOrCreateTagBuilder(AMTagKeys.EntityTypeTags.ZOMBIES);
		for (var entityType : DatagenLists.EntityTypeLists.ZOMBIES) {
			zombiesTag.add(entityType);
		}
		
		var spaceSlimesTag = getOrCreateTagBuilder(AMTagKeys.EntityTypeTags.SPACE_SLIMES);
		for (var entityType : DatagenLists.EntityTypeLists.SPACE_SLIMES) {
			spaceSlimesTag.add(entityType);
		}
		
		var doesNotBreatheTag = getOrCreateTagBuilder(AMTagKeys.EntityTypeTags.DOES_NOT_BREATHE);
		for (var entityType : DatagenLists.EntityTypeLists.DOES_NOT_BREATHE_ENTITY_TYPES) {
			doesNotBreatheTag.add(entityType);
		}
		for (var tag : DatagenLists.EntityTypeTagLists.DOES_NOT_BREATHE_TAGS) {
			doesNotBreatheTag.addTag(tag);
		}
		
		var canBreatheWaterTag = getOrCreateTagBuilder(AMTagKeys.EntityTypeTags.CAN_BREATHE_WATER);
		for (var entityType : DatagenLists.EntityTypeLists.CAN_BREATHE_WATER_ENTITY_TYPES) {
			canBreatheWaterTag.add(entityType);
		}
		for (var tag : DatagenLists.EntityTypeTagLists.CAN_BREATHE_WATER_TAGS) {
			canBreatheWaterTag.addTag(tag);
		}
		
		var canBreatheLavaTag = getOrCreateTagBuilder(AMTagKeys.EntityTypeTags.CAN_BREATHE_LAVA);
		for (var entityType : DatagenLists.EntityTypeLists.CAN_BREATHE_LAVA_ENTITY_TYPES) {
			canBreatheLavaTag.add(entityType);
		}
		
		var cannotBreatheOxygenTag = getOrCreateTagBuilder(AMTagKeys.EntityTypeTags.CANNOT_BREATHE_OXYGEN);
		for (var tag : DatagenLists.EntityTypeTagLists.CANNOT_BREATHE_OXYGEN_TAGS) {
			cannotBreatheOxygenTag.addTag(tag);
		}
	}
}
