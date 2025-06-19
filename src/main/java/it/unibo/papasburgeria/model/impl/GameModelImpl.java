package it.unibo.papasburgeria.model.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import it.unibo.papasburgeria.model.DegreesOfDonenessEnum;
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
    public static final int START_DAY = FIRST_DAY.ordinal();
    public static final int GRILL_ROWS = 4;
    public static final int GRILL_COLUMNS = 3;
    public static final int MAX_COOKED_PATTIES = 5;

    private static final int MAX_DAYS = Integer.MAX_VALUE;
    private static final int DEFAULT_COOK_SPEED = 2;

    private Hamburger hamburgerOnAssembly;
    private Patty[][] pattiesOnGrill;
    private List<Patty> cookedPatties;
    private int currentDay;
    private int cookLevelPerSecond;

    /**
     * Default constructor, initializes currentDay with the starting day.
     */
    @Inject
    public GameModelImpl() {
        this.currentDay = START_DAY;
        this.cookLevelPerSecond = DEFAULT_COOK_SPEED;
        hamburgerOnAssembly = new HamburgerImpl();
        pattiesOnGrill = new Patty[GRILL_ROWS][GRILL_COLUMNS];
        cookedPatties = new ArrayList<>();

        final PattyImpl patty = new PattyImpl();

        patty.setTopCookLevel(DegreesOfDonenessEnum.MEDIUM.getMinCookLevel());
        patty.setBottomCookLevel(DegreesOfDonenessEnum.BURNT.getMinCookLevel());
        cookedPatties.add(new PattyImpl(patty));
        patty.setTopCookLevel(DegreesOfDonenessEnum.RAW.getMinCookLevel());
        patty.setBottomCookLevel(DegreesOfDonenessEnum.RAW.getMinCookLevel());
        cookedPatties.add(new PattyImpl(patty));
        patty.setTopCookLevel(DegreesOfDonenessEnum.WELL_DONE.getMinCookLevel());
        patty.setBottomCookLevel(DegreesOfDonenessEnum.RARE.getMinCookLevel());
        cookedPatties.add(new PattyImpl(patty));
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
    public Patty[][] getPattiesOnGrill() {
        if (pattiesOnGrill == null) {
            return new Patty[GRILL_ROWS][GRILL_COLUMNS];
        }

        final Patty[][] newPattiesOnGrill = new Patty[GRILL_ROWS][GRILL_COLUMNS];
        for (int rowIndex = 0; rowIndex < GRILL_ROWS; rowIndex++) {
            newPattiesOnGrill[rowIndex] = Arrays.copyOf(pattiesOnGrill[rowIndex], pattiesOnGrill[rowIndex].length);
            for (int columnIndex = 0; columnIndex < GRILL_COLUMNS
                    && pattiesOnGrill[rowIndex][columnIndex] != null; columnIndex++) {
                newPattiesOnGrill[rowIndex][columnIndex] = new PattyImpl(pattiesOnGrill[rowIndex][columnIndex]);
            }
        }
        return newPattiesOnGrill;
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
    public int getCurrentDay() {
        return currentDay;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int getCookLevelPerSecond() {
        return cookLevelPerSecond;
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
    public void setCookedPatties(final List<Patty> patties) {
        cookedPatties = List.copyOf(patties);
    }

    /**
     * @inheritDoc
     */
    @Override
    public final void setCookLevelPerSecond(final int newCookLevelPerSecond) {
        cookLevelPerSecond = newCookLevelPerSecond;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void reset() {
        this.currentDay = START_DAY;
    }

    /**
     * @return a string containing the current day.
     */
    @Override
    public String toString() {
        return "[currentDay=" + currentDay + "]"; // TODO update
    }
}
