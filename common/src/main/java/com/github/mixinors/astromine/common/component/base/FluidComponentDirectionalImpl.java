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

package com.github.mixinors.astromine.common.component.base;

import com.github.mixinors.astromine.common.volume.fluid.FluidVolume;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

public class FluidComponentDirectionalImpl extends FluidComponentImpl {
    private TransferComponent transferComponent;

    private final Supplier<TransferComponent> transferComponentSupplier;
    
    <V> FluidComponentDirectionalImpl(V v, int size) {
        super(size);

        transferComponentSupplier = () -> TransferComponent.from(v);
    }
    
    <V> FluidComponentDirectionalImpl(V v, FluidVolume... volumes) {
        super(volumes);

        transferComponentSupplier = () -> TransferComponent.from(v);
    }
    
    @Override
    public boolean canInsert(@Nullable Direction direction, FluidVolume volume, int slot) {
        if (transferComponent == null && transferComponentSupplier != null) {
            transferComponent = transferComponentSupplier.get();
        }

        if (transferComponent == null) {
            return false;
        }

        return direction == null ? super.canInsert(direction, volume, slot) : transferComponent.getFluid(direction).canInsert() && super.canInsert(direction, volume, slot);
    }
    
    @Override
    public boolean canExtract(@Nullable Direction direction, FluidVolume volume, int slot) {
        if (transferComponent == null && transferComponentSupplier != null) {
            transferComponent = transferComponentSupplier.get();
        }

        if (transferComponent == null) {
            return false;
        }

        return direction == null ? super.canExtract(direction, volume, slot) : transferComponent.getFluid(direction).canExtract() && super.canExtract(direction, volume, slot);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FluidComponentDirectionalImpl that = (FluidComponentDirectionalImpl) o;
        return Objects.equals(transferComponent, that.transferComponent);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(transferComponent);
    }
}
