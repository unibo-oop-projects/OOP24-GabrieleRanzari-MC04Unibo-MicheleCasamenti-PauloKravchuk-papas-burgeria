package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.controller.impl.BurgerAssemblyControllerImpl;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.impl.PantryModelImpl;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.Sprite;
import it.unibo.papasburgeria.utils.impl.SpriteImpl;
import org.tinylog.Logger;

import java.awt.Graphics;
import java.awt.Image;
import java.io.Serial;

/**
 * Manages the GUI for the burger assembly scene in the game.
 */
@Singleton
public class BurgerAssemblyViewImpl extends AbstractBaseView {
    private static final int X = 200;
    private static final int Y = 300;

    @Serial
    private static final long serialVersionUID = 1L;
    private final transient Sprite bottomBun;
    private final transient BurgerAssemblyController controller;
    private final transient Image horizontalLock;
    private final transient Image verticalLock;

    /**
     * Default constructor, creates and initializes the UI elements.
     *
     * @param resourceService the service that handles resource obtainment.
     */
    @Inject
    public BurgerAssemblyViewImpl(final ResourceService resourceService) {
        this.controller = new BurgerAssemblyControllerImpl(new PantryModelImpl()); //TODO fix this.controller = controller;
        Logger.info("BurgerAssemblyView created");

        super.setStaticBackgroundImage(resourceService.getImage("assembly_background.png"));
        bottomBun = new SpriteImpl(resourceService.getImage("BBQ_bottle.png"), IngredientEnum.BBQ, X, Y);
        //bottomBun = new SpriteImpl(resourceService.getImage("bottom_bun.png"), IngredientEnum.ONION, X, Y);

        horizontalLock = resourceService.getImage("horizontal_lock.png");
        verticalLock = resourceService.getImage("vertical_lock.png");
    }

    /**
     * @inheritDoc
     */
    @Override
    void update(final double delta) {

    }

    /**
     * @inheritDoc
     */
    @Override
    final void paintComponentDelegate(final Graphics g) {
        drawSprite(bottomBun, g);
    }

    /**
     * Draws a sprite.
     *
     * @param sprite the sprite to draw
     * @param g the graphics
     */
    final void drawSprite(final Sprite sprite, final Graphics g) {
        g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), null);
        if (!controller.isIngredientUnlocked(sprite.getIngredientType())) {
            if (sprite.getWidth() > sprite.getHeight()) {
                g.drawImage(horizontalLock, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), null);
            } else {
                g.drawImage(verticalLock, sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight(), null);
            }
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        Logger.info("BurgerAssemblyView shown");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        Logger.info("BurgerAssemblyView hidden");
    }
}
