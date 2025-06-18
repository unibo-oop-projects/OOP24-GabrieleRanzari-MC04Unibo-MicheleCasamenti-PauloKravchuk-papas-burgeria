package it.unibo.papasburgeria.utils.impl;

import it.unibo.papasburgeria.utils.api.Sprite;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.Objects;

/**
 * Saves a sprite composed of multiple images.
 */
public class CompositeSpriteImpl extends SpriteImpl {
    private final List<Sprite> subSprites;

    /**
     * Default constructor, constructs a composite sprite.
     *
     * @param subSprites the list of sprites.
     */
    public CompositeSpriteImpl(final List<Sprite> subSprites) {
        super(subSprites.getFirst());
        this.subSprites = List.copyOf(subSprites);
    }

    /**
     * Constructor for coping another CompositeSprite.
     *
     * @param sprite the CompositeSprite.
     */
    public CompositeSpriteImpl(final CompositeSpriteImpl sprite) {
        super(sprite.getSubSprites().getFirst());
        this.subSprites = List.copyOf(sprite.getSubSprites());
        setDraggable(sprite.isDraggable());
        setCloneable(sprite.isCloneable());
        setRemovable(sprite.isRemovable());
        setVisible(sprite.isVisible());
    }

    /**
     * @return the list of sub sprites.
     */
    public List<Sprite> getSubSprites() {
        return subSprites;
    }

    /**
     * @inheritDoc
     */
    @Override
    public final void setDraggable(final boolean draggable) {
        super.setDraggable(draggable);
        for (final Sprite sprite : subSprites) {
            sprite.setDraggable(draggable);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public final void setCloneable(final boolean cloneable) {
        super.setCloneable(cloneable);
        for (final Sprite sprite : subSprites) {
            sprite.setCloneable(cloneable);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public final void setRemovable(final boolean removable) {
        super.setRemovable(removable);
        for (final Sprite sprite : subSprites) {
            sprite.setRemovable(removable);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public final void setVisible(final boolean visible) {
        super.setVisible(visible);
        for (final Sprite sprite : subSprites) {
            sprite.setVisible(visible);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPbPositionXScale(final double x) {
        final double deltaX = x - getPbPositionXScale();
        for (final Sprite s : subSprites) {
            s.setPbPositionXScale(s.getPbPositionXScale() + deltaX);
        }
        super.setPbPositionXScale(x);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPbPositionYScale(final double y) {
        final double deltaY = y - getPbPositionYScale();
        for (final Sprite s : subSprites) {
            s.setPbPositionYScale(s.getPbPositionYScale() + deltaY);
        }
        super.setPbPositionYScale(y);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void draw(final Dimension frameSize, final Graphics g) {
        if (isVisible()) {
            final int frameWidth = frameSize.width;
            final int frameHeight = frameSize.height;

            for (final Sprite s : subSprites) {
                g.drawImage(s.getImage(), calculateX(frameWidth), calculateY(frameHeight),
                        calculateWidth(frameWidth), calculateHeight(frameHeight), null);
            }
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
        final CompositeSpriteImpl other = (CompositeSpriteImpl) object;
        if (subSprites.size() != other.subSprites.size()) {
            return false;
        }
        if (!subSprites.equals(other.subSprites)) {
            return false;
        }

        return Objects.equals(getIngredient(), other.getIngredient())
                && Objects.equals(getPbPositionXScale(), other.getPbPositionXScale())
                && Objects.equals(getPbPositionYScale(), other.getPbPositionYScale());
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                getIngredient(),
                getPbPositionXScale(),
                getPbPositionYScale(),
                subSprites
        );
    }

    /**
     * @return a string containing the most important variables.
     */
    @Override
    public String toString() {
        String string = "[CompositeSpriteImpl:";
        for (final Sprite sprite : subSprites) {
            string = subSprites + sprite.toString();
        }
        return string + "]";
    }
}
