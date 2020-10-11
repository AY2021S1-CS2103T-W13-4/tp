package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalTrackIter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTrackIter;
import seedu.address.model.TrackIter;

public class JsonTrackIterStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTrackIterStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTrackIter_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTrackIter(null));
    }

    private java.util.Optional<ReadOnlyTrackIter> readTrackIter(String filePath) throws Exception {
        return new JsonTrackIterStorage(Paths.get(filePath)).readTrackIter(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTrackIter("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readTrackIter("notJsonFormatTrackIter.json"));
    }

    @Test
    public void readTrackIter_invalidPersonTrackIter_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTrackIter("invalidPersonTrackIter.json"));
    }

    @Test
    public void readTrackIter_invalidAndValidPersonTrackIter_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readTrackIter("invalidAndValidPersonTrackIter.json"));
    }

    @Test
    public void readAndSaveTrackIter_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTrackIter.json");
        TrackIter original = getTypicalTrackIter();
        JsonTrackIterStorage jsonTrackIterStorage = new JsonTrackIterStorage(filePath);

        // Save in new file and read back
        jsonTrackIterStorage.saveTrackIter(original, filePath);
        ReadOnlyTrackIter readBack = jsonTrackIterStorage.readTrackIter(filePath).get();
        assertEquals(original, new TrackIter(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addContact(HOON);
        original.removeContact(ALICE);
        jsonTrackIterStorage.saveTrackIter(original, filePath);
        readBack = jsonTrackIterStorage.readTrackIter(filePath).get();
        assertEquals(original, new TrackIter(readBack));

        // Save and read without specifying file path
        original.addContact(IDA);
        jsonTrackIterStorage.saveTrackIter(original); // file path not specified
        readBack = jsonTrackIterStorage.readTrackIter().get(); // file path not specified
        assertEquals(original, new TrackIter(readBack));

    }

    @Test
    public void saveTrackIter_nullTrackIter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTrackIter(null, "SomeFile.json"));
    }

    /**
     * Saves {@code trackIter} at the specified {@code filePath}.
     */
    private void saveTrackIter(ReadOnlyTrackIter trackIter, String filePath) {
        try {
            new JsonTrackIterStorage(Paths.get(filePath))
                .saveTrackIter(trackIter, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTrackIter_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTrackIter(new TrackIter(), null));
    }
}
