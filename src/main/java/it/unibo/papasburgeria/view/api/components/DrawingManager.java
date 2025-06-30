package it.unibo.papasburgeria.view.api.components;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.HamburgerModel;
import it.unibo.papasburgeria.model.api.OrderModel;
import it.unibo.papasburgeria.model.api.PattyModel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;

/**
 * Manages the drawing of varius things.
 */
public interface DrawingManager {

    /**
     * Draws the hamburger.
     *
     * @param hamburger                 the hamburger to draw
     * @param frameSize                 the sizes of the frame
     * @param bottomBunXPosScale        the x position considered the center of the hamburger
     * @param bottomBunYPosScale        the y position of the bottom bun of the hamburger
     * @param draggableHamburgerSprites the list of draggable sprites of the hamburger
     * @param graphics                  the graphics
     */
    void drawHamburger(
            HamburgerModel hamburger,
            Dimension frameSize,
            double bottomBunXPosScale,
            double bottomBunYPosScale,
            List<Sprite> draggableHamburgerSprites,
            Graphics graphics
    );

    /**
     * Draws a sprite.
     *
     * @param sprite              the sprite to draw
     * @param frameSize           the sizes of the frame
     * @param unlockedIngredients the list of unlocked ingredients
     * @param graphics            the graphics
     */
    void drawIngredient(
            Sprite sprite,
            Dimension frameSize,
            List<IngredientEnum> unlockedIngredients,
            Graphics graphics
    );

    /**
     * Draws an order.
     *
     * @param sprite    the sprite to draw
     * @param order     the order to draw
     * @param frameSize the sizes of the frame
     * @param graphics  the graphics
     */
    void drawOrder(
            Sprite sprite,
            OrderModel order,
            Dimension frameSize,
            Graphics graphics
    );

    /**
     * Generates the sprites for the cooked patties.
     *
     * @param cookedPatties           the list of cooked patties
     * @param pbPositionXScale        the x position in scale where to draw
     * @param initialPbPositionYScale the initial y position in scale where to draw
     * @param draggablePattySprites   the list of draggable sprites
     */
    void generateCookedPatties(
            List<PattyModel> cookedPatties,
            double pbPositionXScale,
            double initialPbPositionYScale,
            List<Sprite> draggablePattySprites
    );

    /**
     * Generates the sprites for the patties on the grill.
     *
     * @param pattiesOnGrill          the matrix of patties on the grill
     * @param draggablePattiesOnGrill the list of draggable patties on the grill
     */
    void generatePattiesOnGrill(
            PattyModel[][] pattiesOnGrill,
            List<Sprite> draggablePattiesOnGrill
    );

    /**
     * Generates the sprites for the orders.
     *
     * @param orders                the list of orders
     * @param draggableOrderSprites the list of draggable orders
     * @param spriteOrders          the map of orders for every sprite
     */
    void generateOrderSprites(
            List<OrderModel> orders,
            List<Sprite> draggableOrderSprites,
            Map<Sprite, OrderModel> spriteOrders
    );
}
