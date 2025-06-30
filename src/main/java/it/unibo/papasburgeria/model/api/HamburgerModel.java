package it.unibo.papasburgeria.model.api;

import java.util.List;

/**
 * Interface used for creating Hamburgers in the game.
 */
public interface HamburgerModel {
    /**
     * Adds an ingredient on top of the hamburger.
     *
     * @param ingredient the new ingredient to add to the hamburger
     * @return true if the ingredient was added, false otherwise
     */
    boolean addIngredient(IngredientModel ingredient);

    /**
     * Returns the list of ingredients that form the hamburger.
     *
     * @return the list of ingredients
     */
    List<IngredientModel> getIngredients();

    /**
     * Return a copy of this hamburger.
     *
     * @return a copy of this hamburger
     */
    HamburgerModel copyOf();

    /**
     * Removes the ingredient on top of the hamburger.
     *
     * @return true if the ingredient was removed, false otherwise
     */
    boolean removeLastIngredient();
}
