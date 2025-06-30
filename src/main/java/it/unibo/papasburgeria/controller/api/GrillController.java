package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.model.api.PattyModel;

import java.util.List;

/**
 * Manages the interaction between the View and the Model for the grill scene.
 */
public interface GrillController {

    /**
     * Returns the matrix of patties that are on the grill.
     *
     * @return the matrix of patties
     */
    PattyModel[][] getPattiesOnGrill();

    /**
     * Adds the patty to the grill if the slot is free.
     *
     * @param patty            the patty to add to the grill
     * @param pbPositionXScale the x position in a scale of the patty
     * @param pbPositionYScale the y position in a scale of the patty
     * @return true if the patty was added, false otherwise
     */
    boolean addPattyOnGrill(PattyModel patty, double pbPositionXScale, double pbPositionYScale);

    /**
     * Removes the patty from the grill.
     *
     * @param patty the patty to remove
     */
    void removePattyFromGrill(PattyModel patty);

    /**
     * Returns the list of cooked patties.
     *
     * @return the list of patties
     */
    List<PattyModel> getCookedPatties();

    /**
     * Adds the patty to the list of cooked patties.
     *
     * @param patty the patty to add
     * @return true if the patty was added, false otherwise
     */
    boolean addCookedPatty(PattyModel patty);

    /**
     * Removes the patty from the list of cooked patties.
     *
     * @param patty the patty to remove
     */
    void removeCookedPatty(PattyModel patty);

    /**
     * Flips the patty.
     *
     * @param patty the patty to flip
     */
    void flipPatty(PattyModel patty);

    /**
     * Cooks all the patties on the grill.
     */
    void cookPattiesOnGrill();
}

