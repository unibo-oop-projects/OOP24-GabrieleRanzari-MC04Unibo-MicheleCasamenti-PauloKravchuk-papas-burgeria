package it.unibo.papasburgeria.utils.api;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.Patty;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

/**
 * Manages the drawing of varius things.
 */
public interface DrawingManager {
    /**
     * Creates a Sprite for the Patty.
     *
     * @param patty            the patty
     * @param pbPositionXScale the x position in scale
     * @param pbPositionYScale the y position in scale
     * @return the sprite
     */
    Sprite generatePattySprite(Patty patty, double pbPositionXScale, double pbPositionYScale);

    /**
     * Draws the hamburger.
     *
     * @param hamburgerIngredients      the list of ingredients of the hamburger
     * @param frameSize                 the sizes of the frame
     * @param draggableHamburgerSprites the list of draggable sprites of the hamburger
     * @param graphics                  the graphics
     */
    void drawHamburger(List<Ingredient> hamburgerIngredients, Dimension frameSize,
                       List<Sprite> draggableHamburgerSprites, Graphics graphics);

    /**
     * Draws a sprite.
     *
     * @param sprite              the sprite to draw
     * @param frameSize           the sizes of the frame
     * @param unlockedIngredients the list of unlocked ingredients
     * @param graphics            the graphics
     */
    void drawIngredient(Sprite sprite, Dimension frameSize, List<IngredientEnum> unlockedIngredients, Graphics graphics);

    /**
     * Generates the sprites for the cooked patties.
     *
     * @param cookedPatties           the list of cooked patties
     * @param pbPositionXScale        the x position in scale where to draw
     * @param initialPbPositionYScale the initial y position in scale where to draw
     * @param draggablePattySprites   the list of draggable sprites
     */
    void generateCookedPatties(List<Patty> cookedPatties, double pbPositionXScale,
                               double initialPbPositionYScale, List<Sprite> draggablePattySprites);

    /**
     * Generates the sprites for the patties on the grill.
     *
     * @param pattiesOnGrill          the matrix of patties on the grill
     * @param draggablePattiesOnGrill the list of draggable patties on the grill
     */
    void generatePattiesOnGrill(Patty[][] pattiesOnGrill, List<Sprite> draggablePattiesOnGrill);

    /**
     * calculates the x position in scale given the accuracy.
     *
     * @param accuracy the placement accuracy of the ingredient
     * @return the x position in scale
     */
    double getPositionXScaleFromAccuracy(double accuracy);
}
