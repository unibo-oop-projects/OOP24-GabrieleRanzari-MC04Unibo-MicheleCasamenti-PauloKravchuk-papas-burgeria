package it.unibo.papasburgeria.model;

import java.util.List;

/**
 * Represents the types of ingredients.
 */
public enum IngredientEnum {
    /**
     * Defines the bottom bun.
     */
    BOTTOM_BUN("bottom_bun"),
    /**
     * Defines the top bun.
     */
    TOP_BUN("top_bun"),
    /**
     * Defines the patty.
     */
    PATTY("patty"),
    /**
     * Defines the onion.
     */
    ONION("onion"),
    /**
     * Defines the lettuce.
     */
    LETTUCE("lettuce"),
    /**
     * Defines the pickle.
     */
    PICKLE("pickle"),
    /**
     * Defines the tomato.
     */
    TOMATO("tomato"),
    /**
     * Defines the cheese.
     */
    CHEESE("cheese"),
    /**
     * Defines the ketchup.
     */
    KETCHUP("ketchup"),
    /**
     * Defines the mustard.
     */
    MUSTARD("mustard"),
    /**
     * Defines the BBQ sauce.
     */
    BBQ("BBQ"),
    /**
     * Defines the mayonnaise.
     */
    MAYO("mayo");

    /**
     * Defines witch ingredients are sauces.
     */
    public static final List<IngredientEnum> SAUCES = List.of(
            KETCHUP,
            MUSTARD,
            BBQ,
            MAYO
    );

    private final String name;

    /**
     * Default constructor, sets the name of the ingredient type.
     *
     * @param name the ingredient type name
     */
    IngredientEnum(final String name) {
        this.name = name;
    }

    /**
     * Return the name of the ingredient type.
     *
     * @return the ingredient type name
     */
    public String getName() {
        return name;
    }
}
