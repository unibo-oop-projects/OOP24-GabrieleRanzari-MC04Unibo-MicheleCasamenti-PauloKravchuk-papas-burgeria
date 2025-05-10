package it.unibo.papasburgeria.di;

import com.google.inject.AbstractModule;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.impl.GameModelImpl;

/**
 * Guide module responsible for the Model part of MVC.
 */
public class ModelModule extends AbstractModule {

    /**
     * @inheritDoc
     */
    @Override
    protected void configure() {
        // API to implementation bindings
        bind(GameModel.class).to(GameModelImpl.class);
    }
}
