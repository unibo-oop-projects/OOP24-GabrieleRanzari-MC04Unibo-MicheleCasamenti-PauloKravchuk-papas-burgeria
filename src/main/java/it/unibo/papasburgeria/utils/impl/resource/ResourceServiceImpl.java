package it.unibo.papasburgeria.utils.impl.resource;

import com.google.inject.Singleton;
import it.unibo.papasburgeria.utils.api.ResourceService;
import org.tinylog.Logger;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of ResourceService.
 *
 * <p>
 * See {@link ResourceService} for interface details.
 */
@Singleton
public class ResourceServiceImpl implements ResourceService {
    private static final String IMAGE_PATH = "images/";
    private static final String SFX_PATH = "sfx/";

    private final Map<String, BufferedImage> imageCache;
    private final Map<String, Clip> sfxCache;

    /**
     * Constructs the service.
     */
    public ResourceServiceImpl() {
        this.imageCache = new HashMap<>();
        this.sfxCache = new HashMap<>();
    }

    /**
     * @inheritDoc
     */
    @Override
    public BufferedImage getImage(final String imagePath) {
        validateProvidedPath(imagePath);

        return this.imageCache.computeIfAbsent(imagePath, path -> {
            try (InputStream inputStream = getFile(IMAGE_PATH + path)) {
                return ImageIO.read(inputStream);
            } catch (final IOException exception) {
                throw new ResourceLoadException("Exception while attempting read: " + path, exception);
            }
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    public Clip getSoundEffect(final String soundPath) {
        validateProvidedPath(soundPath);

        return this.sfxCache.computeIfAbsent(soundPath, path -> {
            try (
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                            new BufferedInputStream(getFile(SFX_PATH + path))
                    )
            ) {
                final Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                return clip;
            } catch (final LineUnavailableException | IllegalArgumentException exception) {
                /*
                    Some devices may not have lines available, decided not to propagate the exception
                    but rather handle it with no-ops while providing visual warning.
                 */
                Logger.warn(exception, "Exception while attempting to get sound effect: " + path);
                return null;
            } catch (final UnsupportedAudioFileException | IOException exception) {
                throw new ResourceLoadException("Exception while attempting read: " + path, exception);
            }
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    public void dispose() {
        for (final Clip clip : this.sfxCache.values()) {
            try {
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.close();
            } catch (final SecurityException exception) {
                throw new ResourceLoadException("Exception while attempting clip close: " + clip, exception);
            }
        }

        this.sfxCache.clear();
        this.imageCache.clear();
    }

    /**
     * Used to retrieve file resources.
     *
     * @param filePath path to the file
     * @return the InputStream instance
     */
    private InputStream getFile(final String filePath) {
        validateProvidedPath(filePath);

        final InputStream inputStream = ClassLoader.getSystemResourceAsStream(filePath);
        if (inputStream == null) {
            throw new ResourceLoadException("Could not find resource: " + filePath);
        }
        return inputStream;
    }

    /**
     * Validates the provided paths within the class methods.
     *
     * @param providedPath the path provided
     */
    private void validateProvidedPath(final String providedPath) {
        if (providedPath == null || providedPath.isEmpty()) {
            throw new IllegalArgumentException("Provided path cannot be null or empty");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "ResourceServiceImpl{"
                +
                "imageCache="
                + imageCache
                +
                ", sfxCache="
                + sfxCache
                +
                '}';
    }
}
