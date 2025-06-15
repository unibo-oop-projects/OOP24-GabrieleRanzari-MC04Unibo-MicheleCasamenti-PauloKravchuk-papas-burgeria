package it.unibo.papasburgeria.model;

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
     * @return the name of the degree of doneness.
     */
    public String getName() {
        return name;
    }
}
