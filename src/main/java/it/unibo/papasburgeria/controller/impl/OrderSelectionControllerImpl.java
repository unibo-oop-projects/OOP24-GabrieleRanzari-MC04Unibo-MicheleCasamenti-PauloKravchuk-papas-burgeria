package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.OrderSelectionController;
import it.unibo.papasburgeria.model.api.Customer;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Order;
import it.unibo.papasburgeria.model.api.RegisterModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @inheritDoc
 */
@Singleton
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "model is injected and shared intentionally")
public class OrderSelectionControllerImpl implements OrderSelectionController {
    private final GameModel model;
    private final RegisterModel registerModel;

    /**
     * Default constructor that saves the register model given via injection.
     *
     * @param model         the game model
     * @param registerModel the register model
     */
    @Inject
    public OrderSelectionControllerImpl(final GameModel model, final RegisterModel registerModel) {
        this.model = model;
        this.registerModel = registerModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getOrders() {
        final List<Customer> waitingCustomers = registerModel.getWaitLine();
        final List<Order> orders = new ArrayList<>();
        for (final Customer waitingCustomer : waitingCustomers) {
            orders.add(waitingCustomer.getOrder());
        }
        return new ArrayList<>(orders);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hamburger getHamburger() {
        return model.getHamburgerOnAssembly().copyOf();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedOrder(final Order order) {
        model.setSelectedOrder(order);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTopBun() {
        final Hamburger hamburgerOnAssembly = model.getHamburgerOnAssembly();
        hamburgerOnAssembly.removeLastIngredient();
        model.setHamburgerOnAssembly(hamburgerOnAssembly);
    }
}
