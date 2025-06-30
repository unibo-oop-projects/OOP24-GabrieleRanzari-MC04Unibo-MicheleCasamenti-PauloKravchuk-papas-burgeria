package it.unibo.papasburgeria.utils.impl;

import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.impl.resource.ResourceLoadException;
import it.unibo.papasburgeria.utils.impl.resource.ResourceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link ResourceServiceImpl}.
 */
class ResourceServiceImplTest {

    private ResourceService resourceService;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        this.resourceService = new ResourceServiceImpl();
    }

    /**
     * Tests {@link ResourceServiceImpl#getImage(String)}.
     */
    @Test
    void testGetImage() {
        assertThrows(IllegalArgumentException.class, () -> this.resourceService.getImage(null));
        assertThrows(IllegalArgumentException.class, () -> this.resourceService.getImage(""));
        assertThrows(ResourceLoadException.class, () -> this.resourceService.getImage("this-does-not-exist.png"));

        final BufferedImage image = this.resourceService.getImage("cheese.png");
        assertNotNull(image, "Expected a non-null image.");
        assertTrue(image.getWidth() > 0 && image.getHeight() > 0, "Expected content within image file.");
        this.resourceService.dispose();
    }

    /**
     * Tests {@link ResourceServiceImpl#getSoundEffect(String)}.
     */
    @Test
    void testGetSoundEffect() {
        assertThrows(IllegalArgumentException.class, () -> this.resourceService.getSoundEffect(null));
        assertThrows(IllegalArgumentException.class, () -> this.resourceService.getSoundEffect(""));
        assertThrows(ResourceLoadException.class, () -> this.resourceService.getSoundEffect("this-does-not-exist.wav"));

        if (ServiceHelpers.hasAnyMixer()) {
            final Clip clip = this.resourceService.getSoundEffect("menu_ost.wav");
            assertNotNull(clip, "Expected a non-null clip.");
            assertTrue(clip.isOpen(), "Expected a non-null Clip.");
            clip.close();
            this.resourceService.dispose();
        }
    }

    /**
     * Tests {@link ResourceServiceImpl#dispose()}.
     */
    @Test
    void testDispose() {
        final Clip clion = this.resourceService.getSoundEffect("menu_ost.wav");
        if (clion != null) {
            clion.close();
        }
        this.resourceService.getImage("cheese.png");
        this.resourceService.dispose();

        final String stringPrint = this.resourceService.toString();
        assertFalse(stringPrint.isEmpty(), "Expected a non-empty string.");
        assertTrue(stringPrint.contains("imageCache={}"), "Expected imageCache to be empty after dispose.");
        assertTrue(stringPrint.contains("sfxCache={}"), "Expected sfxCache to be empty after dispose.");
    }
}
