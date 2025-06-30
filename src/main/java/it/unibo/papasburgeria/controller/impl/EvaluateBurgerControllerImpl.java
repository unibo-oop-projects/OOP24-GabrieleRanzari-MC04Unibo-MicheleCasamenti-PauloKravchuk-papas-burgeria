package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import it.unibo.papasburgeria.controller.api.EvaluateBurgerController;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.HamburgerModel;
import it.unibo.papasburgeria.model.api.OrderModel;
import it.unibo.papasburgeria.model.impl.HamburgerModelImpl;

import java.util.Objects;

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
     * {@inheritDoc}
     */
    @Override
    public HamburgerModel getHamburgerOnAssembly() {
        if (Objects.isNull(model.getHamburgerOnAssembly())) {
            return null;
        } else {
            return model.getHamburgerOnAssembly().copyOf();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderModel getSelectedOrder() {
        if (Objects.isNull(model.getSelectedOrder())) {
            return null;
        } else {
            return model.getSelectedOrder().copyOf();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void emptyHamburgerOnAssembly() {
        model.setHamburgerOnAssembly(new HamburgerModelImpl());
    }
}
