package it.unibo.papasburgeria.utils.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.utils.api.Sprite;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

/**
 * Manages the drag component of the sprites.
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Component is used in read-only fashion")
public class SpriteDragManager implements MouseListener, MouseMotionListener {
    private final Component component;
    private final List<Sprite> sprites;
    private Sprite draggedSprite;
    private int dragOffsetX;
    private int dragOffsetY;

    /**
     * @param component the panel where to listen from.
     * @param sprites the list of draggable sprites.
     */
    public SpriteDragManager(final Component component, final List<Sprite> sprites) {
        this.component = component;
        this.sprites = List.copyOf(sprites);
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

        for (int index = sprites.size() - 1; index >= 0; index--) {
            final Sprite sprite = sprites.get(index);

            final int spriteX = sprite.calculateX(component.getWidth());
            final int spriteY = sprite.calculateY(component.getHeight());
            final int spriteWidth = sprite.calculateWidth(component.getWidth());
            final int spriteHeight = sprite.calculateHeight(component.getHeight());
            if (mouseX >= spriteX && mouseX <= spriteX + spriteWidth
                    && mouseY >= spriteY && mouseY <= spriteY + spriteHeight) {
                draggedSprite = sprite;
                dragOffsetX = mouseX - spriteX;
                dragOffsetY = mouseY - spriteY;
                break;
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void mouseReleased(final MouseEvent event) {
        draggedSprite = null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void mouseDragged(final MouseEvent event) {
        if (draggedSprite == null) {
            return;
        }

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
    @Override public void mouseMoved(final MouseEvent event) {

    }

    /**
     * @inheritDoc
     */
    @Override public void mouseClicked(final MouseEvent event) {

    }

    /**
     * @inheritDoc
     */
    @Override public void mouseEntered(final MouseEvent event) {

    }

    /**
     * @inheritDoc
     */
    @Override public void mouseExited(final MouseEvent event) {

    }
}
