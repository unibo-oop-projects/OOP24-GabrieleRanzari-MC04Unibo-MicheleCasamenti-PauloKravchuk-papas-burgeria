package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.GrillController;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Patty;

import java.util.List;
import java.util.Objects;

import static it.unibo.papasburgeria.model.impl.GameModelImpl.GRILL_COLUMNS;
import static it.unibo.papasburgeria.model.impl.GameModelImpl.GRILL_ROWS;
import static it.unibo.papasburgeria.model.impl.GameModelImpl.MAX_COOKED_PATTIES;
import static it.unibo.papasburgeria.model.impl.PattyImpl.MAX_COOK_LEVEL;
import static it.unibo.papasburgeria.model.impl.PattyImpl.MIN_COOK_LEVEL;
import static it.unibo.papasburgeria.view.impl.GameViewImpl.FRAMERATE;
import static it.unibo.papasburgeria.view.impl.GrillViewImpl.COOK_LEVEL_INCREMENT_PER_SECOND;
import static it.unibo.papasburgeria.view.impl.GrillViewImpl.MAX_X_POS_SCALE_TO_DROP_ON_GRILL;
import static it.unibo.papasburgeria.view.impl.GrillViewImpl.MAX_Y_POS_SCALE_TO_DROP_ON_GRILL;
import static it.unibo.papasburgeria.view.impl.GrillViewImpl.MIN_X_POS_SCALE_TO_DROP_ON_GRILL;
import static it.unibo.papasburgeria.view.impl.GrillViewImpl.MIN_Y_POS_SCALE_TO_DROP_ON_GRILL;

/**
 * @inheritDoc
 */
@Singleton
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The game model is injected and shared intentionally")
public class GrillControllerImpl implements GrillController {
    private final GameModel model;

    /**
     * Default constructor that saves the game model given via injection.
     *
     * @param model the game model
     */
    @Inject
    public GrillControllerImpl(final GameModel model) {
        this.model = model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Patty[][] getPattiesOnGrill() {
        return model.getPattiesOnGrill();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addPattyOnGrill(final Patty patty,
                                   final double pbPositionXScale,
                                   final double pbPositionYScale
    ) {
        final Patty[][] pattiesOnGrill = model.getPattiesOnGrill();

        final int row = calculatePosition(pbPositionYScale,
                MIN_Y_POS_SCALE_TO_DROP_ON_GRILL,
                MAX_Y_POS_SCALE_TO_DROP_ON_GRILL,
                GRILL_ROWS);
        final int column = calculatePosition(pbPositionXScale,
                MIN_X_POS_SCALE_TO_DROP_ON_GRILL,
                MAX_X_POS_SCALE_TO_DROP_ON_GRILL,
                GRILL_COLUMNS);

        if (Objects.isNull(pattiesOnGrill[row][column])) {
            pattiesOnGrill[row][column] = patty;
            model.setPattiesOnGrill(pattiesOnGrill);
            return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePattyFromGrill(final Patty patty) {
        final Patty[][] pattiesOnGrill = model.getPattiesOnGrill();

        for (int row = 0; row < GRILL_ROWS; row++) {
            for (int column = 0; column < GRILL_COLUMNS; column++) {
                if (Objects.nonNull(pattiesOnGrill[row][column])
                        && pattiesOnGrill[row][column].equals(patty)) {
                    pattiesOnGrill[row][column] = null;
                    model.setPattiesOnGrill(pattiesOnGrill);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Patty> getCookedPatties() {
        return List.copyOf(model.getCookedPatties());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addCookedPatty(final Patty patty) {
        final List<Patty> patties = model.getCookedPatties();
        if (patties.size() == MAX_COOKED_PATTIES) {
            return false;
        }
        patties.add(patty);
        model.setCookedPatties(patties);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCookedPatty(final Patty patty) {
        final List<Patty> patties = model.getCookedPatties();
        patties.remove(patty);
        model.setCookedPatties(patties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flipPatty(final Patty patty) {
        final Patty[][] pattiesOnGrill = model.getPattiesOnGrill();
        for (final Patty[] pattyRow : pattiesOnGrill) {
            for (final Patty pattyOnGrill : pattyRow) {
                if (Objects.nonNull(pattyOnGrill) && pattyOnGrill.equals(patty)) {
                    pattyOnGrill.flip();
                }
            }
        }
        model.setPattiesOnGrill(pattiesOnGrill);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cookPattiesOnGrill() {
        final Patty[][] patties = model.getPattiesOnGrill();
        for (final Patty[] pattyRow : patties) {
            for (final Patty patty : pattyRow) {
                if (Objects.nonNull(patty)) {
                    cookPatty(patty);
                }
            }
        }
        model.setPattiesOnGrill(patties);
    }

    /**
     * Calculates the segment index given the range, the number of segments and the position.
     *
     * @param position the position in scale
     * @param minPos   the minimum position in scale
     * @param maxPos   the maximum position in scale
     * @param segments the number of segments that the range is divided in
     * @return the segment index corresponding to the given position
     */
    private int calculatePosition(
            final double position,
            final double minPos,
            final double maxPos,
            final int segments
    ) {
        double norm = (position - minPos) / (maxPos - minPos);
        norm = Math.max(MIN_COOK_LEVEL, Math.min(MAX_COOK_LEVEL, norm));
        final int index = (int) (norm * segments);
        return (index == segments) ? segments - 1 : index;
    }

    /**
     * Cooks the face of the patty that is on the grill, keeping count of the FPS.
     *
     * @param patty the patty to cook
     */
    private void cookPatty(final Patty patty) {
        final double cookLevelIncrement = COOK_LEVEL_INCREMENT_PER_SECOND / FRAMERATE;
        if (patty.isFlipped()) {
            patty.setTopCookLevel(patty.getTopCookLevel() + cookLevelIncrement);
        } else {
            patty.setBottomCookLevel(patty.getBottomCookLevel() + cookLevelIncrement);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "GrillControllerImpl{"
                + "model=" + model
                + '}';
    }
}
