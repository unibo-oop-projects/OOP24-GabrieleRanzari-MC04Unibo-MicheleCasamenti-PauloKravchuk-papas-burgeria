package it.unibo.papasburgeria.view.impl.components;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.model.impl.IngredientImpl;
import it.unibo.papasburgeria.model.impl.PattyImpl;
import it.unibo.papasburgeria.view.api.components.Sprite;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of Sprite.
 *
 * <p>
 * See {@link Sprite} for interface details.
 */
public class SpriteImpl implements Sprite {
    private final double pbSizeXScale;
    private final double pbSizeYScale;

    private double pbPositionXScale;
    private double pbPositionYScale;
    private List<Image> images;
    private Ingredient ingredient;

    private boolean draggable; // Indicates whether the sprite can be dragged directly.
    private boolean visible;   // Indicates whether the sprite is currently visible.
    private boolean cloneable; // Indicates whether the sprite can be cloned.
    private boolean removable; // Indicates whether the sprite represents something from the model.
    private boolean flipped;   // Indicates whether the sprite has been vertically flipped.

    /**
     * Constructor for single image, stores the image,
     * the ingredient, its coordinates in scale and its size in scale.
     *
     * @param image            the image
     * @param ingredient       the ingredient
     * @param pbPositionXScale the x position in scale
     * @param pbPositionYScale the y position in scale
     * @param pbSizeXScale     the width in scale
     * @param pbSizeYScale     the height in scale
     */
    public SpriteImpl(final Image image,
                      final Ingredient ingredient,
                      final double pbPositionXScale,
                      final double pbPositionYScale,
                      final double pbSizeXScale,
                      final double pbSizeYScale
    ) {
        this.images = new ArrayList<>(List.of(image));
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
        flipped = false;
    }

    /**
     * Constructor for coping another sprite.
     *
     * @param sprite the sprite to copy.
     */
    public SpriteImpl(final Sprite sprite) {
        this.images = sprite.getImages();
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
        flipped = sprite.isFlipped();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDraggable() {
        return draggable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDraggable(final boolean draggable) {
        this.draggable = draggable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isVisible() {
        return visible;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCloneable() {
        return cloneable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCloneable(final boolean cloneable) {
        this.cloneable = cloneable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRemovable() {
        return removable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRemovable(final boolean removable) {
        this.removable = removable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFlipped() {
        return flipped;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFlipped(final boolean flipped) {
        this.flipped = flipped;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Image> getImages() {
        return new ArrayList<>(images);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addImage(final Image image) {
        images.add(image);
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public IngredientEnum getIngredientType() {
        return ingredient.getIngredientType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateX(final int frameWidth) {
        return (int) (frameWidth * pbPositionXScale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateY(final int frameHeight) {
        return (int) (frameHeight * pbPositionYScale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateWidth(final int frameWidth) {
        return (int) (frameWidth * pbSizeXScale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateHeight(final int frameHeight) {
        return (int) (frameHeight * pbSizeYScale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flipImageVertically() {
        final List<Image> newImages = new ArrayList<>();
        for (final Image image : images) {
            final int width = image.getWidth(null);
            final int height = image.getHeight(null);

            if (width < 0 || height < 0) {
                throw new IllegalStateException("Could not flip: Image not loaded correctly");
            }

            final BufferedImage flippedImage =
                    new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D graphics = flippedImage.createGraphics();

            final AffineTransform transform = AffineTransform.getScaleInstance(1, -1);
            transform.translate(0, -height);

            graphics.drawImage(image, transform, null);
            graphics.dispose();

            setFlipped(!flipped);
            newImages.add(flippedImage);
        }
        images = newImages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPbPositionXScale() {
        return pbPositionXScale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPbPositionXScale(final double newPbPositionXScale) {
        pbPositionXScale = newPbPositionXScale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPbPositionYScale() {
        return pbPositionYScale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPbPositionYScale(final double newPbPositionYScale) {
        pbPositionYScale = newPbPositionYScale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPbSizeXScale() {
        return pbSizeXScale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPbSizeYScale() {
        return pbSizeYScale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Dimension frameSize, final Graphics g) {
        if (!isVisible()) {
            return;
        }
        final int frameWidth = frameSize.width;
        final int frameHeight = frameSize.height;

        final int x = calculateX(frameWidth);
        final int y = calculateY(frameHeight);
        final int width = calculateWidth(frameWidth);
        final int height = calculateHeight(frameHeight);

        for (final Image image : images) {
            g.drawImage(image, x, y, width, height, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final SpriteImpl other = (SpriteImpl) object;
        return Objects.equals(this.ingredient, other.getIngredient())
                && Objects.equals(this.pbPositionXScale, other.getPbPositionXScale())
                && Objects.equals(this.pbPositionYScale, other.getPbPositionYScale());
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SpriteImpl{"
                + "pbSizeXScale=" + pbSizeXScale
                + ", pbSizeYScale=" + pbSizeYScale
                + ", ingredient=" + ingredient
                + ", images=" + images
                + ", pbPositionXScale=" + pbPositionXScale
                + ", pbPositionYScale=" + pbPositionYScale
                + ", draggable=" + draggable
                + ", visible=" + visible
                + ", cloneable=" + cloneable
                + ", removable=" + removable
                + ", flipped=" + flipped
                + '}';
    }
}
