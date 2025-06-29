package it.unibo.papasburgeria.model.api;

/**
 * Interface with the instructions used for the hamburger's assembly.
 */
public interface Order {
    /**
     * Get the hamburger from this order.
     *
     * @return the current hamburger's order.
     */
    Hamburger getHamburger();

    /**
     * Return the order number.
     *
     * @return the order number.
     */
    int getOrderNumber();

    /**
     * Return a copy of this order.
     *
     * @return the copy.
     */
    Order copyOf();
}
