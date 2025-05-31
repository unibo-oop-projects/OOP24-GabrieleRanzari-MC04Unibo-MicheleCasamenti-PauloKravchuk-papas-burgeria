package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.utils.api.scene.BaseScene;
import it.unibo.papasburgeria.utils.api.scene.SceneService;
import it.unibo.papasburgeria.view.api.GameView;
import org.tinylog.Logger;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @inheritDoc
 */
@Singleton
public class GameViewImpl implements GameView {
    private static final double ASPECT_RATIO = 16.0 / 9.0;
    private static final double SIZE_SCALE = 0.7; // in %
    private static final int FRAMERATE = 1;
    //
    private final GameController gameController;
    private final List<AbstractBaseView> views;
    //
    private final JFrame mainFrame;
    private final CardLayout cardLayout;
    private final Timer frameUpdate;
    private AbstractBaseView currentView;
    private boolean gameIsRunning;
    private long lastFrameTime;

    /**
     * Initializes the starting view instance.
     *
     * @param sceneService   scene service
     * @param gameController base main controller
     */
    @Inject
    public GameViewImpl(final GameController gameController, final SceneService sceneService) {
        this.gameController = gameController;

        this.gameIsRunning = false;

        // this should go well for most screens ig
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int scaledWidth = (int) (screenSize.width * SIZE_SCALE);
        final int proportionalHeight = (int) (scaledWidth / ASPECT_RATIO);

        this.mainFrame = new JFrame("Papa's Burgeria");
        this.cardLayout = new CardLayout();
        this.mainFrame.setSize(scaledWidth, proportionalHeight);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setLayout(cardLayout);
        this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent windowEvent) {
                endGame();
            }
        });

        /*
            In case SceneService has views implemented from different sources (like a secondary window),
            we only retrieve the ones related to this implementation to handle updating/redrawing.
        */
        this.views = new ArrayList<>();

        final List<BaseScene> scenes = sceneService.getScenes();
        for (final BaseScene scene : scenes) {
            if (scene instanceof AbstractBaseView && !this.mainFrame.getContentPane().isAncestorOf((AbstractBaseView) scene)) {
                this.views.add((AbstractBaseView) scene);
                this.mainFrame.add((AbstractBaseView) scene);
            }
        }

        sceneService.onSceneChanged(scene -> {
            if (scene instanceof AbstractBaseView) {
                currentView = (AbstractBaseView) scene;
                currentView.revalidate();

                cardLayout.show(mainFrame.getContentPane(), currentView.getClass().getSimpleName());
            }
        });

        this.frameUpdate = new Timer(1000 / FRAMERATE, e -> {
            final long currentFrameTime = System.nanoTime();
            final double delta = (currentFrameTime - lastFrameTime) / 1_000_000_000.0;
            lastFrameTime = currentFrameTime;

            onFrameUpdated(delta);
        });
    }

    private void onFrameUpdated(final double delta) {
        this.views.forEach(view -> view.update(delta));
        if (this.currentView != null) {
            this.currentView.getGamePanel().repaint();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void startGame() {
        if (this.gameIsRunning) {
            Logger.warn("The game has already started!");
        }

        this.gameIsRunning = true;
        this.mainFrame.setVisible(true);
        this.mainFrame.toFront();
        this.lastFrameTime = System.nanoTime();
        this.frameUpdate.start();
        this.gameController.startGame();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void endGame() {
        if (!this.gameIsRunning) {
            Logger.warn("The game has already ended!");
            return;
        }

        this.frameUpdate.stop();
        this.gameController.endGame();
        this.mainFrame.dispose();
        this.gameIsRunning = false;
    }
}
