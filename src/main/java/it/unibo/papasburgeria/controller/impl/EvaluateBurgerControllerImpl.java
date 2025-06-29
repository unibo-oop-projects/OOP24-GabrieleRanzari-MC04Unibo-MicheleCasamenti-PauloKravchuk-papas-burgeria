package it.unibo.papasburgeria.controller.impl;

import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Order;

/**
 * controller for the evaluateBurger.
 */
public class EvaluateBurgerControllerImpl {
    private final GameModel model;

    /**
     * @param model models the game.
     */
    EvaluateBurgerControllerImpl(final GameModel model) {
        this.model = model;
    }

    /**
     * @return the hamburger on assembly in game model.
     */
    public Hamburger getHamburgerOnAssembly() {
        return model.getHamburgerOnAssembly().copyOf();
    }

    /**
     * @return the selected order in game model.
     */
    public Order getSelectedOrder() {
        return model.getSelectedOrder().copyOf();
    }
}
