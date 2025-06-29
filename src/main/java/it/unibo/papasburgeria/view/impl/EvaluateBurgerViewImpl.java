package it.unibo.papasburgeria.view.impl;

import java.awt.Graphics;

import org.tinylog.Logger;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;
import com.google.inject.Singleton;

@Singleton
public class EvaluateBurgerViewImpl extends AbstractBaseView {
    EvaluateBurgerViewImpl(){

    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        if (DEBUG_MODE){
            Logger.info("EvaluateBurgerView shown");
        }  
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        if (DEBUG_MODE){
            Logger.info("EvaluateBurgerView hidden");
        }    
    }

    /**
     * @inheritDoc
     */
    @Override
    void update(double delta) {

    }

    /**
     * @inheritDoc
     */
    @Override
    void paintComponentDelegate(Graphics g) {

    }
}
