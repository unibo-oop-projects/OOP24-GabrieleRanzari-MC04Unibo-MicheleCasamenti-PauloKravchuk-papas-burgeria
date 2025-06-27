package it.unibo.papasburgeria.di;

import com.google.inject.AbstractModule;
import it.unibo.papasburgeria.view.api.GameView;
import it.unibo.papasburgeria.view.api.components.DrawingManager;
import it.unibo.papasburgeria.view.impl.GameViewImpl;
import it.unibo.papasburgeria.view.impl.components.DrawingManagerImpl;

/**
 * Guide module responsible for the View part of MVC.
 */
class ViewModule extends AbstractModule {

    /**
     * @inheritDoc
     */
    @Override
    protected void configure() {
        // API to implementation bindings
        bind(GameView.class).to(GameViewImpl.class);
        bind(DrawingManager.class).to(DrawingManagerImpl.class);
    }
}
