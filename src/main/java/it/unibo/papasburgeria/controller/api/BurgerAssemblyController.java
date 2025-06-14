package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;

import java.util.List;

/**
 * Manages the interaction between the View and the Model for the burger assembly scene.
 */
public interface BurgerAssemblyController {

    /**
     * Adds the ingredient to the current hamburger.
     *
     * @param ingredientType the type of ingredient to add.
     */
    void addIngredient(IngredientEnum ingredientType);

    /**
     * Removes the last ingredient added to the hamburger.
     */
    void removeLastIngredient();

    /**
     * @return the list of ingredients of the current Hamburger.
     */
    List<Ingredient> getIngredients();

    /**
     * @param ingredientType the ingredient
     * @return true if the ingredient is unlocked
     */
    boolean isIngredientUnlocked(IngredientEnum ingredientType);
}
