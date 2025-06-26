package it.unibo.papasburgeria.utils.api;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;

/**
 * Service responsible for the loading and obtainment of resources.
 * Wrapping checked exceptions into RuntimeExceptions is preferred within implementations,
 * this prevents propagation and allows simplifies the use of the service.
 */
public interface ResourceService {
    /**
     * Used to retrieve an image resource in a cached way.
     *
     * @param imagePath path component of <code>resources/images/[path]</code>
     * @return the BufferedImage instance
     */
    BufferedImage getImage(String imagePath);

    /**
     * Used to retrieve sound resources of short length to be used as sfx in a cached way.
     * Remember that there is a dedicated service to play sfx with.
     *
     * @param soundPath path component of <code>resources/sfx/[path]</code>
     * @return the Clip instance
     */
    Clip getSoundEffect(String soundPath);

    /**
     * To be called when the service is supposed to dispose of all
     * the currently cached and loaded assets.
     */
    void dispose();
}
