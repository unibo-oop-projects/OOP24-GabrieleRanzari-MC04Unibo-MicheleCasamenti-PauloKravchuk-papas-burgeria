package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import org.tinylog.Logger;

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
    }

    /**
     * @inheritDoc
     */
    @Override
    void update(final double delta) {
        Logger.info("MenuView updated, last frame: " + delta);
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
