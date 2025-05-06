package it.unibo.papasburgeria.model.api;

/**
 * Interface with the instructions used for the hamburger's assembly.
 */
public interface Order {
    /**
     * @return the current hamburger's order.
     */
    Hamburger getHamburger();

    /**
     * @return the maximum time given for the hamburger's assembly.
     */
    float getMaxTime();
}
