package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.model.api.Patty;

import java.util.List;

/**
 * Manages the interaction between the View and the Model for the grill scene.
 */
public interface GrillController {

    /**
     * @return the matrix of patties on the grill.
     */
    Patty[][] getPattiesOnGrill();

    /**
     * Adds the patty to the grill if the slot is free.
     *
     * @param patty            the patty to add.
     * @param pbPositionXScale the pbPositionXScale of the patty.
     * @param pbPositionYScale the pbPositionYScale of the patty.
     *
     * @return true if the patty was added.
     */
    boolean addPattyOnGrill(Patty patty, double pbPositionXScale, double pbPositionYScale);

    /**
     * Removes the patty in a specific place from the grill.
     *
     * @param pbPositionXScale the pbPositionXScale of the patty.
     * @param pbPositionYScale the pbPositionYScale of the patty.
     */
    void removePattyFromGrill(double pbPositionXScale, double pbPositionYScale);

    /**
     * @return the list of cooked patties.
     */
    List<Patty> getCookedPatties();

    /**
     * Adds the patty to the list of cooked patties.
     *
     * @param patty the patty to add.
     *
     * @return true if the patty was added.
     */
    boolean addCookedPatty(Patty patty);

    /**
     * Removes the patty form the list of cooked patties.
     *
     * @param patty the patty to remove.
     */
    void removeCookedPatty(Patty patty);

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
     */
    void cookPattiesOnGrill();

    /**
     * Given a position and a range divided in segments calculate the position.
     *
     * @param position the position in scale.
     * @param minPos the minimum position in scale.
     * @param maxPos the maximum position in scale.
     * @param segments the number of segments that the range is divided in.
     *
     * @return the index.
     */
    int calculatePosition(double position, double minPos, double maxPos, int segments);
}

