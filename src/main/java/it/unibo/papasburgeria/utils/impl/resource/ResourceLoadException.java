package it.unibo.papasburgeria.utils.impl.resource;

import java.io.Serial;

/**
 * Wraps checked exceptions coming from ResourceService.
 */
public class ResourceLoadException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Throw an unchecked exception with custom text.
     *
     * @param message the text
     */
    public ResourceLoadException(final String message) {
        super(message);
    }

    /**
     * Throws an unchecked exception with custom text and preserving the stack-trace.
     *
     * @param message the text
     * @param cause   the stack-trace
     */
    public ResourceLoadException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
