package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.model.api.Patty;

/**
 * Manages the interaction between the View and the Model for the grill scene.
 */
public interface GrillController {

    /**
     * Adds the patty to the grill if the slot is free.
     *
     * @param patty  the patty to add.
     * @param row    the column of the grill where to add the patty
     * @param column the column of the grill where to add the patty.
     * @return true if the patty was added.
     */
    boolean addPatty(Patty patty, int row, int column);

    /**
     * Removes the patty in a specific place from the grill.
     *
     * @param row    the column of the grill where to remove the patty
     * @param column the column of the grill where to remove the patty.
     * @return the removed patty.
     */
    Patty removePatty(int row, int column);

    /**
     * Flips the patty.
     *
     * @param patty the patty to flip.
     */
    void flipPatty(Patty patty);

    /**
     * Cooks the face the patty that is on the grill, keeping count of the FPS.
     *
     * @param patty the patty to cook.
     */
    void cookPatty(Patty patty);

    /**
     * Cooks all the patties on the grill.
     *
     * @param patties the patties on the grill.
     */
    void cookPattiesOnGrill(Patty[][] patties);
}

