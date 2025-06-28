package it.unibo.papasburgeria.utils.impl.saving;

/**
 * DTO to carry to the view exclusively the displayed information, without exposing the whole save.
 *
 * @param slotNumber    slot number
 * @param playerBalance player's cash balance
 * @param gameDay       game's current day
 */
public record SaveInfo(int slotNumber, int playerBalance, int gameDay) {
    /**
     * Converts a SaveState into a SaveInfo DTO.
     *
     * @param slotNumber slot number
     * @param state      SaveInfo instance
     */
    public SaveInfo(final int slotNumber, final SaveState state) {
        this(slotNumber, state != null ? state.playerBalance() : -1, state != null ? state.gameDay() : -1);
    }

    /**
     * Tells you whether this info represents an empty save or not.
     *
     * @return result
     */
    public boolean checkNoSave() {
        return playerBalance == -1 || gameDay == -1;
    }
}
