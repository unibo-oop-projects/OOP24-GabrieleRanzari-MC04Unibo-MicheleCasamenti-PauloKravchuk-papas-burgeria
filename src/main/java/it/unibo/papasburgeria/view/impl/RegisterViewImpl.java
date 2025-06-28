package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.CustomerController;
<<<<<<< Updated upstream
import it.unibo.papasburgeria.model.api.Customer;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.view.impl.components.ScalableLayoutImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleConstraintImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleImpl;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;
=======
import org.tinylog.Logger;
>>>>>>> Stashed changes

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;

/**
 * Register view.
 */
@SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "model is injected and shared intentionally")
public class RegisterViewImpl extends AbstractBaseView {
    @Serial
    public static final String CUSTOMER_FILE_NAME = "customer";
    public static final String TAKE_ORDER_FILE_NAME = "take_order_button";
    public static final String BACKGROUNT_FILE_NAME = "register_background";
    public static final String FILE_SEPARATOR = "_";
    public static final String FILE_EXTENSION = ".png";

    private static final long serialVersionUID = 1L;

    private static final int MAX_DISPLAYABLE_CUSTOMERS = 5;

    private static final double ORIGIN = 0.0;
    private static final double CUSTOMER_WIDTH = 0.1;
    private static final double CUSTOMER_HEIGHT = 0.5;

    private static final double[] REGISTER_X_POSITION = {0.1, 0.3, 0.5, 0.7, 0.9};
    private static final double REGISTER_Y_POSITION = 0.4;

    private static final double[] WAIT_X_POSITION = {0.2, 0.4, 0.6, 0.8, 1.0};
    private static final double WAIT_Y_POSITION = 0.2;

    private static final double TAKE_ORDER_WIDTH = 0.1;
    private static final double TAKE_ORDER_HEIGHT = 0.2;

    private static final double TAKE_ORDER_X_POSITION = 0.1;
    private static final double TAKE_ORDER_Y_POSITION = 0.2;

    private final JLabel takeOrderLabel;
    private final transient CustomerController customerController;
    private final transient ResourceService resourceService;
    private transient Map<Customer, JLabel> registerLineView = new LinkedHashMap<>();
    private transient Map<Customer, JLabel> waitLineView = new LinkedHashMap<>();
    private final JPanel panel;

    /**
     * @param resourceService used to get resources such as images and audio
     * @param customerController used to manage the customers' line
     */
    @Inject
    public RegisterViewImpl(final ResourceService resourceService,
                            final CustomerController customerController) {
        super.setStaticBackgroundImage(resourceService.getImage(BACKGROUNT_FILE_NAME + FILE_EXTENSION));
        this.customerController = customerController;
        this.resourceService = resourceService;
        this.panel = super.getInterfacePanel();
        panel.setLayout(new ScalableLayoutImpl());

        final ImageIcon iconImage = new ImageIcon(resourceService.getImage(TAKE_ORDER_FILE_NAME + FILE_EXTENSION));
        this.takeOrderLabel = new JLabel(iconImage);

        panel.add(takeOrderLabel, 
                        new ScaleConstraintImpl(
                            new ScaleImpl(TAKE_ORDER_WIDTH, TAKE_ORDER_HEIGHT),
                            new ScaleImpl(TAKE_ORDER_X_POSITION, TAKE_ORDER_Y_POSITION),
                            new ScaleImpl(ORIGIN)
                        ));
        takeOrderLabel.addMouseListener(new TakeOrderListener(customerController));
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
        if (takeOrderLabel.isVisible() && registerLineView.isEmpty()) {
            takeOrderLabel.setVisible(false);
        }

        if (!takeOrderLabel.isVisible() && !registerLineView.isEmpty()) {
            takeOrderLabel.setVisible(true);
        }

        /* if the customers do not coincide */
        if (!waitLineView.keySet().equals(customerController.getWaitLine().stream().collect(Collectors.toSet()))) {
            /* remove all customers' JLabels */
            for (final Entry<Customer, JLabel> entry : waitLineView.entrySet()) {
                if (panel.isAncestorOf(entry.getValue())) {
                    panel.remove(entry.getValue());
                }
            }
            waitLineView.clear();

            /* and fill them back up */
            for (final Customer customer : customerController.getWaitLine()) {
                final ImageIcon iconImage = new ImageIcon(
                    resourceService.getImage(CUSTOMER_FILE_NAME
                    + FILE_SEPARATOR
                    + customer.getSkinType()
                    + FILE_EXTENSION));
                final JLabel imageLabel = new JLabel(iconImage);
                waitLineView.put(customer, imageLabel);
            }

            /* and finally print five of them */
            int position = 0;
            for (final Entry<Customer, JLabel> entry : waitLineView.entrySet()) {
                if (position < MAX_DISPLAYABLE_CUSTOMERS) {
                    panel.add(entry.getValue(), 
                        new ScaleConstraintImpl(
                            new ScaleImpl(CUSTOMER_WIDTH, CUSTOMER_HEIGHT),
                            new ScaleImpl(WAIT_X_POSITION[position], WAIT_Y_POSITION),
                            new ScaleImpl(ORIGIN)
                        )
                    );
                    entry.getValue().repaint();
                }
                position++;
            }
            panel.repaint();
        }

        /* if the customers do not coincide */
        if (!registerLineView.keySet().equals(customerController.getRegisterLine().stream().collect(Collectors.toSet()))) {
            /* remove all customers' JLabels */
            for (final Entry<Customer, JLabel> entry : registerLineView.entrySet()) {
                if (panel.isAncestorOf(entry.getValue())) {
                    panel.remove(entry.getValue());
                }
            }
            registerLineView.clear();

            /* and fill them back up */
            for (final Customer customer : customerController.getRegisterLine()) {
                final ImageIcon iconImage = new ImageIcon(
                    resourceService.getImage(CUSTOMER_FILE_NAME
                    + FILE_SEPARATOR
                    + customer.getSkinType()
                    + FILE_EXTENSION));
                final JLabel imageLabel = new JLabel(iconImage);
                registerLineView.put(customer, imageLabel);
            }

            /* and finally print five of them */
            int position = 0;
            for (final Entry<Customer, JLabel> entry : registerLineView.entrySet()) {
                if (position < MAX_DISPLAYABLE_CUSTOMERS) {
                    panel.add(entry.getValue(), 
                        new ScaleConstraintImpl(
                            new ScaleImpl(CUSTOMER_WIDTH, CUSTOMER_HEIGHT),
                            new ScaleImpl(REGISTER_X_POSITION[position], REGISTER_Y_POSITION),
                            new ScaleImpl(ORIGIN)
                        )
                    );
                    entry.getValue().repaint();
                }
                position++;
            }
            panel.repaint();
        }
        panel.repaint();
        panel.getParent().repaint();
    }

    /**
     * @inheritDoc
     */
    @Override
    void paintComponentDelegate(final Graphics g) {

    }

    /**
     * Custom deserialization method called automatically by the JVM
     * during deserialization of this object.
     *
     * @param in the ObjectInputStream to read the object data from
     * @throws IOException if an I/O error occurs while reading the stream.
     * @throws ClassNotFoundException if a class required for deserialization cannot be found.
     */
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        registerLineView = new LinkedHashMap<>();
        waitLineView = new LinkedHashMap<>();
    }
}
