package it.unibo.papasburgeria.controller.impl;

import it.unibo.papasburgeria.model.api.GameModel;
import it.unibo.papasburgeria.model.api.Patty;
import it.unibo.papasburgeria.model.impl.GameModelImpl;
import it.unibo.papasburgeria.model.impl.PattyImpl;
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
     * Tests {@link GrillControllerImpl#addPattyOnGrill(Patty, double, double)}
     * and {@link GrillControllerImpl#removePattyFromGrill(Patty)}.
     */
    @Test
    void testAddPattyOnGrillAndRemove() {
        final Patty patty = new PattyImpl();
        final double pbPositionXScale = 0.5;
        final double pbPositionYScale = 0.5;
        assertTrue(controller.addPattyOnGrill(patty, pbPositionXScale, pbPositionYScale));

        Patty[][] pattiesOnGrill = controller.getPattiesOnGrill();
        boolean found = false;
        for (final Patty[] pattyRow : pattiesOnGrill) {
            for (final Patty pattyOnGrill : pattyRow) {
                if (Objects.nonNull(pattyOnGrill) && pattyOnGrill.equals(patty)) {
                    found = true;
                }
            }
        }
        assertTrue(found);

        controller.removePattyFromGrill(patty);
        pattiesOnGrill = controller.getPattiesOnGrill();
        found = false;
        for (final Patty[] pattyRow : pattiesOnGrill) {
            for (final Patty pattyOnGrill : pattyRow) {
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
        final Patty patty = new PattyImpl();
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
     * Tests {@link GrillControllerImpl#addCookedPatty(Patty)}
     * and {@link GrillControllerImpl#removeCookedPatty(Patty)}.
     */
    @Test
    void testAddAndRemoveCookedPatty() {
        final Patty patty = new PattyImpl();
        assertTrue(controller.addCookedPatty(patty));
        assertTrue(controller.getCookedPatties().contains(patty));

        controller.removeCookedPatty(patty);
        assertFalse(controller.getCookedPatties().contains(patty));
    }

    /**
     * Tests {@link GrillControllerImpl#flipPatty(Patty)}.
     */
    @Test
    void testFlipPatty() {
        final Patty patty = new PattyImpl();
        final double pbPositionXScale = 0.5;
        final double pbPositionYScale = 0.5;

        controller.addPattyOnGrill(patty, pbPositionXScale, pbPositionYScale);
        assertFalse(patty.isFlipped());
        controller.flipPatty(patty);
        assertTrue(patty.isFlipped());
    }
}
