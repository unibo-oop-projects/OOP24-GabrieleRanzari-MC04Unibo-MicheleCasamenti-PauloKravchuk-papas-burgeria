package it.unibo.papasburgeria.controller.impl;

import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.PattyModel;
import it.unibo.papasburgeria.model.impl.GameModelImpl;
import it.unibo.papasburgeria.model.impl.PattyModelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link GrillControllerImpl}.
 */
class GrillControllerImplTest {
    private GrillControllerImpl controller;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        final GameModel model = new GameModelImpl();
        controller = new GrillControllerImpl(model);
    }

    /**
     * Tests {@link GrillControllerImpl#addPattyOnGrill(PattyModel, double, double)}
     * and {@link GrillControllerImpl#removePattyFromGrill(PattyModel)}.
     */
    @Test
    void testAddPattyOnGrillAndRemove() {
        final PattyModel patty = new PattyModelImpl();
        final double pbPositionXScale = 0.5;
        final double pbPositionYScale = 0.5;
        assertTrue(controller.addPattyOnGrill(patty, pbPositionXScale, pbPositionYScale));

        PattyModel[][] pattiesOnGrill = controller.getPattiesOnGrill();
        boolean found = false;
        for (final PattyModel[] pattyRow : pattiesOnGrill) {
            for (final PattyModel pattyOnGrill : pattyRow) {
                if (Objects.nonNull(pattyOnGrill) && pattyOnGrill.equals(patty)) {
                    found = true;
                }
            }
        }
        assertTrue(found);

        controller.removePattyFromGrill(patty);
        pattiesOnGrill = controller.getPattiesOnGrill();
        found = false;
        for (final PattyModel[] pattyRow : pattiesOnGrill) {
            for (final PattyModel pattyOnGrill : pattyRow) {
                if (Objects.nonNull(pattyOnGrill) && pattyOnGrill.equals(patty)) {
                    found = true;
                }
            }
        }
        assertFalse(found);
    }

    /**
     * Tests {@link GrillControllerImpl#cookPattiesOnGrill()}.
     */
    @Test
    void testCookPattiesOnGrill() {
        final PattyModel patty = new PattyModelImpl();
        final double pbPositionXScale = 0.5;
        final double pbPositionYScale = 0.5;

        controller.addPattyOnGrill(patty, pbPositionXScale, pbPositionYScale);
        final double initialBottom = patty.getBottomCookLevel();
        controller.cookPattiesOnGrill();
        assertTrue(patty.getBottomCookLevel() > initialBottom);

        patty.flip();
        final double initialTop = patty.getTopCookLevel();
        controller.cookPattiesOnGrill();
        assertTrue(patty.getTopCookLevel() > initialTop);
    }

    /**
     * Tests {@link GrillControllerImpl#addCookedPatty(PattyModel)}
     * and {@link GrillControllerImpl#removeCookedPatty(PattyModel)}.
     */
    @Test
    void testAddAndRemoveCookedPatty() {
        final PattyModel patty = new PattyModelImpl();
        assertTrue(controller.addCookedPatty(patty));
        assertTrue(controller.getCookedPatties().contains(patty));

        controller.removeCookedPatty(patty);
        assertFalse(controller.getCookedPatties().contains(patty));
    }

    /**
     * Tests {@link GrillControllerImpl#flipPatty(PattyModel)}.
     */
    @Test
    void testFlipPatty() {
        final PattyModel patty = new PattyModelImpl();
        final double pbPositionXScale = 0.5;
        final double pbPositionYScale = 0.5;

        controller.addPattyOnGrill(patty, pbPositionXScale, pbPositionYScale);
        assertFalse(patty.isFlipped());
        controller.flipPatty(patty);
        assertTrue(patty.isFlipped());
    }
}
