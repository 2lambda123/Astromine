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

package com.github.mixinors.astromine.mixin.common.common;

import com.github.mixinors.astromine.common.component.base.AtmosphereComponent;
import com.github.mixinors.astromine.registry.common.AMNetworks;

import me.shedaniel.architectury.networking.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.fluid.Fluid;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import com.github.mixinors.astromine.common.access.EntityAccessor;
import com.github.mixinors.astromine.client.atmosphere.ClientAtmosphereManager;
import com.github.mixinors.astromine.common.entity.GravityEntity;
import com.github.mixinors.astromine.common.registry.DimensionLayerRegistry;
import com.github.mixinors.astromine.registry.common.AMTags;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;

import java.util.ArrayList;

@Mixin(Entity.class)
public abstract class EntityMixin implements GravityEntity, EntityAccessor {
	@Shadow
	public World world;
	
	@Shadow
	protected boolean firstUpdate;
	
	@Shadow
	protected Object2DoubleMap<Tag<Fluid>> fluidHeight;
	
	private int astromine_lastY = 0;
	
	private Entity astromine_lastVehicle = null;
	
	private TeleportTarget astromine_nextTeleportTarget = null;
	
	private World astromine_lastWorld = null;

	@Shadow
	public abstract BlockPos getBlockPos();

	@Shadow
	public abstract boolean updateMovementInFluid(Tag<Fluid> tag, double d);

	@Shadow
	public abstract double getFluidHeight(Tag<Fluid> fluid);

	@Shadow
	public abstract boolean isSubmergedIn(Tag<Fluid> tag);
	
	@Shadow public int age;
	
	@ModifyVariable(at = @At("HEAD"), method = "handleFallDamage(FF)Z", index = 1)
	float astromine_handleFallDamage(float damageMultiplier) {
		return (float) (damageMultiplier * astromine_getGravity() * astromine_getGravity());
	}

	@Override
	public double astromine_getGravity() {
		var world = ((Entity) (Object) this).world;
		
		return astromine_getGravity(world);
	}

	@Override
	public boolean astromine_isInIndustrialFluid() {
		return !this.firstUpdate && this.fluidHeight.getDouble(AMTags.INDUSTRIAL_FLUID) > 0.0D;
	}

	@Inject(at = @At("HEAD"), method = "tickNetherPortal()V")
	void astromine_tickNetherPortal(CallbackInfo callbackInformation) {
		var entity = (Entity) (Object) this;

		if ((int) entity.getPos().getY() != astromine_lastY && !entity.world.isClient && entity.getVehicle() == null) {
			astromine_lastY = (int) entity.getPos().getY();

			int bottomPortal = DimensionLayerRegistry.INSTANCE.getLevel(DimensionLayerRegistry.Type.BOTTOM, entity.world.getRegistryKey());
			int topPortal = DimensionLayerRegistry.INSTANCE.getLevel(DimensionLayerRegistry.Type.TOP, entity.world.getRegistryKey());

			if (astromine_lastY <= bottomPortal && bottomPortal != Integer.MIN_VALUE) {
				var worldKey = RegistryKey.of(Registry.DIMENSION, DimensionLayerRegistry.INSTANCE.getDimension(DimensionLayerRegistry.Type.BOTTOM, entity.world.getRegistryKey()).getValue());

				astromine_teleport(entity, worldKey, DimensionLayerRegistry.Type.BOTTOM);
			} else if (astromine_lastY >= topPortal && topPortal != Integer.MIN_VALUE) {
				var worldKey = RegistryKey.of(Registry.DIMENSION, DimensionLayerRegistry.INSTANCE.getDimension(DimensionLayerRegistry.Type.TOP, entity.world.getRegistryKey()).getValue());

				astromine_teleport(entity, worldKey, DimensionLayerRegistry.Type.TOP);
			}
		}

		if (entity.getVehicle() != null)
			astromine_lastVehicle = null;
		if (astromine_lastVehicle != null) {
			entity.startRiding(astromine_lastVehicle);
			astromine_lastVehicle = null;
		}
	}

	void astromine_teleport(Entity entity, RegistryKey<World> destinationKey, DimensionLayerRegistry.Type type) {
		var serverWorld = entity.world.getServer().getWorld(destinationKey);

		var existingPassengers = entity.getPassengerList();

		var entries = new ArrayList<DataTracker.Entry>();
		
		for (var entry : entity.getDataTracker().getAllEntries()) {
			entries.add(entry.copy());
		}

		astromine_nextTeleportTarget = DimensionLayerRegistry.INSTANCE.getPlacer(type, entity.world.getRegistryKey()).placeEntity(entity);
		var newEntity = entity.moveToWorld(serverWorld);

		for (var entry : entries) {
			newEntity.getDataTracker().set(entry.getData(), entry.get());
		}

		for (var existingEntity : existingPassengers) {
			((EntityMixin) (Object) existingEntity).astromine_lastVehicle = newEntity;
		}
	}

	@Inject(method = "getTeleportTarget", at = @At("HEAD"), cancellable = true)
	protected void astromine_getTeleportTarget(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> cir) {
		if (astromine_nextTeleportTarget != null) {
			cir.setReturnValue(astromine_nextTeleportTarget);
			
			astromine_nextTeleportTarget = null;
		}
	}

	@Inject(at = @At("HEAD"), method = "tick()V")
	void astromine_tick1(CallbackInfo ci) {
		// TODO: Make this synchronize all visible chunks around the player.
		if ((Object) this instanceof ServerPlayerEntity player && world != astromine_lastWorld) {
			astromine_lastWorld = world;
			
			NetworkManager.sendToPlayer(player, AMNetworks.GAS_ERASED, ClientAtmosphereManager.ofGasErased());

			var atmosphereComponent = AtmosphereComponent.from(world.getChunk(getBlockPos()));

			atmosphereComponent.getVolumes().forEach(((blockPos, volume) -> {
				NetworkManager.sendToPlayer(player, AMNetworks.GAS_ADDED, ClientAtmosphereManager.ofGasAdded(blockPos, volume));
			}));
		}
	}

	@Inject(method = "updateWaterState", at = @At("RETURN"), cancellable = true)
	private void astromine_updateWaterState(CallbackInfoReturnable<Boolean> cir) {
		if (this.updateMovementInFluid(AMTags.INDUSTRIAL_FLUID, 0.014)) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "isInLava", at = @At("RETURN"), cancellable = true)
	protected void astromine_isInLava(CallbackInfoReturnable<Boolean> cir) {}
}
