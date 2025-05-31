package it.unibo.papasburgeria.view.impl;

import it.unibo.papasburgeria.utils.api.scene.BaseScene;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serial;

/**
 * Base class for the views.
 */
abstract class AbstractBaseView extends JLayeredPane implements BaseScene {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JLabel staticBackground; // static background layer
    private final JPanel gamePanel; // layer that gets repainted
    private final JPanel interfacePanel; // static interface layer

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
        this.staticBackground = new JLabel();

        this.gamePanel.setBackground(new Color(0, 0, 0, 0));
        this.interfacePanel.setBackground(Color.BLUE);

        // calling .add of the super-class JLayeredPane, this.add can be overridden
        super.add(this.staticBackground, DEFAULT_LAYER);
        super.add(this.gamePanel, PALETTE_LAYER);
        super.add(this.interfacePanel, MODAL_LAYER);
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
     * @inheritDoc
     */
    @Override
    public void showScene() {
        this.setVisible(true);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        this.setVisible(false);
    }

    @Override
    public void doLayout() {
        final Dimension size = this.getSize();
        for (final Component c : getComponents()) {
            c.setBounds(0, 0, size.width, size.height);
        }
    }

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
    void setStaticBackgroundImage(final Icon imageIcon) {
        if (imageIcon == null) {
            throw new IllegalArgumentException("ImageIcon cannot be null");
        }

        staticBackground.setIcon(imageIcon);
        this.repaint();
    }
}
