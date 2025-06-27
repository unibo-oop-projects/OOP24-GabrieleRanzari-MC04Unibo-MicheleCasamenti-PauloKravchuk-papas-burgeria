package it.unibo.papasburgeria.view.impl.components;

import com.google.inject.Inject;
import it.unibo.papasburgeria.model.DegreesOfDonenessEnum;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.view.api.components.DrawingManager;
import it.unibo.papasburgeria.view.api.components.Sprite;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.unibo.papasburgeria.model.IngredientEnum.PATTY;
import static it.unibo.papasburgeria.model.impl.IngredientImpl.MAX_LEFT_ACCURACY;
import static it.unibo.papasburgeria.model.impl.IngredientImpl.MAX_RIGHT_ACCURACY;
import static it.unibo.papasburgeria.model.impl.PattyImpl.MIN_COOK_LEVEL;
import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.HAMBURGER_SPACING;
import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.HAMBURGER_X_POS_SCALE;
import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.HAMBURGER_Y_POS_SCALE;
import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.MAX_X_POS_SCALE_TO_DROP_ON_HAMBURGER;
import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.MIN_X_POS_SCALE_TO_DROP_ON_HAMBURGER;
import static it.unibo.papasburgeria.view.impl.GrillViewImpl.GRILL_X_SPACING;
import static it.unibo.papasburgeria.view.impl.GrillViewImpl.GRILL_Y_SPACING;
import static it.unibo.papasburgeria.view.impl.GrillViewImpl.PATTY_ON_GRILL_X_POS;
import static it.unibo.papasburgeria.view.impl.GrillViewImpl.PATTY_ON_GRILL_Y_POS;

/**
 * @inheritDoc
 */
public class DrawingManagerImpl implements DrawingManager {
    public static final String EXTENSION = ".png";
    public static final String SEPARATOR = "_";
    public static final String BOTTLE_EXTENSION = "_bottle";
    public static final String BOTTOM_EXTENSION = "_bottom";
    public static final double INGREDIENTS_X_SIZE_SCALE = 0.15;
    public static final double INGREDIENTS_Y_SIZE_SCALE = 0.15;
    public static final double PATTY_SPACING = 0.04;

    private final transient Map<String, Image> pattyImages;
    private final transient Map<IngredientEnum, Image> ingredientImages;
    private final transient Image horizontalLock;
    private final transient Image verticalLock;
    private final transient Image grillMark;

    /**
     * Default constructor, reeds and stores the images needed to draw.
     *
     * @param resourceService the resource service
     */
    @Inject
    DrawingManagerImpl(final ResourceService resourceService) {
        pattyImages = new HashMap<>();
        ingredientImages = new EnumMap<>(IngredientEnum.class);
        horizontalLock = resourceService.getImage("horizontal_lock.png");
        verticalLock = resourceService.getImage("vertical_lock.png");
        grillMark = resourceService.getImage("patty_grill_mark.png");

        for (final IngredientEnum ingredientType : IngredientEnum.values()) {
            final Image image = resourceService.getImage(ingredientType.getName() + EXTENSION);
            ingredientImages.put(ingredientType, image);
        }

        final IngredientEnum ingredientType = PATTY;
        for (final DegreesOfDonenessEnum degree : DegreesOfDonenessEnum.values()) {
            final String pattyName = ingredientType.getName() + SEPARATOR + degree.getName() + EXTENSION;
            final String pattyBottomName = ingredientType.getName() + BOTTOM_EXTENSION + SEPARATOR + degree.getName() + EXTENSION;

            final Image image = resourceService.getImage(pattyName);
            pattyImages.put(pattyName, image);
            final Image imageBottom = resourceService.getImage(pattyBottomName);
            pattyImages.put(pattyBottomName, imageBottom);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public final Sprite generatePattySprite(final Patty patty, final double pbPositionXScale, final double pbPositionYScale) {
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

        if (topCookLevel > MIN_COOK_LEVEL) {
            pattySprite.addImage(grillMark);
        }

        return pattySprite;
    }

    /**
     * @inheritDoc
     */
    @Override
    public final void drawHamburger(final List<Ingredient> hamburgerIngredients, final Dimension frameSize,
                                    final List<Sprite> draggableHamburgerSprites, final Graphics graphics) {
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

            if (IngredientEnum.SAUCES.contains(ingredient.getIngredientType())) {
                sprite.setPbPositionYScale(pbPositionYScale + HAMBURGER_SPACING);
            }

            if (!draggableHamburgerSprites.contains(sprite)) {
                sprite.draw(frameSize, graphics);
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
     * @inheritDoc
     */
    @Override
    public final void drawIngredient(final Sprite sprite, final Dimension frameSize,
                                     final List<IngredientEnum> unlockedIngredients, final Graphics graphics) {
        final int frameWidth = frameSize.width;
        final int frameHeight = frameSize.height;
        sprite.draw(frameSize, graphics);

        if (!unlockedIngredients.contains(sprite.getIngredientType())) {
            Image lock = horizontalLock;
            if (sprite.calculateWidth(frameWidth) < sprite.calculateHeight(frameHeight)) {
                lock = verticalLock;
            }
            graphics.drawImage(lock, sprite.calculateX(frameWidth), sprite.calculateY(frameHeight),
                    sprite.calculateWidth(frameWidth), sprite.calculateHeight(frameHeight), null);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public final void generateCookedPatties(final List<Patty> cookedPatties, final double pbPositionXScale,
                                            final double initialPbPositionYScale, final List<Sprite> draggablePattySprites) {
        double pbPositionYScale = initialPbPositionYScale;
        for (final Patty patty : cookedPatties) {
            final Sprite sprite = generatePattySprite(patty, pbPositionXScale, pbPositionYScale);
            sprite.setCloneable(false);
            sprite.setRemovable(false);
            if (!draggablePattySprites.contains(sprite)) {
                draggablePattySprites.add(sprite);
            }
            pbPositionYScale = pbPositionYScale - PATTY_SPACING;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public final void generatePattiesOnGrill(final Patty[][] pattiesOnGrill, final List<Sprite> draggablePattiesOnGrill) {
        double pbPositionXScale = PATTY_ON_GRILL_X_POS;
        double pbPositionYScale = PATTY_ON_GRILL_Y_POS + GRILL_Y_SPACING;
        for (final Patty[] pattyRow : pattiesOnGrill) {
            for (final Patty patty : pattyRow) {
                if (patty != null) {
                    final Sprite sprite = generatePattySprite(patty, pbPositionXScale, pbPositionYScale);
                    sprite.setCloneable(false);
                    sprite.setRemovable(true);
                    sprite.setDraggable(false);
                    if (!draggablePattiesOnGrill.contains(sprite)) {
                        draggablePattiesOnGrill.add(sprite);
                    }
                }
                pbPositionXScale = pbPositionXScale + INGREDIENTS_X_SIZE_SCALE + GRILL_X_SPACING;
            }
            pbPositionYScale = pbPositionYScale + INGREDIENTS_Y_SIZE_SCALE + GRILL_Y_SPACING;
            pbPositionXScale = PATTY_ON_GRILL_X_POS;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public double getPositionXScaleFromAccuracy(final double accuracy) {
        final double boundedAccuracy = Math.max(MAX_LEFT_ACCURACY,
                Math.min(MAX_RIGHT_ACCURACY, accuracy));
        final double halfRange = (MAX_X_POS_SCALE_TO_DROP_ON_HAMBURGER - MIN_X_POS_SCALE_TO_DROP_ON_HAMBURGER) / 2.0;
        return HAMBURGER_X_POS_SCALE + (boundedAccuracy * halfRange);
    }
}
