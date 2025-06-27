package it.unibo.papasburgeria.controller.impl;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.controller.api.CustomerController;
import it.unibo.papasburgeria.model.CustomerDifficultyEnum;
import it.unibo.papasburgeria.model.UpgradeEnum;
import it.unibo.papasburgeria.model.api.Customer;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.RegisterModel;
import it.unibo.papasburgeria.model.api.PantryModel;
import it.unibo.papasburgeria.model.api.Shop;

/**
 * @inheritDoc
 */
@Singleton
public class CustomerControllerImpl implements CustomerController {
    private final GameModel model;
    private final Shop shop;
    private final RegisterModel registerModel;
    private final PantryModel pantryModel;

    @Inject
    CustomerControllerImpl(
        final GameModel model,
        final Shop shop,
        final RegisterModel registerModel,
        final PantryModel pantryModel) {
        this.model = model;
        this.shop = shop;
        this.registerModel = registerModel;
        this.pantryModel = pantryModel;
    }

    /**
     * @inheritDoc
     */
    @Inject
    public void serveCustomer(final Customer customer) {
        registerModel.removeCustomerWaitLine(customer);
        model.setBalance(model.getBalance() + customer.evaluateBurger(model.getHamburgerOnAssembly(),
                shop.getUpgradeModifier(UpgradeEnum.PLACEMENT_TOLERANCE),
                shop.getUpgradeModifier(UpgradeEnum.INGREDIENT_TOLERANCE)));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void clearAllCustomers() {
        registerModel.clearLines();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void startClientThread(final CustomerDifficultyEnum difficulty) {
        registerModel.startCustomerThread((int) (difficulty.getSpawnIntervalSeconds() +
        (difficulty.getSpawnIntervalSeconds() * shop.getUpgradeModifier(UpgradeEnum.SLOW_CUSTOMERS))),
        (int)(difficulty.getCustomerCount() -
        (difficulty.getCustomerCount() * shop.getUpgradeModifier(UpgradeEnum.LESS_CUSTOMERS))),
        pantryModel.getUnlockedIngredients().stream().toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void stopClientThread() {
        registerModel.killCustomerThread();
    }

    /**
     * @return the register line
     */
    public List<Customer> getRegisterLine() {
        return registerModel.getRegisterLine();
    }

    /**
     * @return the wait line
     */
    public List<Customer> getWaitLine() {
        return registerModel.getWaitLine();
    }
}
