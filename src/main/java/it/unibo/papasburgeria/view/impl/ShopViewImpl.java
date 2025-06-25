package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import it.unibo.papasburgeria.utils.api.ResourceService;
import org.tinylog.Logger;

import java.awt.Graphics;
import java.io.Serial;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;

/**
 * Manages the GUI for the shop scene in the game.
 */
public class ShopViewImpl extends AbstractBaseView {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor, creates and initializes the GUI elements.
     *
     * @param resourceService the service that handles resource obtainment
     */
    @Inject
    public ShopViewImpl(final ResourceService resourceService) {
        if (DEBUG_MODE) {
            Logger.info("Shop created");
        }

        super.setStaticBackgroundImage(resourceService.getImage("shop_background.png"));
    }

    /**
     * @inheritDoc
     */
    @Override
    void update(final double delta) {

    }

    /**
     * @inheritDoc
     */
    @Override
    final void paintComponentDelegate(final Graphics graphics) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        if (DEBUG_MODE) {
            Logger.info("Shop shown");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        if (DEBUG_MODE) {
            Logger.info("Shop hidden");
        }
    }
}
