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
     * @param hamburger the new hamburger on assembly.
     */
    void setHamburgerOnAssembly(Hamburger hamburger);

    /**
     * @return the matrix of patties that are on the grill.
     */
    Patty[][] getPattiesOnGrill();

    /**
     * @param patties the matrix of patties on the grill.
     */
    void setPattiesOnGrill(Patty[][] patties);

    /**
     * @return the list of cooked patties.
     */
    List<Patty> getCookedPatties();

    /**
     * @param patties the new list of cooked patties.
     */
    void setCookedPatties(List<Patty> patties);

    /**
     * @return the current day number.
     */
    int getCurrentDay();

    /**
     * Resets the progress to the first day.
     */
    void reset();
}
