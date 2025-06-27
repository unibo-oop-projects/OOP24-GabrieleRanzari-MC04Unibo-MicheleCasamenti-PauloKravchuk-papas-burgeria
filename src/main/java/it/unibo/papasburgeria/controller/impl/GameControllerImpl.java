package it.unibo.papasburgeria.controller.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.PantryModel;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.scene.SceneService;
import it.unibo.papasburgeria.view.impl.DayChangeViewImpl;
import jakarta.inject.Inject;

/**
 * Implementation of GameController.
 *
 * <p>
 * See {@link GameController} for interface details.
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "model is injected and shared intentionally")
public class GameControllerImpl implements GameController {
    private final GameModel model;
    private final SceneService sceneService;
    private final ResourceService resourceService;
    private final PantryModel pantryModel;

    /**
     * Constructs the controller with its model and several utility classes like for scene-switching or resource disposing.
     *
     * @param model           the GameModel manager
     * @param sceneService    service required to handle scenes
     * @param resourceService service required to handle resources
     * @param pantryModel     the model that stores which ingredients are unlocked
     */
    @Inject
    public GameControllerImpl(final GameModel model, final SceneService sceneService, final ResourceService resourceService,
                              final PantryModel pantryModel) {
        this.model = model;
        this.sceneService = sceneService;
        this.resourceService = resourceService;
        this.pantryModel = pantryModel;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void startGame() {
        sceneService.switchTo("Shop");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void endGame() {
        resourceService.dispose();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void switchToScene(final String sceneName) {
        sceneService.switchTo(sceneName);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void nextDay() {
        model.nextDay();
        pantryModel.unlockForDay(model.getCurrentDayNumber());
        switchToScene(DayChangeViewImpl.VIEW_NAME);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getCurrentDayNumber() {
        return model.getCurrentDayNumber();
    }
}
