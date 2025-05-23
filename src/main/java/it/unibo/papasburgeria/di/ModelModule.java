package it.unibo.papasburgeria.di;

import com.google.inject.AbstractModule;
import it.unibo.papasburgeria.model.api.DayManager;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.IngredientUnlocker;
import it.unibo.papasburgeria.model.impl.DayManagerImpl;
import it.unibo.papasburgeria.model.impl.GameModelImpl;
import it.unibo.papasburgeria.model.impl.IngredientUnlockerImpl;

/**
 * Guide module responsible for the Model part of MVC.
 */
class ModelModule extends AbstractModule {

    /**
     * @inheritDoc
     */
    @Override
    protected void configure() {
        // API to implementation bindings
        bind(GameModel.class).to(GameModelImpl.class);
        bind(DayManager.class).to(DayManagerImpl.class);
        bind(IngredientUnlocker.class).to(IngredientUnlockerImpl.class);
    }
}
