package com.github.chainmailstudios.astromine.common.volume;

import com.github.chainmailstudios.astromine.common.fraction.Fraction;
import com.github.chainmailstudios.astromine.common.volume.fluid.FluidVolume;
import com.google.common.base.Objects;
import com.google.common.math.LongMath;
import net.minecraft.nbt.CompoundTag;

import java.math.RoundingMode;

public class BaseVolume {
	protected Fraction fraction = Fraction.EMPTY;

	protected Fraction size = new Fraction(Integer.MAX_VALUE, 1);

	/**
	 * Instantiates a Volume with an empty fraction.
	 */
	public BaseVolume() {
	}

	/**
	 * Instantiates a Volume with a specified fraction.
	 */
	public BaseVolume(Fraction fraction) {
		this.fraction = fraction;
	}

	public boolean isFull() {
		return getFraction().equals(getSize());
	}

	public boolean isEmpty() {
		return this.getFraction().equals(Fraction.EMPTY);
	}

	public Fraction getFraction() {
		return this.fraction;
	}

	public void setFraction(Fraction fraction) {
		this.fraction = fraction;
	}

	/**
	 * Serializes this Volume and its properties
	 * into a tag.
	 *
	 * @return a tag
	 */
	public CompoundTag toTag(CompoundTag tag) {
		// TODO: Null checks.

		tag.put("fraction", this.fraction.toTag(new CompoundTag()));
		tag.put("size", this.size.toTag(new CompoundTag()));

		return tag;
	}

	/**
	 * Takes a Volume out of this Volume.
	 */
	public <T extends BaseVolume> T take(Fraction taken) {
		T volume = (T) new BaseVolume();
		push(volume, taken);
		return volume;
	}

	/**
	 * Adds to this Volume.
	 */
	public <T extends BaseVolume> T give(Fraction pushed) {
		return (T) new BaseVolume(Fraction.add(fraction, pushed));
	}

	/**
	 * Pull fluids from a Volume into this Volume.
	 * If the Volume's fractional available is smaller than
	 * pulled, ask for the minimum. If not, ask for the
	 * minimum between requested size and available
	 * for pulling into this.
	 */
	public <T extends BaseVolume> void pull(T target, Fraction pulled) {
		Fraction available = Fraction.subtract(this.size, this.fraction);

		pulled = Fraction.min(pulled, available);

		if (target.fraction.isSmallerThan(pulled)) { // If target has less than required.
			setFraction(Fraction.add(getFraction(), target.fraction));
			target.setFraction(Fraction.subtract(target.fraction, target.fraction));

			setFraction(Fraction.simplify(getFraction()));
			target.setFraction(Fraction.simplify(target.getFraction()));
		} else { // If target has more than or equal to required.
			target.setFraction(Fraction.subtract(target.fraction, pulled));
			setFraction(Fraction.add(getFraction(), pulled));

			target.setFraction(Fraction.simplify(target.getFraction()));
			setFraction(Fraction.simplify(getFraction()));
		}
	}

	/**
	 * Push fluids from this Volume into a Volume.
	 * If the Volume's fractional available is smaller than
	 * pushed, ask for the minimum. If not, ask for the
	 * minimum between requested size and available for
	 * pushing into target.
	 */
	public <T extends BaseVolume> void push(T target, Fraction pushed) {
		Fraction available = Fraction.subtract(target.size, target.fraction);

		pushed = Fraction.min(pushed, available);

		if (fraction.isSmallerThan(pushed)) { // If target has less than required.
			target.setFraction(Fraction.add(target.getFraction(), fraction));
			setFraction(Fraction.subtract(fraction, fraction));

			target.setFraction(Fraction.simplify(target.getFraction()));
			setFraction(Fraction.simplify(getFraction()));
		} else { // If target has more than or equal to required.
			target.setFraction(Fraction.add(target.getFraction(), pushed));
			setFraction(Fraction.subtract(fraction, pushed));

			target.setFraction(Fraction.simplify(target.getFraction()));
			setFraction(Fraction.simplify(getFraction()));
		}
	}

	public Fraction getSize() {
		return this.size;
	}

	public void setSize(Fraction size) {
		this.size = size;
	}

	public boolean fits(Fraction fraction) {
		Fraction available = Fraction.subtract(getSize(), getFraction());
		return available.equals(fraction) || available.isBiggerThan(fraction);
	}

	/**
	 * Fraction comparison method.
	 */
	public <T extends BaseVolume> boolean isSmallerThan(T volume) {
		return !this.isBiggerThan(volume);
	}

	/**
	 * Fraction comparison method.
	 */
	public <T extends BaseVolume> boolean isBiggerThan(T volume) {
		return fraction.isBiggerThan(volume.fraction);
	}

	/**
	 * Fraction comparison method.
	 */
	public <T extends BaseVolume> boolean isSmallerOrEqualThan(T volume) {
		return isSmallerThan(volume) || equals(volume);
	}

	/**
	 * Fraction comparison method.
	 */
	public <T extends BaseVolume> boolean isBiggerOrEqualThan(T volume) {
		return isBiggerThan(volume) || equals(volume);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.fraction);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof BaseVolume)) return false;

		BaseVolume volume = (BaseVolume) object;

		return Objects.equal(this.fraction, volume.fraction);
	}
}