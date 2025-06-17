package it.unibo.papasburgeria.di;

import com.google.inject.AbstractModule;

import it.unibo.papasburgeria.controller.api.CustomerController;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.controller.impl.CustomerControllerImpl;
import it.unibo.papasburgeria.controller.impl.GameControllerImpl;

/**
 * Guide module responsible for the Controller part of MVC.
 */
class ControllerModule extends AbstractModule {

    /**
     * @inheritDoc
     */
    @Override
    protected void configure() {
        // API to implementation bindings
        bind(GameController.class).to(GameControllerImpl.class);
        bind(CustomerController.class).to(CustomerControllerImpl.class);
    }
}
