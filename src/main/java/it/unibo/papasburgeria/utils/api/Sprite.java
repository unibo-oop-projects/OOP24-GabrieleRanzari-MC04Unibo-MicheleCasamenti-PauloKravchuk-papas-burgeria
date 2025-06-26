package it.unibo.papasburgeria.utils.api;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

/**
 * Saves an image and its coordinates.
 */
public interface Sprite {
    /**
     * Sets whether the sprite is draggable.
     *
     * @param draggable the new draggable value
     */
    void setDraggable(boolean draggable);

    /**
     * Returns whether the sprite is draggable.
     *
     * @return true if draggable, false otherwise
     */
    boolean isDraggable();

    /**
     * Sets whether the sprite is visible.
     *
     * @param visible the new visible value
     */
    void setVisible(boolean visible);

    /**
     * Returns whether the sprite is visible.
     *
     * @return true if visible, false otherwise
     */
    boolean isVisible();

    /**
     * Sets whether the sprite is cloneable.
     *
     * @param cloneable the new cloneable value
     */
    void setCloneable(boolean cloneable);

    /**
     * Returns whether the sprite is cloneable.
     *
     * @return true if cloneable, false otherwise
     */
    boolean isCloneable();

    /**
     * Sets whether the sprite is removable.
     *
     * @param removable the new removable value
     */
    void setRemovable(boolean removable);

    /**
     * Returns whether the sprite is removable.
     *
     * @return true if removable, false otherwise
     */
    boolean isRemovable();

    /**
     * Sets whether the sprite is flipped.
     *
     * @param flipped the new flipped value
     */
    void setFlipped(boolean flipped);

    /**
     * Returns whether the sprite is flipped.
     *
     * @return true if flipped, false otherwise
     */
    boolean isFlipped();

    /**
     * Returns the list of images that form the sprite.
     *
     * @return the list images
     */
    List<Image> getImages();

    /**
     * Adds an image to the list of images that form the sprite.
     *
     * @param image the new image to add
     */
    void addImage(Image image);

    /**
     * Returns the ingredient of the sprite.
     *
     * @return the ingredient
     */
    Ingredient getIngredient();

    /**
     * Returns the type of the ingredient.
     *
     * @return the ingredient type
     */
    IngredientEnum getIngredientType();

    /**
     * Sets the sprite ingredient to a new ingredient.
     *
     * @param ingredient the new ingredient
     */
    void setIngredient(Ingredient ingredient);

    /**
     * Calculates the x position in pixels of the sprite given the width of the frame.
     *
     * @param frameWidth the width of the frame
     * @return the x position
     */
    int calculateX(int frameWidth);

    /**
     * Calculates the y position in pixels of the sprite given the height of the frame.
     *
     * @param frameHeight the height of the frame
     * @return the y position.
     */
    int calculateY(int frameHeight);

    /**
     * Calculates the width of the sprite in pixels given the width of the frame.
     *
     * @param frameWidth the width of the frame
     * @return the width
     */
    int calculateWidth(int frameWidth);

    /**
     * Calculates the height of the sprite in pixels given the height of the frame.
     *
     * @param frameHeight the height of the frame
     * @return the height
     */
    int calculateHeight(int frameHeight);

    /**
     * Sets the x position on the sprite to a new value in scale.
     *
     * @param newPbPositionXScale the new x position in scale
     */
    void setPbPositionXScale(double newPbPositionXScale);

    /**
     * Sets the y position on the sprite to a new value in scale.
     *
     * @param newPbPositionYScale the new y position in scale
     */
    void setPbPositionYScale(double newPbPositionYScale);

    /**
     * Flips the image vertically.
     */
    void flipImageVertically();

    /**
     * Return the x position of the sprite in scale.
     *
     * @return the x position in scale
     */
    double getPbPositionXScale();

    /**
     * Return the y position of the sprite in scale.
     *
     * @return the y position in scale
     */
    double getPbPositionYScale();

    /**
     * Returns the width of the sprite in scale.
     *
     * @return the width in scale
     */
    double getPbSizeXScale();

    /**
     * Returns the height of the sprite in scale.
     *
     * @return the height in scale
     */
    double getPbSizeYScale();

    /**
     * Draws the sprite.
     *
     * @param frameSize the sizes of the frame
     * @param g         the graphics where to draw
     */
    void draw(Dimension frameSize, Graphics g);
}
