package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
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
 * Menu View.
 */
public class MenuViewImpl extends AbstractBaseView {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs the MenuView.
     *
     * @param resourceService the service that handles resource obtainment
     */
    @Inject
    public MenuViewImpl(final ResourceService resourceService) {
        super.setStaticBackgroundImage(resourceService.getImage("menu-background.jpg"));

        final JPanel interfacePanel = super.getInterfacePanel();
        interfacePanel.setLayout(new ScalableLayoutImpl());

        final double pbSizeXScale = 0.25;
        final double pbSizeYScale = 0.1;
        final double pbPositionXScale = 0.5;
        final double pbPositionYScale = 0.55;
        final double pbOriginScale = 0.5;
        final JButton playButton = new JButton("PLAY");
        playButton.setBackground(DEFAULT_BUTTON_BACKGROUND_COLOR);
        playButton.setForeground(DEFAULT_BUTTON_TEXT_COLOR);
        playButton.setFocusPainted(false);
        playButton.addActionListener(e -> {
            if (DEBUG_MODE) {
                Logger.debug("Clicked button");
            }
        });

        interfacePanel.add(
                playButton,
                new ScaleConstraintImpl(
                        new ScaleImpl(pbSizeXScale, pbSizeYScale),
                        new ScaleImpl(pbPositionXScale, pbPositionYScale),
                        new ScaleImpl(pbOriginScale)
                )
        );
    }

    /**
     * @inheritDoc
     */
    @Override
    void update(final double delta) {

    }

    @Override
    void paintComponentDelegate(final Graphics g) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        if (DEBUG_MODE) {
            Logger.info("MenuView shown");
        }

    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        if (DEBUG_MODE) {
            Logger.info("MenuView hidden");
        }
    }
}
