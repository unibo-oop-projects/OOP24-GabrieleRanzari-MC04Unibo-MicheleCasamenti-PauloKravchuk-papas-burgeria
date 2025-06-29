package it.unibo.papasburgeria.model.api;

import java.util.List;

/**
 * Manages the game state of varius data.
 */
public interface GameModel {

    /**
     * Advances the game by one day.
     *
     * @throws IllegalStateException if the game has reached the maximum day
     */
    void nextDay();

    /**
     * Returns the hamburger on assembly.
     *
     * @return the hamburger
     */
    Hamburger getHamburgerOnAssembly();

    /**
     * Sets the hamburger on assembly to the new hamburger.
     *
     * @param hamburger the new hamburger
     */
    void setHamburgerOnAssembly(Hamburger hamburger);

    /**
     * Returns the matrix of patties that are on the grill.
     *
     * @return the matrix of patties
     */
    Patty[][] getPattiesOnGrill();

    /**
     * Sets the matrix of patties that are on the grill to the new matrix of patties.
     *
     * @param patties the new matrix of patties
     */
    void setPattiesOnGrill(Patty[][] patties);

    /**
     * Returns the list of cooked patties.
     *
     * @return the list of patties
     */
    List<Patty> getCookedPatties();

    /**
     * Sets the list of cooked patties to the new list of patties.
     *
     * @param patties the new list of patties
     */
    void setCookedPatties(List<Patty> patties);

    /**
     * Returns the current day number.
     *
     * @return the current day number
     */
    int getCurrentDay();

    /**
     * Sets the current day to a new number.
     *
     * @param dayNumber the new day number
     */
    void setCurrentDay(int dayNumber);

    /**
     * @return the player's balance
     */
    int getBalance();

    /**
     * @param amount the amount of money given
     */
    void setBalance(int amount);

    /**
     * Returns the order selected.
     *
     * @return the order
     */
    Order getSelectedOrder();

    /**
     * sets a new order as the selected one.
     *
     * @param order the selected order
     */
    void setSelectedOrder(Order order);

    /**
     * Used to obtain the currently selected save slot.
     *
     * @return save slot index
     */
    int getCurrentSaveSlot();

    /**
     * Used to set the current save slot.
     *
     * @param currentSaveSlot save slot index
     */
    void setCurrentSaveSlot(int currentSaveSlot);

    /**
     * Resets the model's resettable variables.
     */
    void reset();
}
