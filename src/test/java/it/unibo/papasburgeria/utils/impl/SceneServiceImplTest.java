package it.unibo.papasburgeria.utils.impl;

import it.unibo.papasburgeria.utils.api.scene.BaseScene;
import it.unibo.papasburgeria.utils.api.scene.SceneService;
import it.unibo.papasburgeria.utils.api.scene.SceneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link SceneServiceImpl}.
 */
class SceneServiceImplTest {

    private SceneService sceneService;
    private TestBaseScene sceneA;
    private TestBaseScene sceneB;

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() {
        final Map<SceneType, BaseScene> scenes = new EnumMap<>(SceneType.class);
        this.sceneA = new TestBaseScene();
        this.sceneB = new TestBaseScene();
        scenes.put(SceneType.REGISTER, this.sceneA);
        scenes.put(SceneType.BURGER_ASSEMBLY, this.sceneB);
        this.sceneService = new SceneServiceImpl(scenes);
    }

    /**
     * Tests {@link SceneServiceImpl#switchTo(SceneType)}.
     */
    @Test
    void testSwitchTo() {
        final SceneService emptySceneService = new SceneServiceImpl(new EnumMap<>(SceneType.class));
        assertThrows(IllegalArgumentException.class, () -> emptySceneService.switchTo(SceneType.REGISTER));

        assertThrows(IllegalArgumentException.class, () -> this.sceneService.switchTo(null));

        this.sceneService.switchTo(SceneType.REGISTER);
        assertEquals(1, sceneA.getShownCount());
        assertEquals(0, sceneA.getHiddenCount());
        this.sceneService.switchTo(SceneType.BURGER_ASSEMBLY);
        assertEquals(1, sceneA.getHiddenCount());
        assertEquals(1, sceneB.getShownCount());
        assertEquals(0, sceneB.getHiddenCount());
        this.sceneService.switchTo(SceneType.BURGER_ASSEMBLY);
        assertEquals(1, sceneA.getHiddenCount());
        assertEquals(1, sceneB.getShownCount());
        assertEquals(0, sceneB.getHiddenCount());
        this.sceneService.switchTo(SceneType.REGISTER);
        assertEquals(2, sceneA.getShownCount());
        assertEquals(1, sceneB.getShownCount());
        assertEquals(1, sceneB.getHiddenCount());
    }

    /**
     * Tests {@link SceneServiceImpl#getScenes()}.
     */
    @Test
    void testGetScenes() {
        final SceneService emptySceneService = new SceneServiceImpl(new EnumMap<>(SceneType.class));
        assertEquals(new EnumMap<>(SceneType.class), emptySceneService.getScenes());

        final Map<SceneType, BaseScene> copy = this.sceneService.getScenes();
        assertThrows(UnsupportedOperationException.class, () -> copy.put(SceneType.REGISTER, this.sceneA));
    }

    /**
     * Tests {@link SceneServiceImpl#onSceneChanged(Consumer)}.
     */
    @Test
    void testOnSceneChanged() {
        final AtomicReference<SceneType> sceneReference = new AtomicReference<>();
        final Consumer<SceneType> onSceneChanged = sceneReference::set;
        this.sceneService.onSceneChanged(onSceneChanged);
        assertThrows(IllegalArgumentException.class, () -> this.sceneService.onSceneChanged(null));
        assertThrows(IllegalArgumentException.class, () -> this.sceneService.onSceneChanged(onSceneChanged));

        this.sceneService.switchTo(SceneType.REGISTER);
        assertEquals(SceneType.REGISTER, sceneReference.get());
    }

    /**
     * Implementation of BaseScene.
     * Helper class to aid in testing of SceneServiceImpl.
     *
     * <p>
     * See {@link BaseScene} for interface details.
     */
    private static class TestBaseScene implements BaseScene {
        private int shown;
        private int hidden;

        /**
         * Initializes the counters.
         */
        TestBaseScene() {
            this.shown = 0;
            this.hidden = 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void showScene() {
            this.shown++;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void hideScene() {
            this.hidden++;
        }

        /**
         * Gets the amount of times the scene has been shown.
         *
         * @return times value
         */
        public int getShownCount() {
            return this.shown;
        }

        /**
         * Gets the amount of times the scene has been hidden.
         *
         * @return times value
         */
        public int getHiddenCount() {
            return this.hidden;
        }
    }
}
