package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Order;

import java.util.List;

/**
 * Manages the interaction between the View and the Model for the order selection scene.
 */
public interface OrderSelectionController {

    /**
     * Returns the list of active orders.
     *
     * @return the list of orders
     */
    List<Order> getOrders();

    /**
     * Returns the hamburger on assembly.
     *
     * @return the hamburger
     */
    Hamburger getHamburger();

    /**
     * sets a new order as the selected one.
     *
     * @param order the selected order
     */
    void setSelectedOrder(Order order);

    /**
     * Removes the top bun from the hamburger on assembly.
     */
    void removeTopBun();
}
