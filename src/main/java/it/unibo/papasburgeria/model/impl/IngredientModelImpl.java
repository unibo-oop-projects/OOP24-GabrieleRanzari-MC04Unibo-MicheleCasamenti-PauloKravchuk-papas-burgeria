package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.IngredientModel;

import java.util.Objects;

/**
 * Implementation of Ingredient.
 *
 * <p>
 * See {@link IngredientModel} for interface details.
 */
public class IngredientModelImpl implements IngredientModel {
    /**
     * Defines the maximum right accuracy.
     */
    public static final double MAX_RIGHT_ACCURACY = 1.0;
    /**
     * Defines the maximum left accuracy.
     */
    public static final double MAX_LEFT_ACCURACY = -MAX_RIGHT_ACCURACY;
    /**
     * Defines the perfect accuracy.
     */
    public static final double PERFECT_ACCURACY =
            MAX_RIGHT_ACCURACY + MAX_LEFT_ACCURACY;

    private static final double DEFAULT_ACCURACY = PERFECT_ACCURACY;

    private final IngredientEnum type;
    private double accuracy;

    /**
     * Default constructor, creates a new ingredient given its type.
     *
     * @param type type of ingredient
     */
    public IngredientModelImpl(final IngredientEnum type) {
        this.type = type;
        accuracy = DEFAULT_ACCURACY;
    }

    /**
     * Constructor for coping another ingredient.
     *
     * @param ingredient the ingredient to copy.
     */
    public IngredientModelImpl(final IngredientModel ingredient) {
        type = ingredient.getIngredientType();
        accuracy = ingredient.getPlacementAccuracy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IngredientEnum getIngredientType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPlacementAccuracy() {
        return accuracy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlacementAccuracy(final double newAccuracy) {
        this.accuracy = newAccuracy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final IngredientModel other = (IngredientModel) object;
        return type == other.getIngredientType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "IngredientImpl{"
                + "type=" + type
                + ", accuracy=" + accuracy
                + '}';
    }
}
