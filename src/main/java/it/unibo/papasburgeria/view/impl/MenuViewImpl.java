package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.papasburgeria.controller.api.GameController;
import it.unibo.papasburgeria.controller.api.MenuController;
import it.unibo.papasburgeria.utils.api.ResourceService;
import it.unibo.papasburgeria.utils.api.SfxService;
import it.unibo.papasburgeria.utils.api.scene.SceneType;
import it.unibo.papasburgeria.utils.impl.saving.SaveInfo;
import it.unibo.papasburgeria.view.api.components.Scale;
import it.unibo.papasburgeria.view.api.components.ScaleConstraint;
import it.unibo.papasburgeria.view.impl.components.ScalableLayoutImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleConstraintImpl;
import it.unibo.papasburgeria.view.impl.components.ScaleImpl;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Menu View.
 */
@SuppressFBWarnings(
        value = "SE_TRANSIENT_FIELD_NOT_RESTORED",
        justification = "Views are never deserialized at runtime"
)
public class MenuViewImpl extends AbstractBaseView {
    @Serial
    private static final long serialVersionUID = 1L;

    private final transient SfxService sfxService;
    private final transient ResourceService resourceService;
    private final transient MenuController menuController;
    private final transient GameController gameController;
    private final transient List<SlotView> slotViews;

    private final JButton playButton;
    private final JButton resumeButton;
    private final JButton savesButton;
    private final JPanel slotPanel;

    private boolean playShown;

    /**
     * Constructs the MenuView.
     *
     * @param menuController  menu controller instance
     * @param gameController  game controller instance
     * @param resourceService the service that handles resource obtainment
     * @param sfxService      sfx player service
     */
    @Inject
    public MenuViewImpl(
            final MenuController menuController,
            final GameController gameController,
            final ResourceService resourceService,
            final SfxService sfxService
    ) {
        super.setStaticBackgroundImage(resourceService.getImage("menu-background.jpg"));

        this.sfxService = sfxService;
        this.resourceService = resourceService;
        this.gameController = gameController;
        this.menuController = menuController;
        this.slotViews = new ArrayList<>();
        this.playShown = false;

        final JPanel interfacePanel = super.getInterfacePanel();
        interfacePanel.setLayout(new ScalableLayoutImpl());

        // declared once and gc'ed at end
        final double bSizeXScale = 0.15;
        final double bSizeYScale = 0.1;
        final double bPositionYScale = 0.55;
        final double nsbPositionYScale = 0.65;
        final Scale bSizeScale = new ScaleImpl(bSizeXScale, bSizeYScale);
        final ScaleConstraint bScaleConstraint = new ScaleConstraintImpl(
                bSizeScale,
                new ScaleImpl(ScaleConstraintImpl.HALF, bPositionYScale),
                ScaleConstraintImpl.ORIGIN_CENTER
        );
        this.playButton = this.createButtonWithImage("play_btn.png");
        this.resumeButton = this.createButtonWithImage("resume_btn.png");
        this.savesButton = this.createButtonWithImage("saves_btn.png");
        this.resumeButton.setVisible(false);
        this.savesButton.setVisible(false);
        interfacePanel.add(this.playButton, bScaleConstraint);
        interfacePanel.add(this.resumeButton, bScaleConstraint);
        bScaleConstraint.setPositionScale(new ScaleImpl(ScaleConstraintImpl.HALF, nsbPositionYScale));
        interfacePanel.add(this.savesButton, bScaleConstraint);

        this.slotPanel = new JPanel();
        this.slotPanel.setLayout(new ScalableLayoutImpl());
        this.slotPanel.setBackground(DEFAULT_BACKGROUND_COLOR);
        this.slotPanel.setVisible(false);
        interfacePanel.add(this.slotPanel, new ScaleConstraintImpl(
                ScaleConstraintImpl.SIZE_HALF_PARENT,
                ScaleConstraintImpl.POSITION_BOTTOM_CENTER,
                ScaleConstraintImpl.ORIGIN_BOTTOM_CENTER
        ));

        final int offset = 1;
        final double padding = 0.01;
        final Scale bgSizeScale = new ScaleImpl(ScaleConstraintImpl.THIRD - padding, ScaleConstraintImpl.FULL);
        final Icon imageIcon = new ImageIcon(resourceService.getImage("slot-background.png"));
        for (int i = 0; i <= 2; i++) {
            final SlotView slotView = new SlotView(i, imageIcon);
            this.slotViews.add(slotView);
            this.slotPanel.add(slotView.getPanel(), new ScaleConstraintImpl(
                    bgSizeScale,
                    new ScaleImpl(
                            ScaleConstraintImpl.HALF + (ScaleConstraintImpl.THIRD * (i - offset)),
                            ScaleConstraintImpl.HALF
                    ),
                    ScaleConstraintImpl.ORIGIN_CENTER
            ));
        }

        final ActionListener savesListener = event -> {
            this.updateSlotInformation();
            this.playButton.setVisible(false);
            this.savesButton.setVisible(false);
            this.resumeButton.setVisible(false);
            this.slotPanel.setVisible(true);
        };
        this.playButton.addActionListener(savesListener);
        this.savesButton.addActionListener(savesListener);
        this.resumeButton.addActionListener(event -> {
            this.gameController.switchToScene(SceneType.REGISTER);
        });
    }

    /**
     * @inheritDoc
     */
    @Override
    void update(final double delta) {

    }

    @Override
    void paintComponentDelegate(final Graphics g) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        if (!this.playShown) {
            this.playShown = true;
            this.playButton.setVisible(true);
            this.resumeButton.setVisible(false);
            this.savesButton.setVisible(false);
        } else {
            this.playButton.setVisible(false);
            this.resumeButton.setVisible(true);
            this.savesButton.setVisible(true);
        }
        this.sfxService.playSoundLooped("MenuIntro.wav");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        this.sfxService.stopSound("MenuIntro.wav");
        this.slotPanel.setVisible(false);
    }

    /**
     * Updates visual information for each slot.
     */
    private void updateSlotInformation() {
        final List<SaveInfo> info = this.menuController.getSaves();
        if (info != null) {
            for (final SlotView slotView : this.slotViews) {
                final int boundIndex = slotView.getIndex();
                final int currentIndex = this.menuController.getCurrentlyUsedSaveIndex();
                final boolean currentlySelected = currentIndex >= 0 && currentIndex == boundIndex;
                final SaveInfo saveInfo = info.get(boundIndex);
                final boolean isEmptySave = saveInfo.checkNoSave();

                slotView.setSlotAsEmpty(); // defaulting
                if (!isEmptySave) {
                    slotView.updateButton(
                            "[SELECT" + (currentlySelected ? "ED]" : "]"), event -> {
                                if (!currentlySelected) {
                                    gameController.processSave();
                                    if (gameController.processLoad(boundIndex)) {
                                        gameController.switchToScene(SceneType.REGISTER);
                                    }
                                } else {
                                    gameController.switchToScene(SceneType.REGISTER);
                                }
                            });
                    slotView.updateLabel(
                            SlotView.SlotLabelEnum.BALANCE, String.valueOf(saveInfo.playerBalance())
                    );
                    slotView.updateLabel(
                            SlotView.SlotLabelEnum.DAY, String.valueOf(saveInfo.gameDay())
                    );
                }
            }
        }
    }

    /**
     * Helper method to create menu buttons.
     *
     * @param imageName name of the image
     * @return JButton instance
     */
    private JButton createButtonWithImage(final String imageName) {
        if (imageName == null) {
            throw new IllegalArgumentException("Image name cannot be null");
        }

        final JButton button = new JButton(new ImageIcon(this.resourceService.getImage(imageName)));
        button.setBackground(DEFAULT_BACKGROUND_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        return button;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        return "MenuViewImpl{"
                +
                "sfxService="
                + sfxService
                +
                ", resourceService="
                + resourceService
                +
                ", menuController="
                + menuController
                +
                ", gameController="
                + gameController
                +
                ", slotViews="
                + slotViews
                +
                ", playButton="
                + playButton
                +
                ", resumeButton="
                + resumeButton
                +
                ", savesButton="
                + savesButton
                +
                ", slotPanel="
                + slotPanel
                +
                ", playShown="
                + playShown
                +
                '}';
    }

    /**
     * Represents a save slot view within the menu.
     */
    private class SlotView {
        private final int index;
        private final JPanel panel;
        private final JButton interactionButton;
        private final Map<SlotLabelEnum, JLabel> labels;

        private ActionListener currentListener;

        /**
         * Constructs a new slot view.
         *
         * @param index     bound index
         * @param imageIcon background icon
         */
        SlotView(final int index, final Icon imageIcon) {
            this.index = index;
            this.labels = new EnumMap<>(SlotLabelEnum.class);

            // Constants, once created and then gc'ed
            final Color bgTransparentColor = new Color(0, 0, 0, 0);
            final Color bgColor = new Color(236, 207, 203);
            final Color textColor = new Color(101, 52, 52);
            final double textYOffset = 0.085;
            final Scale buttonSizeScale = new ScaleImpl(ScaleConstraintImpl.FULL, ScaleConstraintImpl.EIGHTH);
            final Scale buttonPositionScale = new ScaleImpl(ScaleConstraintImpl.HALF, ScaleConstraintImpl.QUARTER);

            this.panel = new JPanel();
            this.panel.setLayout(new ScalableLayoutImpl());
            this.panel.setBackground(bgTransparentColor);
            this.panel.setOpaque(false);

            this.interactionButton = new JButton("[PLACEHOLDER]");
            this.interactionButton.setBackground(bgColor);
            this.interactionButton.setForeground(textColor);
            this.interactionButton.setFocusPainted(false);
            this.interactionButton.setFocusable(false);
            this.interactionButton.setBorder(BorderFactory.createEmptyBorder());
            this.panel.add(this.interactionButton, new ScaleConstraintImpl(
                    buttonSizeScale,
                    buttonPositionScale,
                    ScaleConstraintImpl.ORIGIN_CENTER
            ));

            final double scaleYOffset = ScaleConstraintImpl.HALF - ScaleConstraintImpl.SIXTEENTH;
            int j = 0;
            for (final SlotLabelEnum slotLabel : SlotLabelEnum.values()) {
                final JLabel statLabel = new JLabel(slotLabel.value() + ": _");
                statLabel.setOpaque(false);
                statLabel.setBackground(bgTransparentColor);
                this.labels.put(slotLabel, statLabel);
                this.panel.add(statLabel, new ScaleConstraintImpl(
                        buttonSizeScale,
                        new ScaleImpl(ScaleConstraintImpl.HALF, scaleYOffset + (textYOffset * j)),
                        ScaleConstraintImpl.ORIGIN_CENTER
                ));
                j++;
            }

            final JLabel background = new JLabel(imageIcon);
            this.panel.add(background, new ScaleConstraintImpl(
                    ScaleConstraintImpl.SIZE_FULL,
                    ScaleConstraintImpl.POSITION_CENTER,
                    ScaleConstraintImpl.ORIGIN_CENTER
            ));
            this.panel.setComponentZOrder(background, this.panel.getComponentCount() - 1);
        }

        /**
         * Used to obtain the created associated panel.
         *
         * @return panel
         */
        public JPanel getPanel() {
            return panel;
        }

        /**
         * Used to obtain the bound index.
         *
         * @return index
         */
        public int getIndex() {
            return index;
        }

        /**
         * Updates the label's value in String form.
         *
         * @param slotLabelEnum label enum
         * @param value         value
         */
        public void updateLabel(final SlotLabelEnum slotLabelEnum, final String value) {
            if (slotLabelEnum == null || value == null || value.isEmpty()) {
                throw new IllegalStateException("labelName and value must not be null or empty");
            }

            final JLabel label = this.labels.get(slotLabelEnum);
            if (label == null) {
                throw new IllegalStateException("No label with name " + slotLabelEnum);
            }

            label.setText(slotLabelEnum.value() + ": " + value);
        }

        /**
         * Updates button's text and action listener, helper method.
         *
         * @param text           text string
         * @param actionListener action listener instance
         */
        public void updateButton(final String text, final ActionListener actionListener) {
            if (text == null || text.isEmpty()) {
                throw new IllegalStateException("buttonName must not be null or empty");
            }

            if (this.currentListener != null) {
                this.interactionButton.removeActionListener(this.currentListener);
            }

            this.currentListener = actionListener;
            this.interactionButton.setText(text);
            this.interactionButton.addActionListener(this.currentListener);
        }

        /**
         * Used to set the current slot as empty, using this as a defaulter is recommended when you have to
         * manually set each label's value from a DTO (in case one label doesn't have a corresponding value from the DTO itself).
         */
        public void setSlotAsEmpty() {
            for (final SlotLabelEnum slotLabelEnum : SlotLabelEnum.values()) {
                this.updateLabel(slotLabelEnum, "N/A");
            }
            this.updateButton("[CREATE]", event -> {
                final boolean status = gameController.processSave(index);
                if (status) {
                    gameController.processLoad(index);
                    gameController.switchToScene(SceneType.REGISTER);
                } else {
                    this.interactionButton.setText("[RETRY]");
                }
            });
        }

        @Override
        public String toString() {
            return "SlotView{"
                    +
                    "index="
                    + index
                    +
                    ", panel="
                    + panel
                    +
                    ", interactionButton="
                    + interactionButton
                    +
                    ", labels="
                    + labels
                    +
                    ", currentListener="
                    + currentListener
                    +
                    '}';
        }

        /**
         * Enum bound to JLabels within the interface.
         */
        public enum SlotLabelEnum {
            BALANCE("Balance"),
            DAY("Day");

            private final String label;

            /**
             * Constructs the label enum.
             *
             * @param label string value
             */
            SlotLabelEnum(final String label) {
                this.label = label;
            }

            /**
             * Used to obtain the string value from the enum.
             *
             * @return string value
             */
            public String value() {
                return label;
            }
        }
    }
}
