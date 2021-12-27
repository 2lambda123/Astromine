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

package com.github.mixinors.astromine.common.block.entity;

import com.github.mixinors.astromine.common.component.world.WorldHoloBridgeComponent;
import com.github.mixinors.astromine.common.util.LineUtils;
import com.github.mixinors.astromine.common.util.VectorUtils;
import com.github.mixinors.astromine.registry.common.AMBlockEntityTypes;
import com.github.mixinors.astromine.registry.common.AMBlocks;
import dev.architectury.hooks.block.BlockEntityHooks;
import dev.vini2003.hammer.common.color.Color;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class HoloBridgeProjectorBlockEntity extends BlockEntity implements TickableBlockEntity {
	public ArrayList<Vec3f> segments = null;

	public Color color = Color.of("0x7e80cad4");

	private HoloBridgeProjectorBlockEntity child = null;
	private HoloBridgeProjectorBlockEntity parent = null;

	private BlockPos childPosition = null;
	private BlockPos parentPosition = null;

	private boolean hasCheckedChild = false;
	private boolean hasCheckedParent = false;

	public HoloBridgeProjectorBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(AMBlockEntityTypes.HOLOGRAPHIC_BRIDGE.get(), blockPos, blockState);
	}

	public boolean hasChild() {
		return this.child != null;
	}

	@Override
	public void tick() {
		if (this.world == null || this.world.isClient)
			return;

		if (!this.hasCheckedChild && this.childPosition != null) {
			var childEntity = this.world.getBlockEntity(this.childPosition);

			if (childEntity instanceof HoloBridgeProjectorBlockEntity) {
				this.child = (HoloBridgeProjectorBlockEntity) childEntity;
				this.hasCheckedChild = true;

				this.buildBridge();
			} else if (childEntity != null) {
				this.hasCheckedChild = true;
			}
		}

		if (!this.hasCheckedParent && this.parentPosition != null) {
			var parentEntity = this.world.getBlockEntity(parentPosition);

			if (parentEntity instanceof HoloBridgeProjectorBlockEntity) {
				this.parent = (HoloBridgeProjectorBlockEntity) parentEntity;
				this.hasCheckedParent = true;

				this.buildBridge();
			} else if (parentEntity != null) {
				this.hasCheckedParent = true;
			}
		}
	}

	public boolean attemptToBuildBridge(HoloBridgeProjectorBlockEntity child) {
		var bCP = child.getPos();
		var bOP = this.getPos();

		var nCP = bCP;

		var cD = child.getCachedState().get(HorizontalFacingBlock.FACING);

		if (cD == Direction.EAST) {
			nCP = nCP.add(1, 0, 0);
		} else if (cD == Direction.SOUTH) {
			nCP = nCP.add(0, 0, 1);
		}

		var distance = (int) Math.sqrt(this.getPos().getSquaredDistance(child.getPos()));

		if (distance == 0) {
			return false;
		}

		ArrayList<Vec3f> segments = (ArrayList<Vec3f>) LineUtils.getBresenhamSegments(VectorUtils.toVector3f(bOP.offset(Direction.UP)), VectorUtils.toVector3f(nCP.offset(Direction.UP)), 32);

		for (Vec3f v : segments) {
			var nP = new BlockPos(v.getX(), v.getY(), v.getZ());

			if ((nP.getX() != bCP.getX() && nP.getX() != bOP.getX()) || (nP.getZ() != bCP.getZ() && nP.getZ() != bOP.getZ())) {
				if (!this.world.getBlockState(nP).isAir()) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public void buildBridge() {
		if (this.child == null || this.world == null) {
			return;
		}

		var bCP = this.getChild().getPos();
		var bOP = this.getPos();

		var nCP = bCP;

		var cD = this.getChild().getCachedState().get(HorizontalFacingBlock.FACING);

		if (cD == Direction.EAST) {
			nCP = nCP.add(1, 0, 0);
		} else if (cD == Direction.SOUTH) {
			nCP = nCP.add(0, 0, 1);
		}

		var distance = (int) Math.sqrt(this.getPos().getSquaredDistance(this.getChild().getPos()));

		if (distance == 0) {
			return;
		}

		this.segments = (ArrayList<Vec3f>) LineUtils.getBresenhamSegments(VectorUtils.toVector3f(bOP.offset(Direction.UP)), VectorUtils.toVector3f(nCP.offset(Direction.UP)), 32);
		var bridgeComponent = WorldHoloBridgeComponent.get(world);

		for (Vec3f v : this.segments) {
			var nP = new BlockPos(v.getX(), v.getY(), v.getZ());

			if ((nP.getX() != bCP.getX() && nP.getX() != bOP.getX()) || (nP.getZ() != bCP.getZ() && nP.getZ() != bOP.getZ())) {
				if (this.world.getBlockState(nP).isAir()) {
					this.world.setBlockState(nP, AMBlocks.HOLOGRAPHIC_BRIDGE_INVISIBLE_BLOCK.get().getDefaultState());
				}
			}
			
			bridgeComponent.add(nP, new Vec3i((v.getX() - (int) v.getX()) * 16f, (v.getY() - (int) v.getY()) * 16f, (v.getZ() - (int) v.getZ()) * 16f));
		}
	}

	public HoloBridgeProjectorBlockEntity getChild() {
		return this.child;
	}

	public void setChild(HoloBridgeProjectorBlockEntity child) {
		this.child = child;

		if (this.child != null) {
			this.child.setParent(this);
			this.child.setChild(null);
		}

		this.markDirty();
	}

	public HoloBridgeProjectorBlockEntity getParent() {
		return parent;
	}

	public void setParent(HoloBridgeProjectorBlockEntity parent) {
		this.parent = parent;
		this.setChild(null);

		this.markDirty();
	}

	@Override
	public void markRemoved() {
		if (this.child != null) {
			this.destroyBridge();

			this.setChild(null);

			if (!world.isClient) {
				BlockEntityHooks.syncData(this);
			}
		}

		if (this.parent != null) {
			this.parent.destroyBridge();

			this.parent.setChild(null);

			if (!world.isClient) {
				BlockEntityHooks.syncData(this.parent);
			}
		}


		super.markRemoved();
	}

	public void destroyBridge() {
		if (this.segments != null && this.world != null) {
			var bridgeComponent = WorldHoloBridgeComponent.get(world);

			for (Vec3f vec : this.segments) {
				var pos = new BlockPos(vec.getX(), vec.getY(), vec.getZ());

				bridgeComponent.remove(pos);

				this.world.setBlockState(pos, Blocks.AIR.getDefaultState());
			}

			this.segments.clear();
		}
	}

	@Override
	public void readNbt(@NotNull NbtCompound tag) {
		if (tag.contains("child_position")) {
			this.childPosition = BlockPos.fromLong(tag.getLong("child_position"));
		}

		if (tag.contains("parent_position")) {
			this.parentPosition = BlockPos.fromLong(tag.getLong("parent_position"));
		}

		if (tag.contains("color")) {
			var colorTag = tag.getCompound("color");

			color = new Color(colorTag.getFloat("r"), colorTag.getFloat("g"), colorTag.getFloat("b"), colorTag.getFloat("a"));
		}
		
		if (world.isClient) {
			this.destroyBridge();

			if (this.childPosition != null) {
				this.child = (HoloBridgeProjectorBlockEntity) this.world.getBlockEntity(this.childPosition);
			}

			this.buildBridge();
		}

		super.readNbt(tag);
	}

	@Override
	public void writeNbt(NbtCompound tag) {
		if (this.child != null) {
			tag.putLong("child_position", this.child.getPos().asLong());
		} else if (this.childPosition != null) {
			tag.putLong("child_position", this.childPosition.asLong());
		}

		if (this.parent != null) {
			tag.putLong("parent_position", this.parent.getPos().asLong());
		} else if (this.parentPosition != null) {
			tag.putLong("parent_position", this.parentPosition.asLong());
		}

		var colorTag = new NbtCompound();
		colorTag.putFloat("r", color.getR());
		colorTag.putFloat("g", color.getG());
		colorTag.putFloat("b", color.getB());
		colorTag.putFloat("a", color.getA());

		tag.put("color", colorTag);

		super.writeNbt(tag);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt() {
		return createNbt();
	}

	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
}
