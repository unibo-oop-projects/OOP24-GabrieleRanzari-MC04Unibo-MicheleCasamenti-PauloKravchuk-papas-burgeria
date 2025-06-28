package it.unibo.papasburgeria.di;

import com.google.inject.AbstractModule;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.controller.api.CustomerController;
import it.unibo.papasburgeria.controller.api.DayChangeController;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.controller.api.GrillController;
<<<<<<< Updated upstream
import it.unibo.papasburgeria.controller.api.OrderSelectionController;
=======
import it.unibo.papasburgeria.controller.api.MenuController;
>>>>>>> Stashed changes
import it.unibo.papasburgeria.controller.api.ShopController;
import it.unibo.papasburgeria.controller.impl.BurgerAssemblyControllerImpl;
import it.unibo.papasburgeria.controller.impl.CustomerControllerImpl;
import it.unibo.papasburgeria.controller.impl.DayChangeControllerImpl;
import it.unibo.papasburgeria.controller.impl.GameControllerImpl;
import it.unibo.papasburgeria.controller.impl.GrillControllerImpl;
<<<<<<< Updated upstream
import it.unibo.papasburgeria.controller.impl.OrderSelectionControllerImpl;
=======
import it.unibo.papasburgeria.controller.impl.MenuControllerImpl;
>>>>>>> Stashed changes
import it.unibo.papasburgeria.controller.impl.ShopControllerImpl;

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
        bind(MenuController.class).to(MenuControllerImpl.class);
        bind(CustomerController.class).to(CustomerControllerImpl.class);
        bind(BurgerAssemblyController.class).to(BurgerAssemblyControllerImpl.class);
        bind(GrillController.class).to(GrillControllerImpl.class);
        bind(ShopController.class).to(ShopControllerImpl.class);
        bind(DayChangeController.class).to(DayChangeControllerImpl.class);
        bind(OrderSelectionController.class).to(OrderSelectionControllerImpl.class);
    }
}
