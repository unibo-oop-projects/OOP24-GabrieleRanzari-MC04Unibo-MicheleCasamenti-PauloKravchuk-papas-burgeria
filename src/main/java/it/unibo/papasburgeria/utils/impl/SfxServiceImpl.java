package it.unibo.papasburgeria.utils.impl;

import com.google.inject.Inject;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.SfxService;
import org.tinylog.Logger;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.function.Consumer;

/**
 * Implementation of SfxService.
 *
 * <p>
 * See {@link SfxService} for interface details.
 */
public class SfxServiceImpl implements SfxService {
    private static final float MINIMUM_VOLUME = 0.1f;
    private static final float MAXIMUM_VOLUME = 3f;
    private static final float DEFAULT_VOLUME = 1f;
    private static final float AMPLITUDE_CONVERSION_FACTOR = 20f;

    private final ResourceService resourceService;

    /**
     * Initializes the SFX service.
     *
     * @param resourceService resource provider
     */
    @Inject
    public SfxServiceImpl(final ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playSound(final String soundName) {
        this.playSound(soundName, DEFAULT_VOLUME);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playSound(final String soundName, final float volume) {
        if (volume < MINIMUM_VOLUME) {
            return;
        }

        this.resetSoundWithAction(soundName, clip -> {
            setClipVolume(clip, volume);
            clip.start();
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playSoundLooped(final String soundName) {
        this.playSoundLooped(soundName, DEFAULT_VOLUME);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playSoundLooped(final String soundName, final float volume) {
        if (volume < MINIMUM_VOLUME) {
            return;
        }

        this.resetSoundWithAction(soundName, clip -> {
            setClipVolume(clip, volume);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    public void stopSound(final String soundName) {
        this.resetSoundWithAction(soundName, null);
    }

    /**
     * Helper method to avoid code duplication.
     *
     * @param soundName name if the sfx file
     * @param action    action the helper should apply on the clip
     */
    private void resetSoundWithAction(final String soundName, final Consumer<Clip> action) {
        final Clip clip = this.resourceService.getSoundEffect(soundName);
        if (clip != null) {
            synchronized (clip) {
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.setFramePosition(0);

                if (action != null) {
                    action.accept(clip);
                }
            }
        } else {
            Logger.warn("Clip not available: " + soundName);
        }
    }

    /**
     * Takes a scale in input and converts it to a decibel difference in order to set the clip's
     * volume. Check {@link FloatControl.Type#MASTER_GAIN} for more information.
     *
     * @param clip   clip instance
     * @param volume volume scale [0.1 - 3]
     */
    private void setClipVolume(final Clip clip, final float volume) {
        if (clip == null) {
            throw new IllegalArgumentException("Clip not available, unable to set clip's volume");
        }

        if (volume < MINIMUM_VOLUME || volume > MAXIMUM_VOLUME) {
            throw new IllegalArgumentException("Volume must be between 0.1f and 3f");
        }

        try {
            final FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // check FloatControl.Type#MASTER_GAIN docs for more info
            floatControl.setValue(AMPLITUDE_CONVERSION_FACTOR * (float) Math.log10(volume));
        } catch (final IllegalArgumentException e) {
            Logger.warn(e, "Failed to set volume on clip: " + clip);
        }
    }

    @Override
    public String toString() {
        return "SfxServiceImpl{" +
                "resourceService=" + resourceService +
                '}';
    }
}
