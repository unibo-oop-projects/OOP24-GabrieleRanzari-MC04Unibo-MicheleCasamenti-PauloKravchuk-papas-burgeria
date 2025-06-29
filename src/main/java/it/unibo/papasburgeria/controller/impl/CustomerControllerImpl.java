package it.unibo.papasburgeria.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.controller.api.CustomerController;
import it.unibo.papasburgeria.model.CustomerDifficultyEnum;
import it.unibo.papasburgeria.model.UpgradeEnum;
import it.unibo.papasburgeria.model.api.Customer;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.PantryModel;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.model.api.RegisterModel;
import it.unibo.papasburgeria.model.api.Shop;
import it.unibo.papasburgeria.model.impl.HamburgerImpl;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages the appearance and disappearance of customers.
 */
@Singleton
public class CustomerControllerImpl implements CustomerController {
    /**
     * The maximum payment amount.
     */
    public static final int MAX_PAYMENT = 50;
    /**
     * The default tip percentage.
     */
    public static final double DEFAULT_TIP = 0.05;
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
     * {@inheritDoc}
     */
    @Override
    public void serveCustomer(final Customer customer) {
        registerModel.removeCustomerWaitLine(customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearAllCustomers() {
        registerModel.clearLines();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startClientThread() {
        clearAllCustomers();
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
     * {@inheritDoc}
     */
    @Override
    public void stopClientThread() {
        registerModel.killCustomerThread();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCustomerThreadStatus() {
        return registerModel.isCustomerThreadStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> getRegisterLine() {
        return registerModel.getRegisterLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> getWaitLine() {
        return registerModel.getWaitLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeOrderFromCustomer(final Customer customer) {
        registerModel.removeCustomerRegisterLine(customer);
        registerModel.addCustomerWaitLine(customer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateSatisfactionPercentage(final Hamburger startingHamburger,
                                                  final Hamburger madeHamburger) {
        final List<Ingredient> list1 = startingHamburger.getIngredients();
        final List<Ingredient> list2 = madeHamburger.getIngredients();

        /*if either of them are empty, give no money */
        if (list1 == null || list2 == null || list1.isEmpty() || list2.isEmpty()) {
            return 0;
        }

        final int minLength = Math.min(list1.size(), list2.size());
        int matchCount = 0;

        double totalCookDifference = 0;
        int pattyComparisons = 0;
        for (int i = 0; i < minLength; i++) {
            final Object a = list1.get(i);
            final Object b = list2.get(i);

            if (a == null || b == null) {
                continue;
            }

            if (a.equals(b) && a.getClass().equals(b.getClass())) {
                matchCount++;
                 if (a instanceof Patty && b instanceof Patty) {
                    final double diff = Math.abs(((Patty) a).getBottomCookLevel() - ((Patty) b).getBottomCookLevel())
                                + Math.abs(((Patty) a).getTopCookLevel() - ((Patty) b).getTopCookLevel());

                    totalCookDifference += diff;
                    pattyComparisons++;
                }
            }
        }
        /* calculates how similarly two patties are cooked */
        final double pattySimilarityPercentage;

        if (pattyComparisons > 0) {
            pattySimilarityPercentage = 1.0 - (totalCookDifference / (pattyComparisons * 2.0));
        } else {
            pattySimilarityPercentage = 1.0;
        }

        /* normalize by the max length to penalize extra/missing elements */
        final int maxLength = Math.max(list1.size(), list2.size());
        double similarityPercentage = (double) matchCount / maxLength
                + shop.getUpgradeModifier(UpgradeEnum.INGREDIENT_TOLERANCE);
        if (similarityPercentage > 1.0) {
            similarityPercentage = 1.0;
        }

        double placementAccuracyTotal = 0.0;
        for (final Ingredient ingredient : list1) {
            placementAccuracyTotal += ingredient.getPlacementAccuracy();
        }
        /* calculates the placement accuracy (1 - (averageAccuracy)) */
        double placementPercentage = 1.0 - (placementAccuracyTotal / list1.size())
                + shop.getUpgradeModifier(UpgradeEnum.PLACEMENT_TOLERANCE);
        if (placementPercentage > 1.0) {
            placementPercentage = 1.0;
        }

        /* calculates the difficulty percentage (size/maxsize) */
        final double difficultyPercentage = (double) list1.size() / HamburgerImpl.MAX_INGREDIENTS;

        return (difficultyPercentage + similarityPercentage + placementPercentage + pattySimilarityPercentage) / 4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculatePayment(final double percentage) {
        return (int) (MAX_PAYMENT * percentage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateTips(final int payment) {
        if (ThreadLocalRandom.current().nextDouble() <= shop.getUpgradeModifier(UpgradeEnum.CUSTOMER_TIP)) {
            if (shop.isUpgradeUnlocked(UpgradeEnum.CUSTOMER_MORE_TIP)) {
                return (int) (payment * shop.getUpgradeModifier(UpgradeEnum.CUSTOMER_MORE_TIP));
            } else {
                return (int) (payment * DEFAULT_TIP);
            }
        } else {
            return 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBalance(final int payment) {
        model.setBalance(model.getBalance() + payment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBalance() {
        return model.getBalance();
    }
}
