package it.unibo.papasburgeria.utils.impl;

import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.SfxService;
import it.unibo.papasburgeria.utils.impl.resource.ResourceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link SfxServiceImpl}.
 */
class SfxServiceImplTest {
    private static final String SOUND_NAME = "silent.wav";
    private static final float BASE_VOLUME = 0.1f;
    private static final int SLEEP_TIME = 500;

    private boolean shouldRunTest = true;
    private SfxService sfxService;
    private Clip clip;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        if (!ServiceHelpers.hasAnyMixer()) {
            this.shouldRunTest = false;
        } else {
            final ResourceService resourceService = new ResourceServiceImpl();
            this.sfxService = new SfxServiceImpl(resourceService);
            this.clip = resourceService.getSoundEffect(SOUND_NAME);
            assertNotNull(this.clip);
        }
    }

    /**
     * Tests {@link SfxServiceImpl#playSound(String)} and {@link SfxServiceImpl#stopSound(String)}.
     */
    @Test
    void playStopSound() throws InterruptedException {
        if (shouldRunTest) {
            this.sfxService.playSound(SOUND_NAME, BASE_VOLUME);
            Thread.sleep(SLEEP_TIME); // not practical but implementing locks takes much more
            assertTrue(this.clip.isRunning());

            this.sfxService.stopSound(SOUND_NAME);
            Thread.sleep(SLEEP_TIME);
            assertFalse(this.clip.isRunning());

            assertThrows(
                    IllegalArgumentException.class,
                    () -> this.sfxService.playSound(SOUND_NAME, SfxServiceImpl.MAXIMUM_VOLUME + 1));
        }
    }

    /**
     * Tests {@link SfxServiceImpl#playSoundLooped(String)} and {@link SfxServiceImpl#stopSound(String)}.
     */
    @Test
    void playLoopedStopSound() throws InterruptedException {
        if (shouldRunTest) {
            this.sfxService.playSoundLooped(SOUND_NAME, BASE_VOLUME);
            Thread.sleep(SLEEP_TIME); // not practical but implementing locks takes much more
            assertTrue(this.clip.isRunning());

            this.sfxService.stopSound(SOUND_NAME);
            Thread.sleep(SLEEP_TIME); // not practical but implementing locks takes much more
            assertFalse(this.clip.isRunning());

            assertThrows(
                    IllegalArgumentException.class,
                    () -> this.sfxService.playSoundLooped(SOUND_NAME, SfxServiceImpl.MAXIMUM_VOLUME + 1f));
        }
    }
}
