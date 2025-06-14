package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.PantryModel;
import it.unibo.papasburgeria.model.impl.HamburgerImpl;
import it.unibo.papasburgeria.model.impl.IngredientImpl;
import org.tinylog.Logger;

import java.util.List;

/**
 * @inheritDoc
 */
@Singleton
public class BurgerAssemblyControllerImpl implements BurgerAssemblyController {
    private final Hamburger hamburger;
    private final PantryModel pantryModel;

    /**
     * Default constructor, creates a new empty hamburger.
     *
     * @param pantryModel the model containing the list of unlocked ingredients.
     */
    @Inject
    public BurgerAssemblyControllerImpl(final PantryModel pantryModel) {
        this.hamburger = new HamburgerImpl();
        this.pantryModel = pantryModel;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addIngredient(final IngredientEnum ingredientType) {
        final Ingredient ingredient = new IngredientImpl(ingredientType);
        final String ingredientDescription = "Ingredient (" + ingredient.getIngredientType() + ") ";

        if (hamburger.addIngredient(ingredient)) {
            Logger.info(ingredientDescription + "added successfully");
        } else {
            Logger.error(ingredientDescription + "can't be added");
        }
    }

    /**
     * Removes the last ingredient added to the hamburger.
     */
    @Override
    public void removeLastIngredient() {
        String ingredientDescription = "Ingredient (EMPTY)";

        if (!hamburger.getIngredients().isEmpty()) {
            final Ingredient last = hamburger.getIngredients().getLast();
            ingredientDescription = "Ingredient (" + last.getIngredientType() + ")";
        }

        if (hamburger.removeLastIngredient()) {
            Logger.info(ingredientDescription + " removed successfully");
        } else {
            Logger.error(ingredientDescription + " could not be removed");
        }
    }

    /**
     * @return the list of ingredients of the current Hamburger.
     */
    @Override
    public List<Ingredient> getIngredients() {
        return List.copyOf(hamburger.getIngredients());
    }

    /**
     * @param ingredientType the ingredient
     * @return true if the ingredient is unlocked
     */
    @Override
    public boolean isIngredientUnlocked(final IngredientEnum ingredientType) {
        return pantryModel.isIngredientUnlocked(ingredientType);
    }

    /**
     * @return a string containing the current hamburger.
     */
    @Override
    public String toString() {
        return "[BurgerAssemblyControllerImpl: " + hamburger.toString() + "]";
    }
}
