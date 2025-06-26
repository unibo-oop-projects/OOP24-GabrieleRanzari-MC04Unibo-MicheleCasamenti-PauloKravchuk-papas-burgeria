package it.unibo.papasburgeria.view.impl.components;

import it.unibo.papasburgeria.view.api.components.Scale;
import it.unibo.papasburgeria.view.api.components.ScaleConstraint;

/**
 * Implementation of ScaleConstraint.
 *
 * <p>
 * See {@link ScaleConstraint} for interface details.
 */
public class ScaleConstraintImpl implements ScaleConstraint {
    /* For now there's no need for an enum, it adds overhead by having to re-implement Scale interface */
    public static final Scale SIZE_FULL = new ScaleImpl(1.0, 1.0);
    public static final Scale SIZE_HALF_PARENT = new ScaleImpl(0.5, 0.5);
    public static final Scale SIZE_QUARTER_PARENT = new ScaleImpl(0.25, 0.25);

    public static final Scale POSITION_TOP_LEFT = new ScaleImpl(0.0, 0.0);
    public static final Scale POSITION_TOP_CENTER = new ScaleImpl(0.5, 0.0);
    public static final Scale POSITION_TOP_RIGHT = new ScaleImpl(1.0, 0.0);
    public static final Scale POSITION_CENTER_LEFT = new ScaleImpl(0.0, 0.5);
    public static final Scale POSITION_CENTER = new ScaleImpl(0.5, 0.5);
    public static final Scale POSITION_CENTER_RIGHT = new ScaleImpl(1.0, 0.5);
    public static final Scale POSITION_BOTTOM_LEFT = new ScaleImpl(0.0, 1.0);
    public static final Scale POSITION_BOTTOM_CENTER = new ScaleImpl(0.5, 1.0);
    public static final Scale POSITION_BOTTOM_RIGHT = new ScaleImpl(1.0, 1.0);

    public static final Scale ORIGIN_TOP_LEFT = new ScaleImpl(0.0, 1.0);
    public static final Scale ORIGIN_TOP_CENTER = new ScaleImpl(0.5, 0.0);
    public static final Scale ORIGIN_TOP_RIGHT = new ScaleImpl(1.0, 0.0);
    public static final Scale ORIGIN_CENTER_LEFT = new ScaleImpl(0.0, 0.5);
    public static final Scale ORIGIN_CENTER = new ScaleImpl(0.5, 0.5);
    public static final Scale ORIGIN_CENTER_RIGHT = new ScaleImpl(1.0, 0.5);
    public static final Scale ORIGIN_BOTTOM_LEFT = new ScaleImpl(0.0, 1.0);
    public static final Scale ORIGIN_BOTTOM_CENTER = new ScaleImpl(0.5, 1.0);
    public static final Scale ORIGIN_BOTTOM_RIGHT = new ScaleImpl(1.0, 1.0);

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
}
