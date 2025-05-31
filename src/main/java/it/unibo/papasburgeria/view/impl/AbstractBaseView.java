package it.unibo.papasburgeria.view.impl;

import it.unibo.papasburgeria.utils.api.scene.BaseScene;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
// import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
        this.gamePanel = new JPanel();
        this.interfacePanel = new JPanel();
        this.staticBackground = new JLabel();

        //this.gamePanel.setBackground(new Color(0, 0, 0, 0));
        //this.interfacePanel.setBackground(new Color(0, 0, 0, 0));

        // calling .add of the super-class JLayeredPane, this.add can be overridden
        super.add(this.staticBackground, DEFAULT_LAYER);
        super.add(this.gamePanel, PALETTE_LAYER);
        super.add(this.interfacePanel, MODAL_LAYER);

        super.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                updatePanelSizes();
            }

            @Override
            public void componentShown(final ComponentEvent e) {
                updatePanelSizes();
            }
        });
    }

    private void updatePanelSizes() {
        final Dimension size = this.getSize();
        this.staticBackground.setSize(size.width, size.height);
        this.gamePanel.setSize(size.width, size.height);
        this.interfacePanel.setSize(size.width, size.height);
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
