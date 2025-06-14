package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.GrillController;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.view.impl.GameViewImpl;

import java.util.Objects;

/**
 * @inheritDoc
 */
@Singleton
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "model is injected and shared intentionally")
public class GrillControllerImpl implements GrillController {
    private static final int GRILL_ROWS = 4;
    private static final int GRILL_COLUMNS = 3;

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

    /**
     * @inheritDoc
     */
    @Override
    public void cookPatty(final Patty patty) {
        final double cookLevelIncrement = 3.0 / GameViewImpl.FRAMERATE;
        if (patty.isFlipped()) {
            patty.setBottomCookLevel(patty.getBottomCookLevel() + cookLevelIncrement);
        } else {
            patty.setTopCookLevel(patty.getTopCookLevel() + cookLevelIncrement);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void cookPattiesOnGrill(final Patty[][] patties) {
        for (final Patty[] pattyRow : patties) {
            for (final Patty patty : pattyRow) {
                if (Objects.nonNull(patty)) {
                    cookPatty(patty);
                }
            }
        }
    }
}
