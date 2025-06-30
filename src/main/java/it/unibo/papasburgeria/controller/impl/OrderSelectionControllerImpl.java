package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.OrderSelectionController;
import it.unibo.papasburgeria.model.LineEnum;
import it.unibo.papasburgeria.model.api.CustomerModel;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.HamburgerModel;
import it.unibo.papasburgeria.model.api.OrderModel;
import it.unibo.papasburgeria.model.api.RegisterModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the interaction between the View and the Model for the order selection scene.
 */
@Singleton
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The models are injected and shared intentionally")
public class OrderSelectionControllerImpl implements OrderSelectionController {
    private final GameModel model;
    private final RegisterModel registerModel;

    /**
     * Default constructor that saves the models given via injection.
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
    public List<OrderModel> getOrders() {
        final List<CustomerModel> waitingCustomers = registerModel.getLine(LineEnum.WAIT_LINE);
        final List<OrderModel> orders = new ArrayList<>();
        for (final CustomerModel waitingCustomer : waitingCustomers) {
            orders.add(waitingCustomer.getOrder());
        }
        return new ArrayList<>(orders);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HamburgerModel getHamburger() {
        return model.getHamburgerOnAssembly().copyOf();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedOrder(final OrderModel order) {
        model.setSelectedOrder(order);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTopBun() {
        final HamburgerModel hamburgerOnAssembly = model.getHamburgerOnAssembly();
        hamburgerOnAssembly.removeLastIngredient();
        model.setHamburgerOnAssembly(hamburgerOnAssembly);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "OrderSelectionControllerImpl{"
                + "model=" + model
                + ", registerModel=" + registerModel
                + '}';
    }
}
