package it.unibo.papasburgeria.model.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Patty;

import java.util.ArrayList;
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

        cookedPatties.add(new PattyImpl());
        cookedPatties.add(new PattyImpl());
        cookedPatties.add(new PattyImpl());

        hamburgerOnAssembly.addIngredient(new IngredientImpl(IngredientEnum.BOTTOM_BUN, IngredientImpl.PERFECT_ACCURACY));
        hamburgerOnAssembly.addIngredient(new IngredientImpl(IngredientEnum.PATTY, IngredientImpl.PERFECT_ACCURACY));
        hamburgerOnAssembly.addIngredient(new IngredientImpl(IngredientEnum.CHEESE, IngredientImpl.PERFECT_ACCURACY));
        hamburgerOnAssembly.addIngredient(new IngredientImpl(IngredientEnum.KETCHUP, IngredientImpl.PERFECT_ACCURACY));
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
    public void setHamburgerOnAssembly(final Hamburger hamburger) {
        this.hamburgerOnAssembly = new HamburgerImpl(hamburger.getIngredients());
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
        return List.copyOf(cookedPatties);
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
    public int getCookLevelPerSecond() {
        return cookLevelPerSecond;
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
