package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.Patty;

import java.util.List;

/**
 * Manages the interaction between the View and the Model for the burger assembly scene.
 */
public interface BurgerAssemblyController {

    /**
     * Adds the ingredient to the current hamburger.
     *
     * @param ingredient the ingredient to add
     * @return true if the ingredient was added, false otherwise
     */
    boolean addIngredient(Ingredient ingredient);

    /**
     * Removes the last ingredient added to the hamburger.
     */
    void removeLastIngredient();

    /**
     * Returns the list of ingredients of the current hamburger.
     *
     * @return the list of ingredients
     */
    List<Ingredient> getIngredients();

    /**
     * Checks if the ingredient type is unlocked.
     *
     * @param ingredientType the type of ingredient to check
     * @return true if the ingredient is unlocked, false otherwise
     */
    boolean isIngredientUnlocked(IngredientEnum ingredientType);

    /**
     * Returns the list of cooked patties.
     *
     * @return the list of patties
     */
    List<Patty> getCookedPatties();

    /**
     * Adds the patty to the list of cooked patties.
     *
     * @param patty the patty to add
     * @return true if the patty was added, false otherwise
     */
    boolean addCookedPatty(Patty patty);

    /**
     * Removes the patty form the list of cooked patties.
     *
     * @param patty the patty to remove
     */
    void removeCookedPatty(Patty patty);

    /**
     * Calculates the accuracy given the x position in a scale.
     *
     * @param pbPositionXScale the x position in a scale of the ingredient
     * @return the placement accuracy
     */
    double calculateAccuracy(double pbPositionXScale);

    /**
     * Changes the accuracy of an ingredient in the hamburger on assembly.
     *
     * @param ingredient the ingredient whose accuracy will be updated
     * @param accuracy   the new accuracy value
     */
    void changeIngredientAccuracy(Ingredient ingredient, double accuracy);

    /**
     * Returns the list of unlocked ingredient types.
     *
     * @return the list of unlocked types
     */
    List<IngredientEnum> getUnlockedIngredients();
}
