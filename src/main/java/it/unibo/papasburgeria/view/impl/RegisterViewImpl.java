package it.unibo.papasburgeria.view.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serial;

import javax.swing.JPanel;

import org.tinylog.Logger;
import com.google.inject.Inject;

import it.unibo.papasburgeria.utils.api.ResourceService;

/**
 * Register view.
 */
public class RegisterViewImpl extends AbstractBaseView {
   @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Register view constructor.
     *
     * @param resourceService the service that handles resource obtainment
     */
    @Inject
    public RegisterViewImpl(final ResourceService resourceService) {
        final JPanel interfacePanel = super.getInterfacePanel();
        interfacePanel.setBackground(Color.GREEN);
        resourceService.dispose();
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

    }
}
