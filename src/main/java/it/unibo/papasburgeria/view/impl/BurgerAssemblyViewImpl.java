package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.model.impl.IngredientImpl;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.Sprite;
import it.unibo.papasburgeria.utils.api.SpriteDropListener;
import it.unibo.papasburgeria.utils.impl.DrawingManagerImpl;
import it.unibo.papasburgeria.utils.impl.SpriteDragManagerImpl;
import it.unibo.papasburgeria.utils.impl.SpriteImpl;
import org.tinylog.Logger;

import java.awt.Graphics;
import java.awt.Image;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;
import static it.unibo.papasburgeria.utils.impl.DrawingManagerImpl.BOTTLE_EXTENSION;
import static it.unibo.papasburgeria.utils.impl.DrawingManagerImpl.EXTENSION;
import static it.unibo.papasburgeria.utils.impl.DrawingManagerImpl.INGREDIENTS_X_SIZE_SCALE;
import static it.unibo.papasburgeria.utils.impl.DrawingManagerImpl.INGREDIENTS_Y_SIZE_SCALE;

/**
 * Manages the GUI for the burger assembly scene in the game.
 */
@Singleton
@SuppressFBWarnings(
        value = {"EI_EXPOSE_REP2", "SE_TRANSIENT_FIELD_NOT_RESTORED"},
        justification = "controller is injected and shared intentionally; views are not serialized at runtime"
)
public class BurgerAssemblyViewImpl extends AbstractBaseView implements SpriteDropListener {
    public static final double MIN_X_POS_SCALE_TO_DROP_ON_HAMBURGER = 0.31;
    public static final double MAX_X_POS_SCALE_TO_DROP_ON_HAMBURGER = 0.55;
    public static final double HAMBURGER_X_POS_SCALE =
            (MIN_X_POS_SCALE_TO_DROP_ON_HAMBURGER + MAX_X_POS_SCALE_TO_DROP_ON_HAMBURGER) / 2.0;
    public static final double HAMBURGER_Y_POS_SCALE = 0.71;
    public static final double HAMBURGER_SPACING = 0.04;
    private static final double INGREDIENTS_X_POS_SCALE = 0.005;
    private static final double INGREDIENTS_Y_POS_SCALE = 0.005;
    private static final double INGREDIENTS_MAX_Y_POS_SCALE = 0.6;
    private static final double SAUCE_BOTTLES_X_POS_SCALE = 0.685;
    private static final double SAUCE_BOTTLES_Y_POS_SCALE = 0.68;
    private static final double PATTIES_X_POS_SCALE = 0.128;
    private static final double PATTIES_Y_POS_SCALE = 0.77;
    private static final double SAUCE_BOTTLES_X_SIZE_SCALE = INGREDIENTS_X_SIZE_SCALE / 2;
    private static final double SAUCE_BOTTLES_Y_SIZE_SCALE = INGREDIENTS_Y_SIZE_SCALE * 2;
    private static final double SPACING = 0.005;

    @Serial
    private static final long serialVersionUID = 1L;
    private final transient BurgerAssemblyController controller;
    private final transient DrawingManagerImpl drawingManager;
    private final transient List<Sprite> sprites;
    private final transient List<Sprite> draggableSprites;
    private final transient List<Sprite> draggablePattySprites;
    private final transient List<Sprite> draggableHamburgerSprites;

    /**
     * Default constructor, creates and initializes the UI elements.
     *
     * @param resourceService the service that handles resource obtainment.
     * @param controller      the burger assembly controller.
     * @param drawingManager  the manager for drawing various things.
     */
    @Inject
    public BurgerAssemblyViewImpl(final ResourceService resourceService, final BurgerAssemblyController controller,
                                  final DrawingManagerImpl drawingManager) {
        this.controller = controller;
        this.drawingManager = drawingManager;
        sprites = new ArrayList<>();
        draggableSprites = new ArrayList<>();
        draggablePattySprites = new ArrayList<>();
        draggableHamburgerSprites = new ArrayList<>();

        if (DEBUG_MODE) {
            Logger.info("BurgerAssembly created");
        }

        super.setStaticBackgroundImage(resourceService.getImage("assembly_background.png"));

        double pbPositionXScale = INGREDIENTS_X_POS_SCALE;
        double pbPositionYScale = INGREDIENTS_Y_POS_SCALE;
        for (final IngredientEnum ingredientType : IngredientEnum.values()) {
            final Image image = resourceService.getImage(ingredientType.getName() + EXTENSION);

            if (ingredientType == IngredientEnum.PATTY || IngredientEnum.SAUCES.contains(ingredientType)) {
                continue;
            }

            final Sprite sprite = new SpriteImpl(image, new IngredientImpl(ingredientType),
                    pbPositionXScale, pbPositionYScale, INGREDIENTS_X_SIZE_SCALE, INGREDIENTS_Y_SIZE_SCALE);

            if (controller.isIngredientUnlocked(ingredientType)) {
                draggableSprites.add(sprite);
            } else {
                sprites.add(sprite);
            }

            pbPositionYScale = pbPositionYScale + INGREDIENTS_Y_SIZE_SCALE + SPACING;
            if (pbPositionYScale > INGREDIENTS_MAX_Y_POS_SCALE) {
                pbPositionYScale = INGREDIENTS_Y_POS_SCALE;
                pbPositionXScale = pbPositionXScale + INGREDIENTS_X_SIZE_SCALE + SPACING;
            }
        }

        pbPositionXScale = SAUCE_BOTTLES_X_POS_SCALE;
        pbPositionYScale = SAUCE_BOTTLES_Y_POS_SCALE;
        for (final IngredientEnum ingredientType : IngredientEnum.SAUCES) {
            final Image image = resourceService.getImage(ingredientType.getName() + BOTTLE_EXTENSION + EXTENSION);

            final Sprite sprite = new SpriteImpl(image, new IngredientImpl(ingredientType),
                    pbPositionXScale, pbPositionYScale, SAUCE_BOTTLES_X_SIZE_SCALE, SAUCE_BOTTLES_Y_SIZE_SCALE);

            if (controller.isIngredientUnlocked(ingredientType)) {
                draggableSprites.add(sprite);
            } else {
                sprites.add(sprite);
            }

            pbPositionXScale = pbPositionXScale + SAUCE_BOTTLES_X_SIZE_SCALE + SPACING;
        }

        new SpriteDragManagerImpl(this, draggableHamburgerSprites, this);
        new SpriteDragManagerImpl(this, draggablePattySprites, this);
        new SpriteDragManagerImpl(this, draggableSprites, this);
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
    final void paintComponentDelegate(final Graphics graphics) {
        final List<Ingredient> hamburgerIngredients = controller.getIngredients();
        drawingManager.drawHamburger(hamburgerIngredients, getSize(), controller, draggableHamburgerSprites, graphics);

        final List<Patty> cookedPatties = controller.getCookedPatties();
        drawingManager.generateCookedPatties(cookedPatties, PATTIES_X_POS_SCALE, PATTIES_Y_POS_SCALE, draggablePattySprites);

        for (final Sprite sprite : sprites) {
            drawingManager.drawIngredient(sprite, getSize(), controller, graphics);
        }

        for (final Sprite sprite : draggableHamburgerSprites) {
            drawingManager.drawIngredient(sprite, getSize(), controller, graphics);
        }

        for (final Sprite sprite : draggableSprites) {
            drawingManager.drawIngredient(sprite, getSize(), controller, graphics);
        }

        for (final Sprite sprite : draggablePattySprites) {
            drawingManager.drawIngredient(sprite, getSize(), controller, graphics);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        if (DEBUG_MODE) {
            Logger.info("BurgerAssembly shown");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        if (DEBUG_MODE) {
            Logger.info("BurgerAssembly hidden");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void spriteDropped(final Sprite sprite) {
        double pbPositionXScale = sprite.getPbPositionXScale();
        if (IngredientEnum.SAUCES.contains(sprite.getIngredientType())) {
            pbPositionXScale = pbPositionXScale - SAUCE_BOTTLES_X_SIZE_SCALE / 2;
        }
        if (sprite.isRemovable()) {
            final Ingredient ingredient = sprite.getIngredient();
            if (pbPositionXScale < MIN_X_POS_SCALE_TO_DROP_ON_HAMBURGER
                    || pbPositionXScale > MAX_X_POS_SCALE_TO_DROP_ON_HAMBURGER) {
                if (ingredient instanceof Patty patty && controller.addCookedPatty(patty)) {
                    draggablePattySprites.clear();
                }
                controller.removeLastIngredient();
            } else {
                controller.changeIngredientAccuracy(ingredient, controller.calculateAccuracy(pbPositionXScale));
                if (ingredient instanceof Patty) {
                    draggablePattySprites.clear();
                }
            }
        }
        if (pbPositionXScale > MIN_X_POS_SCALE_TO_DROP_ON_HAMBURGER
                && pbPositionXScale < MAX_X_POS_SCALE_TO_DROP_ON_HAMBURGER
                && !sprite.isRemovable()) {
            final Ingredient ingredient = sprite.getIngredient();
            ingredient.setPlacementAccuracy(controller.calculateAccuracy(pbPositionXScale));

            if (controller.addIngredient(ingredient)) {
                sprite.setDraggable(false);
                if (ingredient instanceof Patty patty) {
                    controller.removeCookedPatty(patty);
                    draggablePattySprites.clear();
                }
            }
        }
        draggableHamburgerSprites.clear();
        draggablePattySprites.remove(sprite);
        draggableSprites.remove(sprite);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void spriteClicked(final Sprite sprite) {
        draggableHamburgerSprites.clear();
        draggablePattySprites.remove(sprite);
        draggableSprites.remove(sprite);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void spritePressed(final Sprite sprite) {

    }
}
