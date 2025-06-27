package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.CustomerController;
import it.unibo.papasburgeria.model.CustomerDifficultyEnum;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.api.Customer;
import it.unibo.papasburgeria.model.api.PantryModel;
import it.unibo.papasburgeria.model.impl.CustomerImpl;
import org.tinylog.Logger;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.Arrays;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;

/**
 * Register view.
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "model is injected and shared intentionally")
public class RegisterViewImpl extends AbstractBaseView {
    public static final String VIEW_NAME = getViewName(RegisterViewImpl.class);

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int X_ADD_CUSTOMER = 100;
    private static final int Y_ADD_CUSTOMER = 100;
    private static final int X_TAKE_ORDER_CUSTOMER = 300;
    private static final int Y_TAKE_ORDER_CUSTOMER = 100;
    private static final int X_SERVE_CUSTOMER = 500;
    private static final int Y_SERVE_CUSTOMER = 100;

    private static final int X_START_THREAD = 100;
    private static final int Y_START_THREAD = 300;
    private static final int X_STOP_THREAD = 300;
    private static final int Y_STOP_THREAD = 300;

    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 50;

    private static final int X_REGISTER_LINE = 1;
    private static final int Y_REGISTER_LINE = 500;
    private static final int X_WAIT_LINE = 1;
    private static final int Y_WAIT_LINE = 1000;

    private static final int TEXTAREA_WIDTH = 1000;
    private static final int TEXTAREA_HEIGHT = 500;

    private final transient CustomerController controller;
    private final CustomerDifficultyEnum customerDifficulty;

    private final JButton addCustomer = new JButton("Add Customer");
    private final JButton takeOrderCustomer = new JButton("Take Order");
    private final JButton serveCustomer = new JButton("Serve Customer");

    private final JButton startCustomerThread = new JButton("Start Thread");
    private final JButton stopCustomerThread = new JButton("Stop Thread");

    private final JTextArea registerLine = new JTextArea();
    private final JTextArea waitLine = new JTextArea();

    /**
     * @param pantryModel        used to get the available ingredients
     * @param customerController used to manage the customers' line
     */
    @Inject
    public RegisterViewImpl(final PantryModel pantryModel, final CustomerController customerController) {
        super.getInterfacePanel().setLayout(null);
        super.getInterfacePanel().setBackground(Color.GREEN);

        this.controller = customerController;
        this.customerDifficulty = CustomerDifficultyEnum.FIRST;

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

        startCustomerThread.setBounds(X_START_THREAD, Y_START_THREAD, BUTTON_WIDTH, BUTTON_HEIGHT);
        startCustomerThread.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.startCustomerThread(customerDifficulty.getSpawnIntervalSeconds(),
                        customerDifficulty.getCustomerCount(), pantryModel.getUnlockedIngredients().stream().toList());
            }

        });

        stopCustomerThread.setBounds(X_STOP_THREAD, Y_STOP_THREAD, BUTTON_WIDTH, BUTTON_HEIGHT);
        stopCustomerThread.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                controller.killCustomerThread();
            }

        });
        registerLine.setBounds(X_REGISTER_LINE, Y_REGISTER_LINE, TEXTAREA_WIDTH, TEXTAREA_HEIGHT);
        registerLine.setBounds(X_WAIT_LINE, Y_WAIT_LINE, TEXTAREA_WIDTH, TEXTAREA_HEIGHT);

        super.getInterfacePanel().add(addCustomer);
        super.getInterfacePanel().add(takeOrderCustomer);
        super.getInterfacePanel().add(serveCustomer);

        super.getInterfacePanel().add(startCustomerThread);
        super.getInterfacePanel().add(stopCustomerThread);

        super.getInterfacePanel().add(registerLine);
        super.getInterfacePanel().add(waitLine);
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
        registerLine.setText("");
        for (final Customer currentCustomer : controller.getRegisterLine()) {
            registerLine.append(currentCustomer.getOrder().getHamburger().toString());
        }

        waitLine.setText("");
        for (final Customer currentCustomer : controller.getWaitLine()) {
            waitLine.append(currentCustomer.getOrder().getHamburger().toString());
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    void paintComponentDelegate(final Graphics g) {

    }

    /**
     * Rebuilds the view.
     */
    @Override
    protected void reset() {
        if (DEBUG_MODE) {
            Logger.info("RegisterView rebuilt");
        }
    }
}
