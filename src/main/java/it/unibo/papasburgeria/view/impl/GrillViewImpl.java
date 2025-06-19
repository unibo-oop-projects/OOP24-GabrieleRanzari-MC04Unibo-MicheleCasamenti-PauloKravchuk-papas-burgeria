package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.GrillController;
import it.unibo.papasburgeria.model.impl.PattyImpl;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.Sprite;
import it.unibo.papasburgeria.utils.api.SpriteDropListener;
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
    @Serial
    private static final long serialVersionUID = 1L;
    private final transient Sprite patty;
    private final transient GrillController controller;
    private final transient List<Sprite> patties;


    /**
     * Default constructor, creates and initializes the UI elements.
     *
     * @param resourceService the service that handles resource obtainment.
     * @param controller      the grill controller.
     */
    @Inject
    public GrillViewImpl(final ResourceService resourceService, final GrillController controller) {
        this.controller = controller;
        patties = new ArrayList<>();
        if (DEBUG_MODE) {
            Logger.info("Grill created");
        }

        final double pbPositionXScale = 0.028;
        final double pbPositionYScale = 0.78;
        final double pbSizeXScale = 0.15;
        final double pbSizeYScale = 0.15;

        super.setStaticBackgroundImage(resourceService.getImage("grill_background.png"));

        patty = new SpriteImpl(resourceService.getImage("patty_raw.png"), new PattyImpl(),
                pbPositionXScale, pbPositionYScale, pbSizeXScale, pbSizeYScale);
        patties.add(patty);

        new SpriteDragManagerImpl(this, patties, this);
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
    void paintComponentDelegate(final Graphics g) {
        for (final Sprite pattySprite : patties) {
            drawPatty(pattySprite, g);
        }
    }

    /**
     * Draws a sprite.
     *
     * @param pattySprite the sprite of the patty to draw
     * @param g           the graphics
     */
    final void drawPatty(final Sprite pattySprite, final Graphics g) {
        pattySprite.draw(getSize(), g);
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
        if (DEBUG_MODE) {
            Logger.info("Patty dropped");
        }
    }
}
