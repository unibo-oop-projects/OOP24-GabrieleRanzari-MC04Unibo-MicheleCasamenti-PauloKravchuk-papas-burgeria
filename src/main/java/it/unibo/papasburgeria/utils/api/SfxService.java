package it.unibo.papasburgeria.utils.api;

/**
 * Service responsible for the playing of SFX.
 */
public interface SfxService {

    /**
     * Plays the specified sound once. Volume defaults to scale 1.
     *
     * @param soundName name of the sfx
     */
    void playSound(String soundName);

    /**
     * Plays the specified sound once with a custom volume.
     *
     * @param soundName name of the sfx
     * @param volume    volume scale to apply [0.1 - 3]
     */
    void playSound(String soundName, float volume);


    /**
     * Plays the specified sound in a looped manner. Volume defaults to scale 1.
     *
     * @param soundName name of the sfx
     */
    void playSoundLooped(String soundName);

    /**
     * Plays the specified sound in a looped manner with a custom volume.
     *
     * @param soundName name of the sfx
     * @param volume    volume scale to apply [0.1 - 3]
     */
    void playSoundLooped(String soundName, float volume);

    /**
     * Stops the specified sound if playing.
     *
     * @param soundName name of the sfx
     */
    void stopSound(String soundName);
}
