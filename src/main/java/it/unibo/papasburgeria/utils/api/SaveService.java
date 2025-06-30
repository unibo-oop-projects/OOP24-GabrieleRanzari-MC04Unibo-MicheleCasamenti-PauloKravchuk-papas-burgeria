package it.unibo.papasburgeria.utils.api;

import it.unibo.papasburgeria.utils.impl.saving.SaveState;

import java.io.IOException;
import java.util.List;

/**
 * Service used to save player slot data.
 */
public interface SaveService {

    /**
     * Saves the provided SaveState DTO under the slot file corresponding to that slot number.
     *
     * @param slotNumber slot number.
     * @param saveState  SaveState DTO.
     * @throws IOException throws an IOException.
     */
    void saveSlot(int slotNumber, SaveState saveState) throws IOException;

    /**
     * Used to obtain the SaveState DTO from the given slot number.
     *
     * @param slotNumber slot number.
     * @return fetched SaveState DTO.
     * @throws IOException throws an IOException.
     */
    SaveState loadSlot(int slotNumber) throws IOException;

    /**
     * Used to obtain a list of SaveState DTOs made out of all the saves. The list has null elements if no save state was found.
     *
     * @return list of fetched SaveState DTOs.
     * @throws IOException throws an IOException.
     */
    List<SaveState> loadAllSlots() throws IOException;
}
