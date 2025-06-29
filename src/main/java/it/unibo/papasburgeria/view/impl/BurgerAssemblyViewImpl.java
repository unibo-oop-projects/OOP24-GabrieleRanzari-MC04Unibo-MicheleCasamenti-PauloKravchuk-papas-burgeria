package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.controller.api.OrderSelectionController;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.Order;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.model.impl.IngredientImpl;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.SfxService;
import it.unibo.papasburgeria.utils.api.scene.SceneType;
import it.unibo.papasburgeria.view.api.components.Sprite;
import it.unibo.papasburgeria.view.api.components.SpriteDropListener;
import it.unibo.papasburgeria.view.impl.components.DrawingManagerImpl;
import it.unibo.papasburgeria.view.impl.components.ScalableLayoutImpl;
import it.unibo.papasburgeria.view.impl.components.SpriteDragManagerImpl;
import it.unibo.papasburgeria.view.impl.components.SpriteImpl;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.unibo.papasburgeria.model.IngredientEnum.PATTY;
import static it.unibo.papasburgeria.model.IngredientEnum.SAUCES;
import static it.unibo.papasburgeria.model.IngredientEnum.TOP_BUN;
import static it.unibo.papasburgeria.view.impl.components.DrawingManagerImpl.BOTTLE_EXTENSION;
import static it.unibo.papasburgeria.view.impl.components.DrawingManagerImpl.EXTENSION;
import static it.unibo.papasburgeria.view.impl.components.DrawingManagerImpl.INGREDIENTS_X_SIZE_SCALE;
import static it.unibo.papasburgeria.view.impl.components.DrawingManagerImpl.INGREDIENTS_Y_SIZE_SCALE;

/**
 * Manages the GUI for the burger assembly scene in the game.
 */
@SuppressFBWarnings(
        value = {"EI_EXPOSE_REP2", "SE_TRANSIENT_FIELD_NOT_RESTORED"},
        justification = "The controller is injected and shared intentionally; The views are not serialized at runtime"
)
public class BurgerAssemblyViewImpl extends AbstractBaseView implements SpriteDropListener {
    public static final double MIN_X_POS_SCALE_TO_DROP_ON_HAMBURGER = 0.31;
    public static final double MAX_X_POS_SCALE_TO_DROP_ON_HAMBURGER = 0.55;
    public static final double HALF_RANGE = (MAX_X_POS_SCALE_TO_DROP_ON_HAMBURGER - MIN_X_POS_SCALE_TO_DROP_ON_HAMBURGER) / 2.0;
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
    private final transient GameController gameController;
    private final transient OrderSelectionController orderSelectionController;
    private final transient SfxService sfxService;
    private final transient List<Sprite> sprites;
    private final transient List<Sprite> draggableSprites;
    private final transient List<Sprite> draggablePattySprites;
    private final transient List<Sprite> draggableHamburgerSprites;
    private final transient List<Sprite> orderSprites;
    private final transient Map<Sprite, Order> spriteOrders;

    /**
     * Default constructor, creates and initializes the GUI elements.
     *
     * @param resourceService          the service that handles resource obtainment
     * @param controller               the burger assembly controller
     * @param drawingManager           the manager for drawing various things
     * @param gameController           the game controller
     * @param orderSelectionController the order selection controller
     * @param sfxService      the service that handles sfx playing
     */
    @Inject
    public BurgerAssemblyViewImpl(final ResourceService resourceService,
                                  final SfxService sfxService,
                                  final BurgerAssemblyController controller,
                                  final DrawingManagerImpl drawingManager,
                                  final GameController gameController,
                                  final OrderSelectionController orderSelectionController) {
        this.controller = controller;
        this.sfxService = sfxService;
        this.drawingManager = drawingManager;
        this.gameController = gameController;
        this.orderSelectionController = orderSelectionController;
        sprites = new ArrayList<>();
        draggableSprites = new ArrayList<>();
        draggablePattySprites = new ArrayList<>();
        draggableHamburgerSprites = new ArrayList<>();
        orderSprites = new ArrayList<>();
        spriteOrders = new HashMap<>();

        readOrders();

        super.setStaticBackgroundImage(resourceService.getImage("assembly_background.png"));

        final JPanel interfacePanel = super.getInterfacePanel();
        interfacePanel.setLayout(new ScalableLayoutImpl());

        double pbPositionXScale = INGREDIENTS_X_POS_SCALE;
        double pbPositionYScale = INGREDIENTS_Y_POS_SCALE;
        for (final IngredientEnum ingredientType : IngredientEnum.values()) {
            if (ingredientType != PATTY && !SAUCES.contains(ingredientType)) {
                final Image image = resourceService.getImage(ingredientType.getName() + EXTENSION);
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
        }

        pbPositionXScale = SAUCE_BOTTLES_X_POS_SCALE;
        pbPositionYScale = SAUCE_BOTTLES_Y_POS_SCALE;
        for (final IngredientEnum ingredientType : SAUCES) {
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
     * {@inheritDoc}
     */
    @Override
    void update(final double delta) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    final void paintComponentDelegate(final Graphics graphics) {
        for (final Sprite sprite : orderSprites) {
            final Order order = spriteOrders.get(sprite);
            drawingManager.drawOrder(sprite, order, getSize(), graphics);
        }

        drawingManager.drawHamburger(controller.getHamburgerOnAssembly(), getSize(), HALF_RANGE, HAMBURGER_Y_POS_SCALE,
                draggableHamburgerSprites, graphics);

        final List<Patty> cookedPatties = controller.getCookedPatties();
        drawingManager.generateCookedPatties(cookedPatties, PATTIES_X_POS_SCALE, PATTIES_Y_POS_SCALE, draggablePattySprites);

        for (final Sprite sprite : sprites) {
            drawingManager.drawIngredient(sprite, getSize(), controller.getUnlockedIngredients(), graphics);
        }

        for (final Sprite sprite : draggableHamburgerSprites) {
            drawingManager.drawIngredient(sprite, getSize(), controller.getUnlockedIngredients(), graphics);
        }

        for (final Sprite sprite : draggableSprites) {
            drawingManager.drawIngredient(sprite, getSize(), controller.getUnlockedIngredients(), graphics);
        }

        for (final Sprite sprite : draggablePattySprites) {
            drawingManager.drawIngredient(sprite, getSize(), controller.getUnlockedIngredients(), graphics);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showScene() {
        readOrders();

        draggablePattySprites.clear();
        draggableHamburgerSprites.clear();
        final List<IngredientEnum> unlockedIngredients = controller.getUnlockedIngredients();
        final List<Sprite> spritesToUnlock = new ArrayList<>();
        for (final Sprite sprite : sprites) {
            if (unlockedIngredients.contains(sprite.getIngredientType())) {
                spritesToUnlock.add(sprite);
            }
        }
        for (final Sprite sprite : spritesToUnlock) {
            sprites.remove(sprite);
            draggableSprites.add(sprite);
        }
        this.sfxService.playSoundLooped("assembly_ost.wav", DEFAULT_SOUND_VOLUME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideScene() {
        this.sfxService.stopSound("assembly_ost.wav");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void spriteDropped(final Sprite sprite) {
        double pbPositionXScale = sprite.getPbPositionXScale();
        if (SAUCES.contains(sprite.getIngredientType())) {
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
                if (ingredient.getIngredientType().equals(TOP_BUN)) {
                    draggableHamburgerSprites.clear();
                    draggablePattySprites.remove(sprite);
                    draggableSprites.remove(sprite);
                    gameController.switchToScene(SceneType.ORDER_SELECTION);
                }
            }
        }
        draggableHamburgerSprites.clear();
        draggablePattySprites.remove(sprite);
        draggableSprites.remove(sprite);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void spriteClicked(final Sprite sprite) {
        draggableHamburgerSprites.clear();
        draggablePattySprites.remove(sprite);
        draggableSprites.remove(sprite);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void spritePressed(final Sprite sprite) {
    }

    /**
     * Creates the sprites for the orders stored in the model.
     */
    private void readOrders() {
        final List<Order> orders = orderSelectionController.getOrders();
        orderSprites.clear();
        spriteOrders.clear();
        drawingManager.generateOrderSprites(orders, orderSprites, spriteOrders);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BurgerAssemblyViewImpl{"
                + "controller=" + controller
                + ", drawingManager=" + drawingManager
                + ", gameController=" + gameController
                + ", sprites=" + sprites
                + ", draggableSprites=" + draggableSprites
                + ", draggablePattySprites=" + draggablePattySprites
                + ", draggableHamburgerSprites=" + draggableHamburgerSprites
                + ", orderSprites=" + orderSprites
                + ", spriteOrders=" + spriteOrders
                + '}';
    }
}
