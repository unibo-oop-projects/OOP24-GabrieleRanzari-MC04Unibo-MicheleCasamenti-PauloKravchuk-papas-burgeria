package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.model.DegreesOfDonenessEnum;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.model.impl.IngredientImpl;
import it.unibo.papasburgeria.model.impl.PattyImpl;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.Sprite;
import it.unibo.papasburgeria.utils.api.SpriteDropListener;
import it.unibo.papasburgeria.utils.impl.SpriteDragManagerImpl;
import it.unibo.papasburgeria.utils.impl.SpriteImpl;
import org.tinylog.Logger;

import java.awt.Graphics;
import java.awt.Image;
import java.io.Serial;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Manages the GUI for the burger assembly scene in the game.
 */
@Singleton
@SuppressFBWarnings(
        value = { "EI_EXPOSE_REP2", "SE_TRANSIENT_FIELD_NOT_RESTORED" },
        justification = "controller is injected and shared intentionally; views are not serialized at runtime"
)
public class BurgerAssemblyViewImpl extends AbstractBaseView implements SpriteDropListener {
    public static final Set<IngredientEnum> SAUCES = Set.of(
            IngredientEnum.KETCHUP,
            IngredientEnum.MUSTARD,
            IngredientEnum.BBQ,
            IngredientEnum.MAYO
    );

    private static final double INGREDIENTS_X_POS_SCALE = 0.005;
    private static final double INGREDIENTS_Y_POS_SCALE = 0.005;
    private static final double INGREDIENTS_MAX_Y_POS_SCALE = 0.6;
    private static final double SAUCES_X_POS_SCALE = 0.685;
    private static final double SAUCES_Y_POS_SCALE = 0.68;
    private static final double PATTIES_X_POS_SCALE = 0.128;
    private static final double PATTIES_Y_POS_SCALE = 0.77;
    private static final double INGREDIENTS_X_SIZE_SCALE = 0.15;
    private static final double INGREDIENTS_Y_SIZE_SCALE = 0.15;
    private static final double SAUCES_X_SIZE_SCALE = INGREDIENTS_X_SIZE_SCALE / 2;
    private static final double SAUCES_Y_SIZE_SCALE = INGREDIENTS_Y_SIZE_SCALE * 2;
    private static final double SPACING = 0.005;
    private static final double PATTY_SPACING = 0.04;
    private static final double MIN_X_POS_SCALE_TO_DROP = 0.31;
    private static final double MAX_X_POS_SCALE_TO_DROP = 0.55;
    private static final double HAMBURGER_X_POS_SCALE =
            (MIN_X_POS_SCALE_TO_DROP + MAX_X_POS_SCALE_TO_DROP) / 2.0;
    private static final double HAMBURGER_Y_POS_SCALE = 0.71;
    private static final double HAMBURGER_SPACING = 0.04;
    private static final String EXTENSION = ".png";
    private static final String SEPARATOR = "_";
    private static final String BOTTLE_EXTENSION = "_bottle";
    private static final String DROP_EXTENSION = "_drop";
    private static final String BOTTOM_EXTENSION = "_bottom";

    @Serial
    private static final long serialVersionUID = 1L;
    private final transient BurgerAssemblyController controller;
    private final transient List<Sprite> sprites;
    private final transient List<Sprite> draggableSprites;
    private final transient List<Sprite> draggablePattySprites;
    private final transient List<Sprite> draggableHamburgerSprites;
    private final transient Map<String, Image> pattyImages;
    private final transient Map<IngredientEnum, Image> ingredientImages;
    private final transient Image horizontalLock;
    private final transient Image verticalLock;
    private final transient Image grillMark;

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
        draggablePattySprites = new ArrayList<>();
        draggableHamburgerSprites = new ArrayList<>();
        pattyImages = new HashMap<>();
        ingredientImages = new EnumMap<>(IngredientEnum.class);
        Logger.info("BurgerAssembly created");

        super.setStaticBackgroundImage(resourceService.getImage("assembly_background.png"));

        horizontalLock = resourceService.getImage("horizontal_lock.png");
        verticalLock = resourceService.getImage("vertical_lock.png");
        grillMark = resourceService.getImage("patty_grill_mark.png");

        double pbPositionXScale = INGREDIENTS_X_POS_SCALE;
        double pbPositionYScale = INGREDIENTS_Y_POS_SCALE;
        for (final IngredientEnum ingredientType : IngredientEnum.values()) {
            final Image image = resourceService.getImage(ingredientType.getName() + EXTENSION);

            ingredientImages.put(ingredientType, image);

            if (ingredientType == IngredientEnum.PATTY || SAUCES.contains(ingredientType)) {
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

        pbPositionXScale = SAUCES_X_POS_SCALE;
        pbPositionYScale = SAUCES_Y_POS_SCALE;
        for (final IngredientEnum ingredientType : SAUCES) {
            final Image image = resourceService.getImage(ingredientType.getName() + BOTTLE_EXTENSION + EXTENSION);

            final Sprite sprite = new SpriteImpl(image, new IngredientImpl(ingredientType),
                    pbPositionXScale, pbPositionYScale, SAUCES_X_SIZE_SCALE, SAUCES_Y_SIZE_SCALE);

            if (controller.isIngredientUnlocked(ingredientType)) {
                draggableSprites.add(sprite);
            } else {
                sprites.add(sprite);
            }

            pbPositionXScale = pbPositionXScale + SAUCES_X_SIZE_SCALE + SPACING;
        }

        final IngredientEnum ingredientType = IngredientEnum.PATTY;
        for (final DegreesOfDonenessEnum degree : DegreesOfDonenessEnum.values()) {
            final String pattyName = ingredientType.getName() + SEPARATOR + degree.getName() + EXTENSION;
            final String pattyBottomName = ingredientType.getName() + BOTTOM_EXTENSION + SEPARATOR + degree.getName() + EXTENSION;

            final Image image = resourceService.getImage(pattyName);
            pattyImages.put(pattyName, image);
            final Image imageBottom = resourceService.getImage(pattyBottomName);
            pattyImages.put(pattyBottomName, imageBottom);
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
    final void paintComponentDelegate(final Graphics g) {
        final List<Ingredient> hamburgerIngredients = controller.getIngredients();
        drawHamburger(hamburgerIngredients, g);

        final List<Patty> patties = controller.getCookedPatties();
        double pbPositionYScale = PATTIES_Y_POS_SCALE;
        for (final Patty patty : patties) {
            final Sprite sprite = generatePattySprite(patty, PATTIES_X_POS_SCALE, pbPositionYScale);
            sprite.setCloneable(false);
            sprite.setRemovable(false);
            if (!draggablePattySprites.contains(sprite)) {
                draggablePattySprites.add(sprite);
            }
            pbPositionYScale = pbPositionYScale - PATTY_SPACING;
        }

        for (final Sprite sprite : sprites) {
            drawIngredient(sprite, g);
        }

        for (final Sprite sprite : draggableHamburgerSprites) {
            drawIngredient(sprite, g);
        }

        for (final Sprite sprite : draggableSprites) {
            drawIngredient(sprite, g);
        }

        for (final Sprite sprite : draggablePattySprites) {
            drawIngredient(sprite, g);
        }
    }

    /**
     * Draws the hamburger.
     *
     * @param hamburgerIngredients the list of ingredients of the hamburger.
     * @param g the graphics.
     */
    final void drawHamburger(final List<Ingredient> hamburgerIngredients, final Graphics g) {
        double pbPositionYScale = HAMBURGER_Y_POS_SCALE;
        Sprite sprite = null;
        for (final Ingredient ingredient : hamburgerIngredients) {
            final double pbPositionXScale = getPositionXScaleFromAccuracy(ingredient.getPlacementAccuracy());
            if (ingredient instanceof Patty) {
                sprite = generatePattySprite((Patty) ingredient, pbPositionXScale, pbPositionYScale);
            } else {
                final Image image = ingredientImages.get(ingredient.getIngredientType());
                sprite = new SpriteImpl(image, ingredient,
                        pbPositionXScale, pbPositionYScale,
                        INGREDIENTS_X_SIZE_SCALE, INGREDIENTS_Y_SIZE_SCALE);
            }

            if (SAUCES.contains(ingredient.getIngredientType())) {
                sprite.setPbPositionYScale(pbPositionYScale + HAMBURGER_SPACING);
            }

            if (!draggableHamburgerSprites.contains(sprite)) {
                sprite.draw(getSize(), g);
            }

            pbPositionYScale = pbPositionYScale - HAMBURGER_SPACING;
        }
        if (sprite != null && draggableHamburgerSprites.isEmpty()) {
            sprite.setCloneable(false);
            sprite.setRemovable(true);
            draggableHamburgerSprites.add(sprite);
        }
    }

    /**
     * Draws a sprite.
     *
     * @param sprite the sprite to draw.
     * @param g the graphics.
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
     * Creates a Sprite for the Patty.
     *
     * @param patty the patty.
     * @param pbPositionXScale the pbPositionXScale.
     * @param pbPositionYScale the pbPositionYScale.
     *
     * @return the Sprite.
     */
    final Sprite generatePattySprite(final Patty patty, final double pbPositionXScale, final double pbPositionYScale) {
        double topCookLevel = patty.getTopCookLevel();
        DegreesOfDonenessEnum topDegree = DegreesOfDonenessEnum.calculateDegree(topCookLevel);

        final double bottomCookLevel = patty.getBottomCookLevel();
        DegreesOfDonenessEnum bottomDegree = DegreesOfDonenessEnum.calculateDegree(bottomCookLevel);

        if (patty.isFlipped()) {
            final DegreesOfDonenessEnum temp = topDegree;
            topDegree = bottomDegree;
            bottomDegree = temp;

            topCookLevel = bottomCookLevel;
        }

        final String pattyName = ((Ingredient) patty).getIngredientType().getName()
                + SEPARATOR + topDegree.getName() + EXTENSION;
        final Sprite pattySprite = new SpriteImpl(pattyImages.get(pattyName), (Ingredient) patty,
                pbPositionXScale, pbPositionYScale, INGREDIENTS_X_SIZE_SCALE, INGREDIENTS_Y_SIZE_SCALE);

        if (topDegree != bottomDegree) {
            final String pattyBottomName = ((Ingredient) patty).getIngredientType().getName()
                    + BOTTOM_EXTENSION + SEPARATOR + bottomDegree.getName() + EXTENSION;
            pattySprite.addImage(pattyImages.get(pattyBottomName));
        }

        if (topCookLevel > PattyImpl.MIN_COOK_LEVEL) {
            pattySprite.addImage(grillMark);
        }

        return pattySprite;
    }

    /**
     * calculates the accuracy given the pbPositionXScale.
     *
     * @param pbPositionXScale the pbPositionXScale of the ingredient.
     *
     * @return the placement accuracy.
     */
    public double calculateAccuracy(final double pbPositionXScale) {
        final double halfRange = (MAX_X_POS_SCALE_TO_DROP - MIN_X_POS_SCALE_TO_DROP) / 2.0;
        final double difference = pbPositionXScale - HAMBURGER_X_POS_SCALE;
        final double accuracy = difference / halfRange;
        return Math.max(IngredientImpl.MAX_LEFT_ACCURACY, Math.min(IngredientImpl.MAX_RIGHT_ACCURACY, accuracy));
    }

    /**
     * calculates the pbPositionXScale given the accuracy.
     *
     * @param accuracy the placement accuracy of the ingredient.
     *
     * @return the pbPositionXScale.
     */
    public double getPositionXScaleFromAccuracy(final double accuracy) {
        final double boundedAccuracy = Math.max(IngredientImpl.MAX_LEFT_ACCURACY,
                Math.min(IngredientImpl.MAX_RIGHT_ACCURACY, accuracy));
        final double halfRange = (MAX_X_POS_SCALE_TO_DROP - MIN_X_POS_SCALE_TO_DROP) / 2.0;
        return HAMBURGER_X_POS_SCALE + (boundedAccuracy * halfRange);
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

    /**
     * @inheritDoc
     */
    @Override
    public void spriteDropped(final Sprite sprite) {
        double pbPositionXScale = sprite.getPbPositionXScale();
        if (SAUCES.contains(sprite.getIngredientType())) {
            pbPositionXScale = pbPositionXScale - SAUCES_X_SIZE_SCALE / 2;
        }
        if (sprite.isRemovable()) {
            final Ingredient ingredient = sprite.getIngredient();
            if (pbPositionXScale < MIN_X_POS_SCALE_TO_DROP || pbPositionXScale > MAX_X_POS_SCALE_TO_DROP) {
                if (ingredient instanceof Patty patty && controller.addCookedPatty(patty)) {
                    draggablePattySprites.clear();
                }
                controller.removeLastIngredient();
            } else {
                ingredient.setPlacementAccuracy(calculateAccuracy(pbPositionXScale));
                controller.removeLastIngredient();
                if (ingredient instanceof Patty) {
                    draggablePattySprites.clear();
                }
                controller.addIngredient(ingredient);
            }
        }
        if (pbPositionXScale > MIN_X_POS_SCALE_TO_DROP && pbPositionXScale < MAX_X_POS_SCALE_TO_DROP && !sprite.isRemovable()) {
            final Ingredient ingredient = sprite.getIngredient();
            ingredient.setPlacementAccuracy(calculateAccuracy(pbPositionXScale));

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
}
