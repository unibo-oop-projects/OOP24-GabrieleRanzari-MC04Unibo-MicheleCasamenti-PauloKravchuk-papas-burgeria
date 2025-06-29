package it.unibo.papasburgeria.model.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.model.DaysEnum;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Order;
import it.unibo.papasburgeria.model.api.Patty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static it.unibo.papasburgeria.model.DaysEnum.FIRST_DAY;

/**
 * @inheritDoc
 */
@Singleton
public class GameModelImpl implements GameModel {
    public static final DaysEnum START_DAY = FIRST_DAY;
    public static final int GRILL_ROWS = 4;
    public static final int GRILL_COLUMNS = 3;
    public static final int MAX_COOKED_PATTIES = 5;

    private static final int MAX_DAYS = Integer.MAX_VALUE;
    private static final int STARTING_BALANCE = 50;

    private int balance;
    private int currentSaveSlot;

    private Hamburger hamburgerOnAssembly;
    private Patty[][] pattiesOnGrill;
    private List<Patty> cookedPatties;
    private Order selectedOrder;
    private int currentDay;

    /**
     * Default constructor, initializes currentDay with the starting day.
     */
    @Inject
    public GameModelImpl() {
        this.currentSaveSlot = -1;
        this.currentDay = START_DAY.getNumber();
        hamburgerOnAssembly = new HamburgerImpl();
        pattiesOnGrill = new Patty[GRILL_ROWS][GRILL_COLUMNS];
        cookedPatties = new ArrayList<>();
        selectedOrder = null;
        balance = STARTING_BALANCE;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void nextDay() {
        if (currentDay == MAX_DAYS) {
            throw new IllegalStateException("Cannot advance beyond day " + MAX_DAYS);
        } else {
            currentDay++;
            reset();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public Hamburger getHamburgerOnAssembly() {
        return new HamburgerImpl(hamburgerOnAssembly.getIngredients());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setHamburgerOnAssembly(final Hamburger hamburger) {
        this.hamburgerOnAssembly = new HamburgerImpl(hamburger.getIngredients());
    }

    /**
     * @inheritDoc
     */
    @Override
    public Patty[][] getPattiesOnGrill() {
        if (pattiesOnGrill == null) {
            return new Patty[GRILL_ROWS][GRILL_COLUMNS];
        }

        final Patty[][] newPattiesOnGrill = new Patty[GRILL_ROWS][GRILL_COLUMNS];
        for (int row = 0; row < GRILL_ROWS; row++) {
            newPattiesOnGrill[row] = Arrays.copyOf(pattiesOnGrill[row], pattiesOnGrill[row].length);
            for (int column = 0; column < GRILL_COLUMNS
                    && pattiesOnGrill[row][column] != null; column++) {
                newPattiesOnGrill[row][column] = new PattyImpl(pattiesOnGrill[row][column]);
            }
        }
        return newPattiesOnGrill;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPattiesOnGrill(final Patty[][] patties) {
        pattiesOnGrill = new Patty[patties.length][];
        for (int index = 0; index < patties.length; index++) {
            pattiesOnGrill[index] = patties[index].clone();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Patty> getCookedPatties() {
        return new ArrayList<>(cookedPatties);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setCookedPatties(final List<Patty> patties) {
        cookedPatties = List.copyOf(patties);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getCurrentDay() {
        return currentDay;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setCurrentDay(final int dayNumber) {
        currentDay = dayNumber;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getBalance() {
        return balance;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setBalance(final int amount) {
        this.balance = amount;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Order getSelectedOrder() {
        if (Objects.isNull(selectedOrder)) {
            return null;
        } else {
            return selectedOrder.copyOf();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setSelectedOrder(final Order order) {
        selectedOrder = order;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getCurrentSaveSlot() {
        return currentSaveSlot;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setCurrentSaveSlot(final int currentSaveSlot) {
        this.currentSaveSlot = currentSaveSlot;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void reset() {
        hamburgerOnAssembly = new HamburgerImpl();
        pattiesOnGrill = new Patty[GRILL_ROWS][GRILL_COLUMNS];
        cookedPatties = new ArrayList<>();
        selectedOrder = null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "GameModelImpl{"
                + "balance="
                + balance
                + ", currentSaveSlot="
                + currentSaveSlot
                + ", hamburgerOnAssembly="
                + hamburgerOnAssembly
                + ", pattiesOnGrill="
                + Arrays.toString(pattiesOnGrill)
                + ", cookedPatties="
                + cookedPatties
                + ", selectedOrder="
                + selectedOrder
                + ", currentDay="
                + currentDay
                + '}';
    }
}
