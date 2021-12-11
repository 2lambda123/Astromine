package com.github.mixinors.astromine.mixin.common;

import com.github.mixinors.astromine.common.recipe.condition.manager.ConditionalRecipeManager;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerResourceManager.class)
public class ServerResourceManagerMixin {
	@Mutable @Shadow @Final private ReloadableResourceManager resourceManager;
	
//	@Inject(at = @At("RETURN"), method = "<init>")
//	void astromine_init( DynamicRegistryManager dynamicRegistryManager, CommandManager.RegistrationEnvironment registrationEnvironment, CallbackInfo ci) {
//		resourceManager.registerReloader(new ConditionalRecipeManager((ServerResourceManager) (Object) this));
//	}
}
