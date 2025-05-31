package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import org.tinylog.Logger;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.Serial;

/**
 * Manages the GUI for the burger assembly scene in the game.
 */
public class BurgerAssemblyViewImpl extends AbstractBaseView {
    static final int X = 10;
    static final int Y = 10;
    static final int W = 100;
    static final int H = 100;

    @Serial
    private static final long serialVersionUID = 1L;

    private final transient BurgerAssemblyController controller;



    /**
     * Default constructor.
     *
     * @param controller Controller for the burger assembly scene.
     */
    @Inject
    public BurgerAssemblyViewImpl(final BurgerAssemblyController controller) {
        Logger.info("BurgerAssembly created");
        this.controller = controller;

        super.getInterfacePanel().setLayout(new FlowLayout());
        super.getInterfacePanel().setBackground(Color.RED);

        final JButton button = new JButton("Lettuce");
        button.setSize(W, H);
        super.getInterfacePanel().add(button);
    }

    /**
     * @inheritDoc
     */
    @Override
    void update(final double delta) {
        Logger.info("BurgerAssembly updated, last frame: " + delta);
    }

    /**
     * @inheritDoc
     */
    @Override
    final void paintComponentDelegate(final Graphics g) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        super.showScene();

        Logger.info("BurgerAssemblyView shown");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        Logger.info("BurgerAssemblyView hidden");

        super.hideScene();
    }

    /**
     * @return a string containing the controller.
     */
    @Override
    public String toString() {
        return "[BurgerAssemblyViewImpl: " + controller.toString() + "]";
    }
}
