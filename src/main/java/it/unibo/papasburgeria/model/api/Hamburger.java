package it.unibo.papasburgeria.model.api;

import java.util.List;

/**
 * Interface used for creating Hamburgers in the game.
 */
public interface Hamburger {
    /**
     * @param ingredient to add to the hamburger
     * @return true if the operation was successful, false otherwise
     */
    boolean addIngredient(Ingredient ingredient);

    /**
     * @return all the ingredient added to the burger
     */
    List<Ingredient> getIngredients();

    /**
     * Removes the last ingredient added.
     *
     * @return true if the removal was successful.
     */
    boolean removeLastIngredient();
}
