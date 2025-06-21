package it.unibo.papasburgeria.view.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Arrays;

import javax.swing.JButton;

import org.tinylog.Logger;
import com.google.inject.Inject;

import it.unibo.papasburgeria.controller.api.CustomerController;
import it.unibo.papasburgeria.controller.impl.CustomerControllerImpl;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.impl.CustomerImpl;

/**
 * Register view.
 */
public class RegisterViewImpl extends AbstractBaseView {
   @Serial
    private static final long serialVersionUID = 1L;

    private static final int X_ADD_CUSTOMER = 100;
    private static final int Y_ADD_CUSTOMER = 100;
    private static final int X_TAKE_ORDER_CUSTOMER = 300;
    private static final int Y_TAKE_ORDER_CUSTOMER = 100;
    private static final int X_SERVE_CUSTOMER = 500;
    private static final int Y_SERVE_CUSTOMER = 100;
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 50;

    private final transient CustomerController controller;
    private final JButton addCustomer = new JButton("Add Customer");
    private final JButton takeOrderCustomer = new JButton("Take Order");
    private final JButton serveCustomer = new JButton("Serve Customer");

    /**
     * Register view constructor.
     */
    @Inject
    public RegisterViewImpl() {
        Logger.info("RegisterView created");
        super.getInterfacePanel().setLayout(null);
        super.getInterfacePanel().setBackground(Color.GREEN);

        this.controller = new CustomerControllerImpl();

        addCustomer.setBounds(X_ADD_CUSTOMER, Y_ADD_CUSTOMER, BUTTON_WIDTH, BUTTON_HEIGHT);
        addCustomer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.pushCustomerRegisterLine(new CustomerImpl(Arrays.asList(IngredientEnum.values())));
                Logger.info(controller.toString());
            }

        });

        takeOrderCustomer.setBounds(X_TAKE_ORDER_CUSTOMER, Y_TAKE_ORDER_CUSTOMER, BUTTON_WIDTH, BUTTON_HEIGHT);
        takeOrderCustomer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.pushCustomerWaitLine(controller.popCustomerRegisterLine());
                Logger.info(controller.toString());
            }

        });

        serveCustomer.setBounds(X_SERVE_CUSTOMER, Y_SERVE_CUSTOMER, BUTTON_WIDTH, BUTTON_HEIGHT);
        serveCustomer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.popCustomerWaitLine();
                Logger.info(controller.toString());
            }

        });

        super.getInterfacePanel().add(addCustomer);
        super.getInterfacePanel().add(takeOrderCustomer);
        super.getInterfacePanel().add(serveCustomer);

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
