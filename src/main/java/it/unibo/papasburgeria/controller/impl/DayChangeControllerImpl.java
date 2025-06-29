package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.DayChangeController;
import it.unibo.papasburgeria.model.DaysEnum;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.GameModel;

import java.util.List;
import java.util.Objects;

import static it.unibo.papasburgeria.model.impl.UnlockSchedule.UNLOCK_SCHEDULE;

/**
 * @inheritDoc
 */
@Singleton
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The game model is injected and shared intentionally")
public class DayChangeControllerImpl implements DayChangeController {
    private final GameModel model;

    /**
     * Default constructor that saves the game model given via injection.
     *
     * @param model the game model
     */
    @Inject
    public DayChangeControllerImpl(final GameModel model) {
        this.model = model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IngredientEnum> getIngredientsUnlockedToday() {
        final DaysEnum currentDay = DaysEnum.getDay(model.getCurrentDay());
        if (Objects.isNull(currentDay)) {
            return List.of();
        } else {
            return List.copyOf(UNLOCK_SCHEDULE.get(currentDay));
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getCurrentDayNumber() {
        return model.getCurrentDay();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "DayChangeControllerImpl{"
                + "model=" + model
                + '}';
    }
}
