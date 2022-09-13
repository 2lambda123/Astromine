package com.github.mixinors.astromine.mixin.common;

import com.github.mixinors.astromine.registry.common.AMWorlds;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.WorldPreset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;

@Mixin(targets = "net.minecraft.world.gen.WorldPresets$Registrar")
public class WorldPresetsMixin {
	
	@Inject(method = "createPreset", at = @At("RETURN"))
	private void astromine$addAstromineDimensions(DimensionOptions dimensionOptions, CallbackInfoReturnable<WorldPreset> cir) {
		var preset = cir.getReturnValue();
		preset.dimensions = new HashMap<>(preset.dimensions);
		AMWorlds.DIMENSIONS.forEach((key, value) -> preset.dimensions.put(key, value.get()));
	}
}
