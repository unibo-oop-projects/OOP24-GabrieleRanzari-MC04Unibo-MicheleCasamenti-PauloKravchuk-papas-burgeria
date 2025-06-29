package it.unibo.papasburgeria;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import it.unibo.papasburgeria.di.MainModule;
import it.unibo.papasburgeria.view.api.GameView;

/**
 * Main class.
 */
public final class Main {
    /**
     * Defines the debug flag to enable logs.
     */
    public static final boolean DEBUG_MODE = true;

    private Main() {
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(final String[] args) {
        // Using the production stage to construct services before the game logic starts
        final Injector injector = Guice.createInjector(Stage.PRODUCTION, new MainModule());
        final GameView gameView = injector.getInstance(GameView.class);
        gameView.startGame();
    }
}
