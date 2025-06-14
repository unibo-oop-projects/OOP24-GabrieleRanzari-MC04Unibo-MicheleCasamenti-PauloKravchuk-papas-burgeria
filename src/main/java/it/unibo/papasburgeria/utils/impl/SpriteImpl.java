package it.unibo.papasburgeria.utils.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.utils.api.Sprite;

import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

/**
 * @inheritDoc
 */
public class SpriteImpl implements Sprite {
    private final Image image;
    private final IngredientEnum ingredientType;
    private final double pbSizeXScale;
    private final double pbSizeYScale;
    private double pbPositionXScale;
    private double pbPositionYScale;

    /**
     * Default constructor, stores an image, its coordinates in % and its size in %.
     *
     * @param image the image
     * @param ingredientType the type of the ingredient
     * @param pbPositionXScale the x position
     * @param pbPositionYScale the y position
     * @param pbSizeXScale the width of the image
     * @param pbSizeYScale the height of the image
     */
    public SpriteImpl(final Image image, final IngredientEnum ingredientType,
                      final double pbPositionXScale, final double pbPositionYScale,
                      final double pbSizeXScale, final double pbSizeYScale) {
        this.image = new ImageIcon(image).getImage();
        this.ingredientType = ingredientType;
        this.pbPositionXScale = pbPositionXScale;
        this.pbPositionYScale = pbPositionYScale;
        this.pbSizeXScale = pbSizeXScale;
        this.pbSizeYScale = pbSizeYScale;
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
    public int calculateX(final int frameWidth) {
        return (int) (frameWidth * pbPositionXScale);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int calculateY(final int frameHeight) {
        return (int) (frameHeight * pbPositionYScale);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int calculateWidth(final int frameWidth) {
        return (int) (frameWidth * pbSizeXScale);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int calculateHeight(final int frameHeight) {
        return (int) (frameHeight * pbSizeYScale);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPbPositionXScale(final double newPbPositionXScale) {
        pbPositionXScale = newPbPositionXScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPbPositionYScale(final double newPbPositionYScale) {
        pbPositionYScale = newPbPositionYScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void draw(final Dimension frameSize, final Graphics g) {
        final int frameWidth = frameSize.width;
        final int frameHeight = frameSize.height;

        final int x = calculateX(frameWidth);
        final int y = calculateY(frameHeight);
        final int width = calculateWidth(frameWidth);
        final int height = calculateHeight(frameHeight);

        g.drawImage(getImage(), x, y, width, height, null);
    }
}
