package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Patty;

/**
 * @inheritDoc
 */
public class PattyImpl extends IngredientImpl implements Patty {
    private double topCookLevel;
    private double bottomCookLevel;
    private boolean flipped;

    /**
     * Default constructor for the patty, creates a raw patty not flipped.
     */
    public PattyImpl() {
        super(IngredientEnum.PATTY);
        topCookLevel = 0;
        bottomCookLevel = 0;
        flipped = false;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void flipPatty() {
        flipped = !flipped;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isFlipped() {
        return flipped;
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getTopCookLevel() {
        return topCookLevel;
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getBottomCookLevel() {
        return bottomCookLevel;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setTopCookLevel(final double cookLevel) {
        topCookLevel = cookLevel;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setBottomCookLevel(final double cookLevel) {
        bottomCookLevel = cookLevel;
    }

    /**
     * @return a string containing the meat's type, placement accuracy , the cook level and if it is flipped.
     */
    @Override
    public String toString() {
        return "[ type:" + this.getIngredientType()
                + ", accuracy:" + this.getPlacementAccuracy()
                + ", top cook level:" + this.getTopCookLevel()
                + ", bottom cook level:" + this.getBottomCookLevel()
                + ", flipped:" + this.isFlipped() + " ]";
    }
}
