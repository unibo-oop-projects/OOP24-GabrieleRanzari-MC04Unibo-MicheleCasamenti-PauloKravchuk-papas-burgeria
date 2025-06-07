package it.unibo.papasburgeria.view.impl;

import java.awt.Graphics;
import java.io.Serial;
import org.tinylog.Logger;
import com.google.inject.Inject;

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
        Logger.info("RegisterView created");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        Logger.info("RegisterView shown");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        Logger.info("RegisterView hidden");
    }
 
    /**
     * @inheritDoc
     */
    @Override
    void update(final double delta) {
        Logger.info("RegisterView updated, last frame: " + delta);
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
