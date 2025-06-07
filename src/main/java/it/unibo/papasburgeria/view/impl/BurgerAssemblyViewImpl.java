package it.unibo.papasburgeria.view.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import it.unibo.papasburgeria.controller.api.BurgerAssemblyController;
import it.unibo.papasburgeria.controller.impl.BurgerAssemblyControllerImpl;
import it.unibo.papasburgeria.model.IngredientEnum;
import it.unibo.papasburgeria.model.impl.HamburgerImpl;
import org.tinylog.Logger;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serial;

/**
 * Manages the GUI for the burger assembly scene in the game.
 */
@Singleton
public class BurgerAssemblyViewImpl extends AbstractBaseView {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 50;
    private static final int BUTTON_X_SPACING = 30;
    private static final int BUTTON_Y_SPACING = 40;
    private static final int BUTTON_X_INITIAL_POSITION = 50;
    private static final int BUTTON_Y_INITIAL_POSITION = 100;
    private static final int INGREDIENTS_PER_ROW = 3;

    private static final int TEXT_AREA_WIDTH = 400;
    private static final int TEXT_AREA_HEIGHT = 50;
    private static final int TEXT_AREA_X = 600;
    private static final int TEXT_AREA_Y = 600;

    private final JTextArea hamburgerTextArea;
    private final transient BurgerAssemblyController controller;

    /**
     * Default constructor, creates and initializes the UI elements.
     *
     * @param controller the burger assembly controller.
     */
    @Inject
    public BurgerAssemblyViewImpl(final BurgerAssemblyController controller) {
        Logger.info("BurgerAssembly created");
        this.controller = new BurgerAssemblyControllerImpl(new HamburgerImpl(controller.getIngredients()));

        super.getInterfacePanel().setLayout(null);
        super.getInterfacePanel().setBackground(Color.RED);

        int row = 0;
        int column = 0;
        for (final IngredientEnum ingredient : IngredientEnum.values()) {
            final JButton button = new JButton(ingredient.toString());

            final int x = BUTTON_X_INITIAL_POSITION + (BUTTON_WIDTH + BUTTON_X_SPACING) * column;
            final int y = BUTTON_Y_INITIAL_POSITION + (BUTTON_HEIGHT + BUTTON_Y_SPACING) * row;
            button.setBounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT);

            button.addActionListener(e -> controller.addIngredient(ingredient));

            super.getInterfacePanel().add(button);

            column++;
            if (column == INGREDIENTS_PER_ROW) {
                column = 0;
                row++;
            }
        }

        final JButton undoButton = new JButton("Undo");
        final int y = BUTTON_Y_INITIAL_POSITION + (BUTTON_HEIGHT + BUTTON_Y_SPACING) * row + BUTTON_Y_SPACING;
        undoButton.setBounds(BUTTON_X_INITIAL_POSITION, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        undoButton.addActionListener(e -> this.controller.removeLastIngredient());
        super.getInterfacePanel().add(undoButton);

        hamburgerTextArea = new JTextArea();
        hamburgerTextArea.setEditable(false);
        hamburgerTextArea.setBounds(TEXT_AREA_X, TEXT_AREA_Y, TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
        hamburgerTextArea.setText(controller.getIngredients().toString());
        super.getInterfacePanel().add(hamburgerTextArea);
    }

    /**
     * @inheritDoc
     */
    @Override
    void update(final double delta) {
        //Logger.info("BurgerAssembly updated, last frame: " + delta);
        hamburgerTextArea.setText(controller.getIngredients().toString());
    }

    /**
     * @inheritDoc
     */
    @Override
    final void paintComponentDelegate(final Graphics g) {

    }

    /**
     * @inheritDoc
     */
    @Override
    public void showScene() {
        Logger.info("BurgerAssemblyView shown");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void hideScene() {
        Logger.info("BurgerAssemblyView hidden");
    }
}
