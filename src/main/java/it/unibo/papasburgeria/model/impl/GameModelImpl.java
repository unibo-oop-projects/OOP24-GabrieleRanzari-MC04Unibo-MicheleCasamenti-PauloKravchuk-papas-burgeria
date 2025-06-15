package it.unibo.papasburgeria.model.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Patty;

import static it.unibo.papasburgeria.model.DaysEnum.FIRST_DAY;

/**
 * @inheritDoc
 */
@Singleton
public class GameModelImpl implements GameModel {
    public static final int START_DAY = FIRST_DAY.ordinal();
    private static final int MAX_DAYS = Integer.MAX_VALUE;
    private static final int GRILL_ROWS = 4;
    private static final int GRILL_COLUMNS = 3;
    private static final int DEFAULT_COOK_SPEED = 2;

    private Hamburger hamburgerOnAssembly;
    private Patty[][] pattiesOnGrill;
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
        final Patty[][] copy = new Patty[pattiesOnGrill.length][];
        for (int i = 0; i < pattiesOnGrill.length; i++) {
            final Patty[] row = pattiesOnGrill[i];
            copy[i] = new Patty[row.length];
            for (int j = 0; j < row.length; j++) {
                copy[i][j] = new PattyImpl(row[j]);
            }
        }
        return copy;
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
        return "[currentDay=" + currentDay + "]";
    }
}
