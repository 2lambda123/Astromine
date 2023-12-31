package com.github.mixinors.astromine.common.util.fabric;

import com.github.mixinors.astromine.AMCommon;
import com.github.mixinors.astromine.client.fluid.render.ExtendedFluidRenderHandler;
import com.github.mixinors.astromine.common.fluid.ExtendedFluid;
import com.github.mixinors.astromine.common.resource.ExtendedFluidResourceListener;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.texture.Sprite;
import net.minecraft.resource.ResourceType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public class ClientUtilsImpl {
	public static void registerExtendedFluid(String name, int tint, RegistrySupplier<ExtendedFluid.Still> still, RegistrySupplier<ExtendedFluid.Flowing> flowing) {
		var stillSpriteId = new Identifier("block/water_still");
		var flowingSpriteId = new Identifier("block/water_flow");
		var id = AMCommon.id(name + "_reload_listener");
		
		var sprites = new Sprite[2];
		
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(($, registry) -> {
			registry.register(stillSpriteId);
			registry.register(flowingSpriteId);
		});
		
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new ExtendedFluidResourceListener(sprites, id, flowingSpriteId, stillSpriteId));
		
		var handler = new ExtendedFluidRenderHandler(sprites, tint);
		
		FluidRenderHandlerRegistry.INSTANCE.register(still.get(), handler);
		FluidRenderHandlerRegistry.INSTANCE.register(flowing.get(), handler);
	}
}
