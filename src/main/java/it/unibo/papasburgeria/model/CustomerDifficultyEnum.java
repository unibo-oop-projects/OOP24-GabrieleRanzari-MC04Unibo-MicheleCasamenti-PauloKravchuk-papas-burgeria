package it.unibo.papasburgeria.model;

/**
 * defines the amount of enemies arriving per day.
 */
public enum CustomerDifficultyEnum {
    /**
     * used on first day.
     */
    FIRST(3, 30),
    /**
     * Used on second day.
     */
    SECOND(5, 30),
    /**
     * Used on third day.
     */
    THIRD(8, 25),
    /**
     * Used on fourth day.
     */
    FORTH(11, 25),
    /**
     * Used from fifth day onward.
     */
    FIFTH(14, 20);

    private final int customerCount;
    private final int customerSpawnInterval;

    CustomerDifficultyEnum(final int customerCount, final int customerSpawnInterval) {
        this.customerCount = customerCount;
        this.customerSpawnInterval = customerSpawnInterval;
    }

    /**
     * Gets the number of customer to generate.
     *
     * @return the customer amount
     */
    public int getCustomerCount() {
        return customerCount;
    }

    /**
     * Gets the spawn interval (in seconds).
     *
     * @return the rate at which customers arrive
     */
    public int getSpawnIntervalSeconds() {
        return customerSpawnInterval;
    }
}
