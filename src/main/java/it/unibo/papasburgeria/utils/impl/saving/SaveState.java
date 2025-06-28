package it.unibo.papasburgeria.utils.impl.saving;

import it.unibo.papasburgeria.model.UpgradeEnum;

import java.util.EnumMap;
import java.util.Map;

/**
 * DTO used to carry around game save state.
 *
 * @param playerBalance cash balance
 * @param gameDay       current game day
 * @param upgrades      unlocked upgrades
 */
public record SaveState(
        int playerBalance,
        int gameDay,
        Map<UpgradeEnum, Boolean> upgrades
) {
    /**
     * Initializes this record.
     *
     * @param playerBalance cash balance
     * @param gameDay       current game day
     * @param upgrades      unlocked upgrades
     */
    public SaveState {
        upgrades = copyMap(upgrades);
    }

    /**
     * Used to obtain the upgrades map from the save state.
     *
     * @return upgrades map
     */
    @Override
    public Map<UpgradeEnum, Boolean> upgrades() {
        return copyMap(upgrades);
    }

    /**
     * Helper method to make defensive copies of the maps.
     *
     * @param map input map
     * @return defensive copy instance
     */
    private Map<UpgradeEnum, Boolean> copyMap(final Map<UpgradeEnum, Boolean> map) {
        final Map<UpgradeEnum, Boolean> copy = new EnumMap<>(UpgradeEnum.class);
        if (map != null) {
            copy.putAll(map);
        }
        return copy;
    }
}
