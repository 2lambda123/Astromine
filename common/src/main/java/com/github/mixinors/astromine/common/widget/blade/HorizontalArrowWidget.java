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

package com.github.mixinors.astromine.common.widget.blade;

import com.github.mixinors.astromine.common.util.ClientUtils;
import com.github.mixinors.astromine.common.util.TextUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import com.github.mixinors.astromine.AMCommon;
import com.github.mixinors.astromine.client.BaseRenderer;
import com.github.vini2003.blade.client.utilities.Layers;
import com.github.vini2003.blade.client.utilities.Scissors;
import com.github.vini2003.blade.common.widget.base.AbstractWidget;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.IntSupplier;

/**
 * A horizontal arrow widget depicting
 * the progress level of the {@link #progressSupplier} relative
 * to the {@link #limitSupplier}.
 */
public class HorizontalArrowWidget extends AbstractWidget {
	private static final Identifier BACKGROUND = AMCommon.id("textures/widget/horizontal_arrow_background.png");
	private static final Identifier FOREGROUND = AMCommon.id("textures/widget/horizontal_arrow_foreground.png");

	private IntSupplier progressSupplier = () -> 0;
	private IntSupplier limitSupplier = () -> 100;

	/** Returns this widget's {@link #progressSupplier}. */
	public IntSupplier getProgressSupplier() {
		return progressSupplier;
	}

	/** Sets this widget's {@link #progressSupplier} to the specified one. */
	public void setProgressSupplier(IntSupplier progressSupplier) {
		this.progressSupplier = progressSupplier;
	}

	/** Returns this widget's {@link #limitSupplier}. */
	public IntSupplier getLimitSupplier() {
		return limitSupplier;
	}

	/** Sets this widget's {@link #limitSupplier} to the specified one. */
	public void setLimitSupplier(IntSupplier limitSupplier) {
		this.limitSupplier = limitSupplier;
	}

	/** Returns this widget's tooltip. */
	@NotNull
	@Override
	public List<Text> getTooltip() {
		return List.of(
				TextUtils.getRatio(progressSupplier.getAsInt(), limitSupplier.getAsInt())
		);
	}

	/** Renders this widget. */
	@Environment(EnvType.CLIENT)
	@Override
	public void drawWidget(MatrixStack matrices, VertexConsumerProvider provider) {
		if (getHidden())
			return;

		var x = getPosition().getX();
		var y = getPosition().getY();

		var sX = getSize().getWidth();
		var sY = getSize().getHeight();

		var rawHeight = ClientUtils.getInstance().getWindow().getHeight();
		var scale = (float) ClientUtils.getInstance().getWindow().getScaleFactor();

		var sBGX = (int) (((sX / limitSupplier.getAsInt()) * progressSupplier.getAsInt()));

		var backgroundLayer = Layers.get(BACKGROUND);
		var foregroundLayer = Layers.get(FOREGROUND);

		var area = new Scissors(provider, (int) (x * scale), (int) (rawHeight - ((y + sY) * scale)), (int) (sX * scale), (int) (sY * scale));

		BaseRenderer.drawTexturedQuad(matrices, provider, backgroundLayer, getPosition().getX(), getPosition().getY(), getSize().getWidth(), getSize().getHeight(), BACKGROUND);

		area.destroy(provider);

		area = new Scissors(provider, (int) (x * scale), (int) (rawHeight - ((y + sY) * scale)), (int) (sBGX * scale), (int) (sY * scale));

		BaseRenderer.drawTexturedQuad(matrices, provider, foregroundLayer, getPosition().getX(), getPosition().getY(), getSize().getWidth(), getSize().getHeight(), FOREGROUND);

		area.destroy(provider);
	}
}
