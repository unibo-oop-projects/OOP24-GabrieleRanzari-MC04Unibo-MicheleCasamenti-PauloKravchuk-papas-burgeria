package it.unibo.papasburgeria.utils.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.utils.api.Sprite;
import it.unibo.papasburgeria.utils.api.SpriteDropListener;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

/**
 * Manages the drag component of the sprites.
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Component is used in read-only fashion")
public class SpriteDragManagerImpl implements MouseListener, MouseMotionListener {
    private final Component component;
    private final List<Sprite> sprites;
    private final SpriteDropListener dropListener;
    private Sprite draggedSprite;
    private Sprite originalSprite;
    private int dragOffsetX;
    private int dragOffsetY;
    private boolean dragged;

    /**
     * @param component          the panel where to listen from.
     * @param sprites            the list of draggable sprites.
     * @param spriteDropListener the listener for if sprites are dropped.
     */
    public SpriteDragManagerImpl(final Component component, final List<Sprite> sprites,
                                 final SpriteDropListener spriteDropListener) {
        this.component = component;
        this.sprites = sprites;
        this.dropListener = spriteDropListener;
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void mousePressed(final MouseEvent event) {
        final int mouseX = event.getX();
        final int mouseY = event.getY();
        dragged = false;

        final Sprite sprite = getSprite(mouseX, mouseY);
        if (sprite != null) {
            dropListener.spritePressed(sprite);

            final int spriteX = sprite.calculateX(component.getWidth());
            final int spriteY = sprite.calculateY(component.getHeight());
            final Ingredient ingredient = sprite.getIngredient();
            if (ingredient instanceof Patty patty) {
                patty.setStopCooking(true);
                sprite.setIngredient(ingredient);
            }
            if (!sprite.isDraggable()) {
                final Sprite copy = new SpriteImpl(sprite);
                if (IngredientEnum.SAUCES.contains(sprite.getIngredientType())
                        && !sprite.isRemovable()) {
                    sprite.setVisible(false);
                    originalSprite = sprite;
                    copy.flipImageVertically();
                }
                sprites.add(copy);
                if (!sprite.isCloneable()) {
                    sprite.setVisible(false);
                    originalSprite = sprite;
                }
                draggedSprite = copy;
            } else {
                draggedSprite = sprite;
            }
            dragOffsetX = mouseX - spriteX;
            dragOffsetY = mouseY - spriteY;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void mouseReleased(final MouseEvent event) {
        if (draggedSprite != null) {
            final Ingredient ingredient = draggedSprite.getIngredient();
            if (!dragged) {
                dropListener.spriteClicked(originalSprite);
            } else {
                if (ingredient instanceof Patty patty) {
                    patty.setStopCooking(false);
                    draggedSprite.setIngredient(ingredient);
                }
                dropListener.spriteDropped(draggedSprite);
                if (draggedSprite.isFlipped()) {
                    draggedSprite.flipImageVertically();
                }
            }
            draggedSprite = null;
            if (originalSprite != null) {
                originalSprite.setVisible(true);
                originalSprite = null;
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void mouseDragged(final MouseEvent event) {
        if (draggedSprite == null) {
            return;
        }
        dragged = true;

        final int newSpriteX = event.getX() - dragOffsetX;
        final int newSpriteY = event.getY() - dragOffsetY;
        final double spriteXRatio = newSpriteX / (double) component.getWidth();
        final double spriteYRatio = newSpriteY / (double) component.getHeight();

        draggedSprite.setPbPositionXScale(spriteXRatio);
        draggedSprite.setPbPositionYScale(spriteYRatio);
        component.repaint();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void mouseMoved(final MouseEvent event) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void mouseClicked(final MouseEvent event) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void mouseEntered(final MouseEvent event) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void mouseExited(final MouseEvent event) {

    }

    /**
     * Finds and return the sprite that you interacted with.
     *
     * @param mouseX the x value of the mouse.
     * @param mouseY the y value of the mouse.
     * @return the sprite that you interacted with.
     */
    private Sprite getSprite(final int mouseX, final int mouseY) {
        for (int index = sprites.size() - 1; index >= 0; index--) {
            final Sprite sprite = sprites.get(index);

            final int spriteX = sprite.calculateX(component.getWidth());
            final int spriteY = sprite.calculateY(component.getHeight());
            final int spriteWidth = sprite.calculateWidth(component.getWidth());
            final int spriteHeight = sprite.calculateHeight(component.getHeight());
            if (mouseX >= spriteX && mouseX <= spriteX + spriteWidth
                    && mouseY >= spriteY && mouseY <= spriteY + spriteHeight) {
                return sprite;
            }
        }
        return null;
    }
}
