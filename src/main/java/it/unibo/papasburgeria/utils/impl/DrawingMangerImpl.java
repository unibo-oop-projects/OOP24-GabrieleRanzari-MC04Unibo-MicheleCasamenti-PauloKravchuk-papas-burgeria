package it.unibo.papasburgeria.utils.impl;

import java.awt.Graphics2D;
import java.awt.Image;

/**
 * Manages of varius components.
 */
public class DrawingMangerImpl {
    private final Graphics2D graphics;

    /**
     * @param graphics the graphics where to draw.
     */
    DrawingMangerImpl(final Graphics2D graphics) {
        this.graphics = graphics;
    }

    /**
     * Draws something.
     *
     * @param image the something.
     */
    public void drawSomething(final Image image) {
        graphics.drawImage(image, 0, 0, null);
    }
}
