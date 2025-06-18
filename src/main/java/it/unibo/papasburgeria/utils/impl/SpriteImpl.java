package it.unibo.papasburgeria.utils.impl;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.model.impl.IngredientImpl;
import it.unibo.papasburgeria.model.impl.PattyImpl;
import it.unibo.papasburgeria.utils.api.Sprite;

import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * @inheritDoc
 */
public class SpriteImpl implements Sprite {
    private Ingredient ingredient;
    private Image image;
    private double pbSizeXScale;
    private double pbSizeYScale;
    private double pbPositionXScale;
    private double pbPositionYScale;
    private boolean draggable;
    private boolean visible;
    private boolean cloneable;
    private boolean removable;

    /**
     * Default constructor, stores an image, its coordinates in % and its size in %.
     *
     * @param image the image
     * @param ingredient the ingredient
     * @param pbPositionXScale the x position
     * @param pbPositionYScale the y position
     * @param pbSizeXScale the width of the image
     * @param pbSizeYScale the height of the image
     */
    public SpriteImpl(final Image image, final Ingredient ingredient,
                      final double pbPositionXScale, final double pbPositionYScale,
                      final double pbSizeXScale, final double pbSizeYScale) {
        this.image = new ImageIcon(image).getImage();
        if (ingredient instanceof Patty patty) {
            this.ingredient = new PattyImpl(patty);
        } else {
            this.ingredient = new IngredientImpl(ingredient);
        }
        this.pbPositionXScale = pbPositionXScale;
        this.pbPositionYScale = pbPositionYScale;
        this.pbSizeXScale = pbSizeXScale;
        this.pbSizeYScale = pbSizeYScale;
        draggable = false;
        visible = true;
        cloneable = true;
        removable = false;
    }

    /**
     * Constructor for coping another sprite.
     *
     * @param sprite the sprite to copy.
     */
    public SpriteImpl(final Sprite sprite) {
        this.image = sprite.getImage();
        final Ingredient newIngredient = sprite.getIngredient();
        if (newIngredient instanceof Patty patty) {
            this.ingredient = new PattyImpl(patty);
        } else {
            this.ingredient = new IngredientImpl(newIngredient);
        }
        this.pbPositionXScale = sprite.getPbPositionXScale();
        this.pbPositionYScale = sprite.getPbPositionYScale();
        this.pbSizeXScale = sprite.getPbSizeXScale();
        this.pbSizeYScale = sprite.getPbSizeYScale();
        draggable = sprite.isDraggable();
        visible = sprite.isVisible();
        cloneable = sprite.isCloneable();
        removable = sprite.isRemovable();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setDraggable(final boolean draggable) {
        this.draggable = draggable;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isDraggable() {
        return draggable;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isVisible() {
        return visible;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setCloneable(final boolean cloneable) {
        this.cloneable = cloneable;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isCloneable() {
        return cloneable;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setRemovable(final boolean removable) {
        this.removable = removable;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isRemovable() {
        return removable;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Image getImage() {
        return new ImageIcon(image).getImage();
    }

    /**
     * @return the ingredient.
     */
    @Override
    public Ingredient getIngredient() {
        if (ingredient instanceof Patty patty) {
            return new PattyImpl(patty);
        } else {
            return new IngredientImpl(ingredient);
        }
    }

    /**
     * @return the ingredient type.
     */
    @Override
    public IngredientEnum getIngredientType() {
        return ingredient.getIngredientType();
    }

    /**
     * @param newIngredient the ingredient.
     */
    @Override
    public void setIngredient(final Ingredient newIngredient) {
        if (newIngredient instanceof Patty patty) {
            ingredient = new PattyImpl(patty);
        } else {
            ingredient = new IngredientImpl(newIngredient);
        }
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
    public void setPbSizeXScale(final double newPbSizeXScale) {
        pbSizeXScale = newPbSizeXScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPbSizeYScale(final double newPbSizeYScale) {
        pbSizeYScale = newPbSizeYScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void flipImageVertically() {
        final int width = image.getWidth(null);
        final int height = image.getHeight(null);

        final BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = flipped.createGraphics();

        final AffineTransform transform = AffineTransform.getScaleInstance(1, -1);
        transform.translate(0, -height);

        g.drawImage(image, transform, null);
        g.dispose();

        image = flipped;
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getPbPositionXScale() {
        return pbPositionXScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getPbPositionYScale() {
        return pbPositionYScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getPbSizeXScale() {
        return pbSizeXScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getPbSizeYScale() {
        return pbSizeYScale;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void draw(final Dimension frameSize, final Graphics g) {
        if (isVisible()) {
            final int frameWidth = frameSize.width;
            final int frameHeight = frameSize.height;

            final int x = calculateX(frameWidth);
            final int y = calculateY(frameHeight);
            final int width = calculateWidth(frameWidth);
            final int height = calculateHeight(frameHeight);

            g.drawImage(getImage(), x, y, width, height, null);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Sprite other = (Sprite) object;
        return Objects.equals(this.ingredient, other.getIngredient())
                && Objects.equals(this.pbPositionXScale, other.getPbPositionXScale())
                && Objects.equals(this.pbPositionYScale, other.getPbPositionYScale());
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                ingredient,
                pbPositionXScale,
                pbPositionYScale
        );
    }


    /**
     * @return a string containing the most important variables.
     */
    @Override
    public String toString() {
        return "[SpriteImpl:"
                + "[ingredient: " + ingredient + "] "
                + "[pbPositionXScale: " + pbPositionXScale + "] "
                + "[pbPositionYScale: " + pbPositionYScale + "] "
                + "[pbSizeXScale: " + pbSizeXScale + "] "
                + "[pbSizeYScale: " + pbSizeYScale + "]"
                + "]";
    }
}
