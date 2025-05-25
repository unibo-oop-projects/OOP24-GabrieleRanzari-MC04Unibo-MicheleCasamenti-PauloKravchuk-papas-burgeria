package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.impl.HamburgerImpl;

/**
 * @inheritDoc
 */
public class BurgerAssemblyControllerImpl implements BurgerAssemblyController {
    private final Hamburger hamburger;

    /**
     * Default constructor.
     *
     * @param hamburger the current hamburger in assembly.
     */
    @Inject
    public BurgerAssemblyControllerImpl(final Hamburger hamburger) {
        this.hamburger = new HamburgerImpl(hamburger.getIngredients());
    }

    /**
     * Adds the ingredient to the hamburger if it can be added.
     *
     * @param ingredient the ingredient to add.
     */
    public void addIngredient(final Ingredient ingredient) {
        hamburger.addIngredient(ingredient);
    }

    /**
     * @return a string containing the controller.
     */
    @Override
    public String toString() {
        return "[BurgerAssemblyControllerImpl: " + hamburger.toString() + "]";
    }
}
