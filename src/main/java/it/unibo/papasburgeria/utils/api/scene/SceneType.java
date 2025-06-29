package it.unibo.papasburgeria.utils.api.scene;

/**
 * Enum specifying the game's scene types in an abstract way to maintain MVC.
 */
public enum SceneType {
    REGISTER("Register"),
    BURGER_ASSEMBLY("BurgerAssembly"),
    GRILL("Grill"),
    MENU("Menu"),
    SHOP("Shop"),
    DAY_CHANGE("DayChange"),
    ORDER_SELECTION("OrderSelection"), 
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
}
