package it.unibo.papasburgeria.model;

/**
 * Enum with all types of ingredients and a string with its name.
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

    private final String name;

    /**
     * @param name the name of the ingredient.
     */
    IngredientEnum(final String name) {
        this.name = name;
    }

    /**
     * @return the name of the ingredient.
     */
    public String getName() {
        return name;
    }
}
