package it.unibo.papasburgeria.di;

import com.google.inject.AbstractModule;

/**
 * Main Guice module that installs the rest submodules.
 */
public class MainModule extends AbstractModule {

    /**
     * Constructs a main module that installs all the others and is wired into Guice.
     */
    public MainModule() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        /*
         Follows an instantiation order to an extent, utils first and views last.

         Bind API to their implementation within these modules, that way they can be
         inter-exchanged easily without relying on hardcoded implementation in classes.
        */
        install(new UtilsModule());
        install(new ModelModule());
        install(new ControllerModule());
        install(new ViewModule());
    }
}
