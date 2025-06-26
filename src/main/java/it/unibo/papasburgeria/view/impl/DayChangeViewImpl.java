package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.view.impl.components.ScalableLayoutImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleConstraintImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleImpl;
import org.tinylog.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.io.Serial;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;

/**
 * Manages the GUI for the day changing scene in the game.
 */
public class DayChangeViewImpl extends AbstractBaseView {
    private static final double NEW_DAY_BUTTON_X_POS = 0.5;
    private static final double NEW_DAY_BUTTON_Y_POS = 0.8;
    private static final double NEW_DAY_BUTTON_X_SIZE = 0.1;
    private static final double NEW_DAY_BUTTON_Y_SIZE = 0.05;
    private static final double NEW_DAY_BUTTON_ORIGIN = 0.5;

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor, creates and initializes the GUI elements.
     *
     * @param resourceService the service that handles resource obtainment
     * @param controller the controller for the game
     */
    @Inject
    public DayChangeViewImpl(final ResourceService resourceService, final GameController controller) {
        if (DEBUG_MODE) {
            Logger.info("DayChange created");
        }

        super.setStaticBackgroundImage(resourceService.getImage("shop_background.png"));

        final JPanel interfacePanel = super.getInterfacePanel();
        interfacePanel.setLayout(new ScalableLayoutImpl());

        final JButton newDayButton = new JButton("New Day");
        newDayButton.setBackground(DEFAULT_BUTTON_BACKGROUND_COLOR);
        newDayButton.setForeground(DEFAULT_BUTTON_TEXT_COLOR);
        newDayButton.setFocusPainted(false);
        newDayButton.addActionListener(e -> controller.switchToScene("Register"));
        interfacePanel.add(
                newDayButton,
                new ScaleConstraintImpl(
                        new ScaleImpl(NEW_DAY_BUTTON_X_SIZE, NEW_DAY_BUTTON_Y_SIZE),
                        new ScaleImpl(NEW_DAY_BUTTON_X_POS, NEW_DAY_BUTTON_Y_POS),
                        new ScaleImpl(NEW_DAY_BUTTON_ORIGIN)
                )
        );
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
        if (DEBUG_MODE) {
            Logger.info("DayChange shown");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        if (DEBUG_MODE) {
            Logger.info("DayChange hidden");
        }
    }
}
