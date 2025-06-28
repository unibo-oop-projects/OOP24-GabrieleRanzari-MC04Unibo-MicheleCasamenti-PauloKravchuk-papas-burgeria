package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.utils.impl.saving.SaveInfo;

import java.util.List;

/**
 * Manages interactions from the Menu View calling its methods.
 */
public interface MenuController {

    /**
     * Processes data saving for the given slot number, provides success feedback.
     *
     * @param slotNumber slot number
     * @return whether the save is successful or not
     */
    boolean processSave(int slotNumber);

    /**
     * Processes data loading for the given slot number, provides success feedback.
     *
     * @param slotNumber slot number
     * @return whether the loading is successful or not
     */
    boolean processLoad(int slotNumber);

    /**
     * Returns a list of info DTOs, one for each save.
     *
     * @return list of SaveInfo DTOs
     */
    List<SaveInfo> getSaves();
}
