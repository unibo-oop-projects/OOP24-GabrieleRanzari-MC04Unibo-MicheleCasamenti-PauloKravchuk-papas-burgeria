package it.unibo.papasburgeria.view.impl.components;

import com.google.inject.Inject;
import it.unibo.papasburgeria.model.DegreesOfDonenessEnum;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Ingredient;
import it.unibo.papasburgeria.model.api.Order;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.model.impl.IngredientImpl;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.view.api.components.DrawingManager;
import it.unibo.papasburgeria.view.api.components.Sprite;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static it.unibo.papasburgeria.model.IngredientEnum.PATTY;
import static it.unibo.papasburgeria.model.impl.IngredientImpl.MAX_LEFT_ACCURACY;
import static it.unibo.papasburgeria.model.impl.IngredientImpl.MAX_RIGHT_ACCURACY;
import static it.unibo.papasburgeria.model.impl.PattyImpl.MIN_COOK_LEVEL;
import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.HAMBURGER_SPACING;
import static it.unibo.papasburgeria.view.impl.BurgerAssemblyViewImpl.HAMBURGER_X_POS_SCALE;
import static it.unibo.papasburgeria.view.impl.GrillViewImpl.GRILL_X_SPACING;
import static it.unibo.papasburgeria.view.impl.GrillViewImpl.GRILL_Y_SPACING;
import static it.unibo.papasburgeria.view.impl.GrillViewImpl.PATTY_ON_GRILL_X_POS;
import static it.unibo.papasburgeria.view.impl.GrillViewImpl.PATTY_ON_GRILL_Y_POS;

/**
 * Implementation of DrawingManager.
 *
 * <p>
 * See {@link DrawingManager} for interface details.
 */
public class DrawingManagerImpl implements DrawingManager {
    /**
     * Defines the file extension.
     */
    public static final String EXTENSION = ".png";
    /**
     * Defines the file separator.
     */
    public static final String SEPARATOR = "_";
    /**
     * Defines the file name for the bottles.
     */
    public static final String BOTTLE_EXTENSION = "_bottle";
    /**
     * Defines the file name for the bottom of patties.
     */
    public static final String BOTTOM_EXTENSION = "_bottom";

    /**
     * Defines the width of the ingredients.
     */
    public static final double INGREDIENTS_X_SIZE_SCALE = 0.15;
    /**
     * Defines the height of the ingredients.
     */
    public static final double INGREDIENTS_Y_SIZE_SCALE = 0.15;
    /**
     * Defines the width of the orders.
     */
    public static final double ORDER_X_SIZE_SCALE = 0.1199;
    /**
     * Defines the height of the orders.
     */
    public static final double ORDER_Y_SIZE_SCALE = 0.4385;
    /**
     * Defines the spacing between patties.
     */
    public static final double PATTY_SPACING = 0.04;
    /**
     * Defines the ingredient of the orders.
     */
    public static final Ingredient ORDER_INGREDIENT = new IngredientImpl(IngredientEnum.TOP_BUN);

    private static final double ORDER_X_POS_SCALE = 0.68;
    private static final double ORDER_Y_POS_SCALE = 0.02;
    private static final double ORDER_NUMBER_X_POS_SCALE = 0.005;
    private static final double ORDER_NUMBER_Y_POS_SCALE = 0.0475;
    private static final double ORDER_INGREDIENT_X_SIZE_SCALE = ORDER_X_SIZE_SCALE / 4;
    private static final double ORDER_INGREDIENT_Y_SIZE_SCALE = ORDER_X_SIZE_SCALE / 4;
    private static final double ORDER_INGREDIENT_X_POS_SCALE = 0.045;
    private static final double ORDER_INGREDIENT_Y_POS_SCALE = 0.408;
    private static final double ORDER_INGREDIENT_SPACING = 0.0312;

    private static final Font DEFAULT_FONT = new Font("Comic Sans MS", Font.BOLD, 25);

    private final transient Map<String, Image> pattyImages;
    private final transient Map<IngredientEnum, Image> ingredientImages;
    private final transient Image horizontalLock;
    private final transient Image verticalLock;
    private final transient Image grillMark;
    private final transient Image orderCard;

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
        orderCard = resourceService.getImage("order.png");

        for (final IngredientEnum ingredientType : IngredientEnum.values()) {
            final Image image = resourceService.getImage(ingredientType.getName() + EXTENSION);
            ingredientImages.put(ingredientType, image);
        }

        final IngredientEnum ingredientType = PATTY;
        for (final DegreesOfDonenessEnum degree : DegreesOfDonenessEnum.values()) {
            final String pattyName =
                    ingredientType.getName() + SEPARATOR + degree.getName() + EXTENSION;
            final String pattyBottomName =
                    ingredientType.getName() + BOTTOM_EXTENSION + SEPARATOR + degree.getName() + EXTENSION;

            final Image image = resourceService.getImage(pattyName);
            pattyImages.put(pattyName, image);
            final Image imageBottom = resourceService.getImage(pattyBottomName);
            pattyImages.put(pattyBottomName, imageBottom);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void drawHamburger(
            final Hamburger hamburger,
            final Dimension frameSize,
            final double bottomBunXPosScale,
            final double bottomBunYPosScale,
            final List<Sprite> draggableHamburgerSprites,
            final Graphics graphics
    ) {
        final List<Ingredient> hamburgerIngredients = hamburger.getIngredients();
        double pbPositionYScale = bottomBunYPosScale;
        Sprite sprite = null;
        for (final Ingredient ingredient : hamburgerIngredients) {
            final double pbPositionXScale =
                    getPositionXScaleFromAccuracy(ingredient.getPlacementAccuracy(), bottomBunXPosScale);
            if (ingredient instanceof Patty) {
                sprite = generatePattySprite((Patty) ingredient, pbPositionXScale, pbPositionYScale,
                        INGREDIENTS_X_SIZE_SCALE, INGREDIENTS_Y_SIZE_SCALE);
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
     * {@inheritDoc}
     */
    @Override
    public final void drawIngredient(
            final Sprite sprite,
            final Dimension frameSize,
            final List<IngredientEnum> unlockedIngredients,
            final Graphics graphics
    ) {
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
     * {@inheritDoc}
     */
    @Override
    public void drawOrder(
            final Sprite sprite,
            final Order order,
            final Dimension frameSize,
            final Graphics graphics
    ) {
        sprite.draw(frameSize, graphics);
        if (Objects.isNull(order)) {
            return;
        }

        final int numberXPosition = sprite.calculateX(frameSize.width)
                + (int) (frameSize.getWidth() * ORDER_NUMBER_X_POS_SCALE);
        final int numberYPosition = sprite.calculateY(frameSize.height)
                + (int) (frameSize.getHeight() * ORDER_NUMBER_Y_POS_SCALE);
        graphics.setFont(DEFAULT_FONT);
        graphics.drawString("0" + order.getOrderNumber(), numberXPosition, numberYPosition);

        double ingredientPbPositionYScale = ORDER_INGREDIENT_Y_POS_SCALE;
        for (final Ingredient ingredient : order.getHamburger().getIngredients()) {
            final Sprite ingredientSprite;
            if (ingredient instanceof Patty) {
                ingredientSprite = generatePattySprite((Patty) ingredient,
                        sprite.getPbPositionXScale() + ORDER_INGREDIENT_X_POS_SCALE,
                        sprite.getPbPositionYScale() + ingredientPbPositionYScale,
                        ORDER_INGREDIENT_X_SIZE_SCALE, ORDER_INGREDIENT_Y_SIZE_SCALE);
            } else {
                final Image image = ingredientImages.get(ingredient.getIngredientType());
                ingredientSprite = new SpriteImpl(image, ingredient,
                        sprite.getPbPositionXScale() + ORDER_INGREDIENT_X_POS_SCALE,
                        sprite.getPbPositionYScale() + ingredientPbPositionYScale,
                        ORDER_INGREDIENT_X_SIZE_SCALE, ORDER_INGREDIENT_Y_SIZE_SCALE);
            }
            ingredientSprite.draw(frameSize, graphics);

            ingredientPbPositionYScale = ingredientPbPositionYScale - ORDER_INGREDIENT_SPACING;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void generateCookedPatties(
            final List<Patty> cookedPatties,
            final double pbPositionXScale,
            final double initialPbPositionYScale,
            final List<Sprite> draggablePattySprites
    ) {
        double pbPositionYScale = initialPbPositionYScale;
        for (final Patty patty : cookedPatties) {
            final Sprite sprite = generatePattySprite(patty, pbPositionXScale, pbPositionYScale,
                    INGREDIENTS_X_SIZE_SCALE, INGREDIENTS_Y_SIZE_SCALE);
            sprite.setCloneable(false);
            sprite.setRemovable(false);
            if (!draggablePattySprites.contains(sprite)) {
                draggablePattySprites.add(sprite);
            }
            pbPositionYScale = pbPositionYScale - PATTY_SPACING;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void generatePattiesOnGrill(
            final Patty[][] pattiesOnGrill,
            final List<Sprite> draggablePattiesOnGrill
    ) {
        double pbPositionXScale = PATTY_ON_GRILL_X_POS;
        double pbPositionYScale = PATTY_ON_GRILL_Y_POS + GRILL_Y_SPACING;
        for (final Patty[] pattyRow : pattiesOnGrill) {
            for (final Patty patty : pattyRow) {
                if (patty != null) {
                    final Sprite sprite = generatePattySprite(patty, pbPositionXScale, pbPositionYScale,
                            INGREDIENTS_X_SIZE_SCALE, INGREDIENTS_Y_SIZE_SCALE);
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
     * {@inheritDoc}
     */
    @Override
    public void generateOrderSprites(
            final List<Order> orders,
            final List<Sprite> draggableOrderSprites,
            final Map<Sprite, Order> spriteOrders
    ) {
        double pbPositionXScale = ORDER_X_POS_SCALE;
        final double orderSpacing =
                (1.0 - ORDER_X_POS_SCALE - ORDER_X_SIZE_SCALE) / (orders.size() - 1);
        for (final Order order : orders) {
            final Sprite sprite =
                    new SpriteImpl(orderCard, ORDER_INGREDIENT,
                            pbPositionXScale, ORDER_Y_POS_SCALE,
                            ORDER_X_SIZE_SCALE, ORDER_Y_SIZE_SCALE);
            sprite.setDraggable(true);
            sprite.setCloneable(false);
            sprite.setRemovable(true);
            if (!draggableOrderSprites.contains(sprite)) {
                draggableOrderSprites.add(sprite);
            }
            spriteOrders.put(sprite, order);

            pbPositionXScale = pbPositionXScale + orderSpacing;
        }
    }

    /**
     * Calculates the x position in scale given the accuracy.
     *
     * @param accuracy  the placement accuracy of the ingredient
     * @param halfRange the x position to be centered
     * @return the x position in scale
     */
    private double getPositionXScaleFromAccuracy(
            final double accuracy,
            final double halfRange
    ) {
        final double boundedAccuracy = Math.max(MAX_LEFT_ACCURACY,
                Math.min(MAX_RIGHT_ACCURACY, accuracy));
        return HAMBURGER_X_POS_SCALE + (boundedAccuracy * halfRange);
    }

    /**
     * Creates a Sprite for the Patty.
     *
     * @param patty            the patty
     * @param pbPositionXScale the x position in scale
     * @param pbPositionYScale the y position in scale
     * @param pbSizeXScale     the x size in scale
     * @param pbSizeYScale     the y size in scale
     * @return the sprite
     */
    private Sprite generatePattySprite(
            final Patty patty,
            final double pbPositionXScale,
            final double pbPositionYScale,
            final double pbSizeXScale,
            final double pbSizeYScale
    ) {
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
                pbPositionXScale, pbPositionYScale, pbSizeXScale, pbSizeYScale);

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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DrawingManagerImpl{"
                + "pattyImages=" + pattyImages
                + ", ingredientImages=" + ingredientImages
                + ", horizontalLock=" + horizontalLock
                + ", verticalLock=" + verticalLock
                + ", grillMark=" + grillMark
                + ", orderCard=" + orderCard
                + '}';
    }
}
