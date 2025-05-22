package it.unibo.papasburgeria.view.impl;

import it.unibo.papasburgeria.utils.api.scene.BaseScene;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
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

    private final JPanel gamePanel;
    private final JPanel interfacePanel;

    /**
     * Constructs itself as a JLayeredPane, having a base game panel to be redrawn
     * and an overlay interface panel for UI components.
     */
    AbstractBaseView() {
        this.gamePanel = new JPanel();
        this.interfacePanel = new JPanel();

        // calling .add of the super-class JLayeredPane, this.add can be overridden
        super.add(this.gamePanel, DEFAULT_LAYER);
        super.add(this.interfacePanel, PALETTE_LAYER);

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
        final Dimension size = super.getSize();
        this.gamePanel.setBounds(0, 0, size.width, size.height);
        this.interfacePanel.setBounds(0, 0, size.width, size.height);
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
}
