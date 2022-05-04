/*
 * MIT License
 *
 * Copyright (c) 2020 - 2022 Mixinors
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

package com.github.mixinors.astromine.common.widget.blade;

import java.util.Arrays;
import java.util.List;

import com.github.mixinors.astromine.AMCommon;
import com.github.mixinors.astromine.client.BaseRenderer;
import com.github.mixinors.astromine.common.block.entity.base.ExtendedBlockEntity;
import com.github.mixinors.astromine.common.transfer.StorageSiding;
import com.github.mixinors.astromine.common.transfer.StorageType;
import com.github.mixinors.astromine.common.util.MirrorUtils;
import com.github.mixinors.astromine.common.util.NetworkingUtils;
import com.github.mixinors.astromine.registry.common.AMNetworks;
import dev.architectury.networking.NetworkManager;
import dev.vini2003.hammer.common.widget.Widget;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class StorageSidingWidget extends Widget {
	private ExtendedBlockEntity blockEntity;
	
	private StorageSiding siding;
	
	private StorageType type;
	
	private Direction direction;
	
	private Direction rotation;
	
	public static final Identifier TEXTURE_INSERT= AMCommon.id("textures/widget/insert.png");
	public static final Identifier TEXTURE_EXTRACT = AMCommon.id("textures/widget/extract.png");
	public static final Identifier TEXTURE_INSERT_EXTRACT = AMCommon.id("textures/widget/insert_extract.png");
	public static final Identifier TEXTURE_NONE = AMCommon.id("textures/widget/none.png");

	private Identifier getTexture() {
		var sidings = new StorageSiding[6];
		
		if (type == StorageType.ITEM) {
			sidings = blockEntity.getItemStorage().getSidings();
		}
		
		if (type == StorageType.FLUID) {
			sidings = blockEntity.getFluidStorage().getSidings();
		}
		
		return switch (sidings[direction.ordinal()]) {
			case INSERT -> TEXTURE_INSERT;
			case EXTRACT -> TEXTURE_EXTRACT;
			case INSERT_EXTRACT ->  TEXTURE_INSERT_EXTRACT;
			case NONE -> TEXTURE_NONE;
		};
	}
	
	@Override
	public void onMouseClicked(float mouseX, float mouseY, int button) {
		super.onMouseClicked(mouseX, mouseY, button);
		
		if (!getHidden() && getFocused() && getHandled().getClient()) {
			var sidings = (StorageSiding[]) null;
			
			if (type == StorageType.ITEM) {
				sidings = blockEntity.getItemStorage().getSidings();
			}
			
			if (type == StorageType.FLUID) {
				sidings = blockEntity.getFluidStorage().getSidings();
			}
			
			var next = (StorageSiding) null;
			
			if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
				next = sidings[direction.ordinal()].next();
			} else if (button == GLFW.GLFW_MOUSE_BUTTON_2) {
				next = sidings[direction.ordinal()].previous();
			} else {
				return;
			}
			
			var buf = NetworkingUtils.ofStorageSiding(next, type, direction, blockEntity.getPos());
			
			NetworkManager.sendToServer(AMNetworks.STORAGE_SIDING_UPDATE, buf);
		}
	}

	@Environment(EnvType.CLIENT)
	@Override
	public @NotNull List<Text> getTooltip() {
		var offset = MirrorUtils.rotate(direction, rotation);
		
		var sidings = new StorageSiding[6];
		
		if (type == StorageType.ITEM) {
			sidings = blockEntity.getItemStorage().getSidings();
		}
		
		if (type == StorageType.FLUID) {
			sidings = blockEntity.getFluidStorage().getSidings();
		}
		
		var name = switch (sidings[direction.ordinal()]) {
			case INSERT -> new TranslatableText("text.astromine.siding.insert").styled(style -> style.withColor(0x0078FF));
			case EXTRACT -> new TranslatableText("text.astromine.siding.extract").styled(style -> style.withColor(0xFF6100));
			case INSERT_EXTRACT ->
				new TranslatableText("text.astromine.siding.insert").styled(style -> style.withColor(0x9800FF)).append(
						new LiteralText(" / ").styled(style -> style.withColor(0x9800FF))
				).append(
						new TranslatableText("text.astromine.siding.extract").styled(style -> style.withColor(0x9800FF))
				);
			
			case NONE -> new TranslatableText("text.astromine.siding.none").styled(style -> style.withColor(0x909090));
		};
		
		return Arrays.asList(new TranslatableText("text.astromine.siding." + offset.getName()), name);
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void drawWidget(@NotNull MatrixStack matrices, @NotNull VertexConsumerProvider provider, float delta) {
		if (getHidden()) {
			return;
		}

		BaseRenderer.drawTexturedQuad(matrices, provider, getPosition().getX(), getPosition().getY(), getSize().getWidth(), getSize().getHeight(), getTexture());
	}
	
	public ExtendedBlockEntity getBlockEntity() {
		return blockEntity;
	}
	
	public void setBlockEntity(ExtendedBlockEntity blockEntity) {
		this.blockEntity = blockEntity;
	}
	
	public StorageSiding getSiding() {
		return siding;
	}
	
	public void setSiding(StorageSiding siding) {
		this.siding = siding;
	}
	
	public StorageType getType() {
		return type;
	}
	
	public void setType(StorageType type) {
		this.type = type;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public Direction getRotation() {
		return rotation;
	}
	
	public void setRotation(Direction rotation) {
		this.rotation = rotation;
	}
}