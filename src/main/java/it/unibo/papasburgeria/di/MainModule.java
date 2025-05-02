package it.unibo.papasburgeria.di;

import com.google.inject.AbstractModule;

/**
 * Main Guice module that installs the rest submodules.
 */
public class MainModule extends AbstractModule {

    /**
     * @inheritDoc
     */
    @Override
    protected void configure() {
        // follows an instantiation order to an extent, utils first and views last
        install(new UtilsModule());
        install(new ModelModule());
        install(new ControllerModule());
        install(new ViewModule());
    }
}
