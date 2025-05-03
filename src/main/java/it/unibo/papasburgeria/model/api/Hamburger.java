package it.unibo.papasburgeria.model.api;

import java.util.List;

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
}
