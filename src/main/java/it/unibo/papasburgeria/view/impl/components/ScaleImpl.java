package it.unibo.papasburgeria.view.impl.components;

import it.unibo.papasburgeria.view.api.components.Scale;

import java.util.Objects;

/**
 * Implementation of Scale.
 *
 * <p>
 * See {@link Scale} for interface details.
 */
public class ScaleImpl implements Scale {
    private final double xScale;
    private final double yScale;

    /**
     * Create a scale instance by providing X and Y scales.
     *
     * @param xScale X scale value
     * @param yScale Y scale value
     */
    public ScaleImpl(final double xScale, final double yScale) {
        if (xScale < 0 || yScale < 0) {
            throw new IllegalArgumentException("xScale and yScale must be non-negative");
        }

        this.xScale = xScale;
        this.yScale = yScale;
    }

    /**
     * Create a scale instance with both X and Y scales in equal value.
     *
     * @param scale scale to be given to both X and Y
     */
    public ScaleImpl(final double scale) {
        this(scale, scale);
    }

    /**
     * Constructs a Scale from another Scale.
     *
     * @param scale Scale instance
     */
    public ScaleImpl(final Scale scale) {
        if (scale == null) {
            throw new IllegalArgumentException("Scale cannot be null");
        }

        final double fetchedXScale = scale.getXScale();
        final double fetchedYScale = scale.getYScale();
        if (fetchedXScale < 0 || fetchedYScale < 0) {
            throw new IllegalArgumentException("xScale and yScale must be non-negative");
        }

        this.xScale = fetchedXScale;
        this.yScale = fetchedYScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getXScale() {
        return xScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getYScale() {
        return yScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getXScaledValue(final int value) {
        return (int) Math.round(xScale * value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getYScaledValue(final int value) {
        return (int) Math.round(yScale * value);
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "ScaleImpl{"
                + "xScale=" + xScale
                + ", yScale=" + yScale
                + '}';
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScaleImpl scale)) {
            return false;
        }

        return Double.compare(xScale, scale.xScale) == 0 && Double.compare(yScale, scale.yScale) == 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode() {
        return Objects.hash(xScale, yScale);
    }
}
