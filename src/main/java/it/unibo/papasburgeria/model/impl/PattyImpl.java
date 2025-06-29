package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Patty;

import java.util.Objects;

/**
 * Implementation of Patty. Extension of IngredientImpl.
 *
 * <p>
 * See {@link Patty} for interface details.
 * See {@link IngredientImpl} for superclass details.
 */
public class PattyImpl extends IngredientImpl implements Patty {
    public static final double MAX_COOK_LEVEL = 1.0;
    public static final double MIN_COOK_LEVEL = 0.0;

    private double topCookLevel;
    private double bottomCookLevel;
    private boolean flipped;

    /**
     * Default constructor for the patty, creates a raw patty not flipped.
     */
    public PattyImpl() {
        super(IngredientEnum.PATTY);
        topCookLevel = MIN_COOK_LEVEL;
        bottomCookLevel = MIN_COOK_LEVEL;
        flipped = false;
    }

    /**
     * Constructor for coping another patty.
     *
     * @param patty the patty to copy.
     */
    public PattyImpl(final Patty patty) {
        super(IngredientEnum.PATTY);
        topCookLevel = patty.getTopCookLevel();
        bottomCookLevel = patty.getBottomCookLevel();
        flipped = patty.isFlipped();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flip() {
        flipped = !flipped;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFlipped() {
        return flipped;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTopCookLevel() {
        return topCookLevel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTopCookLevel(final double cookLevel) {
        topCookLevel = cookLevel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBottomCookLevel() {
        return bottomCookLevel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBottomCookLevel(final double cookLevel) {
        bottomCookLevel = cookLevel;
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
        final PattyImpl other = (PattyImpl) object;
        return getIngredientType() == other.getIngredientType()
                && Objects.equals(this.topCookLevel, other.topCookLevel)
                && Objects.equals(this.bottomCookLevel, other.bottomCookLevel)
                && this.flipped == other.flipped;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                getIngredientType(),
                topCookLevel,
                bottomCookLevel,
                flipped
        );
    }

    /**
     * @return a string containing the meat's type, placement accuracy , the cook level and if it is flipped.
     */
    @Override
    public String toString() {
        return "Patty[type:" + this.getIngredientType()
                + ", accuracy:" + this.getPlacementAccuracy()
                + ", top cook level:" + this.getTopCookLevel()
                + ", bottom cook level:" + this.getBottomCookLevel()
                + ", flipped:" + this.isFlipped() + "]";
    }
}
