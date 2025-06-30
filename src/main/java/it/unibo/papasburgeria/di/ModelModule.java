package it.unibo.papasburgeria.di;

import com.google.inject.AbstractModule;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.HamburgerModel;
import it.unibo.papasburgeria.model.api.PantryModel;
import it.unibo.papasburgeria.model.api.RegisterModel;
import it.unibo.papasburgeria.model.api.ShopModel;
import it.unibo.papasburgeria.model.impl.GameModelImpl;
import it.unibo.papasburgeria.model.impl.HamburgerModelImpl;
import it.unibo.papasburgeria.model.impl.PantryModelImpl;
import it.unibo.papasburgeria.model.impl.RegisterModelImpl;
import it.unibo.papasburgeria.model.impl.ShopModelImpl;

/**
 * Guide module responsible for the Model part of MVC.
 */
class ModelModule extends AbstractModule {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        // API to implementation bindings
        bind(GameModel.class).to(GameModelImpl.class);
        bind(PantryModel.class).to(PantryModelImpl.class);
        bind(HamburgerModel.class).to(HamburgerModelImpl.class);
        bind(ShopModel.class).to(ShopModelImpl.class);
        bind(RegisterModel.class).to(RegisterModelImpl.class);
    }
}
