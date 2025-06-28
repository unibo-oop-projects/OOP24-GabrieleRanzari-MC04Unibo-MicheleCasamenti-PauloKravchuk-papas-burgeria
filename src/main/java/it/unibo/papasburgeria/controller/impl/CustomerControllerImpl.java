package it.unibo.papasburgeria.controller.impl;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    private static final double DEFAULT_TIP = 0.05;
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
    @Override
    public void serveCustomer(final Customer customer) {
        registerModel.removeCustomerWaitLine(customer);
        final int payment = customer.evaluateBurger(model.getHamburgerOnAssembly(),
                shop.getUpgradeModifier(UpgradeEnum.PLACEMENT_TOLERANCE),
                shop.getUpgradeModifier(UpgradeEnum.INGREDIENT_TOLERANCE));
        int tip = 0;
        if (ThreadLocalRandom.current().nextDouble() <= shop.getUpgradeModifier(UpgradeEnum.CUSTOMER_TIP)) {
            if (shop.isUpgradeUnlocked(UpgradeEnum.CUSTOMER_MORE_TIP)) {
                tip = (int) (payment * shop.getUpgradeModifier(UpgradeEnum.CUSTOMER_MORE_TIP));
            } else {
                tip = (int) (payment * DEFAULT_TIP);
            }
        }
        model.setBalance(model.getBalance() + payment + tip);
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
    public void startClientThread() {
        final CustomerDifficultyEnum difficulty;
        switch (model.getCurrentDay()) {
            case 1:
                difficulty = CustomerDifficultyEnum.FIRST;
            break;

            case 2:
                difficulty = CustomerDifficultyEnum.SECOND;
            break;

            case 3:
            difficulty = CustomerDifficultyEnum.THIRD;
            break;

            case 4:
                difficulty = CustomerDifficultyEnum.FORTH;
            break;

            default:
                difficulty = CustomerDifficultyEnum.FIFTH;
            break;
        }

        registerModel.startCustomerThread((int) (difficulty.getSpawnIntervalSeconds()
        + (difficulty.getSpawnIntervalSeconds() * shop.getUpgradeModifier(UpgradeEnum.SLOW_CUSTOMERS))),
        (int) (difficulty.getCustomerCount()
        - (difficulty.getCustomerCount() * shop.getUpgradeModifier(UpgradeEnum.LESS_CUSTOMERS))),
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
     * @inheritDoc
     */
    @Override
    public List<Customer> getRegisterLine() {
        return registerModel.getRegisterLine();
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Customer> getWaitLine() {
        return registerModel.getWaitLine();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void takeOrderFromCustomer(final Customer customer) {
        registerModel.removeCustomerRegisterLine(customer);
        registerModel.addCustomerWaitLine(customer);
    }
}
