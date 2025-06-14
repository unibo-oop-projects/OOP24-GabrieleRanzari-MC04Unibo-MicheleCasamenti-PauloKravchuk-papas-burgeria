package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import it.unibo.papasburgeria.utils.api.ResourceService;
import org.tinylog.Logger;

import java.awt.Graphics;
import java.io.Serial;

/**
 * Manages the GUI for the grill scene in the game.
 */
public class GrillViewImpl extends AbstractBaseView {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor, creates and initializes the UI elements.
     *
     * @param resourceService the service that handles resource obtainment.
     */
    @Inject
    public GrillViewImpl(final ResourceService resourceService) {
        Logger.info("GrillView created");

        super.setStaticBackgroundImage(resourceService.getImage("grill_background.png"));
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
    void paintComponentDelegate(final Graphics g) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        Logger.info("GrillView shown");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        Logger.info("GrillView hidden");
    }
}
