package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import org.tinylog.Logger;
import java.io.Serial;

/**
 * Manages the GUI for the burger assembly scene in the game.
 */
public class BurgerAssemblyViewImpl extends AbstractBaseView {
    @Serial
    private static final long serialVersionUID = 1L;

    private final transient BurgerAssemblyController controller;

    /**
     * Default constructor.
     *
     * @param controller Controller for the burger assembly scene.
     */
    @Inject
    public BurgerAssemblyViewImpl(final BurgerAssemblyController controller) {
        this.controller = controller;
    }

    /**
     * @inheritDoc
     */
    @Override
    void update() {
        Logger.info("BurgerAssemblyView updated");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        super.showScene();

        Logger.info("BurgerAssemblyView shown");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        Logger.info("BurgerAssemblyView hidden");

        super.hideScene();
    }

    /**
     * @return a string containing the controller.
     */
    @Override
    public String toString() {
        return "[BurgerAssemblyViewImpl: " + controller.toString() + "]";
    }
}
