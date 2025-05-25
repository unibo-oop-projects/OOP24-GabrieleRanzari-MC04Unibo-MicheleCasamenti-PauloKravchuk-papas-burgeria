package it.unibo.papasburgeria.di;

import com.google.inject.AbstractModule;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.controller.impl.BurgerAssemblyControllerImpl;
import it.unibo.papasburgeria.controller.impl.GameControllerImpl;

/**
 * Guide module responsible for the Controller part of MVC.
 */
public class ControllerModule extends AbstractModule {

    /**
     * @inheritDoc
     */
    @Override
    protected void configure() {
        // API to implementation bindings
        bind(GameController.class).to(GameControllerImpl.class);
        bind(BurgerAssemblyController.class).to(BurgerAssemblyControllerImpl.class);
    }
}
