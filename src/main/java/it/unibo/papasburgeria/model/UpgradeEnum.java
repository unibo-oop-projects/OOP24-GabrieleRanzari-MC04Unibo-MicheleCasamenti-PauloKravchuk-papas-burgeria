package it.unibo.papasburgeria.model;

/**
 * Defines the upgrades and their modifiers.
 */
public enum UpgradeEnum {
    /**
     * Makes customers arrival slower by 10%.
     */
    SLOW_CUSTOMERS("Slow Customers",
            "Makes customers arrival slower by 10%!",
            10, 0.10),
    /**
     * Makes less customers arrive per day.
     */
    LESS_CUSTOMERS("Less Customers",
            "Makes less customers arrive per day!",
            10, 0.20),
    /**
     * Customers are more tolerant if you place the wrong ingredients.
     */
    INGREDIENT_TOLERANCE("Ingredient Tolerance",
            "Customers are more tolerant if you place the wrong ingredients!",
            25, 0.15),
    /**
     * Customers are more tolerant if you place the ingredients incorrectly.
     */
    PLACEMENT_TOLERANCE("Placement Tolerance",
            "Customers are more tolerant if you place the ingredients incorrectly!",
            25, 0.15),
    /**
     * Customers tips are set to 20%.
     */
    CUSTOMER_TIP("Customer Tip",
            "Customers tips are set to 20%!",
            50, 0.2),
    /**
     * Customers' tips probability is set to 33%.
     */
    CUSTOMER_MORE_TIP("Customer More Tips",
            "Customers' tips probability is set to 33%!",
            100, 0.33);

    /**
     * The upgrade's name.
     */
    private final String name;
    /**
     * The upgrade's description.
     */
    private final String description;
    /**
     * The upgrade's cost.
     */
    private final int cost;
    /**
     * The upgrade's name.
     */
    private final double modifier;

    /**
     * Constructs a new UpgradeEnum.
     *
     * @param name        upgrade's name.
     * @param description upgrade's description.
     * @param cost        upgrade's cost.
     * @param modifier    upgrade's percentage modifier.
     */
    UpgradeEnum(final String name, final String description, final int cost, final double modifier) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.modifier = modifier;
    }

    /**
     * Get the upgrade's name.
     *
     * @return the upgrade's name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the upgrade descriptor.
     *
     * @return the upgrade's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the upgrade cost.
     *
     * @return the upgrade's cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Get the upgrade modifier.
     *
     * @return the upgrade's modifier percentage
     */
    public double getModifier() {
        return modifier;
    }
}
