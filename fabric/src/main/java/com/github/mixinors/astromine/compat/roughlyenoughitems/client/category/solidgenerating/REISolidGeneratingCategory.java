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

package com.github.mixinors.astromine.compat.roughlyenoughitems.client.category.solidgenerating;

import com.github.mixinors.astromine.compat.roughlyenoughitems.client.display.solidgenerating.REISolidGeneratingDisplay;
import com.github.mixinors.astromine.registry.common.AMBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import com.github.mixinors.astromine.compat.roughlyenoughitems.client.REIPlugin;
import com.github.mixinors.astromine.compat.roughlyenoughitems.client.category.generating.REIAbstractEnergyGeneratingCategory;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.widgets.Widgets;
import me.shedaniel.rei.gui.widget.Widget;

import java.text.DecimalFormat;
import java.util.List;

@Environment(EnvType.CLIENT)
public class REISolidGeneratingCategory extends REIAbstractEnergyGeneratingCategory<REISolidGeneratingDisplay> {
	@Override
	public Identifier getIdentifier() {
		return REIPlugin.SOLID_GENERATING;
	}

	@Override
	public String getCategoryName() {
		return I18n.translate("category.astromine.solid_generating");
	}

	@Override
	public EntryStack getLogo() {
		return EntryStack.create(AMBlocks.ADVANCED_SOLID_GENERATOR.get());
	}

	@Override
	public List<Widget> setupDisplay(REISolidGeneratingDisplay recipeDisplay, Rectangle bounds) {
		DecimalFormat df = new DecimalFormat("###.##");
		List<Widget> widgets = super.setupDisplay(recipeDisplay, bounds);
		Rectangle innerBounds = new Rectangle(bounds.getCenterX() - 55, bounds.y, 110, bounds.height);
		widgets.add(Widgets.createSlot(new Point(innerBounds.getX() + 20, innerBounds.getY() + 26)).entries(recipeDisplay.getInputEntries().get(0)).markInput());
		widgets.add(Widgets.createLabel(new Point(innerBounds.x + 5, innerBounds.y + 5), new TranslatableText("category.astromine.cooking.time", df.format(recipeDisplay.getTime()))).noShadow().leftAligned().color(0xFF404040, 0xFFBBBBBB));
		widgets.add(Widgets.createArrow(new Point(innerBounds.getX() + 45, innerBounds.getY() + 26)));
		return widgets;
	}
}
