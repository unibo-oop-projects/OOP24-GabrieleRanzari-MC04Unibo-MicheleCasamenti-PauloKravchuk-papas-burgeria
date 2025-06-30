package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.HamburgerModel;
import it.unibo.papasburgeria.model.api.IngredientModel;
import it.unibo.papasburgeria.model.api.PantryModel;
import it.unibo.papasburgeria.model.api.PattyModel;
import it.unibo.papasburgeria.model.api.RegisterModel;

import java.util.List;

import static it.unibo.papasburgeria.model.impl.GameModelImpl.MAX_COOKED_PATTIES;
import static it.unibo.papasburgeria.model.impl.HamburgerModelImpl.MAX_INGREDIENTS;
import static it.unibo.papasburgeria.model.impl.IngredientModelImpl.MAX_LEFT_ACCURACY;
import static it.unibo.papasburgeria.model.impl.IngredientModelImpl.MAX_RIGHT_ACCURACY;
import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.HAMBURGER_X_POS_SCALE;
import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.MAX_X_POS_SCALE_TO_DROP_ON_HAMBURGER;
import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.MIN_X_POS_SCALE_TO_DROP_ON_HAMBURGER;

/**
 * Manages the interaction between the View and the Model for the burger assembly scene.
 */
@Singleton
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The models are injected and shared intentionally")
public class BurgerAssemblyControllerImpl implements BurgerAssemblyController {
    private final GameModel model;
    private final PantryModel pantryModel;
    private final RegisterModel registerModel;

    /**
     * Default constructor that saves the models given via injection.
     *
     * @param model         the game model
     * @param pantryModel   the model containing the list of unlocked ingredients
     * @param registerModel the register model
     */
    @Inject
    public BurgerAssemblyControllerImpl(final GameModel model,
                                        final PantryModel pantryModel,
                                        final RegisterModel registerModel
    ) {
        this.model = model;
        this.pantryModel = pantryModel;
        this.registerModel = registerModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addIngredient(final IngredientModel ingredient) {
        if (model.getHamburgerOnAssembly().getIngredients().size() == MAX_INGREDIENTS + 2) {
            return false;
        }

        final HamburgerModel hamburger = model.getHamburgerOnAssembly();
        if (hamburger.addIngredient(ingredient)) {
            model.setHamburgerOnAssembly(hamburger);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLastIngredient() {
        final HamburgerModel hamburger = model.getHamburgerOnAssembly();
        if (hamburger.getIngredients().isEmpty()) {
            return;
        }

        if (hamburger.removeLastIngredient()) {
            model.setHamburgerOnAssembly(hamburger);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HamburgerModel getHamburgerOnAssembly() {
        return model.getHamburgerOnAssembly().copyOf();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isIngredientUnlocked(final IngredientEnum ingredientType) {
        return pantryModel.isIngredientUnlocked(ingredientType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PattyModel> getCookedPatties() {
        return List.copyOf(model.getCookedPatties());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addCookedPatty(final PattyModel patty) {
        final List<PattyModel> patties = model.getCookedPatties();
        if (patties.size() == MAX_COOKED_PATTIES) {
            return false;
        }
        patties.add(patty);
        model.setCookedPatties(patties);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCookedPatty(final PattyModel patty) {
        final List<PattyModel> patties = model.getCookedPatties();
        patties.remove(patty);
        model.setCookedPatties(patties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateAccuracy(final double pbPositionXScale) {
        final double halfRange =
                (MAX_X_POS_SCALE_TO_DROP_ON_HAMBURGER - MIN_X_POS_SCALE_TO_DROP_ON_HAMBURGER) / 2.0;
        final double difference = pbPositionXScale - HAMBURGER_X_POS_SCALE;
        final double accuracy = difference / halfRange;
        return Math.max(MAX_LEFT_ACCURACY, Math.min(MAX_RIGHT_ACCURACY, accuracy));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeIngredientAccuracy(final IngredientModel ingredient, final double accuracy) {
        ingredient.setPlacementAccuracy(accuracy);
        removeLastIngredient();
        addIngredient(ingredient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IngredientEnum> getUnlockedIngredients() {
        return List.copyOf(pantryModel.getUnlockedIngredients());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BurgerAssemblyControllerImpl{"
                + "model=" + model
                + ", pantryModel=" + pantryModel
                + ", registerModel=" + registerModel
                + '}';
    }
}
