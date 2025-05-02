package it.unibo.papasburgeria.view.impl;

import it.unibo.papasburgeria.utils.api.scene.BaseView;
import org.tinylog.Logger;

/**
 * Menu View.
 */
public class MenuViewImpl implements BaseView {

    /**
     * @inheritDoc
     */
    @Override
    public void show() {
        Logger.info("Menu view shown");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hide() {
        Logger.info("Menu view hidden");
    }
}
