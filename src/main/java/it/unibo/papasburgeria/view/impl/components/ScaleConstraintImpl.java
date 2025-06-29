package it.unibo.papasburgeria.view.impl.components;

import it.unibo.papasburgeria.view.api.components.Scale;
import it.unibo.papasburgeria.view.api.components.ScaleConstraint;

import java.util.Objects;

/**
 * Implementation of ScaleConstraint.
 *
 * <p>
 * See {@link ScaleConstraint} for interface details.
 */
public class ScaleConstraintImpl implements ScaleConstraint {
    /*
     * There probably was a better way but enum is redundant and having to re-declare each time these
     * final values is a pain.
     */
    public static final double FULL = 1.0;
    public static final double HALF = 0.5;
    public static final double QUARTER = 0.25;
    public static final double EIGHTH = 0.125;
    public static final double SIXTEENTH = 0.0625;
    public static final double THIRD = 0.333_333;
    public static final double ZERO = 0.0;

    public static final Scale SIZE_FULL = new ScaleImpl(FULL, FULL);
    public static final Scale SIZE_HALF_PARENT = new ScaleImpl(HALF, HALF);
    public static final Scale SIZE_QUARTER_PARENT = new ScaleImpl(QUARTER, QUARTER);

    public static final Scale POSITION_TOP_LEFT = new ScaleImpl(ZERO, ZERO);
    public static final Scale POSITION_TOP_CENTER = new ScaleImpl(HALF, ZERO);
    public static final Scale POSITION_TOP_RIGHT = new ScaleImpl(FULL, ZERO);
    public static final Scale POSITION_CENTER_LEFT = new ScaleImpl(ZERO, HALF);
    public static final Scale POSITION_CENTER = new ScaleImpl(HALF, HALF);
    public static final Scale POSITION_CENTER_RIGHT = new ScaleImpl(FULL, HALF);
    public static final Scale POSITION_BOTTOM_LEFT = new ScaleImpl(ZERO, FULL);
    public static final Scale POSITION_BOTTOM_CENTER = new ScaleImpl(HALF, FULL);
    public static final Scale POSITION_BOTTOM_RIGHT = new ScaleImpl(FULL, FULL);

    public static final Scale ORIGIN_TOP_LEFT = new ScaleImpl(ZERO, ZERO);
    public static final Scale ORIGIN_TOP_CENTER = new ScaleImpl(HALF, ZERO);
    public static final Scale ORIGIN_TOP_RIGHT = new ScaleImpl(FULL, ZERO);
    public static final Scale ORIGIN_CENTER_LEFT = new ScaleImpl(ZERO, HALF);
    public static final Scale ORIGIN_CENTER = new ScaleImpl(HALF, HALF);
    public static final Scale ORIGIN_CENTER_RIGHT = new ScaleImpl(FULL, HALF);
    public static final Scale ORIGIN_BOTTOM_LEFT = new ScaleImpl(ZERO, FULL);
    public static final Scale ORIGIN_BOTTOM_CENTER = new ScaleImpl(HALF, FULL);
    public static final Scale ORIGIN_BOTTOM_RIGHT = new ScaleImpl(FULL, FULL);

    private Scale sizeScale;
    private Scale positionScale;
    private Scale originScale;

    /**
     * Constructs a ScaleConstraint with default values.
     */
    public ScaleConstraintImpl() {
        this(SIZE_HALF_PARENT, POSITION_CENTER, ORIGIN_CENTER);
    }

    /**
     * Constructs a scale constraint with the provided scales.
     *
     * @param sizeScale     Scale instance
     * @param positionScale Scale instance
     * @param originScale   Scale instance
     */
    public ScaleConstraintImpl(final Scale sizeScale, final Scale positionScale, final Scale originScale) {
        this.sizeScale = sizeScale;
        this.positionScale = positionScale;
        this.originScale = originScale;
    }

    /**
     * Constructs a ScaleConstraint from another ScaleConstraint.
     *
     * @param constraint ScaleConstraint instance
     */
    public ScaleConstraintImpl(final ScaleConstraint constraint) {
        this.sizeScale = constraint.getSizeScale();
        this.positionScale = constraint.getPositionScale();
        this.originScale = constraint.getOriginScale();
    }

    /**
     * @inheritDoc
     */
    @Override
    public Scale getSizeScale() {
        return sizeScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setSizeScale(final Scale scale) {
        this.sizeScale = new ScaleImpl(scale);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Scale getPositionScale() {
        return positionScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPositionScale(final Scale scale) {
        this.positionScale = new ScaleImpl(scale);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Scale getOriginScale() {
        return originScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setOriginScale(final Scale scale) {
        this.originScale = new ScaleImpl(scale);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScaleConstraintImpl that)) {
            return false;
        }
        return Objects.equals(sizeScale, that.sizeScale)
                && Objects.equals(positionScale, that.positionScale)
                && Objects.equals(originScale, that.originScale);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode() {
        return Objects.hash(sizeScale, positionScale, originScale);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "ScaleConstraintImpl{"
                +
                "sizeScale="
                + sizeScale
                +
                ", positionScale="
                + positionScale
                +
                ", originScale="
                + originScale
                +
                '}';
    }
}
