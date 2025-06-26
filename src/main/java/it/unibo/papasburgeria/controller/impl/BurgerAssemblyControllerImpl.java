package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.PantryModel;
import it.unibo.papasburgeria.model.api.Patty;
import org.tinylog.Logger;

import java.util.List;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;
import static it.unibo.papasburgeria.model.impl.GameModelImpl.MAX_COOKED_PATTIES;
import static it.unibo.papasburgeria.model.impl.IngredientImpl.MAX_LEFT_ACCURACY;
import static it.unibo.papasburgeria.model.impl.IngredientImpl.MAX_RIGHT_ACCURACY;
import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.HAMBURGER_X_POS_SCALE;
import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.MAX_X_POS_SCALE_TO_DROP_ON_HAMBURGER;
import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.MIN_X_POS_SCALE_TO_DROP_ON_HAMBURGER;

/**
 * @inheritDoc
 */
@Singleton
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "model is injected and shared intentionally")
public class BurgerAssemblyControllerImpl implements BurgerAssemblyController {
    private final GameModel model;
    private final PantryModel pantryModel;

    /**
     * Default constructor that saves the game model and the pantryModel given via injection.
     *
     * @param model       the game model
     * @param pantryModel the model containing the list of unlocked ingredients
     */
    @Inject
    public BurgerAssemblyControllerImpl(final GameModel model, final PantryModel pantryModel) {
        this.model = model;
        this.pantryModel = pantryModel;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean addIngredient(final Ingredient ingredient) {
        final String ingredientDescription;
        final Hamburger hamburger = model.getHamburgerOnAssembly();
        if (DEBUG_MODE) {
            ingredientDescription = "Ingredient (" + ingredient.getIngredientType().getName() + ") ";
        }

        if (hamburger.addIngredient(ingredient)) {
            if (DEBUG_MODE) {
                Logger.info(ingredientDescription + "added successfully");
            }
            model.setHamburgerOnAssembly(hamburger);
            return true;
        } else {
            if (DEBUG_MODE) {
                Logger.error(ingredientDescription + "can't be added");
            }
            return false;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeLastIngredient() {
        String ingredientDescription;
        if (DEBUG_MODE) {
            ingredientDescription = "Ingredient (empty)";
        }

        final Hamburger hamburger = model.getHamburgerOnAssembly();
        if (hamburger.getIngredients().isEmpty()) {
            if (DEBUG_MODE) {
                Logger.error(ingredientDescription + " could not be removed");
            }
            return;
        }

        if (DEBUG_MODE) {
            final Ingredient last = hamburger.getIngredients().getLast();
            ingredientDescription = "Ingredient" + " (" + last.getIngredientType().getName() + ")";
        }

        if (hamburger.removeLastIngredient()) {
            if (DEBUG_MODE) {
                Logger.info(ingredientDescription + " removed successfully");
            }
            model.setHamburgerOnAssembly(hamburger);
        } else {
            if (DEBUG_MODE) {
                Logger.error(ingredientDescription + " could not be removed");
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Ingredient> getIngredients() {
        return List.copyOf(model.getHamburgerOnAssembly().getIngredients());
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isIngredientUnlocked(final IngredientEnum ingredientType) {
        return pantryModel.isIngredientUnlocked(ingredientType);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Patty> getCookedPatties() {
        return List.copyOf(model.getCookedPatties());
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean addCookedPatty(final Patty patty) {
        final List<Patty> patties = model.getCookedPatties();
        if (patties.size() == MAX_COOKED_PATTIES) {
            return false;
        }
        patties.add(patty);
        model.setCookedPatties(patties);
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeCookedPatty(final Patty patty) {
        final List<Patty> patties = model.getCookedPatties();
        patties.remove(patty);
        model.setCookedPatties(patties);
    }

    /**
     * @inheritDoc
     */
    @Override
    public double calculateAccuracy(final double pbPositionXScale) {
        final double halfRange = (MAX_X_POS_SCALE_TO_DROP_ON_HAMBURGER - MIN_X_POS_SCALE_TO_DROP_ON_HAMBURGER) / 2.0;
        final double difference = pbPositionXScale - HAMBURGER_X_POS_SCALE;
        final double accuracy = difference / halfRange;
        return Math.max(MAX_LEFT_ACCURACY, Math.min(MAX_RIGHT_ACCURACY, accuracy));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void changeIngredientAccuracy(final Ingredient ingredient, final double accuracy) {
        ingredient.setPlacementAccuracy(accuracy);
        removeLastIngredient();
        addIngredient(ingredient);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<IngredientEnum> getUnlockedIngredients() {
        return List.copyOf(pantryModel.getUnlockedIngredients());
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "[BurgerAssemblyControllerImpl]";
    }
}
