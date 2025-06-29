package it.unibo.papasburgeria.model;

import static it.unibo.papasburgeria.model.impl.PattyImpl.MAX_COOK_LEVEL;
import static it.unibo.papasburgeria.model.impl.PattyImpl.MIN_COOK_LEVEL;

/**
 * Enumeration containing the degrees of doneness for the patties.
 */
public enum DegreesOfDonenessEnum {
    /**
     * Defines the degree of doneness raw.
     */
    RAW("raw"),
    /**
     * Defines the degree of doneness rare.
     */
    RARE("rare"),
    /**
     * Defines the degree of doneness medium.
     */
    MEDIUM("medium"),
    /**
     * Defines the degree of doneness well done.
     */
    WELL_DONE("well_done"),
    /**
     * Defines the degree of doneness burnt.
     */
    BURNT("burnt");

    private final String name;

    /**
     * Default constructor, sets the name of the degree of doneness.
     *
     * @param name the name of the degree of doneness
     */
    DegreesOfDonenessEnum(final String name) {
        this.name = name;
    }

    /**
     * Calculate the degree of doneness given the cook level.
     *
     * @param cookLevel the cook level
     * @return the degree of doneness
     */
    public static DegreesOfDonenessEnum calculateDegree(final double cookLevel) {
        final double clampedValue =
                Math.max(MIN_COOK_LEVEL, Math.min(MAX_COOK_LEVEL, cookLevel));

        final DegreesOfDonenessEnum[] values = values();
        final int numberOfValues = values.length;
        final double segment = MAX_COOK_LEVEL / numberOfValues;

        int levelIndex = (int) Math.floor(clampedValue / segment);
        if (levelIndex >= numberOfValues) {
            levelIndex = numberOfValues - 1;
        }

        return values[levelIndex];
    }

    /**
     * Return the name of the degree of doneness.
     *
     * @return the name of the degree of doneness
     */
    public String getName() {
        return name;
    }
}
