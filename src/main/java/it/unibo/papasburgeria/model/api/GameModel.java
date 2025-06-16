package it.unibo.papasburgeria.model.api;

import java.util.List;

/**
 * Manages the game state of varius data.
 */
public interface GameModel {

    /**
     * Advances the game by one day.
     *
     * @throws IllegalStateException if the game has reached the maximum day.
     */
    void nextDay();

    /**
     * @return the hamburger on assembly
     */
    Hamburger getHamburgerOnAssembly();

    /**
     * @return the matrix of patties that are on the grill.
     */
    Patty[][] getPattiesOnGrill();

    /**
     * @return the list of cooked patties.
     */
    List<Patty> getCookedPatties();

    /**
     * @return the current day number.
     */
    int getCurrentDay();

    /**
     * @return the cook level increased every second for the patties.
     */
    int getCookLevelPerSecond();

    /**
     * @param hamburger the new hamburger on assembly.
     */
    void setHamburgerOnAssembly(Hamburger hamburger);

    /**
     * @param patties the matrix of patties on the grill.
     */
    void setPattiesOnGrill(Patty[][] patties);

    /**
     * @param patties the new list of cooked patties.
     */
    void setCookedPatties(List<Patty> patties);

    /**
     * @param cookLevelPerSecond the cook level increased every second for the patties to set.
     */
    void setCookLevelPerSecond(int cookLevelPerSecond);

    /**
     * Resets the progress to the first day.
     */
    void reset();
}
