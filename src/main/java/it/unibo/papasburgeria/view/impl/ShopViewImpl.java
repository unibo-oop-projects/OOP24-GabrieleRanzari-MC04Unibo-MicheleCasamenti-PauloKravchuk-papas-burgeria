package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import it.unibo.papasburgeria.controller.api.ShopController;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.view.impl.components.ScalableLayoutImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleConstraintImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleImpl;
import org.tinylog.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serial;

import static it.unibo.papasburgeria.Main.DEBUG_MODE;
import static java.awt.Color.GRAY;
import static java.awt.Color.GREEN;
import static java.awt.Color.WHITE;

/**
 * Manages the GUI for the shop scene in the game.
 */
public class ShopViewImpl extends AbstractBaseView {
    private static final double NEXT_DAY_BUTTON_X_POS = 0.5;
    private static final double NEXT_DAY_BUTTON_Y_POS = 0.93;
    private static final double NEXT_DAY_BUTTON_X_SIZE = 0.10;
    private static final double NEXT_DAY_BUTTON_Y_SIZE = 0.05;
    private static final double NEXT_DAY_BUTTON_ORIGIN = 0.5;

    private static final double MONEY_LABEL_X_POS = 0.65;
    private static final double MONEY_LABEL_Y_POS = 0.85;
    private static final double MONEY_LABEL_X_SIZE = 0.3;
    private static final double MONEY_LABEL_Y_SIZE = 0.1;

    private static final double UPGRADE_PANEL_X_POS = 0.05;
    private static final double UPGRADE_PANEL_Y_POS = 0.05;
    private static final double UPGRADE_PANEL_X_SIZE = 0.4;
    private static final double UPGRADE_PANEL_Y_SIZE = 0.225;
    private static final double UPGRADE_PANEL_X_SPACING = (1.0 - UPGRADE_PANEL_X_SIZE * 2) / 2;
    private static final double UPGRADE_PANEL_Y_SPACING = 0.05;
    private static final int UPGRADE_PANEL_BORDER_THICKNESS = 10;
    private static final Color UPGRADE_PANEL_BACKGROUND_COLOR = WHITE;
    private static final Color UPGRADE_PANEL_BORDER_COLOR = GRAY;

    private static final double UPGRADE_NAME_X_POS = 0.05;
    private static final double UPGRADE_NAME_Y_POS = 0.05;
    private static final double UPGRADE_NAME_X_SIZE = 0.5;
    private static final double UPGRADE_NAME_Y_SIZE = 0.15;

    private static final double UPGRADE_PURCHASE_BUTTON_X_POS = 0.6;
    private static final double UPGRADE_PURCHASE_BUTTON_Y_POS = UPGRADE_NAME_Y_POS;
    private static final double UPGRADE_PURCHASE_BUTTON_X_SIZE = 0.35;
    private static final double UPGRADE_PURCHASE_BUTTON_Y_SIZE = UPGRADE_NAME_Y_SIZE;

    private static final double UPGRADE_SEPARATOR_X_POS = 0.05;
    private static final double UPGRADE_SEPARATOR_Y_POS = 0.03 + UPGRADE_NAME_Y_POS + UPGRADE_NAME_Y_SIZE;
    private static final double UPGRADE_SEPARATOR_X_SIZE = 0.9;
    private static final double UPGRADE_SEPARATOR_Y_SIZE = 0.01;

    private static final double UPGRADE_IMAGE_X_POS = 0.05;
    private static final double UPGRADE_IMAGE_Y_POS = 0.05 + UPGRADE_SEPARATOR_Y_POS + UPGRADE_SEPARATOR_Y_SIZE;
    private static final double UPGRADE_IMAGE_X_SIZE = 0.3;
    private static final double UPGRADE_IMAGE_Y_SIZE = 0.7;

    private static final double UPGRADE_COST_X_POS = UPGRADE_PURCHASE_BUTTON_X_POS;
    private static final double UPGRADE_COST_Y_POS = 0.05 + UPGRADE_SEPARATOR_Y_POS + UPGRADE_SEPARATOR_Y_SIZE;
    private static final double UPGRADE_COST_X_SIZE = UPGRADE_PURCHASE_BUTTON_X_SIZE;
    private static final double UPGRADE_COST_Y_SIZE = 0.15;

    private static final double UPGRADE_DESCRIPTION_X_POS = 0.05 + UPGRADE_IMAGE_X_POS + UPGRADE_IMAGE_X_SIZE;
    private static final double UPGRADE_DESCRIPTION_Y_POS = 0.05 + UPGRADE_COST_Y_POS + UPGRADE_COST_Y_SIZE;
    private static final double UPGRADE_DESCRIPTION_X_SIZE = 1.0 - UPGRADE_DESCRIPTION_X_POS - 0.1;
    private static final double UPGRADE_DESCRIPTION_Y_SIZE = 1.0 - UPGRADE_DESCRIPTION_Y_POS - 0.1;

    private static final int MAX_NUMBER_OF_UPGRADE_PANELS = 6;
    private static final double ORIGIN = 0.0;
    private static final String DEFAULT_FONT_NAME = "Comic Sans MS";
    private static final int DEFAULT_FONT_SIZE = 20;
    private static final Font DEFAULT_FONT = new Font(DEFAULT_FONT_NAME, Font.BOLD, DEFAULT_FONT_SIZE);
    private static final int DESCRIPTION_FONT_SIZE = 18;
    private static final String DESCRIPTION_FONT_NAME = "Comic Sans";
    private static final int MONEY_LABEL_FONT_SIZE = 45;
    private static final Color MONEY_LABEL_TEXT_COLOR = new Color(60, 180, 53);

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor, creates and initializes the GUI elements.
     *
     * @param resourceService the service that handles resource obtainment
     * @param controller      the controller for the shop view
     */
    @Inject
    public ShopViewImpl(final ResourceService resourceService, final ShopController controller) {
        if (DEBUG_MODE) {
            Logger.info("Shop created");
        }

        super.setStaticBackgroundImage(resourceService.getImage("shop_background.png"));

        final JPanel interfacePanel = super.getInterfacePanel();
        interfacePanel.setLayout(new ScalableLayoutImpl());

        final JButton nextDayButton = new JButton("End Day");
        nextDayButton.setFont(DEFAULT_FONT);
        nextDayButton.setBackground(DEFAULT_BUTTON_BACKGROUND_COLOR);
        nextDayButton.setForeground(DEFAULT_BUTTON_TEXT_COLOR);
        nextDayButton.setFocusPainted(false);
        interfacePanel.add(
                nextDayButton,
                new ScaleConstraintImpl(
                        new ScaleImpl(NEXT_DAY_BUTTON_X_SIZE, NEXT_DAY_BUTTON_Y_SIZE),
                        new ScaleImpl(NEXT_DAY_BUTTON_X_POS, NEXT_DAY_BUTTON_Y_POS),
                        new ScaleImpl(NEXT_DAY_BUTTON_ORIGIN)
                )
        );

        final JLabel moneyLabel = new JLabel("Money:" + " $");
        moneyLabel.setFont(new Font(DEFAULT_FONT_NAME, Font.BOLD, MONEY_LABEL_FONT_SIZE));
        moneyLabel.setForeground(MONEY_LABEL_TEXT_COLOR);
        interfacePanel.add(
                moneyLabel,
                new ScaleConstraintImpl(
                        new ScaleImpl(MONEY_LABEL_X_SIZE, MONEY_LABEL_Y_SIZE),
                        new ScaleImpl(MONEY_LABEL_X_POS, MONEY_LABEL_Y_POS),
                        new ScaleImpl(ORIGIN)
                )
        );

        double pbSizeXScale = UPGRADE_PANEL_X_POS;
        double pbSizeYScale = UPGRADE_PANEL_Y_POS;
        for (int index = 0; index < MAX_NUMBER_OF_UPGRADE_PANELS; index++) {
            final JPanel upgradePanel = new JPanel();
            upgradePanel.setLayout(new ScalableLayoutImpl());
            upgradePanel.setBackground(UPGRADE_PANEL_BACKGROUND_COLOR);
            upgradePanel.setBorder(BorderFactory.createLineBorder(
                    UPGRADE_PANEL_BORDER_COLOR, UPGRADE_PANEL_BORDER_THICKNESS));

            final JLabel nameLabel = new JLabel("Upgrade Name");
            nameLabel.setFont(DEFAULT_FONT);
            upgradePanel.add(
                    nameLabel,
                    new ScaleConstraintImpl(
                            new ScaleImpl(UPGRADE_NAME_X_SIZE, UPGRADE_NAME_Y_SIZE),
                            new ScaleImpl(UPGRADE_NAME_X_POS, UPGRADE_NAME_Y_POS),
                            new ScaleImpl(ORIGIN)
                    )
            );

            final JButton purchaseButton = new JButton();
            purchaseButton.setFont(DEFAULT_FONT);
            purchaseButton.setBackground(DEFAULT_BUTTON_BACKGROUND_COLOR);
            purchaseButton.setForeground(DEFAULT_BUTTON_TEXT_COLOR);
            purchaseButton.setFocusPainted(false);
            if (controller.isUpgradeUnlocked()) {
                purchaseButton.setText("Already purchased");
                purchaseButton.setEnabled(false);
            } else if (controller.isUpgradePurchasable()) {
                purchaseButton.setText("Purchase");
                purchaseButton.addActionListener(e -> {
                    if (controller.buyUpgrade()) {
                        if (DEBUG_MODE) {
                            Logger.debug("Upgrade purchased");
                        }
                    } else {
                        if (DEBUG_MODE) {
                            Logger.debug("Upgrade could not be purchased");
                        }
                    }
                });
            } else {
                purchaseButton.setText("Need more money");
                purchaseButton.setEnabled(false);
            }
            upgradePanel.add(
                    purchaseButton,
                    new ScaleConstraintImpl(
                            new ScaleImpl(UPGRADE_PURCHASE_BUTTON_X_SIZE, UPGRADE_PURCHASE_BUTTON_Y_SIZE),
                            new ScaleImpl(UPGRADE_PURCHASE_BUTTON_X_POS, UPGRADE_PURCHASE_BUTTON_Y_POS),
                            new ScaleImpl(ORIGIN)
                    )
            );

            final JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setBackground(UPGRADE_PANEL_BORDER_COLOR);
            upgradePanel.add(
                    separator,
                    new ScaleConstraintImpl(
                            new ScaleImpl(UPGRADE_SEPARATOR_X_SIZE, UPGRADE_SEPARATOR_Y_SIZE),
                            new ScaleImpl(UPGRADE_SEPARATOR_X_POS, UPGRADE_SEPARATOR_Y_POS),
                            new ScaleImpl(ORIGIN)
                    )
            );

            final ImageIcon imageIcon = new ImageIcon(resourceService.getImage("patty.png"));
            final JLabel imageLabel = new JLabel(imageIcon);
            upgradePanel.add(
                    imageLabel,
                    new ScaleConstraintImpl(
                            new ScaleImpl(UPGRADE_IMAGE_X_SIZE, UPGRADE_IMAGE_Y_SIZE),
                            new ScaleImpl(UPGRADE_IMAGE_X_POS, UPGRADE_IMAGE_Y_POS),
                            new ScaleImpl(ORIGIN)
                    )
            );

            final JLabel costLabel = new JLabel("Upgrade Cost:" + " $");
            costLabel.setFont(DEFAULT_FONT);
            upgradePanel.add(
                    costLabel,
                    new ScaleConstraintImpl(
                            new ScaleImpl(UPGRADE_COST_X_SIZE, UPGRADE_COST_Y_SIZE),
                            new ScaleImpl(UPGRADE_COST_X_POS, UPGRADE_COST_Y_POS),
                            new ScaleImpl(ORIGIN)
                    )
            );

            final JTextArea descriptionTextArea = new JTextArea("Upgrade description");
            descriptionTextArea.setFont(new Font(DESCRIPTION_FONT_NAME, Font.PLAIN, DESCRIPTION_FONT_SIZE));
            descriptionTextArea.setEditable(false);
            descriptionTextArea.setFocusable(false);
            descriptionTextArea.setLineWrap(true);
            descriptionTextArea.setWrapStyleWord(true);
            upgradePanel.add(
                    descriptionTextArea,
                    new ScaleConstraintImpl(
                            new ScaleImpl(UPGRADE_DESCRIPTION_X_SIZE, UPGRADE_DESCRIPTION_Y_SIZE),
                            new ScaleImpl(UPGRADE_DESCRIPTION_X_POS, UPGRADE_DESCRIPTION_Y_POS),
                            new ScaleImpl(ORIGIN)
                    )
            );

            interfacePanel.add(
                    upgradePanel,
                    new ScaleConstraintImpl(
                            new ScaleImpl(UPGRADE_PANEL_X_SIZE, UPGRADE_PANEL_Y_SIZE),
                            new ScaleImpl(pbSizeXScale, pbSizeYScale),
                            new ScaleImpl(ORIGIN)
                    )
            );

            pbSizeXScale = pbSizeXScale + UPGRADE_PANEL_X_SPACING + UPGRADE_PANEL_X_SIZE;
            if (pbSizeXScale + UPGRADE_PANEL_X_SIZE > 1.0) {
                pbSizeXScale = UPGRADE_PANEL_X_POS;
                pbSizeYScale = pbSizeYScale + UPGRADE_PANEL_Y_SPACING + UPGRADE_PANEL_Y_SIZE;
            }
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
    final void paintComponentDelegate(final Graphics graphics) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        if (DEBUG_MODE) {
            Logger.info("Shop shown");
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        if (DEBUG_MODE) {
            Logger.info("Shop hidden");
        }
    }
}
