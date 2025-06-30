package it.unibo.papasburgeria.utils.api.scene;

/**
 * Enum specifying the game's scene types in an abstract way to maintain MVC.
 */
public enum SceneType {
    /**
     * Register scene.
     */
    REGISTER("Register"),
    /**
     * Burger assembly scene.
     */
    BURGER_ASSEMBLY("BurgerAssembly"),
    /**
     * Grill scene.
     */
    GRILL("Grill"),
    /**
     * Menu scene.
     */
    MENU("Menu"),
    /**
     * Shop scene.
     */
    SHOP("Shop"),
    /**
     * Day change scene.
     */
    DAY_CHANGE("DayChange"),
    /**
     * Order selection scene.
     */
    ORDER_SELECTION("OrderSelection"),
    /**
     * Burger evaluation scene.
     */
    EVALUATE_BURGER("EvaluateBurger");

    private final String value;

    /**
     * Constructs the enum providing the string value associated.
     *
     * @param value string value
     */
    SceneType(final String value) {
        this.value = value;
    }

    /**
     * Used to obtain the associated string value.
     *
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SceneType{"
                +
                "value='"
                + value
                + '\''
                +
                '}';
    }
}
