package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.scene.BaseScene;
import it.unibo.papasburgeria.utils.api.scene.SceneService;
import it.unibo.papasburgeria.utils.api.scene.SceneType;
import it.unibo.papasburgeria.view.api.GameView;
import it.unibo.papasburgeria.view.api.components.Scale;
import it.unibo.papasburgeria.view.impl.components.ScalableLayoutImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleConstraintImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleImpl;
import org.tinylog.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;

/**
 * Implementation of GameView.
 *
 * <p>
 * See {@link GameView} for interface details.
 */
@Singleton
public class GameViewImpl implements GameView {
    public static final int FRAMERATE = 60;
    public static final double ASPECT_RATIO = 16.0 / 9.0;
    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(0, 0, 0, 0);
    private static final double SIZE_SCALE = 0.7; // in %
    //
    private final GameController gameController;
    private final List<AbstractBaseView> views;
    //
    private final JFrame mainFrame;
    private final JPanel mainPanel;
    private final JPanel interfacePanel;
    private final CardLayout cardLayout;
    private final Timer frameUpdate;
    private AbstractBaseView currentView;
    private boolean gameIsRunning;
    private long lastFrameTime;

    /**
     * Initializes the starting view instance, acts as entry-point for the
     * game and as a core handler of the views when it comes to updating them.
     *
     * @param sceneService    scene service
     * @param gameController  base main controller
     * @param resourceService resource provider
     */
    @Inject
    public GameViewImpl(
            final GameController gameController,
            final SceneService sceneService,
            final ResourceService resourceService
    ) {
        this.gameController = gameController;
        this.gameIsRunning = false;
        this.views = new ArrayList<>();

        // this should go well for most screens ig
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int scaledWidth = (int) (screenSize.width * SIZE_SCALE);
        final int proportionalHeight = (int) (scaledWidth / ASPECT_RATIO);

        /*
            Defining base for all views + overlay on top.
            This part looks somewhat of a little dupe from AbstractBaseView, but I'd rather
            not have to use AbstractBaseView at all as we don't need its full functionality.
         */
        final JLayeredPane layeredPane = new JLayeredPane() {
            @Override
            public void doLayout() {
                final Dimension size = this.getSize();
                for (final Component component : this.getComponents()) {
                    component.setSize(size.width, size.height);
                }
            }
        };

        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(this.cardLayout);
        this.interfacePanel = new JPanel(new ScalableLayoutImpl());
        this.interfacePanel.setBackground(Color.BLUE);

        for (final JPanel panel : List.of(this.mainPanel, this.interfacePanel)) {
            panel.setBackground(DEFAULT_BACKGROUND_COLOR);
            panel.setOpaque(false);
        }

        layeredPane.add(this.mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(this.interfacePanel, JLayeredPane.PALETTE_LAYER);

        this.mainFrame = new JFrame("Papa's Burgeria");
        this.mainFrame.setSize(scaledWidth, proportionalHeight);
        this.mainFrame.setLocationRelativeTo(null);
        this.mainFrame.setLayout(new BorderLayout());
        this.mainFrame.add(layeredPane, BorderLayout.CENTER);
        this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent windowEvent) {
                endGame();
            }
        });

        final JPanel bottomPanel = new JPanel(new ScalableLayoutImpl());
        bottomPanel.setBackground(DEFAULT_BACKGROUND_COLOR);
        bottomPanel.setOpaque(false);
        this.interfacePanel.add(bottomPanel, new ScaleConstraintImpl(
                new ScaleImpl(ScaleConstraintImpl.FULL, ScaleConstraintImpl.EIGHTH),
                ScaleConstraintImpl.POSITION_BOTTOM_CENTER,
                ScaleConstraintImpl.ORIGIN_BOTTOM_CENTER
        ));

        final JLabel imageLabel = new JLabel(new ImageIcon(resourceService.getImage("bottom_border.png")));
        bottomPanel.add(imageLabel, new ScaleConstraintImpl(
                ScaleConstraintImpl.SIZE_FULL,
                ScaleConstraintImpl.POSITION_CENTER,
                ScaleConstraintImpl.ORIGIN_CENTER
        ));

        // somewhat hard-coded, but it's alright, it's just a view construction
        final List<SceneType> btnSceneTypes = List.of(
                SceneType.MENU,
                SceneType.REGISTER,
                SceneType.GRILL,
                SceneType.BURGER_ASSEMBLY
        );
        final Scale sizeScale = new ScaleImpl(ScaleConstraintImpl.EIGHTH, ScaleConstraintImpl.FULL - ScaleConstraintImpl.QUARTER);
        final Scale menuPositionScale = new ScaleImpl(ScaleConstraintImpl.EIGHTH, ScaleConstraintImpl.HALF);

        int i = -1;
        for (final SceneType sceneType : btnSceneTypes) {
            final JButton btn = new JButton(new ImageIcon(resourceService.getImage(sceneType.getValue() + "_btn.png")));
            btn.setBorder(BorderFactory.createEmptyBorder());
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setOpaque(false);
            btn.addActionListener(e -> {
                gameController.switchToScene(sceneType);
            });

            if (sceneType.equals(SceneType.MENU)) {
                bottomPanel.add(btn, new ScaleConstraintImpl(
                        sizeScale,
                        menuPositionScale,
                        ScaleConstraintImpl.ORIGIN_CENTER
                ));
            } else {
                bottomPanel.add(btn, new ScaleConstraintImpl(
                        sizeScale,
                        new ScaleImpl(ScaleConstraintImpl.HALF + (ScaleConstraintImpl.EIGHTH * i), ScaleConstraintImpl.HALF),
                        ScaleConstraintImpl.ORIGIN_CENTER
                ));
                i++;
            }
        }
        bottomPanel.setComponentZOrder(imageLabel, bottomPanel.getComponentCount() - 1);
        bottomPanel.setVisible(false);

        /*
            In the case of when SceneService has views implemented from different sources,
            we only retrieve the ones related to this implementation to handle updating/redrawing.
        */
        final Map<SceneType, BaseScene> sceneMap = sceneService.getScenes();
        for (final Map.Entry<SceneType, BaseScene> entry : sceneMap.entrySet()) {
            final SceneType sceneType = entry.getKey();
            final BaseScene scene = entry.getValue();
            if (scene instanceof AbstractBaseView && !this.mainFrame.getContentPane().isAncestorOf((AbstractBaseView) scene)) {
                this.views.add((AbstractBaseView) scene);
                this.mainPanel.add((AbstractBaseView) scene, sceneType.getValue());
            }
        }

        // This implementation uses card-layout so we centralize through a callback the showing/hiding of the views
        sceneService.onSceneChanged(sceneType -> {
            final BaseScene scene = sceneMap.get(sceneType);
            if (scene instanceof AbstractBaseView) {
                currentView = (AbstractBaseView) scene;
                currentView.revalidate();

                bottomPanel.setVisible(btnSceneTypes.contains(sceneType) && !sceneType.equals(SceneType.MENU));
                cardLayout.show(mainPanel, sceneType.getValue());
            }
        });

        // Frame handler
        this.frameUpdate = new Timer(1000 / FRAMERATE, e -> {
            final long currentFrameTime = System.nanoTime();
            final double delta = (currentFrameTime - lastFrameTime) / 1_000_000_000.0;
            lastFrameTime = currentFrameTime;

            onFrameUpdated(delta);
        });
    }

    /**
     * Fires each frame, calls {@link AbstractBaseView#update(double)} for each view,
     * to handle frame-based state update.
     *
     * <p>
     * For the currently displayed view, calls {@link javax.swing.JPanel#repaint()} on the view's
     * game panel, to handle frame-based graphics update.
     *
     * @param delta time between current and last frame
     */
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
            if (DEBUG_MODE) {
                Logger.warn("The game has already started!");
            }
            return;
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
            if (DEBUG_MODE) {
                Logger.warn("The game has already ended!");
            }
            return;
        }

        this.gameController.processSave();
        this.frameUpdate.stop();
        this.gameController.endGame();
        this.mainFrame.dispose();
        this.gameIsRunning = false;
    }
}
