package it.unibo.papasburgeria.utils.api.loading;

import it.unibo.papasburgeria.model.IngredientEnum;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Saves an image and its coordinates.
 */
public interface Sprite {
    /**
     * @return the image.
     */
    Image getImage();

    /**
     * @return the ingredient type.
     */
    IngredientEnum getIngredientType();

    /**
     * @return the x position.
     */
    int getX();

    /**
     * @return the y position.
     */
    int getY();

    /**
     * @return the width.
     */
    int getWidth();

    /**
     * @return the height.
     */
    int getHeight();

    /**
     * draws the image. If the ingredient is not unlocked, it will draw a lock.
     *
     * @param g the graphic component
     */
    void draw(Graphics g);
}
