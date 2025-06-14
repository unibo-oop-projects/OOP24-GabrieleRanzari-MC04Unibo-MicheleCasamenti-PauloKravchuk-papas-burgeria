package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.controller.api.GrillController;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Patty;

/**
 * @inheritDoc
 */
@Singleton
public class GrillControllerImpl implements GrillController {
    private final GameModel model;

    /**
     * Default constructor.
     *
     * @param model the game model.
     */
    @Inject
    public GrillControllerImpl(final GameModel model) {
        this.model = model;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean addPatty(final Patty patty, final int row, final int column) {
        return false;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Patty removePatty(final int row, final int column) {
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void flipPatty(final Patty patty) {
        patty.flip();
    }
}
