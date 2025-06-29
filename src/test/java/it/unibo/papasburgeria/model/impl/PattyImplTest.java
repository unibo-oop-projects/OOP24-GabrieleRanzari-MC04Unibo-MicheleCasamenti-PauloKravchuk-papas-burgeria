package it.unibo.papasburgeria.model.impl;

import it.unibo.papasburgeria.model.api.Patty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link PattyImpl}.
 */
class PattyImplTest {
    private Patty patty;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        patty = new PattyImpl();
    }

    /**
     * Tests {@link PattyImpl#PattyImpl()}.
     */
    @Test
    void testInitialState() {
        assertEquals(0.0, patty.getTopCookLevel());
        assertEquals(0.0, patty.getBottomCookLevel());
        assertFalse(patty.isFlipped());
    }

    /**
     * Tests {@link PattyImpl#PattyImpl(Patty)}.
     */
    @Test
    void testCopyConstructor() {
        final double topCookLevel = 0.6;
        final double bottomCookLevel = 0.4;

        patty.setTopCookLevel(topCookLevel);
        patty.setBottomCookLevel(bottomCookLevel);
        patty.flip();

        final Patty copy = new PattyImpl(patty);

        assertEquals(patty.getTopCookLevel(), copy.getTopCookLevel());
        assertEquals(patty.getBottomCookLevel(), copy.getBottomCookLevel());
        assertEquals(patty.isFlipped(), copy.isFlipped());
    }

    /**
     * Tests:
     * {@link PattyImpl#setTopCookLevel(double)},
     * {@link PattyImpl#setBottomCookLevel(double)},
     * {@link PattyImpl#getTopCookLevel()},
     * {@link PattyImpl#getBottomCookLevel()}.
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
     * Tests {@link PattyImpl#isFlipped()}.
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
     * Tests {@link PattyImpl#equals(Object)}
     * and {@link PattyImpl#hashCode()}.
     */
    @Test
    void testEqualsAndHashCode() {
        final double topCookLevel = 0.6;
        final double bottomCookLevel = 0.4;

        patty.setTopCookLevel(topCookLevel);
        patty.setBottomCookLevel(bottomCookLevel);
        patty.flip();

        final Patty patty2 = new PattyImpl(patty);
        assertEquals(patty, patty2);
        assertEquals(patty.hashCode(), patty2.hashCode());
    }
}
