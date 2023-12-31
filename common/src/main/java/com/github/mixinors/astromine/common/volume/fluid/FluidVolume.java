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

package com.github.mixinors.astromine.common.volume.fluid;

import com.github.mixinors.astromine.common.volume.base.Volume;
import com.github.mixinors.astromine.common.util.NumberUtils;
import com.github.mixinors.astromine.registry.common.AMConfig;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import com.github.mixinors.astromine.common.component.base.FluidComponent;
import io.netty.buffer.ByteBuf;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * A {@link Volume} of {@link Fluid}s, whose amount
 * and size are represented by a {@link Long}
 *
 * It is not an inventory, thus it is recommended to use a {@link FluidComponent}.
 *
 * A few utility methods for instantiation are provided, that being:
 *
 * - {@link #ofEmpty()}, returning an empty volume.
 *
 * - {@link #ofEmpty(Runnable)}, returning an empty volume, with an a listener.
 *
 * - {@link #of(long, Fluid)}, returning a volume with the specified
 * amount as a {@link long}, and the given {@link Fluid}.
 *
 * - {@link #of(long, Fluid, Runnable)}, returning a volume with the
 * specified amount as a {@link long}, the given {@link Fluid},
 * and a listener as a {@link Runnable}.
 *
 * - {@link #of(long, long, Fluid)}, returning a volume with the
 * specified amount as a {@link long}, size as a {@link long},
 * and the given {@link Fluid}.
 *
 *- {@link #of(long, long, Fluid, Runnable)}, returning a value with
 * the specified amount as a {@link long}, size as a {@link long},
 * the given {@link Fluid}, and a listener as a {@link Runnable}.
 *
 * It is recommended that you always set the volume's listener to
 * {@link FluidComponent#updateListeners()}, if using one.
 *
 * Serialization and deserialization methods are provided for:
 * - {@link CompoundTag} - through {@link #toTag()} and {@link #fromTag(CompoundTag)}.
 * - {@link JsonElement} - through {@link #toJson()} and {@link #fromJson(JsonElement)}.
 * - {@link ByteBuf} - through {@link #toPacket(PacketByteBuf)} and {@link #fromPacket(PacketByteBuf)}.
 */
public class FluidVolume extends Volume<Long> {
	public static final long BUCKET = 81000;
	public static final long BOTTLE = BUCKET / 3L;
	
	public static final DecimalFormat FORMAT = new DecimalFormat("#0.00");

	public static long getTransfer() {
		return AMConfig.get().fluidTransfer;
	}

	private Fluid fluid;

	/** Instantiates a {@link FluidVolume}. */
	protected FluidVolume(long amount, long size, Fluid fluid) {
		super(amount, size);
		this.fluid = fluid;
	}

	/** Instantiates a {@link FluidVolume} and a listener. */
	protected FluidVolume(long amount, long size, Fluid fluid, Runnable runnable) {
		super(amount, size, runnable);
		this.fluid = fluid;
	}

	/** Instantiates an empty {@link FluidVolume}. */
	public static FluidVolume ofEmpty() {
		return new FluidVolume(0L, Long.MAX_VALUE, Fluids.EMPTY);
	}

	/** Instantiates an empty {@link FluidVolume} with a listener. */
	public static FluidVolume ofEmpty(Runnable runnable) {
		return new FluidVolume(0L, Long.MAX_VALUE, Fluids.EMPTY, runnable);
	}

	/** Instantiates a {@link FluidVolume}. */
	public static FluidVolume of(long amount, Fluid fluid) {
		return new FluidVolume(amount, Long.MAX_VALUE, fluid);
	}

	/** Instantiates a {@link FluidVolume} and a listener. */
	public static FluidVolume of(long amount, Fluid fluid, Runnable runnable) {
		return new FluidVolume(amount, Long.MAX_VALUE, fluid, runnable);
	}

	/** Instantiates a {@link FluidVolume}. */
	public static FluidVolume of(long amount, long size, Fluid fluid) {
		return new FluidVolume(amount, size, fluid);
	}

	/** Instantiates a {@link FluidVolume} and a listener. */
	public static FluidVolume of(long amount, long size, Fluid fluid, Runnable runnable) {
		return new FluidVolume(amount, size, fluid, runnable);
	}

	/** Asserts the equality of the volume's fluids. */
	public static boolean fluidsEqual(FluidVolume first, FluidVolume second) {
		return first.getFluid().equals(second.getFluid());
	}

	/** Asserts the equality of the volume's fluids. */
	public boolean fluidEquals(FluidVolume volume) {
		return getFluid().equals(volume.getFluid());
	}

	/** Returns the volume's fluid. */
	public Fluid getFluid() {
		return this.fluid;
	}

	/** Sets the volume's fluid to the given one. */
	public void setFluid(Fluid fluid) {
		this.fluid = fluid;
	}

	/** Returns the identifier of the volume's fluid. */
	public Identifier getFluidId() {
		return Registry.FLUID.getId(getFluid());
	}

	/** Asserts whether the fluid can be inserted into this volume or not. */
	public boolean test(Fluid fluid) {
		return (this.fluid == fluid && fluid != Fluids.EMPTY) || this.isEmpty();
	}

	/** Asserts whether this volume can be inserted into the given one or not. */
	public boolean test(FluidVolume volume) {
		return volume.test(getFluid()) && volume.hasAvailable(getAmount());
	}

	/** Asserts whether this volume is empty or not. */
	@Override
	public boolean isEmpty() {
		return super.isEmpty() || getFluid() == Fluids.EMPTY;
	}

	/**
	 * Attempts to give the given {@link V} volume a {@link Long} of this
	 * volume's content.
	 *
	 * If we both have the same fluid, the target receives as much as possible.
	 *
	 * If our fluids are not the same, but the theirs is {@link Fluids#EMPTY}
	 * empty, change their fluid to {@link #fluid} ours. They then receive
	 * as much as possible.
	 *
	 * If our fluids not the same, and theirs is not {@link Fluids#EMPTY},
	 * do nothing.
	 *
	 * The amount transferred is the {@link Math#min(long, long)} between
	 * the target's available space, our amount, and the specified amount.
	 */
	@Override
	public <V extends Volume<Long>> void give(V volume, Long l) {
		if (!(volume instanceof FluidVolume))
			return;

		if (((FluidVolume) volume).getFluid() != getFluid()) {
			if (volume.isEmpty()) {
				((FluidVolume) volume).setFluid((this).getFluid());
			} else {
				return;
			}
		}

		var amount = Math.min(volume.getSize() - volume.getAmount(), Math.min(getAmount(), l));

		if (amount > 0L) {
			volume.setAmount(volume.getAmount() + amount);
			setAmount(getAmount() - amount);
		}

		if (isEmpty()) {
			setFluid(Fluids.EMPTY);
			setAmount(0L);
		}
	}

	/** Gives this volume the minimum between the available amount and the
	 * specified amount. */
	@Override
	public void give(Long l) {
		setAmount(Math.min(getSize(), getAmount() + l));
	}

	/**
	 * Attempts to take the given {@link Long} from a {@link V}
	 * volume's content.
	 *
	 * If we both have the same fluid, we receive as much as possible.
	 *
	 * If our fluids are not the same, but the ours is {@link Fluids#EMPTY}
	 * empty, change our fluid to {@link #fluid} theirs. We then receive
	 * as much as possible.
	 *
	 * If our fluids not the same, and ours is not {@link Fluids#EMPTY},
	 * do nothing.
	 *
	 * The amount transferred is the {@link Math#min(long, long)} between
	 * the our available space, their amount, and the specified amount.
	 */
	@Override
	public <V extends Volume<Long>> void take(V volume, Long amount) {
		if (!(volume instanceof FluidVolume))
			return;

		if (((FluidVolume) volume).getFluid() != getFluid()) {
			if (this.isEmpty()) {
				this.setFluid(((FluidVolume) volume).getFluid());
			} else {
				return;
			}
		}

		volume.give(this, amount);
	}

	/** Takes the minimum between the stored amount and the
	 * specified amount from this volume. */
	@Override
	public void take(Long fraction) {
		setAmount(Math.max(0, getAmount() - fraction));
	}

	/** Returns a copy of this volume. */
	@Override
	public <V extends Volume<Long>> V copy() {
		return (V) of(getAmount(), getSize(), getFluid());
	}

	/** Asserts the equality of the objects. */
	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;

		if (!(object instanceof FluidVolume volume))
			return false;

		if (!super.equals(object))
			return false;
		
		return FluidVolume.fluidsEqual(this, volume);
	}

	/** Returns the hash for this volume. */
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getAmount(), getSize(), fluid);
	}

	/** Returns this volume's string representation.
	 * For example, it may be "minecraft:water, 16.50 of 32.00 Buckets" */
	@Override
	public String toString() {
		return String.format("%s, %s / %s", getFluidId(), NumberUtils.shorten(getAmount().doubleValue(), "U"), NumberUtils.shorten(getSize().doubleValue(), "U"));
	}

	/** Deserializes a volume from a {@link CompoundTag}. */
	public static FluidVolume fromTag(CompoundTag tag) {
		return of(tag.getLong("Amount"), tag.getLong("Size"), Registry.FLUID.get(new Identifier(tag.getString("Fluid"))));
	}

	/** Serializes this volume to a {@link CompoundTag}. */
	@Override
	public CompoundTag toTag() {
		var tag = new CompoundTag();
		
		tag.putLong("Amount", getAmount());
		tag.putLong("Size", getSize());
		tag.putString("Fluid", Registry.FLUID.getId(getFluid()).toString());
		
		return tag;
	}

	/** Deserializes a volume from a {@link JsonElement}. */
	public static FluidVolume fromJson(JsonElement jsonElement) {
		if (!(jsonElement instanceof JsonObject)) {
			if (jsonElement.isJsonPrimitive()) {
				var jsonPrimitive = jsonElement.getAsJsonPrimitive();

				if (jsonPrimitive.isString()) {
					return of(BUCKET, Registry.FLUID.get(new Identifier(jsonPrimitive.getAsString())));
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			var jsonObject = jsonElement.getAsJsonObject();

			if (!jsonObject.has("fluid")) return null;
			if (!jsonObject.has("amount")) {
				return of(BUCKET, Registry.FLUID.get(new Identifier(jsonObject.get("fluid").getAsString())));
			} else {
				return of(jsonObject.get("amount").getAsLong(), Registry.FLUID.get(new Identifier(jsonObject.get("fluid").getAsString())));
			}
		}
	}

	/** Serializes this volume to a {@link JsonElement}. */
	public JsonElement toJson() {
		var object = new JsonObject();
		
		object.addProperty("fluid", Registry.FLUID.getId(getFluid()).toString());
		object.addProperty("amount", getAmount());
		object.addProperty("size", getSize());
		
		return object;
	}

	/** Deserializes a volume from a {@link ByteBuf}. */
	public static FluidVolume fromPacket(PacketByteBuf buffer) {
		if (!buffer.readBoolean()) {
			return ofEmpty();
		} else {
			var id = buffer.readVarInt();

			var amount = buffer.readVarLong();
			var size = buffer.readVarLong();

			return of(amount, size, Registry.FLUID.get(id));
		}
	}

	/** Serialize this volume to a {@link ByteBuf}. */
	public PacketByteBuf toPacket(PacketByteBuf buffer) {
		if (this.isEmpty()) {
			buffer.writeBoolean(false);
		} else {
			buffer.writeBoolean(true);

			buffer.writeVarInt(Registry.FLUID.getRawId(getFluid()));

			buffer.writeVarLong(getAmount());
			buffer.writeVarLong(getSize());
		}

		return buffer;
	}
}
