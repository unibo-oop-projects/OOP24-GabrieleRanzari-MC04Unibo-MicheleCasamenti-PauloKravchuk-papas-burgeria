package it.unibo.papasburgeria.model.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.model.DaysEnum;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.HamburgerModel;
import it.unibo.papasburgeria.model.api.OrderModel;
import it.unibo.papasburgeria.model.api.PattyModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static it.unibo.papasburgeria.model.DaysEnum.FIRST_DAY;

/**
 * Implementation of GameModel.
 *
 * <p>
 * See {@link GameModel} for interface details.
 */
@Singleton
public class GameModelImpl implements GameModel {
    /**
     * Defines the starting day.
     */
    public static final DaysEnum START_DAY = FIRST_DAY;
    /**
     * Defines the number of grill rows.
     */
    public static final int GRILL_ROWS = 4;
    /**
     * Defines the number of grill columns.
     */
    public static final int GRILL_COLUMNS = 3;
    /**
     * Defines the maximum number of cooked patties in the cookedPatties list.
     */
    public static final int MAX_COOKED_PATTIES = 5;

    private static final int MAX_DAYS = Integer.MAX_VALUE;
    private static final int STARTING_BALANCE = 0;

    private int currentDay;
    private int balance;
    private int currentSaveSlot;

    private HamburgerModel hamburgerOnAssembly;
    private PattyModel[][] pattiesOnGrill;
    private List<PattyModel> cookedPatties;
    private OrderModel selectedOrder;

    /**
     * Default constructor, initializes the model's variables.
     */
    @Inject
    public GameModelImpl() {
        this.currentSaveSlot = -1;
        this.currentDay = START_DAY.getNumber();
        this.balance = STARTING_BALANCE;
        reset();
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public HamburgerModel getHamburgerOnAssembly() {
        return new HamburgerModelImpl(hamburgerOnAssembly.getIngredients());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHamburgerOnAssembly(final HamburgerModel hamburger) {
        this.hamburgerOnAssembly = new HamburgerModelImpl(hamburger.getIngredients());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PattyModel[][] getPattiesOnGrill() {
        if (pattiesOnGrill == null) {
            return new PattyModel[GRILL_ROWS][GRILL_COLUMNS];
        }

        final PattyModel[][] newPattiesOnGrill = new PattyModel[GRILL_ROWS][GRILL_COLUMNS];
        for (int row = 0; row < GRILL_ROWS; row++) {
            newPattiesOnGrill[row] =
                    Arrays.copyOf(pattiesOnGrill[row], pattiesOnGrill[row].length);
            for (int column = 0; column < GRILL_COLUMNS
                    && pattiesOnGrill[row][column] != null; column++) {
                newPattiesOnGrill[row][column] =
                        new PattyModelImpl(pattiesOnGrill[row][column]);
            }
        }
        return newPattiesOnGrill;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPattiesOnGrill(final PattyModel[][] patties) {
        pattiesOnGrill = new PattyModel[patties.length][];
        for (int index = 0; index < patties.length; index++) {
            pattiesOnGrill[index] = patties[index].clone();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PattyModel> getCookedPatties() {
        return new ArrayList<>(cookedPatties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCookedPatties(final List<PattyModel> patties) {
        cookedPatties = List.copyOf(patties);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentDay() {
        return currentDay;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCurrentDay(final int dayNumber) {
        currentDay = dayNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBalance() {
        return balance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBalance(final int amount) {
        this.balance = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderModel getSelectedOrder() {
        if (Objects.isNull(selectedOrder)) {
            return null;
        } else {
            return selectedOrder.copyOf();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedOrder(final OrderModel order) {
        selectedOrder = order;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentSaveSlot() {
        return currentSaveSlot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCurrentSaveSlot(final int currentSaveSlot) {
        this.currentSaveSlot = currentSaveSlot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void reset() {
        hamburgerOnAssembly = new HamburgerModelImpl();
        pattiesOnGrill = new PattyModel[GRILL_ROWS][GRILL_COLUMNS];
        cookedPatties = new ArrayList<>();
        selectedOrder = null;
    }

    /**
     * {@inheritDoc}
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
