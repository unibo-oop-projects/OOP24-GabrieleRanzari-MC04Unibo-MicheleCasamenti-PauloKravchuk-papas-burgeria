package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.Sprite;
import it.unibo.papasburgeria.utils.impl.SpriteDragManagerImpl;
import it.unibo.papasburgeria.utils.impl.SpriteImpl;
import org.tinylog.Logger;

import java.awt.Graphics;
import java.awt.Image;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Manages the GUI for the burger assembly scene in the game.
 */
@Singleton
@SuppressFBWarnings(
        value = { "EI_EXPOSE_REP2", "SE_TRANSIENT_FIELD_NOT_RESTORED" },
        justification = "controller is injected and shared intentionally; views are not serialized at runtime"
)
public class BurgerAssemblyViewImpl extends AbstractBaseView {
    private static final double INGREDIENTS_X_POS_SCALE = 0.005;
    private static final double INGREDIENTS_Y_POS_SCALE = 0.005;
    private static final double SAUCES_X_POS_SCALE = 0.685;
    private static final double SAUCES_Y_POS_SCALE = 0.68;
    private static final double INGREDIENTS_X_SIZE_SCALE = 0.15;
    private static final double INGREDIENTS_Y_SIZE_SCALE = 0.15;
    private static final double SAUCES_X_SIZE_SCALE = INGREDIENTS_X_SIZE_SCALE / 2;
    private static final double SAUCES_Y_SIZE_SCALE = INGREDIENTS_Y_SIZE_SCALE * 2;
    private static final double SPACING = 0.005;
    private static final String EXTENSION = ".png";
    private static final String SEPARATOR = "_";
    private static final String BOTTLE_EXTENSION = "_bottle";
    private static final String DROP_EXTENSION = "_drop";

    private static final Set<IngredientEnum> INGREDIENTS = Set.of(
            IngredientEnum.BOTTOM_BUN,
            IngredientEnum.TOP_BUN,
            IngredientEnum.LETTUCE,
            IngredientEnum.ONION,
            IngredientEnum.TOMATO,
            IngredientEnum.PICKLE,
            IngredientEnum.CHEESE
    );

    private static final Set<IngredientEnum> SAUCES = Set.of(
            IngredientEnum.KETCHUP,
            IngredientEnum.MUSTARD,
            IngredientEnum.BBQ,
            IngredientEnum.MAYO
    );

    @Serial
    private static final long serialVersionUID = 1L;
    private final transient List<Sprite> sprites;
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
        sprites = new ArrayList<>();
        draggableSprites = new ArrayList<>();
        Logger.info("BurgerAssembly created");

        super.setStaticBackgroundImage(resourceService.getImage("assembly_background.png"));

        horizontalLock = resourceService.getImage("horizontal_lock.png");
        verticalLock = resourceService.getImage("vertical_lock.png");

        final double maxYPosScale = 0.6;
        double pbPositionXScale = INGREDIENTS_X_POS_SCALE;
        double pbPositionYScale = INGREDIENTS_Y_POS_SCALE;
        for (final IngredientEnum ingredient : INGREDIENTS) {
            final Image image = resourceService.getImage(ingredient.getName() + EXTENSION);

            final Sprite sprite = new SpriteImpl(image, ingredient,
                    pbPositionXScale, pbPositionYScale, INGREDIENTS_X_SIZE_SCALE, INGREDIENTS_Y_SIZE_SCALE);

            if (controller.isIngredientUnlocked(ingredient)) {
                draggableSprites.add(sprite);
            } else {
                sprites.add(sprite);
            }

            pbPositionYScale = pbPositionYScale + INGREDIENTS_Y_SIZE_SCALE + SPACING;
            if (pbPositionYScale > maxYPosScale) {
                pbPositionYScale = INGREDIENTS_Y_POS_SCALE;
                pbPositionXScale = pbPositionXScale + INGREDIENTS_X_SIZE_SCALE + SPACING;
            }
        }

        pbPositionXScale = SAUCES_X_POS_SCALE;
        pbPositionYScale = SAUCES_Y_POS_SCALE;
        for (final IngredientEnum ingredient : SAUCES) {
            final Image image = resourceService.getImage(ingredient.getName() + BOTTLE_EXTENSION + EXTENSION);

            final Sprite sprite = new SpriteImpl(image, ingredient,
                    pbPositionXScale, pbPositionYScale, SAUCES_X_SIZE_SCALE, SAUCES_Y_SIZE_SCALE);

            if (controller.isIngredientUnlocked(ingredient)) {
                draggableSprites.add(sprite);
            } else {
                sprites.add(sprite);
            }

            pbPositionXScale = pbPositionXScale + SAUCES_X_SIZE_SCALE + SPACING;
        }

        new SpriteDragManagerImpl(this, draggableSprites);
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
        for (final Sprite sprite : sprites) {
            drawIngredient(sprite, g);
        }
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
