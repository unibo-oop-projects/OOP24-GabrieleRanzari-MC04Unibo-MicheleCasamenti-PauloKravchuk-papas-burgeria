package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.IngredientEnum;

/**
 * Class extending IngredientImpl used for creating meat. 
 */
public class MeatImpl extends IngredientImpl {

    private double cookLevel;

    /**
     * simple constructor for meat.
     */
    public MeatImpl() {
        super(IngredientEnum.MEAT);
        cookLevel = 0;
    }

    /**
     * @return how much the hamburger was cooked.
     */
    public double getCookLevel() {
        return cookLevel;
    }

    /**
     * @param setCookLevel how much the hamburger was cooked.
     */
    public void setCookLevel(final double setCookLevel) {
        this.cookLevel = setCookLevel;
    }

    /**
     * @return a string containing the meat's type, placement accuracy and cook level.
     */
    @Override
    public String toString() {
        return "[ type:" + this.getIngredientType()
        + ", acc:" + this.getPlacementAccuracy()
        + ", cook:" + this.getCookLevel() + " ]";
    }
}
