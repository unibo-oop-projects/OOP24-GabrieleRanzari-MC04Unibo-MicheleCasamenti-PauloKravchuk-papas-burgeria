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
    private static final Scale DEFAULT_SIZE_SCALE = new ScaleImpl(0.5, 0.5);
    private static final Scale DEFAULT_POSITION_SCALE = new ScaleImpl(0.5, 0.5);
    private static final Scale DEFAULT_ORIGIN_SCALE = new ScaleImpl(0, 0);

    private Scale sizeScale;
    private Scale positionScale;
    private Scale originScale;

    /**
     * Constructs a ScaleConstraint with default values.
     */
    public ScaleConstraintImpl() {
        this(DEFAULT_SIZE_SCALE, DEFAULT_POSITION_SCALE, DEFAULT_ORIGIN_SCALE);
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
