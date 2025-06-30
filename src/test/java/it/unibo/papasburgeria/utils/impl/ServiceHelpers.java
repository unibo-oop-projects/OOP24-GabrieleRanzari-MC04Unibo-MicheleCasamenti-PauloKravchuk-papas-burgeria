package it.unibo.papasburgeria.utils.impl;

import it.unibo.papasburgeria.utils.api.ResourceService;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

/**
 * Class of static helper methods to test on services.
 */
public final class ServiceHelpers {

    /**
     * Constructs this class.
     */
    private ServiceHelpers() {
    }

    /**
     * Helper method to check whether the current device has mixers. If no mixers are present
     * the {@link ResourceService#getSoundEffect(String)} method will throw an exception.
     *
     * @return whether any mixers are present
     */
    public static boolean hasAnyMixer() {
        final Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        return mixers.length > 0;
    }
}
