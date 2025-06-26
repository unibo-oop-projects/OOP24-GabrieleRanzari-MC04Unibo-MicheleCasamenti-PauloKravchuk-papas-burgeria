package it.unibo.papasburgeria.model;

import it.unibo.papasburgeria.model.impl.PattyImpl;

/**
 * Enumeration containing the degrees of doneness for the patties
 * and a string containing its name.
 */
public enum DegreesOfDonenessEnum {
    RAW("raw"),
    RARE("rare"),
    MEDIUM("medium"),
    WELL_DONE("well_done"),
    BURNT("burnt");

    private final String name;

    /**
     * @param name the name of the degree of doneness.
     */
    DegreesOfDonenessEnum(final String name) {
        this.name = name;
    }

    /**
     * @param cookLevel the cook level of the face of the patty
     * @return the degree of doneness.
     */
    public static DegreesOfDonenessEnum calculateDegree(final double cookLevel) {
        final double value = Math.max(PattyImpl.MIN_COOK_LEVEL, Math.min(PattyImpl.MAX_COOK_LEVEL, cookLevel));

        final DegreesOfDonenessEnum[] values = values();
        final int num = values.length;
        final double segment = PattyImpl.MAX_COOK_LEVEL / num;

        int idx = (int) Math.floor(value / segment);
        if (idx >= num) {
            idx = num - 1;
        }

        return values[idx];
    }

    /**
     * @return the name of the degree of doneness.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the minimum cook level for the degree of doneness.
     */
    public double getMinCookLevel() {
        final int index = this.ordinal();
        final int total = values().length;
        final double segment = PattyImpl.MAX_COOK_LEVEL / total;

        return segment * index;
    }
}
