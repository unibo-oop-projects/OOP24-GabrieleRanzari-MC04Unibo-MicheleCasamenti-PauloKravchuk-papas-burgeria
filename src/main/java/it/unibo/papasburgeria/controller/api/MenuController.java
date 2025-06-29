package it.unibo.papasburgeria.controller.api;

import it.unibo.papasburgeria.utils.impl.saving.SaveInfo;

import java.util.List;

/**
 * Manages interactions from the Menu View calling its methods.
 */
public interface MenuController {

    /**
     * Returns a list of info DTOs, one for each save.
     *
     * @return list of SaveInfo DTOs
     */
    List<SaveInfo> getSaves();
}
