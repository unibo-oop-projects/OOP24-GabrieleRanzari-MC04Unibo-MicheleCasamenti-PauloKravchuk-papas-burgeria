package it.unibo.papasburgeria.model;

/**
 * Enumeration containing the days in order.
 */
public enum DaysEnum {
    FIRST_DAY(1),
    SECOND_DAY(2),
    THIRD_DAY(3),
    FOURTH_DAY(4),
    FIFTH_DAY(5);

    private final int number;

    /**
     * Default constructor, sets the number of the day.
     *
     * @param number the number of the day
     */
    DaysEnum(final int number) {
        this.number = number;
    }

    /**
     * Returns the number of the day.
     *
     * @return the day number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the DaysEnum corresponding to the day number or null.
     *
     * @param dayNumber the day number
     * @return the day, or null if there is no corresponding DaysEnum
     */
    public static DaysEnum getDay(final int dayNumber) {
        for (final DaysEnum day : values()) {
            if (day.getNumber() == dayNumber) {
                return day;
            }
        }
        return null;
    }
}
