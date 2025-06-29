package it.unibo.papasburgeria.view.impl;

import it.unibo.papasburgeria.utils.api.scene.BaseScene;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serial;

/**
 * Base class for the views. An extension of JLayeredPane,
 * with an implementation of methods to act as a scene as well.
 *
 * <p>
 * See {@link BaseScene} for interface details.
 */
abstract class AbstractBaseView extends JLayeredPane implements BaseScene {
    static final Color DEFAULT_BACKGROUND_COLOR = new Color(0, 0, 0, 0);
    static final Color SHADOW_OVERLAY_COLOR = new Color(0, 0, 0, 128);
    static final Color DEFAULT_BUTTON_BACKGROUND_COLOR = new Color(40, 122, 33);
    static final Color DEFAULT_BUTTON_TEXT_COLOR = Color.WHITE;

    static final float DEFAULT_SOUND_VOLUME = 0.2f;

    @Serial
    private static final long serialVersionUID = 1L;
    private final JLabel staticBackground; // static background layer
    private final JPanel gamePanel; // layer that gets repainted
    private final JPanel interfacePanel; // static interface layer;

    private Image backgroundImage;

    /**
     * Constructs itself as a JLayeredPane, having a base game panel to be redrawn
     * and an overlay interface panel for UI components.
     */
    AbstractBaseView() {
        this.gamePanel = new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);

                // template method ig, not sure if this is the best way to do it
                AbstractBaseView.this.paintComponentDelegate(g);
            }
        };

        this.interfacePanel = new JPanel();
        this.staticBackground = new JLabel() {
            // this is called at each frame due to the interfacePanel & gamePanel being opaque
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);

                // ScalableLayout supports image scaling, but JLayeredPanel doesn't support ScalableLayout
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        this.staticBackground.setBackground(DEFAULT_BACKGROUND_COLOR);
        this.staticBackground.setOpaque(true);
        this.gamePanel.setBackground(DEFAULT_BACKGROUND_COLOR);
        this.gamePanel.setOpaque(false);
        this.interfacePanel.setBackground(DEFAULT_BACKGROUND_COLOR);
        this.interfacePanel.setOpaque(false);

        super.add(this.staticBackground, DEFAULT_LAYER);
        super.add(this.gamePanel, PALETTE_LAYER);
        super.add(this.interfacePanel, MODAL_LAYER);

        super.revalidate();
        super.setVisible(false);
    }

    /**
     * Override to make sure children panels/label are always scaled to 1.
     *
     * <p>
     * Unfortunately the use of {@link it.unibo.papasburgeria.view.api.components.ScaleConstraint}, when adding components,
     * is not possible due to how Swing handles JLayeredPanes.
     */
    @Override
    public void doLayout() {
        final Dimension size = this.getSize();
        for (final Component component : this.getComponents()) {
            component.setSize(size.width, size.height);
        }
    }

    /**
     * Used to interact and update the view instances at framerate.
     * Make sure to keep implementations lightweight and check for visibility if
     * something has to be processed only when this instance is visible.
     *
     * @param delta time that has elapsed since the last call in seconds
     */
    abstract void update(double delta);

    /**
     * Called within the game interface's paintComponent override, to
     * allow subclasses to implement their own painting.
     *
     * @param g the Graphics object to protect
     */
    abstract void paintComponentDelegate(Graphics g);

    /**
     * Retrieves the base game panel for rendering game content.
     *
     * @return the JPanel that represents the game panel
     */
    JPanel getGamePanel() {
        return gamePanel;
    }

    /**
     * Retrieves the overlay panel for UI components.
     *
     * @return the JPanel that represents the interface panel
     */
    JPanel getInterfacePanel() {
        return interfacePanel;
    }

    /**
     * Sets an image icon as a background to be displayed in this view.
     *
     * @param imageIcon image to display
     */
    void setStaticBackgroundImage(final Image imageIcon) {
        if (imageIcon == null) {
            throw new IllegalArgumentException("ImageIcon cannot be null");
        }

        this.backgroundImage = imageIcon;
    }

    /**
     * Returns a string version of this class.
     */
    @Override
    public String toString() {
        return "AbstractBaseView{"
                +
                "staticBackground="
                + staticBackground
                +
                ", gamePanel="
                + gamePanel
                +
                ", interfacePanel="
                + interfacePanel
                +
                ", backgroundImage="
                + backgroundImage
                +
                '}';
    }
}
