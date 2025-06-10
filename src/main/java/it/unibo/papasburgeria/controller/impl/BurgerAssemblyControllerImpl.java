package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Ingredient;
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

    /**
     * Default constructor, creates a new empty hamburger.
     */
    @Inject
    public BurgerAssemblyControllerImpl() {
        this.hamburger = new HamburgerImpl();
        this.hamburger.addIngredient(new IngredientImpl(IngredientEnum.BOTTOM_BUN));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addIngredient(final IngredientEnum ingredientType) {
        final Ingredient ingredient = new IngredientImpl(ingredientType);

        if (hamburger.addIngredient(ingredient)) {
            Logger.info("Ingredient (" + ingredient.getIngredientType() + " added successfully");
        } else {
            Logger.error("Ingredient can't be added");
        }
    }

    /**
     * Removes the last ingredient added to the hamburger.
     */
    @Override
    public void removeLastIngredient() {
        if (hamburger.removeLastIngredient()) {
            Logger.info("Ingredient removed successfully");
        } else {
            Logger.error("Ingredient can't be removed");
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
     * @return a string containing the current hamburger.
     */
    @Override
    public String toString() {
        return "[BurgerAssemblyControllerImpl: " + hamburger.toString() + "]";
    }
}
