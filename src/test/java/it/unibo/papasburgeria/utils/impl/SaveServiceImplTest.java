package it.unibo.papasburgeria.utils.impl;

import it.unibo.papasburgeria.model.UpgradeEnum;
import it.unibo.papasburgeria.utils.api.SaveService;
import it.unibo.papasburgeria.utils.impl.saving.SaveServiceImpl;
import it.unibo.papasburgeria.utils.impl.saving.SaveState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link SaveServiceImpl}.
 */
class SaveServiceImplTest {

    @TempDir
    private static Path tempDirectory;

    private SaveService saveService;

    /**
     * Called before all tests.
     */
    @BeforeAll
    static void changeUserHome() {
        System.setProperty("user.home", tempDirectory.toString());
    }

    /**
     * Called before each test.
     */
    @BeforeEach
    void setUp() throws IOException {
        this.saveService = new SaveServiceImpl();
    }

    /**
     * Tests {@link SaveServiceImpl#saveSlot(int, SaveState)}.
     */
    @Test
    void testSaveSlot() {
        final SaveState temp = new SaveState(0, 0, new EnumMap<>(UpgradeEnum.class));
        assertThrows(IllegalArgumentException.class, () -> this.saveService.saveSlot(0, null));
        assertThrows(IllegalArgumentException.class, () -> this.saveService.saveSlot(-1, temp));
        assertThrows(
                IllegalArgumentException.class,
                () -> this.saveService.saveSlot(SaveServiceImpl.MAX_SAVE_SLOT_INDEX + 1, temp)
        );
    }

    /**
     * Tests {@link SaveServiceImpl#loadSlot(int)}.
     */
    @Test
    void testLoadSlot() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> this.saveService.loadSlot(-1));
        assertThrows(
                IllegalArgumentException.class,
                () -> this.saveService.loadSlot(SaveServiceImpl.MAX_SAVE_SLOT_INDEX + 1)
        );
        assertNull(this.saveService.loadSlot(0));
    }

    /**
     * Tests {@link SaveServiceImpl#saveSlot(int, SaveState)} and {@link SaveServiceImpl#loadSlot(int)} together.
     */
    @Test
    void testSaveLoad() throws IOException {
        final int slotIndex = 0;
        final int balance = 123;
        final int day = 456;
        final Map<UpgradeEnum, Boolean> upgrades = new EnumMap<>(UpgradeEnum.class);
        upgrades.put(UpgradeEnum.CUSTOMER_TIP, true);

        final SaveState sent = new SaveState(balance, day, upgrades);
        this.saveService.saveSlot(slotIndex, sent);

        final SaveState received = this.saveService.loadSlot(slotIndex);
        assertNotNull(received);
        assertEquals(balance, received.playerBalance());
        assertEquals(day, received.gameDay());
        assertEquals(upgrades, received.upgrades());
    }

    /**
     * Tests {@link SaveServiceImpl#loadAllSlots()}.
     */
    @Test
    void testLoadAllSlots() throws IOException {
        // 0 was filled before
        final SaveState state = new SaveState(1, 1, new EnumMap<>(UpgradeEnum.class));
        for (int i = 1; i < SaveServiceImpl.MAX_SAVE_SLOT_INDEX; i++) {
            this.saveService.saveSlot(i, state);
        }

        final List<SaveState> slots = this.saveService.loadAllSlots();
        assertEquals(SaveServiceImpl.MAX_SAVE_SLOT_INDEX + 1, slots.size());
        for (int i = 0; i < SaveServiceImpl.MAX_SAVE_SLOT_INDEX; i++) {
            assertNotNull(slots.get(i));
        }
    }
}
