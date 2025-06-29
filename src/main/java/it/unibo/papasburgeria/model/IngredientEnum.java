package it.unibo.papasburgeria.model;

import java.util.List;

/**
 * Represents the types of ingredients.
 */
public enum IngredientEnum {
    BOTTOM_BUN("bottom_bun"),
    TOP_BUN("top_bun"),
    PATTY("patty"),
    ONION("onion"),
    LETTUCE("lettuce"),
    PICKLE("pickle"),
    TOMATO("tomato"),
    CHEESE("cheese"),
    KETCHUP("ketchup"),
    MUSTARD("mustard"),
    BBQ("BBQ"),
    MAYO("mayo");

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
