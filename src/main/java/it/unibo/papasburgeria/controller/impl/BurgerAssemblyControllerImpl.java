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
import it.unibo.papasburgeria.model.impl.GameModelImpl;
import org.tinylog.Logger;

import java.util.List;

/**
 * @inheritDoc
 */
@Singleton
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "model is injected and shared intentionally")
public class BurgerAssemblyControllerImpl implements BurgerAssemblyController {
    private final GameModel model;
    private final PantryModel pantryModel;

    /**
     * Default constructor, creates a new empty hamburger.
     *
     * @param model the game model.
     * @param pantryModel the model containing the list of unlocked ingredients.
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
        final String ingredientDescription = "Ingredient (" + ingredient.getIngredientType() + ") ";
        final Hamburger hamburger = model.getHamburgerOnAssembly();

        if (hamburger.addIngredient(ingredient)) {
            Logger.info(ingredientDescription + "added successfully");
            model.setHamburgerOnAssembly(hamburger);
            return true;
        } else {
            Logger.error(ingredientDescription + "can't be added");
            return false;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeLastIngredient() {
        String ingredientDescription = "Ingredient (EMPTY)";
        final Hamburger hamburger = model.getHamburgerOnAssembly();

        if (!hamburger.getIngredients().isEmpty()) {
            final Ingredient last = hamburger.getIngredients().getLast();
            ingredientDescription = "Ingredient (" + last.getIngredientType() + ")";
        }

        if (hamburger.removeLastIngredient()) {
            Logger.info(ingredientDescription + " removed successfully");
            model.setHamburgerOnAssembly(hamburger);
        } else {
            Logger.error(ingredientDescription + " could not be removed");
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
        if (patties.size() == GameModelImpl.MAX_COOKED_PATTIES) {
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
     * @return a string containing the name of the class.
     */
    @Override
    public String toString() {
        return "[BurgerAssemblyControllerImpl]";
    }
}
