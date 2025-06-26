package it.unibo.papasburgeria.utils.api;

/**
 * Service responsible for the playing of SFX.
 */
public interface SfxService {

    /**
     * Plays the specified sound once.
     *
     * @param soundName name of the sfx
     */
    void playSound(String soundName);

    /**
     * Plays the specified sound in a looped manner.
     *
     * @param soundName name of the sfx
     */
    void playSoundLooped(String soundName);

    /**
     * Stops the specified sound if playing.
     *
     * @param soundName name of the sfx
     */
    void stopSound(String soundName);
}
