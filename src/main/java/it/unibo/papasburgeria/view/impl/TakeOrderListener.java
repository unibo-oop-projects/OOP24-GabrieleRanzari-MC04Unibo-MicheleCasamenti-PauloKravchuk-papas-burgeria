package it.unibo.papasburgeria.view.impl;

import it.unibo.papasburgeria.controller.api.CustomerController;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * manages the order taking of customers when the button is pressed.
 */
class TakeOrderListener implements MouseListener {
    private final CustomerController controller;

    TakeOrderListener(final CustomerController controller) {
        this.controller = controller;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void mouseClicked(final MouseEvent e) {
        controller.takeOrderFromCustomer(controller.getRegisterLine().getFirst());
    }

    /**
     * @inheritDoc
     */
    @Override
    public void mousePressed(final MouseEvent e) {
    }

    /**
     * @inheritDoc
     */
    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    /**
     * @inheritDoc
     */
    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    /**
     * @inheritDoc
     */
    @Override
    public void mouseExited(final MouseEvent e) {
    }
}
