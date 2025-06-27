package it.unibo.papasburgeria.model.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.model.DaysEnum;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Patty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private Hamburger hamburgerOnAssembly;
    private Patty[][] pattiesOnGrill;
    private List<Patty> cookedPatties;
    private int currentDay;

    /**
     * Default constructor, initializes currentDay with the starting day.
     */
    @Inject
    public GameModelImpl() {
        this.currentDay = START_DAY.getNumber();
        hamburgerOnAssembly = new HamburgerImpl();
        pattiesOnGrill = new Patty[GRILL_ROWS][GRILL_COLUMNS];
        cookedPatties = new ArrayList<>();
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
            hamburgerOnAssembly = new HamburgerImpl();
            pattiesOnGrill = new Patty[GRILL_ROWS][GRILL_COLUMNS];
            cookedPatties = new ArrayList<>();
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
     * @return a string containing the current day.
     */
    @Override
    public String toString() {
        return "[currentDay=" + currentDay + "]"; // TODO update
    }
}
