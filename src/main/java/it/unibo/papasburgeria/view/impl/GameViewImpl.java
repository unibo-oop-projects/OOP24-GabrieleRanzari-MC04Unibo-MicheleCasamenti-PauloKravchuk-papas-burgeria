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
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @inheritDoc
 */
@Singleton
public class GameViewImpl implements GameView {
    private static final int DEFAULT_WIDTH = 1280;
    private static final int DEFAULT_HEIGHT = 720;
    private static final int FRAMERATE = 1;
    private final GameController gameController;
    private final JFrame mainFrame;
    private final Timer frameUpdate;
    private final List<AbstractBaseView> views;
    private long lastFrameTime;
    private AbstractBaseView currentView;

    /**
     * Initializes the starting view instance.
     *
     * @param sceneService   scene service
     * @param gameController base main controller
     */
    @Inject
    public GameViewImpl(final GameController gameController, final SceneService sceneService) {
        this.gameController = gameController;

        this.mainFrame = new JFrame("Papa's Burgeria");
        this.mainFrame.setLayout(new BorderLayout());
        this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(final WindowEvent e) {
                onWindowOpened();
            }

            @Override
            public void windowClosing(final WindowEvent windowEvent) {
                onWindowClosing();
            }
        });
        this.mainFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.mainFrame.setLocationRelativeTo(null);

        this.views = new ArrayList<>();

        /*
            In case SceneService has views implemented from different sources (like a secondary window),
            we only retrieve the ones related to this implementation to handle updating/redrawing.
        */
        final List<BaseScene> scenes = sceneService.getScenes();
        for (final BaseScene scene : scenes) {
            if (scene instanceof AbstractBaseView && !this.mainFrame.getContentPane().isAncestorOf((AbstractBaseView) scene)) {
                this.views.add((AbstractBaseView) scene);
                this.mainFrame.add((AbstractBaseView) scene, BorderLayout.CENTER);
            }
        }

        sceneService.onSceneChanged(scene -> {
            if (scene instanceof AbstractBaseView) {
                this.currentView = (AbstractBaseView) scene;
            }
        });

        this.frameUpdate = new Timer(1000 / FRAMERATE, e -> {
            final long currentFrameTime = System.nanoTime();
            final double delta = (currentFrameTime - lastFrameTime) / 1_000_000_000.0;
            lastFrameTime = currentFrameTime;

            onFrameUpdated(delta);
        });
    }

    /**
     * Handled the opening procedure of the application.
     */
    private void onWindowOpened() {
        Logger.info("Game window opened");

        this.gameController.startGame();
    }

    /**
     * Handles the closing procedure of the application.
     */
    private void onWindowClosing() {
        Logger.info("Game window closing");

        this.frameUpdate.stop();
        this.gameController.endGame();
        this.mainFrame.dispose();
    }

    private void onFrameUpdated(final double delta) {
        this.views.forEach(view -> view.update(delta));
        if (this.currentView != null) {
            this.currentView.repaint();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void show() {
        if (this.mainFrame.isVisible()) {
            Logger.warn("The main frame is already visible");
            return;
        }

        this.mainFrame.setVisible(true);
        this.lastFrameTime = System.nanoTime();
        this.frameUpdate.start();
    }
}
