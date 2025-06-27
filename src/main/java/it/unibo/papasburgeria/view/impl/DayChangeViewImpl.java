package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import it.unibo.papasburgeria.controller.api.DayChangeController;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.view.impl.components.ScalableLayoutImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleConstraintImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleImpl;
import org.tinylog.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serial;
import java.util.List;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;
import static it.unibo.papasburgeria.utils.impl.DrawingManagerImpl.EXTENSION;
import static it.unibo.papasburgeria.utils.impl.DrawingManagerImpl.INGREDIENTS_X_SIZE_SCALE;
import static it.unibo.papasburgeria.utils.impl.DrawingManagerImpl.INGREDIENTS_Y_SIZE_SCALE;

/**
 * Manages the GUI for the day changing scene in the game.
 */
public class DayChangeViewImpl extends AbstractBaseView {
    public static final String VIEW_NAME = "Day Change";

    private static final double DAY_LABEL_X_POS = 0.5;
    private static final double DAY_LABEL_Y_POS = 0.15;
    private static final double DAY_LABEL_X_SIZE = 0.2;
    private static final double DAY_LABEL_Y_SIZE = 0.1;
    private static final double DAY_LABEL_ORIGIN = 0.5;

    private static final double UNLOCKED_INGREDIENTS_LABEL_X_POS = 0.5;
    private static final double UNLOCKED_INGREDIENTS_LABEL_Y_POS = 0.25;
    private static final double UNLOCKED_INGREDIENTS_LABEL_X_SIZE = 0.5;
    private static final double UNLOCKED_INGREDIENTS_LABEL_Y_SIZE = 0.1;
    private static final double UNLOCKED_INGREDIENTS_LABEL_ORIGIN = 0.5;

    private static final double UNLOCKED_INGREDIENTS_IMAGE_X_POS = 0.2;
    private static final double UNLOCKED_INGREDIENTS_IMAGE_Y_POS = 0.1 + UNLOCKED_INGREDIENTS_LABEL_Y_POS;
    private static final double UNLOCKED_INGREDIENTS_IMAGE_X_SIZE = INGREDIENTS_X_SIZE_SCALE;
    private static final double UNLOCKED_INGREDIENTS_IMAGE_Y_SIZE = INGREDIENTS_Y_SIZE_SCALE;
    private static final double UNLOCKED_INGREDIENTS_IMAGE_ORIGIN = 0.0;
    private static final double UNLOCKED_INGREDIENTS_IMAGE_X_SPACING = 0.05;
    private static final double UNLOCKED_INGREDIENTS_IMAGE_Y_SPACING = 0.05;

    private static final double NEW_DAY_BUTTON_X_POS = 0.5;
    private static final double NEW_DAY_BUTTON_Y_POS = 0.9;
    private static final double NEW_DAY_BUTTON_X_SIZE = 0.15;
    private static final double NEW_DAY_BUTTON_Y_SIZE = 0.075;
    private static final double NEW_DAY_BUTTON_ORIGIN = 0.5;

    private static final String DEFAULT_FONT_NAME = "Comic Sans MS";
    private static final int DEFAULT_FONT_SIZE = 40;
    private static final Font DEFAULT_FONT = new Font(DEFAULT_FONT_NAME, Font.BOLD, DEFAULT_FONT_SIZE);

    @Serial
    private static final long serialVersionUID = 1L;
    private final transient ResourceService resourceService;
    private final transient GameController gameController;
    private final transient DayChangeController controller;

    /**
     * Default constructor, creates and initializes the GUI elements.
     *
     * @param resourceService the service that handles resource obtainment
     * @param gameController  the controller for the game
     * @param controller      the controller for the day change
     */
    @Inject
    public DayChangeViewImpl(final ResourceService resourceService, final GameController gameController,
                             final DayChangeController controller) {
        this.resourceService = resourceService;
        this.gameController = gameController;
        this.controller = controller;
        if (DEBUG_MODE) {
            Logger.info(VIEW_NAME + " created");
        }

        super.setStaticBackgroundImage(resourceService.getImage("day_change_background.png"));

        resetIfNeeded(gameController.getCurrentDayNumber());
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

    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        this.resetIfNeeded(gameController.getCurrentDayNumber());
        if (DEBUG_MODE) {
            Logger.info(VIEW_NAME + " shown");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        if (DEBUG_MODE) {
            Logger.info(VIEW_NAME + " hidden");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    protected void reset() {
        if (DEBUG_MODE) {
            Logger.info(VIEW_NAME + " rebuilt");
        }

        final JPanel interfacePanel = super.getInterfacePanel();
        interfacePanel.removeAll();
        interfacePanel.setLayout(new ScalableLayoutImpl());

        final JLabel dayLabel = new JLabel("Current Day: " + gameController.getCurrentDayNumber());
        dayLabel.setFont(DEFAULT_FONT);
        interfacePanel.add(
                dayLabel,
                new ScaleConstraintImpl(
                        new ScaleImpl(DAY_LABEL_X_SIZE, DAY_LABEL_Y_SIZE),
                        new ScaleImpl(DAY_LABEL_X_POS, DAY_LABEL_Y_POS),
                        new ScaleImpl(DAY_LABEL_ORIGIN)
                )
        );

        final List<IngredientEnum> unlockedIngredients = controller.getIngredientsUnlockedToday();
        final JLabel unlockedIngredientsLabel = new JLabel("Ingredients unlocked today:");
        unlockedIngredientsLabel.setFont(DEFAULT_FONT);
        if (unlockedIngredients.isEmpty()) {
            unlockedIngredientsLabel.setText("There are no ingredients unlocked today");
        }
        interfacePanel.add(
                unlockedIngredientsLabel,
                new ScaleConstraintImpl(
                        new ScaleImpl(UNLOCKED_INGREDIENTS_LABEL_X_SIZE, UNLOCKED_INGREDIENTS_LABEL_Y_SIZE),
                        new ScaleImpl(UNLOCKED_INGREDIENTS_LABEL_X_POS, UNLOCKED_INGREDIENTS_LABEL_Y_POS),
                        new ScaleImpl(UNLOCKED_INGREDIENTS_LABEL_ORIGIN)
                )
        );
        double pbPositionXScale = UNLOCKED_INGREDIENTS_IMAGE_X_POS;
        double pbPositionYScale = UNLOCKED_INGREDIENTS_IMAGE_Y_POS;
        for (final IngredientEnum ingredient : unlockedIngredients) {
            final ImageIcon iconImage = new ImageIcon(resourceService.getImage(ingredient.name() + EXTENSION));
            final JLabel imageLabel = new JLabel(iconImage);
            interfacePanel.add(
                    imageLabel,
                    new ScaleConstraintImpl(
                            new ScaleImpl(UNLOCKED_INGREDIENTS_IMAGE_X_SIZE, UNLOCKED_INGREDIENTS_IMAGE_Y_SIZE),
                            new ScaleImpl(pbPositionXScale, pbPositionYScale),
                            new ScaleImpl(UNLOCKED_INGREDIENTS_IMAGE_ORIGIN)
                    )
            );
            pbPositionXScale = pbPositionXScale + UNLOCKED_INGREDIENTS_IMAGE_X_SIZE + UNLOCKED_INGREDIENTS_IMAGE_X_SPACING;
            if (pbPositionXScale + UNLOCKED_INGREDIENTS_IMAGE_X_SIZE > 1.0 - UNLOCKED_INGREDIENTS_IMAGE_X_POS) {
                pbPositionYScale = pbPositionYScale + UNLOCKED_INGREDIENTS_IMAGE_Y_SIZE + UNLOCKED_INGREDIENTS_IMAGE_Y_SPACING;
                pbPositionXScale = UNLOCKED_INGREDIENTS_IMAGE_X_POS;
            }
        }

        final JButton newDayButton = new JButton("New Day");
        newDayButton.setFont(DEFAULT_FONT);
        newDayButton.setBackground(DEFAULT_BUTTON_BACKGROUND_COLOR);
        newDayButton.setForeground(DEFAULT_BUTTON_TEXT_COLOR);
        newDayButton.setFocusPainted(false);
        newDayButton.addActionListener(e -> gameController.switchToScene("Register"));
        interfacePanel.add(
                newDayButton,
                new ScaleConstraintImpl(
                        new ScaleImpl(NEW_DAY_BUTTON_X_SIZE, NEW_DAY_BUTTON_Y_SIZE),
                        new ScaleImpl(NEW_DAY_BUTTON_X_POS, NEW_DAY_BUTTON_Y_POS),
                        new ScaleImpl(NEW_DAY_BUTTON_ORIGIN)
                )
        );
    }
}
