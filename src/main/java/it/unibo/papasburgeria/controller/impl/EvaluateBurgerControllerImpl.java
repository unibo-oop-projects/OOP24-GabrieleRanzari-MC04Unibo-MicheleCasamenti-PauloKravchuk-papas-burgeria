package it.unibo.papasburgeria.controller.impl;

import java.util.Objects;

import com.google.inject.Inject;

import it.unibo.papasburgeria.controller.api.EvaluateBurgerController;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Order;

/**
 * controller for the evaluateBurger.
 */
public class EvaluateBurgerControllerImpl implements EvaluateBurgerController {
    private final GameModel model;

    /**
     * @param model models the game.
     */
    @Inject
    EvaluateBurgerControllerImpl(final GameModel model) {
        this.model = model;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Hamburger getHamburgerOnAssembly() {
        if (Objects.isNull(model.getHamburgerOnAssembly())) {
            return null;
        } else {
            return model.getHamburgerOnAssembly().copyOf();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public Order getSelectedOrder() {
        if (Objects.isNull(model.getSelectedOrder())) {
            return null;
        } else {
            return model.getSelectedOrder().copyOf();
        }
    }
}
