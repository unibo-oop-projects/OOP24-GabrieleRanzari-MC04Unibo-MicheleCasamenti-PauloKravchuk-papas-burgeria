package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.controller.impl.EvaluateBurgerControllerImpl;
import it.unibo.papasburgeria.model.api.Hamburger;
import it.unibo.papasburgeria.model.api.Order;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.view.api.components.DrawingManager;
import it.unibo.papasburgeria.view.api.components.Sprite;
import it.unibo.papasburgeria.view.impl.components.DrawingManagerImpl;
import it.unibo.papasburgeria.view.impl.components.SpriteImpl;
import org.tinylog.Logger;

import java.awt.Graphics;
import java.io.Serial;
import java.util.ArrayList;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;
import static it.unibo.papasburgeria.view.impl.components.DrawingManagerImpl.ORDER_INGREDIENT;

/**
 * the interface which contains the hamburger evaluation.
 */
@Singleton
public class EvaluateBurgerViewImpl extends AbstractBaseView {
    @Serial
    private static final long serialVersionUID = 1L;
    private final transient EvaluateBurgerControllerImpl controller;
    private final transient DrawingManager drawingManager;
    private final transient ResourceService resourceService;
    private transient Hamburger burger;
    private transient Order order;

    @Inject
    EvaluateBurgerViewImpl(final EvaluateBurgerControllerImpl controller,
                           final DrawingManager drawingManager, final ResourceService resourceService) {
        this.controller = controller;
        this.drawingManager = drawingManager;
        this.resourceService = resourceService;
        read();
    }

    /**
     * reads the burger and the order.
     */
    private void read() {
        burger = controller.getHamburgerOnAssembly();
        order = controller.getSelectedOrder();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        read();
        if (DEBUG_MODE) {
            Logger.info("EvaluateBurgerView shown");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        if (DEBUG_MODE) {
            Logger.info("EvaluateBurgerView hidden");
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
        drawingManager.drawHamburger(burger,
                getSize(), BurgerAssemblyViewImpl.HALF_RANGE,
                BurgerAssemblyViewImpl.HAMBURGER_Y_POS_SCALE, new ArrayList<>(), g);

        final Sprite orderSprite = new SpriteImpl(resourceService.getImage("order.png"),
                ORDER_INGREDIENT,
                OrderSelectionViewImpl.ORDER_SELECTED_X_POSITION,
                OrderSelectionViewImpl.ORDER_SELECTED_Y_POSITION,
                DrawingManagerImpl.ORDER_X_SIZE_SCALE,
                DrawingManagerImpl.ORDER_Y_SIZE_SCALE);
        drawingManager.drawOrder(orderSprite, order, getSize(), g);
    }
}
