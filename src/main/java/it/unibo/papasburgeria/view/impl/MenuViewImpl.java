package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import org.tinylog.Logger;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.Serial;

/**
 * Menu View.
 */
public class MenuViewImpl extends AbstractBaseView {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs the MenuView.
     */
    @Inject
    public MenuViewImpl() {
        Logger.info("MenuView created");

        super.getInterfacePanel().setLayout(new FlowLayout());

        final JButton button = new JButton("Start Game");
        button.setSize(100, 50);
        super.getInterfacePanel().add(button);
    }

    /**
     * @inheritDoc
     */
    @Override
    void update(final double delta) {
        Logger.info("MenuView updated, last frame: " + delta);
    }

    @Override
    void paintComponentDelegate(final Graphics g) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        super.showScene();

        Logger.info("MenuView shown");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        Logger.info("MenuView hidden");

        super.hideScene();
    }
}
