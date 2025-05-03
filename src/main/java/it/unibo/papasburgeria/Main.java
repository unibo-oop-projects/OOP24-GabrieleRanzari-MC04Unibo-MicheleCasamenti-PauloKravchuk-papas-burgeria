package it.unibo.papasburgeria;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.controller.impl.GameControllerImpl;
import it.unibo.papasburgeria.di.MainModule;

/**
 * Main class.
 */
public class Main {

    /**
     * Main method.
     * @param args arguments
     */
    public static void main(final String[] args) {
        // Using production stage to construct services before the game logic starts
        final Injector injector = Guice.createInjector(Stage.PRODUCTION, new MainModule());

        GameController controller = new GameControllerImpl();
        controller.startGame();
        System.out.printf(controller.toString());
    }
}
