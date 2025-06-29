package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Order;

/**
 * Manages the appearance of the evaluateBurger station.
 */
public interface EvaluateBurgerController {
    /**
     * get the current hamburger on the build station.
     *
     * @return the hamburger on assembly in game model.
     */
    Hamburger getHamburgerOnAssembly();

    /**
     * get the order selected in game model.
     *
     * @return the selected order in game model.
     */
    Order getSelectedOrder();

    /**
     * Empties the current hamburger on assemblyline.
     */
    void emptyHamburgerOnAssembly();
}
