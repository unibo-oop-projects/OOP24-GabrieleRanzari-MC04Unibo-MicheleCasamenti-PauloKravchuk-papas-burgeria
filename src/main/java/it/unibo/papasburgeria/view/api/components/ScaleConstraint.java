package it.unibo.papasburgeria.view.api.components;

/**
 * Defines a constraint containing three main scale values: size, position, origin.
 */
public interface ScaleConstraint {
    /**
     * Returns size scale value.
     *
     * @return Scale instance
     */
    Scale getSizeScale();

    /**
     * Sets a size scale value.
     *
     * @param scale scale instance
     */
    void setSizeScale(Scale scale);

    /**
     * Returns position scale value.
     *
     * @return Scale instance
     */
    Scale getPositionScale();

    /**
     * Sets a position scale value.
     *
     * @param scale scale instance
     */
    void setPositionScale(Scale scale);

    /**
     * Returns origin scale value.
     *
     * @return Scale instance
     */
    Scale getOriginScale();

    /**
     * Sets a position scale value.
     *
     * @param scale scale instance
     */
    void setOriginScale(Scale scale);
}
