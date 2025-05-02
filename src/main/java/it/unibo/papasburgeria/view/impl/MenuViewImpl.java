package it.unibo.papasburgeria.view.impl;

import it.unibo.papasburgeria.utils.api.scene.BaseView;
import org.tinylog.Logger;

public class MenuViewImpl implements BaseView {
    @Override
    public void show() {
        Logger.info("Menu view shown");
    }

    @Override
    public void hide() {
        Logger.info("Menu view hidden");
    }
}
