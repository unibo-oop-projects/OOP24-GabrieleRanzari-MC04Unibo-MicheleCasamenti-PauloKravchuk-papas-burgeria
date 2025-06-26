package it.unibo.papasburgeria.model;

/**
 * defines the upgrades and their modifiers.
 */
public enum UpgradeEnum {
    SLOW_CUSTOMERS("slow_customers",
        "Makes customers arrival slower by 10%!",
        10, 0.10),
    LESS_CUSTOMERS("less_customers",
        "Makes less customers arrive per day!",
        10, 0.20),
    INGREDIENT_TOLLERANCE("ingredient_tollerance",
        "Customers are more tollerant if you place the wrong ingredients!",
        25, 0.15),
    PLACEMENT_TOLLERANCE("placement_tollerance",
        "Customers are more tollerant if you place the ingredients incorrectly!",
        25, 0.15),
    CUSTOMER_TIP("customer_tip",
        "Customers tips increase by 5%!",
        50, 0.05),
    CUSTOMER_MORE_TIP("customer_more_tip",
        "Customers' tips are 10% more likely!",
        100, 0.10);

    private final String name;
    private final String description;
    private final int cost;
    private final double modifier;

    UpgradeEnum(final String name, final String description, final int cost, final double modifier) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.modifier = modifier;
    }

    /**
     * @return the upgrade's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the upgrade's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the upgrade's cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * @return the upgrade's modifier percentage
     */
    public double getModifier() {
        return modifier;
    }
}
