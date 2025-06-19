package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import org.tinylog.Logger;

import java.awt.Graphics;
import java.io.Serial;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;

/**
 * Register view.
 */
public class RegisterViewImpl extends AbstractBaseView {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Register view constructor.
     */
    @Inject
    public RegisterViewImpl() {
        if (DEBUG_MODE) {
            Logger.info("RegisterView created");
        }
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'paintComponentDelegate'");
    }
}
