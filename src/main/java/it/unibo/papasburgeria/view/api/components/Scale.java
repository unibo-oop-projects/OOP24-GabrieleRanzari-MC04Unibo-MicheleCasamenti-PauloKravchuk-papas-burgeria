package it.unibo.papasburgeria.view.api.components;

/**
 * Represents X and Y scale values for simplicity.
 */
public interface Scale {
    /**
     * Retrieves X scale value.
     *
     * @return X scale
     */
    double getXScale();

    /**
     * Retrieves Y scale value.
     *
     * @return Y scale
     */
    double getYScale();

    /**
     * Returns the provided value scaled by X.
     *
     * @param value value to scale
     * @return scaled value
     */
    int getXScaledValue(int value);

    /**
     * Returns the provided value scaled by Y.
     *
     * @param value value to scale
     * @return scaled value
     */
    int getYScaledValue(int value);
}
