package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.SfxService;
import it.unibo.papasburgeria.view.impl.components.ScalableLayoutImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleConstraintImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleImpl;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.io.Serial;

/**
 * Menu View.
 */
public class MenuViewImpl extends AbstractBaseView {
    public static final String VIEW_NAME = getViewName(MenuViewImpl.class);

    @Serial
    private static final long serialVersionUID = 1L;

    private final transient SfxService sfxService;

    /**
     * Constructs the MenuView.
     *
     * @param resourceService the service that handles resource obtainment
     * @param gameController  game controller instance
     * @param sfxService      sfx player service
     */
    @Inject
    public MenuViewImpl(final GameController gameController, final ResourceService resourceService, final SfxService sfxService) {
        super.setStaticBackgroundImage(resourceService.getImage("menu-background.jpg"));

        this.sfxService = sfxService;

        final JPanel interfacePanel = super.getInterfacePanel();
        interfacePanel.setLayout(new ScalableLayoutImpl());

        final double pbSizeXScale = 0.15;
        final double pbSizeYScale = 0.1;
        final double pbPositionYScale = 0.55;
        final JButton playButton = new JButton(new ImageIcon(resourceService.getImage("play_btn.png")));
        playButton.setBackground(DEFAULT_BACKGROUND_COLOR);
        playButton.setBorder(BorderFactory.createEmptyBorder());
        playButton.setContentAreaFilled(false);
        playButton.setOpaque(false);
        playButton.setFocusPainted(false);

        playButton.addActionListener(e -> {
            playButton.setVisible(false);
            gameController.switchToScene("Register");
        });

        interfacePanel.add(
                playButton,
                new ScaleConstraintImpl(
                        new ScaleImpl(pbSizeXScale, pbSizeYScale),
                        new ScaleImpl(ScaleConstraintImpl.HALF, pbPositionYScale),
                        ScaleConstraintImpl.ORIGIN_CENTER
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
     * Rebuilds the view.
     */
    @Override
    protected void reset() {
        if (DEBUG_MODE) {
            Logger.info("MenuView rebuilt");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        this.sfxService.playSoundLooped("MenuIntro.wav");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
    }
}
