package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.api.PattyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link PattyModelImpl}.
 */
class PattyModelImplTest {
    private PattyModel patty;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        patty = new PattyModelImpl();
    }

    /**
     * Tests {@link PattyModelImpl#PattyModelImpl()}.
     */
    @Test
    void testInitialState() {
        assertEquals(0.0, patty.getTopCookLevel());
        assertEquals(0.0, patty.getBottomCookLevel());
        assertFalse(patty.isFlipped());
    }

    /**
     * Tests {@link PattyModelImpl#PattyModelImpl(PattyModel)}.
     */
    @Test
    void testCopyConstructor() {
        final double topCookLevel = 0.6;
        final double bottomCookLevel = 0.4;

        patty.setTopCookLevel(topCookLevel);
        patty.setBottomCookLevel(bottomCookLevel);
        patty.flip();

        final PattyModel copy = new PattyModelImpl(patty);

        assertEquals(patty.getTopCookLevel(), copy.getTopCookLevel());
        assertEquals(patty.getBottomCookLevel(), copy.getBottomCookLevel());
        assertEquals(patty.isFlipped(), copy.isFlipped());
    }

    /**
     * Tests:
     * {@link PattyModelImpl#setTopCookLevel(double)},
     * {@link PattyModelImpl#setBottomCookLevel(double)},
     * {@link PattyModelImpl#getTopCookLevel()},
     * {@link PattyModelImpl#getBottomCookLevel()}.
     */
    @Test
    void testSetAndGetCookLevels() {
        final double topCookLevel = 0.6;
        final double bottomCookLevel = 0.4;

        patty.setTopCookLevel(topCookLevel);
        patty.setBottomCookLevel(bottomCookLevel);

        assertEquals(topCookLevel, patty.getTopCookLevel());
        assertEquals(bottomCookLevel, patty.getBottomCookLevel());
    }

    /**
     * Tests {@link PattyModelImpl#isFlipped()}.
     */
    @Test
    void testFlip() {
        assertFalse(patty.isFlipped());
        patty.flip();
        assertTrue(patty.isFlipped());
        patty.flip();
        assertFalse(patty.isFlipped());
    }

    /**
     * Tests {@link PattyModelImpl#equals(Object)}
     * and {@link PattyModelImpl#hashCode()}.
     */
    @Test
    void testEqualsAndHashCode() {
        final double topCookLevel = 0.6;
        final double bottomCookLevel = 0.4;

        patty.setTopCookLevel(topCookLevel);
        patty.setBottomCookLevel(bottomCookLevel);
        patty.flip();

        final PattyModel patty2 = new PattyModelImpl(patty);
        assertEquals(patty, patty2);
        assertEquals(patty.hashCode(), patty2.hashCode());
    }
}
