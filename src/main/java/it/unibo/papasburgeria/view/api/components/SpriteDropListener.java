package it.unibo.papasburgeria.view.api.components;

/**
 * Interface to listen for sprite actions.
 */
public interface SpriteDropListener {

    /**
     * Called when a sprite is dragged and dropped.
     *
     * @param sprite the sprite dropped
     */
    void spriteDropped(Sprite sprite);

    /**
     * Called when a sprite is just clicked.
     *
     * @param sprite the sprite clicked
     */
    void spriteClicked(Sprite sprite);

    /**
     * Called when a sprite is just pressed.
     *
     * @param sprite the sprite pressed
     */
    void spritePressed(Sprite sprite);
}
