package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.CustomerController;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;

import java.awt.Graphics;
import java.io.Serial;

import org.tinylog.Logger;

/**
 * Register view.
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "model is injected and shared intentionally")
public class RegisterViewImpl extends AbstractBaseView {
    @Serial
    private static final long serialVersionUID = 1L;

    private final transient CustomerController controller;

    /**
     * @param customerController used to manage the customers' line
     */
    @Inject
    public RegisterViewImpl(final CustomerController customerController) {
        super.getInterfacePanel().setLayout(null);

        this.controller = customerController;
        this.controller.stopClientThread();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        if (DEBUG_MODE) {
            Logger.info("RegisterView shown");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        if (DEBUG_MODE) {
            Logger.info("RegisterView hidden");
        }
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
}
