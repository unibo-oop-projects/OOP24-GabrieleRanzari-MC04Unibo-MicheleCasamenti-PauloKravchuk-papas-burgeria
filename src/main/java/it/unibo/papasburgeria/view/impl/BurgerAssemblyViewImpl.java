package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.Sprite;
import it.unibo.papasburgeria.utils.impl.SpriteDragManager;
import it.unibo.papasburgeria.utils.impl.SpriteImpl;
import org.tinylog.Logger;

import java.awt.Graphics;
import java.awt.Image;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the GUI for the burger assembly scene in the game.
 */
@Singleton
@SuppressFBWarnings(
        value = { "EI_EXPOSE_REP2", "SE_TRANSIENT_FIELD_NOT_RESTORED" },
        justification = "controller is injected and shared intentionally; views are not serialized at runtime"
)
public class BurgerAssemblyViewImpl extends AbstractBaseView {

    @Serial
    private static final long serialVersionUID = 1L;
    private final transient List<Sprite> draggableSprites;
    private final transient BurgerAssemblyController controller;
    private final transient Image horizontalLock;
    private final transient Image verticalLock;

    /**
     * Default constructor, creates and initializes the UI elements.
     *
     * @param resourceService the service that handles resource obtainment.
     * @param controller the burger assembly controller.
     */
    @Inject
    public BurgerAssemblyViewImpl(final ResourceService resourceService, final BurgerAssemblyController controller) {
        this.controller = controller;
        draggableSprites = new ArrayList<>();
        Logger.info("BurgerAssembly created");

        super.setStaticBackgroundImage(resourceService.getImage("assembly_background.png"));

        final double pbPositionXScale = 0.4;
        final double pbPositionYScale = 0.65;
        final double pbSizeXScale = 0.2;
        final double pbSizeYScale = 0.2;

        final Sprite bottomBun = new SpriteImpl(resourceService.getImage("bottom_bun.png"), IngredientEnum.BOTTOM_BUN,
                pbPositionXScale, pbPositionYScale, pbSizeXScale, pbSizeYScale);
        draggableSprites.add(bottomBun);

        final Sprite topBun = new SpriteImpl(resourceService.getImage("top_bun.png"), IngredientEnum.TOP_BUN,
                pbPositionXScale + pbSizeXScale, pbPositionYScale, pbSizeXScale, pbSizeYScale);
        draggableSprites.add(topBun);

        horizontalLock = resourceService.getImage("horizontal_lock.png");
        verticalLock = resourceService.getImage("vertical_lock.png");

        new SpriteDragManager(this, draggableSprites);
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
        for (final Sprite sprite : draggableSprites) {
            drawIngredient(sprite, g);
        }
    }

    /**
     * Draws a sprite.
     *
     * @param sprite the sprite to draw
     * @param g the graphics
     */
    final void drawIngredient(final Sprite sprite, final Graphics g) {
        final int frameWidth = getWidth();
        final int frameHeight = getHeight();
        sprite.draw(getSize(), g);

        if (!controller.isIngredientUnlocked(sprite.getIngredientType())) {
            Image lock = horizontalLock;
            if (sprite.calculateWidth(frameWidth) < sprite.calculateHeight(frameHeight)) {
                lock = verticalLock;
            }
            g.drawImage(lock, sprite.calculateX(frameWidth), sprite.calculateY(frameHeight),
                    sprite.calculateWidth(frameWidth), sprite.calculateHeight(frameHeight), null);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        Logger.info("BurgerAssembly shown");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        Logger.info("BurgerAssembly hidden");
    }
}
