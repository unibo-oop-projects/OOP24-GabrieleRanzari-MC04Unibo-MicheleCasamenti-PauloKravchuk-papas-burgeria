package it.unibo.papasburgeria.utils.impl.saving;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unibo.papasburgeria.utils.api.SaveService;
import org.tinylog.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of SaveServiceImpl.
 *
 * <p>
 * See {@link SaveService} for interface details.
 */
public class SaveServiceImpl implements SaveService {
    public static final int MAX_SAVE_SLOTS = 2;
    private static final Path DIRECTORY = Paths.get(System.getProperty("user.home"), ".papasburgeria", "slots");

    private final ObjectMapper mapper;

    /**
     * Initializes the data saving service.
     *
     * @throws IOException thrown upon failing of the creation of directories
     */
    public SaveServiceImpl() throws IOException {
        Files.createDirectories(DIRECTORY);

        this.mapper = new ObjectMapper();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveSlot(final int slotNumber, final SaveState saveState) throws IOException {
        validateSlotNumber(slotNumber);

        if (saveState == null) {
            throw new IllegalArgumentException("Invalid save state instance provided");
        }

        // no need for files exist check, the writer opens or creates it
        final Path path = DIRECTORY.resolve(slotNumber + ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            Logger.debug("Writing slot " + slotNumber + " to " + path);
            writer.write(mapper.writeValueAsString(saveState));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SaveState loadSlot(final int slotNumber) throws IOException {
        validateSlotNumber(slotNumber);

        final Path path = DIRECTORY.resolve(slotNumber + ".txt");
        if (!Files.exists(path)) {
            return null;
        }

        return mapper.readValue(Files.readString(path), SaveState.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SaveState> loadAllSlots() throws IOException {
        final List<SaveState> slots = new ArrayList<>();
        for (int i = 0; i <= MAX_SAVE_SLOTS; i++) {
            slots.add(loadSlot(i)); // state or null
        }
        return slots;
    }

    /**
     * Helper used to validate slot numbers.
     *
     * @param slotNumber slot number
     */
    private void validateSlotNumber(final int slotNumber) {
        if (slotNumber < 0 || slotNumber > MAX_SAVE_SLOTS) {
            throw new IllegalArgumentException(
                    "Invalid slot number, should be a positive integer and equal or below " + MAX_SAVE_SLOTS
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SaveServiceImpl{"
                +
                "mapper="
                + mapper
                +
                '}';
    }
}
