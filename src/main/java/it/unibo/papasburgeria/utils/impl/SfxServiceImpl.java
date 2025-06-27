package it.unibo.papasburgeria.utils.impl;

import com.google.inject.Inject;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.SfxService;
import org.tinylog.Logger;

import javax.sound.sampled.Clip;
import java.util.function.Consumer;

/**
 * Implementation of SfxService.
 *
 * <p>
 * See {@link SfxService} for interface details.
 */
public class SfxServiceImpl implements SfxService {
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
        this.resetSoundWithAction(soundName, Clip::start);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void playSoundLooped(final String soundName) {
        this.resetSoundWithAction(soundName, clip -> clip.loop(Clip.LOOP_CONTINUOUSLY));
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
}
