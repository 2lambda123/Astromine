package com.github.mixinors.astromine.mixin.fabric.common;

import com.github.mixinors.astromine.compat.techreborn.common.component.provider.TREnergyComponentProvider;
import com.github.mixinors.astromine.common.component.general.provider.EnergyComponentProvider;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EnergyComponentProvider.class)
public interface EnergyComponentProviderMixin extends TREnergyComponentProvider {}
