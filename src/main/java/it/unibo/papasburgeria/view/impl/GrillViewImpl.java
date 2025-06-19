package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.GrillController;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.model.impl.PattyImpl;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.Sprite;
import it.unibo.papasburgeria.utils.api.SpriteDropListener;
import it.unibo.papasburgeria.utils.impl.DrawingManagerImpl;
import it.unibo.papasburgeria.utils.impl.SpriteDragManagerImpl;
import it.unibo.papasburgeria.utils.impl.SpriteImpl;
import org.tinylog.Logger;

import java.awt.Graphics;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;

/**
 * Manages the GUI for the grill scene in the game.
 */
@Singleton
@SuppressFBWarnings(
        value = {"EI_EXPOSE_REP2", "SE_TRANSIENT_FIELD_NOT_RESTORED"},
        justification = "controller is injected and shared intentionally; views are not serialized at runtime"
)
public class GrillViewImpl extends AbstractBaseView implements SpriteDropListener {
    public static final double MIN_X_POS_SCALE_TO_DROP_ON_GRILL = 0.237;
    public static final double MAX_X_POS_SCALE_TO_DROP_ON_GRILL = 0.742;
    public static final double MIN_Y_POS_SCALE_TO_DROP_ON_GRILL = 0.203;
    public static final double MAX_Y_POS_SCALE_TO_DROP_ON_GRILL = 0.981;
    public static final double PATTY_ON_GRILL_X_POS = MIN_X_POS_SCALE_TO_DROP_ON_GRILL + 0.02;
    public static final double PATTY_ON_GRILL_Y_POS = MIN_Y_POS_SCALE_TO_DROP_ON_GRILL + 0.02;
    public static final double GRILL_X_SPACING = 0.05;
    public static final double GRILL_Y_SPACING = 0.03;

    private static final double MIN_X_POS_SCALE_TO_DELETE = 0.025;
    private static final double MAX_X_POS_SCALE_TO_DELETE = 0.192;
    private static final double MIN_Y_POS_SCALE_TO_DELETE = 0.203;
    private static final double MAX_Y_POS_SCALE_TO_DELETE = 0.333;
    private static final double COOKED_PATTIES_X_POS_SCALE = 0.9;
    private static final double COOKED_PATTIES_Y_POS_SCALE = 0.9;

    @Serial
    private static final long serialVersionUID = 1L;
    private final transient GrillController controller;
    private final transient DrawingManagerImpl drawingManager;
    private final transient List<Sprite> draggableRawPatties;
    private final transient List<Sprite> draggableCookedPatties;
    private final transient List<Sprite> draggablePattiesOnGrill;

    /**
     * Default constructor, creates and initializes the UI elements.
     *
     * @param resourceService the service that handles resource obtainment.
     * @param controller      the grill controller.
     *  @param drawingManager the manager for drawing various things.
     */
    @Inject
    public GrillViewImpl(final ResourceService resourceService, final GrillController controller,
                         final DrawingManagerImpl drawingManager) {
        this.controller = controller;
        this.drawingManager = drawingManager;
        draggableRawPatties = new ArrayList<>();
        draggableCookedPatties = new ArrayList<>();
        draggablePattiesOnGrill = new ArrayList<>();

        if (DEBUG_MODE) {
            Logger.info("Grill created");
        }

        final double pbPositionXScale = 0.028;
        final double pbPositionYScale = 0.78;
        final double pbSizeXScale = 0.15;
        final double pbSizeYScale = 0.15;

        super.setStaticBackgroundImage(resourceService.getImage("grill_background.png"));

        final Sprite pattySprite = new SpriteImpl(resourceService.getImage("patty_raw.png"), new PattyImpl(),
                pbPositionXScale, pbPositionYScale, pbSizeXScale, pbSizeYScale);
        pattySprite.setDraggable(false);
        draggableRawPatties.add(pattySprite);

        new SpriteDragManagerImpl(this, draggableRawPatties, this);
        new SpriteDragManagerImpl(this, draggableCookedPatties, this);
        new SpriteDragManagerImpl(this, draggablePattiesOnGrill, this);
    }

    /**
     * @inheritDoc
     */
    @Override
    void update(final double delta) {
        controller.cookPattiesOnGrill();
    }

    /**
     * @inheritDoc
     */
    @Override
    void paintComponentDelegate(final Graphics graphics) {
        final List<Patty> cookedPatties = controller.getCookedPatties();
        drawingManager.drawCookedPatties(cookedPatties, COOKED_PATTIES_X_POS_SCALE, COOKED_PATTIES_Y_POS_SCALE,
                draggablePattiesOnGrill);

        for (final Sprite sprite : draggableRawPatties) {
            sprite.draw(getSize(), graphics);
        }
        for (final Sprite sprite : draggableCookedPatties) {
            sprite.draw(getSize(), graphics);
        }

        final Patty[][] pattiesOnGrill = controller.getPattiesOnGrill();
        drawingManager.drawPattiesOnGrill(pattiesOnGrill, draggablePattiesOnGrill);

        for (final Sprite sprite : draggablePattiesOnGrill) {
            sprite.draw(getSize(), graphics);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        if (DEBUG_MODE) {
            Logger.info("Grill shown");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        if (DEBUG_MODE) {
            Logger.info("Grill hidden");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void spriteDropped(final Sprite sprite) {
        final double pbPositionXScale = sprite.getPbPositionXScale();
        final double pbPositionYScale = sprite.getPbPositionYScale();
        final Patty patty = (Patty) sprite.getIngredient();

        draggableRawPatties.remove(sprite);

        if (pbPositionXScale > MIN_X_POS_SCALE_TO_DROP_ON_GRILL
                && pbPositionXScale < MAX_X_POS_SCALE_TO_DROP_ON_GRILL
                && pbPositionYScale > MIN_Y_POS_SCALE_TO_DROP_ON_GRILL
                && pbPositionYScale < MAX_Y_POS_SCALE_TO_DROP_ON_GRILL) {
            if (controller.addPattyOnGrill(patty, pbPositionXScale, pbPositionYScale)) {
                draggablePattiesOnGrill.add(sprite);
            }
        } else if (patty.getBottomCookLevel() > 0.0 && patty.getTopCookLevel() > 0.0
                && pbPositionXScale > MAX_X_POS_SCALE_TO_DROP_ON_GRILL) {
            if (controller.addCookedPatty(patty)) {
                draggableCookedPatties.add(sprite);
            }
        } else if (pbPositionXScale >= MIN_X_POS_SCALE_TO_DELETE
                && pbPositionXScale <= MAX_X_POS_SCALE_TO_DELETE
                && pbPositionYScale >= MIN_Y_POS_SCALE_TO_DELETE
                && pbPositionYScale <= MAX_Y_POS_SCALE_TO_DELETE) {
            if (DEBUG_MODE) {
                Logger.info("Patty deleted");
            }
        } else {
            draggableCookedPatties.add(sprite);
        }
    }
}
