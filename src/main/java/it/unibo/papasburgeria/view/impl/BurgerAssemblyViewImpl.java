package it.unibo.papasburgeria.view.impl;

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

    @Serial
    private static final long serialVersionUID = 1L;

    //private final transient BurgerAssemblyController controller;

    /**
     * Default constructor.
     */
    //@Inject
    public BurgerAssemblyViewImpl(/* final BurgerAssemblyController controller*/) {
        Logger.info("BurgerAssembly created");
        //this.controller = controller;

        super.getInterfacePanel().setLayout(new FlowLayout());
        super.getInterfacePanel().setBackground(Color.RED);

        final JButton button = new JButton("Lettuce");
        button.setSize(100, 100 / 2);
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
        Logger.info("BurgerAssemblyView shown");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        Logger.info("BurgerAssemblyView hidden");
    }
}
