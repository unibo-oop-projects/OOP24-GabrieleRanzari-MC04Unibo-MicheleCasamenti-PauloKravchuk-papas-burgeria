package it.unibo.papasburgeria.model.api;

/**
 * Interface with the instructions used for the hamburger's assembly.
 */
public interface OrderModel {
    /**
     * Get the hamburger from this order.
     *
     * @return the current hamburger's order.
     */
    HamburgerModel getHamburger();

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
    OrderModel copyOf();
}
