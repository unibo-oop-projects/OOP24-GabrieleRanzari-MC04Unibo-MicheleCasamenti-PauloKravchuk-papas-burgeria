package it.unibo.papasburgeria.model;

/**
 * defines the amount of enemies arriving per day.
 */
public enum CustomerDifficultyEnum {
    FIRST(3, 30),
    SECOND(5, 30),
    THIRD(8, 25),
    FORTH(11, 25),
    FIFTH(14, 20);

    private final int customerCount;
    private final int customerSpawnInterval;

    CustomerDifficultyEnum(final int customerCount, final int customerSpawnInterval) {
        this.customerCount = customerCount;
        this.customerSpawnInterval = customerSpawnInterval;
    }

    /**
     * @return the customer amount
     */
    public int getCustomerCount() {
        return customerCount;
    }

    /**
     * @return the rate at which customers arrive
     */
    public int getSpawnIntervalSeconds() {
        return customerSpawnInterval;
    }
}
