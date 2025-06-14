package it.unibo.papasburgeria.utils.api;

import it.unibo.papasburgeria.model.IngredientEnum;

import java.awt.Dimension;
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
     * @param frameWidth the width of the frame
     *
     * @return the x position.
     */
    int calculateX(int frameWidth);

    /**
     * @param frameHeight the height of the frame
     *
     * @return the y position.
     */
    int calculateY(int frameHeight);

    /**
     * @param frameWidth the width of the frame
     *
     * @return the width.
     */
    int calculateWidth(int frameWidth);

    /**
     * @param frameHeight the height of the frame
     *
     * @return the height.
     */
    int calculateHeight(int frameHeight);

    /**
     * @param newPbPositionXScale the new value of PositionXScale to set.
     */
    void setPbPositionXScale(double newPbPositionXScale);

    /**
     * @param newPbPositionYScale the new value of PositionYScale to set.
     */
    void setPbPositionYScale(double newPbPositionYScale);

    /**
     * @param frameSize the sizes of the frame.
     * @param g the graphics where to draw.
     */
    void draw(Dimension frameSize, Graphics g);
}
