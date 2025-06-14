package it.unibo.papasburgeria.utils.api.scene;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.utils.api.loading.Sprite;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @inheritDoc
 */
public class SpriteImpl implements Sprite {
    private final Image image;
    private final IngredientEnum ingredientType;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    /**
     * Default constructor, stores an image and it's coordinates using the original size.
     *
     * @param image the image
     * @param ingredientType the type of the ingredient
     * @param x the x position
     * @param y the y position
     */
    public SpriteImpl(final Image image, final IngredientEnum ingredientType, final int x, final int y) {
        this.image = new ImageIcon(image).getImage();
        this.ingredientType = ingredientType;
        this.x = x;
        this.y = y;
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    /**
     * Second constructor, stores an image, its coordinates and the new size.
     *
     * @param image the image
     * @param ingredientType the type of the ingredient
     * @param x the x position
     * @param y the y position
     * @param width the width of the image
     * @param height the height of the image
     */
    public SpriteImpl(final Image image, final IngredientEnum ingredientType, final int x, final int y,
                      final int width, final int height) {
        this.image = new ImageIcon(image).getImage();
        this.ingredientType = ingredientType;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void draw(final Graphics g) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public Image getImage() {
        return new ImageIcon(image).getImage();
    }

    /**
     * @return the ingredient type.
     */
    @Override
    public IngredientEnum getIngredientType() {
        return ingredientType;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getHeight() {
        return height;
    }
}
