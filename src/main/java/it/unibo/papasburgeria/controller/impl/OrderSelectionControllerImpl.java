package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.OrderSelectionController;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Customer;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.Order;
import it.unibo.papasburgeria.model.api.RegisterModel;
import it.unibo.papasburgeria.model.impl.HamburgerImpl;
import it.unibo.papasburgeria.model.impl.OrderImpl;

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
    private final Hamburger hamburger;
    private final List<Order> ordersTemp;

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
        hamburger = HamburgerImpl.generateRandomHamburger(List.of(IngredientEnum.values()));

        ordersTemp = new ArrayList<>();
        final int maxOrders = 4;
        for (int index = 0; index < maxOrders; index++) {
            final List<Ingredient> ingredients = HamburgerImpl.generateRandomHamburger(
                    List.of(IngredientEnum.values())).getIngredients();
            final List<IngredientEnum> ingredientEnums = new ArrayList<>(ingredients.size());
            for (final Ingredient ingredient : ingredients) {
                ingredientEnums.add(ingredient.getIngredientType());
            }
            ordersTemp.add(new OrderImpl(ingredientEnums, index + 1));
        }
    }

    /**
     * @inheritDoc
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
     * TEST Returns the list of active orders.
     *
     * @return TEST the list of orders
     */
    @Override
    public List<Order> testGetOrders() {
        return new ArrayList<>(ordersTemp);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Hamburger getHamburger() {
        return hamburger.copyOf();
        // return model.getHamburgerOnAssembly().copyOf();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setSelectedOrder(final Order order) {
        model.setSelectedOrder(order);
    }
}
