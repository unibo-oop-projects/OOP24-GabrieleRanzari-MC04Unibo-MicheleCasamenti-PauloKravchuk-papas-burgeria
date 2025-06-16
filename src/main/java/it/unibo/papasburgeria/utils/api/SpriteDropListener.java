package it.unibo.papasburgeria.utils.api;

/**
 * Interface to listen for sprites.
 */
@FunctionalInterface
public interface SpriteDropListener {

    /**
     * Called when a sprite is dropped.
     *
     * @param sprite the sprite dropped.
     */
    void spriteDropped(Sprite sprite);
}
