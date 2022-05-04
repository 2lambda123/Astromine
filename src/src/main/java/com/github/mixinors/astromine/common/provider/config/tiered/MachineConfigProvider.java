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

package com.github.mixinors.astromine.common.provider.config.tiered;

import com.github.mixinors.astromine.common.config.entry.tiered.MachineConfig;
import com.github.mixinors.astromine.common.provider.EnergyStorageSizeProvider;
import com.github.mixinors.astromine.common.provider.EnergyStorageSpeedProvider;
import com.github.mixinors.astromine.common.provider.SpeedProvider;

public interface MachineConfigProvider<T extends MachineConfig<?>> extends TieredConfigProvider<T>, SpeedProvider, EnergyStorageSizeProvider, EnergyStorageSpeedProvider {
	@Override
	default long getEnergyStorageSize() {
		return getConfig().getEnergyStorageSize(getMachineTier());
	}

	@Override
	default long getMaxTransferRate() {
		return (long) (2048 * getSpeed());
	}

	@Override
	default double getSpeed() {
		return getConfig().getSpeed(getMachineTier());
	}
}
