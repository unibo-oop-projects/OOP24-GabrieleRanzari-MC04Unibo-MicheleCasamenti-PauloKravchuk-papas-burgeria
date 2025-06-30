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
    private static final String SOUND_NAME = "menu_ost.wav";

    private boolean shouldRunTest = true;

    private SfxService sfxService;
    private ResourceService resourceService;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        if (!ServiceHelpers.hasAnyMixer()) {
            this.shouldRunTest = false;
        } else {
            this.resourceService = new ResourceServiceImpl();
            this.sfxService = new SfxServiceImpl(this.resourceService);
        }
    }

    /**
     * Tests {@link SfxServiceImpl#playSound(String)} and {@link SfxServiceImpl#stopSound(String)}.
     */
    @Test
    void playStopSound() {
        if (shouldRunTest) {
            final Clip clip = this.resourceService.getSoundEffect(SOUND_NAME);
            assertNotNull(clip);
            this.sfxService.playSound(SOUND_NAME);
            assertTrue(clip.isRunning());
            this.sfxService.stopSound(SOUND_NAME);
            assertFalse(clip.isRunning());

            assertThrows(
                    IllegalArgumentException.class,
                    () -> this.sfxService.playSound(SOUND_NAME, SfxServiceImpl.MAXIMUM_VOLUME + 1));
            assertThrows(
                    IllegalArgumentException.class,
                    () -> this.sfxService.playSound(SOUND_NAME, SfxServiceImpl.MINIMUM_VOLUME - 1));
        }
    }

    /**
     * Tests {@link SfxServiceImpl#playSoundLooped(String)} and {@link SfxServiceImpl#stopSound(String)}.
     */
    @Test
    void playLoopedStopSound() {
        if (shouldRunTest) {
            final Clip clip = this.resourceService.getSoundEffect(SOUND_NAME);
            assertNotNull(clip);
            this.sfxService.playSoundLooped(SOUND_NAME);
            assertTrue(clip.isRunning());
            this.sfxService.stopSound(SOUND_NAME);
            assertFalse(clip.isRunning());

            assertThrows(
                    IllegalArgumentException.class,
                    () -> this.sfxService.playSoundLooped(SOUND_NAME, SfxServiceImpl.MAXIMUM_VOLUME + 1f));
            assertThrows(
                    IllegalArgumentException.class,
                    () -> this.sfxService.playSoundLooped(SOUND_NAME, SfxServiceImpl.MINIMUM_VOLUME - 1f));
        }
    }
}
